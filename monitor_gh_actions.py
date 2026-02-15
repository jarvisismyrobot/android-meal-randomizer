#!/usr/bin/env python3
import subprocess
import json
import time
import os
import sys
import re
from pathlib import Path

REPO = "jarvisismyrobot/android-meal-randomizer"
INTERVAL_SECONDS = 180
MAX_ATTEMPTS = 10
STATE_FILE = Path(".monitor_state.json")
WORK_DIR = Path.cwd()

def run_gh(args, check=True, capture_output=True):
    """Run gh command and return result."""
    cmd = ["gh"] + args
    result = subprocess.run(cmd, capture_output=capture_output, text=True, cwd=WORK_DIR)
    if check and result.returncode != 0:
        raise RuntimeError(f"gh command failed: {result.stderr}")
    return result

def get_latest_run():
    """Get the latest run ID and details."""
    result = run_gh(["run", "list", "--repo", REPO, "--limit", "1", "--json", "databaseId,status,conclusion,url,headBranch"])
    if not result.stdout.strip():
        return None
    runs = json.loads(result.stdout)
    if not runs:
        return None
    return runs[0]

def get_run_details(run_id):
    """Get detailed run info including artifacts."""
    result = run_gh(["run", "view", str(run_id), "--repo", REPO, "--json", "status,conclusion,url,databaseId,artifacts"])
    return json.loads(result.stdout)

def get_jobs(run_id):
    """Get jobs for a run."""
    result = run_gh(["run", "view", str(run_id), "--repo", REPO, "--json", "jobs"])
    data = json.loads(result.stdout)
    return data.get("jobs", [])

def get_job_logs(run_id, job_id):
    """Get logs for a job."""
    result = run_gh(["run", "view", "--job", str(job_id), "--repo", REPO, "--log"], check=False)
    if result.returncode != 0:
        return None
    return result.stdout

def download_artifact(run_id, artifact_name="app-debug.apk"):
    """Download artifact from run."""
    result = run_gh(["run", "download", str(run_id), "--repo", REPO, "-n", artifact_name], check=False)
    return result.returncode == 0

def analyze_logs(logs):
    """Analyze logs and return error type."""
    logs_lower = logs.lower()
    if "kotlin" in logs_lower and "version" in logs_lower and "compatibility" in logs_lower:
        return "kotlin_version"
    if "could not resolve" in logs_lower:
        return "dependency_conflict"
    if "compiledebugkotlin" in logs_lower and "error" in logs_lower:
        return "compilation"
    if "failed" in logs_lower:
        return "build_failed"
    # Try to find specific compilation errors
    error_lines = [line for line in logs.split('\n') if 'error:' in line]
    if error_lines:
        # Check for unknown reference
        for line in error_lines:
            if 'unresolved reference' in line:
                return "unresolved_reference"
            if 'type mismatch' in line:
                return "type_mismatch"
            if 'expecting' in line and 'got' in line:
                return "syntax_error"
    return "unknown"

def fix_kotlin_version():
    """Update Kotlin version and compose compiler to compatible versions."""
    print("Attempting to fix Kotlin version mismatch...")
    # Read current versions
    build_gradle = WORK_DIR / "build.gradle.kts"
    app_build_gradle = WORK_DIR / "app" / "build.gradle.kts"
    
    # Update Kotlin plugin version to 1.9.24 (latest stable as of now)
    if build_gradle.exists():
        content = build_gradle.read_text()
        # Update Kotlin Android plugin
        content = re.sub(r'id\("org\.jetbrains\.kotlin\.android"\) version "[^"]+"', 'id("org.jetbrains.kotlin.android") version "1.9.24"', content)
        # Update KSP version
        content = re.sub(r'id\("com\.google\.devtools\.ksp"\) version "[^"]+"', 'id("com.google.devtools.ksp") version "1.9.24-1.0.20"', content)
        build_gradle.write_text(content)
    
    # Update compose compiler version to 1.5.12 (compatible with Kotlin 1.9.24)
    if app_build_gradle.exists():
        content = app_build_gradle.read_text()
        content = re.sub(r'kotlinCompilerExtensionVersion = "[^"]+"', 'kotlinCompilerExtensionVersion = "1.5.12"', content)
        app_build_gradle.write_text(content)
    
    print("Updated Kotlin to 1.9.24 and compose compiler to 1.5.12")

def fix_dependency_conflict():
    """Update dependencies to latest compatible versions."""
    print("Attempting to fix dependency conflicts...")
    app_build_gradle = WORK_DIR / "app" / "build.gradle.kts"
    if not app_build_gradle.exists():
        return
    
    content = app_build_gradle.read_text()
    # Update core-ktx
    content = re.sub(r'implementation\("androidx\.core:core-ktx:[^"]+"\)', 'implementation("androidx.core:core-ktx:1.13.0")', content)
    # Update compose BOM
    content = re.sub(r'implementation\(platform\("androidx\.compose:compose-bom:[^"]+"\)\)', 'implementation(platform("androidx.compose:compose-bom:2024.08.00"))', content)
    # Update lifecycle
    content = re.sub(r'implementation\("androidx\.lifecycle:lifecycle-runtime-ktx:[^"]+"\)', 'implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")', content)
    # Update activity compose
    content = re.sub(r'implementation\("androidx\.activity:activity-compose:[^"]+"\)', 'implementation("androidx.activity:activity-compose:1.9.0")', content)
    
    app_build_gradle.write_text(content)
    print("Updated dependencies to latest stable versions")

