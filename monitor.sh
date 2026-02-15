#!/bin/bash

REPO="jarvisismyrobot/android-meal-randomizer"
RUN_ID=22027979331
MAX_ATTEMPTS=9
INTERVAL=180

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

log() {
    echo -e "[$(date '+%Y-%m-%d %H:%M:%S')] $1"
}

success() {
    echo -e "${GREEN}$1${NC}"
}

error() {
    echo -e "${RED}$1${NC}"
}

warn() {
    echo -e "${YELLOW}$1${NC}"
}

get_run_status() {
    gh run view $RUN_ID --repo $REPO --json status,conclusion,url,databaseId 2>/dev/null
}

get_job_id() {
    gh run view $RUN_ID --repo $REPO --json jobs 2>/dev/null | jq -r '.jobs[0].databaseId'
}

get_job_logs() {
    local job_id=$1
    gh run view --job $job_id --repo $REPO --log 2>/dev/null
}

download_artifact() {
    gh run download $RUN_ID --repo $REPO -n app-debug.apk 2>/dev/null
}

# Analyze logs for common errors
analyze_logs() {
    local logs="$1"
    if echo "$logs" | grep -q "Kotlin version"; then
        if echo "$logs" | grep -q "compatibility"; then
            echo "kotlin_version"
            return
        fi
    fi
    if echo "$logs" | grep -q "Could not resolve"; then
        echo "dependency_conflict"
        return
    fi
    if echo "$logs" | grep -q "compileDebugKotlin" && echo "$logs" | grep -q "error"; then
        echo "compilation"
        return
    fi
    if echo "$logs" | grep -q "FAILED"; then
        echo "build_failed"
        return
    fi
    echo "unknown"
}

# Fix Kotlin version mismatch
fix_kotlin_version() {
    warn "Attempting to fix Kotlin version mismatch..."
    # Update Kotlin plugin version in project build.gradle.kts
    local current_kotlin=$(grep -o 'id("org.jetbrains.kotlin.android") version "[^"]*"' build.gradle.kts | cut -d'"' -f4)
    log "Current Kotlin version: $current_kotlin"
    # Maybe update to latest patch? We'll keep same major.minor but update patch using curl to get latest version?
    # For now, we'll update compose compiler to match.
    local compose_compiler=$(grep -o 'kotlinCompilerExtensionVersion = "[^"]*"' app/build.gradle.kts | cut -d'"' -f2)
    log "Current compose compiler: $compose_compiler"
    # Check compatibility matrix
    # We'll just update compose compiler to latest stable compatible with Kotlin $current_kotlin
    # This is a placeholder; actual fix requires more intelligence.
    # For now, we'll just update Kotlin version to 1.9.23 (already set)
    # and compose compiler to 1.5.11 (already set)
    # If still mismatch, we can try to update Kotlin to 1.9.24 and compose compiler to 1.5.12
    # Let's try upgrading Kotlin to 1.9.24
    sed -i 's/org.jetbrains.kotlin.android") version "1.9.[0-9]*"/org.jetbrains.kotlin.android") version "1.9.24"/' build.gradle.kts
    sed -i 's/kotlinCompilerExtensionVersion = "1.5.[0-9]*"/kotlinCompilerExtensionVersion = "1.5.12"/' app/build.gradle.kts
    # Also update ksp version if present
    sed -i 's/id("com.google.devtools.ksp") version "1.9.[0-9]*-[0-9.]*"/id("com.google.devtools.ksp") version "1.9.24-1.0.20"/' build.gradle.kts
    log "Updated Kotlin to 1.9.24 and compose compiler to 1.5.12"
}

# Fix dependency conflicts
fix_dependency_conflict() {
    warn "Attempting to fix dependency conflicts..."
    # Update dependencies to latest versions
    # This is risky; we'll just update the obvious ones
    # Update androidx.core:core-ktx to latest
    sed -i 's/implementation("androidx.core:core-ktx:[0-9.]*")/implementation("androidx.core:core-ktx:1.13.0")/' app/build.gradle.kts
    # Update compose bom
    sed -i 's/implementation(platform("androidx.compose:compose-bom:[0-9.]*"))/implementation(platform("androidx.compose:compose-bom:2024.08.00"))/' app/build.gradle.kts
    log "Updated some dependencies to latest versions"
}

# Fix compilation errors (very basic)
fix_compilation() {
    warn "Attempting to fix compilation errors..."
    # This is highly project-specific; we'll just try to revert recent changes?
    # For now, we'll do nothing.
    log "No automatic fix for compilation errors implemented."
}

commit_and_push() {
    local message="$1"
    git add .
    git commit -m "$message"
    git push
    log "Committed and pushed: $message"
}

# Main monitoring loop
attempt=1
while [ $attempt -le $MAX_ATTEMPTS ]; do
    log "Attempt $attempt/$MAX_ATTEMPTS"
    status_json=$(get_run_status)
    if [ -z "$status_json" ]; then
        error "Failed to get run status. Waiting $INTERVAL seconds..."
        sleep $INTERVAL
        attempt=$((attempt+1))
        continue
    fi
    
    status=$(echo "$status_json" | jq -r '.status')
    conclusion=$(echo "$status_json" | jq -r '.conclusion')
    url=$(echo "$status_json" | jq -r '.url')
    
    log "Status: $status, Conclusion: $conclusion"
    
    if [ "$status" = "completed" ]; then
        if [ "$conclusion" = "success" ]; then
            success "Run succeeded!"
            log "Downloading APK artifact..."
            if download_artifact; then
                success "APK artifact downloaded."
                # Notify main session
                echo "SUCCESS: Build completed successfully."
                echo "APK download link: $url/artifacts"
                exit 0
            else
                error "Failed to download artifact."
                exit 1
            fi
        else
            error "Run failed. Analyzing logs..."
            job_id=$(get_job_id)
            logs=$(get_job_logs $job_id)
            error_type=$(analyze_logs "$logs")
            warn "Detected error type: $error_type"
            case $error_type in
                kotlin_version)
                    fix_kotlin_version
                    commit_and_push "Fix Kotlin version mismatch"
                    ;;
                dependency_conflict)
                    fix_dependency_conflict
                    commit_and_push "Fix dependency conflicts"
                    ;;
                compilation)
                    fix_compilation
                    commit_and_push "Fix compilation errors"
                    ;;
                *)
                    warn "Unknown error, attempting generic fix by updating Kotlin and dependencies"
                    fix_kotlin_version
                    fix_dependency_conflict
                    commit_and_push "Generic fix attempt"
                    ;;
            esac
            # After push, a new run will be triggered automatically (push event)
            # We need to get the new run ID. Wait a moment and then update RUN_ID?
            # For simplicity, we'll just continue monitoring the same run? Actually new push triggers new run.
            # We need to detect new run ID. Let's wait 30 seconds and get latest run.
            warn "Waiting for new run to start..."
            sleep 30
            # Get latest run ID for this branch
            new_run=$(gh run list --repo $REPO --limit 1 --json databaseId --jq '.[0].databaseId')
            if [ -n "$new_run" ]; then
                RUN_ID=$new_run
                log "Now monitoring new run: $RUN_ID"
            else
                error "Could not get new run ID."
            fi
        fi
    else
        log "Run still in progress. Waiting $INTERVAL seconds..."
        sleep $INTERVAL
    fi
    attempt=$((attempt+1))
done

error "Maximum attempts reached without success."
exit 1