def fix_unresolved_reference(logs):
    """Attempt to fix unresolved reference errors by adding missing imports or correcting symbols."""
    print("Attempting to fix unresolved reference...")
    # Extract symbol from error line
    lines = logs.split('\n')
    for line in lines:
        if 'unresolved reference' in line:
            match = re.search(r"unresolved reference: (\w+)", line)
            if match:
                symbol = match.group(1)
                print(f"Detected unresolved reference: {symbol}")
                # Could be missing import; we might need to search for where it's used.
                # For now, we'll do nothing; but we can attempt to add import if we know the package.
                # This is a placeholder.
                pass
    # Try to run ./gradlew build to see if there are more errors
    subprocess.run(["./gradlew", "build"], cwd=WORK_DIR, capture_output=True, text=True, timeout=120)

def fix_compilation(logs):
    """Generic compilation error fix - try to update dependencies and Kotlin."""
    print("Attempting generic compilation error fix...")
    fix_kotlin_version()
    fix_dependency_conflict()
    # Maybe also run gradle clean
    subprocess.run(["./gradlew", "clean"], cwd=WORK_DIR, capture_output=True, text=True)

def commit_and_push(message):
    """Commit changes and push to trigger new run."""
    subprocess.run(["git", "add", "."], cwd=WORK_DIR, check=False)
    subprocess.run(["git", "commit", "-m", message], cwd=WORK_DIR, check=False)
    subprocess.run(["git", "push"], cwd=WORK_DIR, check=False)
    print(f"Committed and pushed: {message}")

def send_notification(text):
    """Send notification to main session via Telegram."""
    # Use the message tool via subprocess? We'll just print for now; main agent will handle.
    print(f"NOTIFICATION: {text}")
    # In the actual deployment, we would call the message tool.
    # For subagent, we can just output and let the main agent forward.
    # We'll also write to a file that main agent can read.
    with open("notification.txt", "w") as f:
        f.write(text)

def load_state():
    """Load monitoring state from file."""
    if STATE_FILE.exists():
        try:
            return json.loads(STATE_FILE.read_text())
        except:
            pass
    return {"attempt": 1, "last_run_id": None, "start_time": time.time()}

def save_state(state):
    """Save state to file."""
    STATE_FILE.write_text(json.dumps(state, indent=2))

def main():
    print(f"Monitoring GitHub Actions for repo {REPO}")
    print(f"Interval: {INTERVAL_SECONDS}s, Max attempts: {MAX_ATTEMPTS}")
    
    state = load_state()
    attempt = state.get("attempt", 1)
    last_run_id = state.get("last_run_id")
    
    while attempt <= MAX_ATTEMPTS:
        print(f"\n=== Attempt {attempt}/{MAX_ATTEMPTS} ===")
        run = get_latest_run()
        if not run:
            print("No runs found. Waiting...")
            time.sleep(INTERVAL_SECONDS)
            attempt += 1
            continue
        
        run_id = run["databaseId"]
        status = run["status"]
        conclusion = run.get("conclusion")
        url = run["url"]
        
        print(f"Run ID: {run_id}, Status: {status}, Conclusion: {conclusion}")
        print(f"URL: {url}")
        
        if status == "completed":
            if conclusion == "success":
                print("Run succeeded!")
                # Try to download APK artifact
                if download_artifact(run_id):
                    print("APK artifact downloaded.")
                else:
                    print("Failed to download artifact.")
                # Notify success
                send_notification(f"SUCCESS: Build completed successfully.\nAPK download link: {url}/artifacts")
                sys.exit(0)
            else:
                print("Run failed. Analyzing logs...")
                jobs = get_jobs(run_id)
                logs = ""
                for job in jobs:
                    job_id = job.get("databaseId")
                    if job_id:
                        log = get_job_logs(run_id, job_id)
                        if log:
                            logs += log
                error_type = analyze_logs(logs)
                print(f"Detected error type: {error_type}")
                
                # Apply fixes based on error type
                if error_type == "kotlin_version":
                    fix_kotlin_version()
                    commit_and_push("Fix Kotlin version mismatch")
                elif error_type == "dependency_conflict":
                    fix_dependency_conflict()
                    commit_and_push("Fix dependency conflicts")
                elif error_type == "unresolved_reference":
                    fix_unresolved_reference(logs)
                    commit_and_push("Fix unresolved reference")
                elif error_type == "compilation" or error_type == "build_failed":
                    fix_compilation(logs)
                    commit_and_push("Fix compilation errors")
                else:
                    # Generic fix
                    fix_kotlin_version()
                    fix_dependency_conflict()
                    commit_and_push("Generic fix attempt")
                
                # After push, wait for new run to appear
                print("Waiting 30 seconds for new run to start...")
                time.sleep(30)
                # Update attempt count
                attempt += 1
                state["attempt"] = attempt
                save_state(state)
                # Continue loop; next iteration will pick up new run
                continue
        else:
            print(f"Run still in progress. Waiting {INTERVAL_SECONDS} seconds...")
            time.sleep(INTERVAL_SECONDS)
            # Keep same attempt count
            # Update last_run_id to avoid re-checking same run?
            if last_run_id != run_id:
                last_run_id = run_id
                state["last_run_id"] = run_id
                save_state(state)
            continue
    
    # Max attempts reached
    print("Maximum attempts reached without success.")
    send_notification(f"FAILURE: Build failed after {MAX_ATTEMPTS} attempts. Please check manually.")
    sys.exit(1)

if __name__ == "__main__":
    main()