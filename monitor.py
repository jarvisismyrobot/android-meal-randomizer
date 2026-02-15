#!/usr/bin/env python3
import subprocess
import json
import time
import sys
import os
import re

REPO = "jarvisismyrobot/android-meal-randomizer"
RUN_ID = 22027900151
MAX_ATTEMPTS = 10
INTERVAL_SECONDS = 180

def run_gh(args):
    cmd = ["gh", "run", "view", str(RUN_ID), "--repo", REPO] + args
    result = subprocess.run(cmd, capture_output=True, text=True)
    return result

def get_run_status():
    result = run_gh(["--json", "status,conclusion,url,databaseId"])
    if result.returncode != 0:
        print(f"Error getting run status: {result.stderr}")
        return None
    data = json.loads(result.stdout)
    return data

def get_job_logs(job_id):
    # Get logs for a job
    cmd = ["gh", "run", "view", "--job", str(job_id), "--repo", REPO, "--log"]
    result = subprocess.run(cmd, capture_output=True, text=True)
    if result.returncode != 0:
        return None
    return result.stdout

def get_jobs():
    result = run_gh(["--json", "jobs"])
    if result.returncode != 0:
        return []
    data = json.loads(result.stdout)
    return data.get("jobs", [])

def download_artifact(artifact_name):
    # gh run download <run-id> -n <name>
    cmd = ["gh", "run", "download", str(RUN_ID), "--repo", REPO, "-n", artifact_name]
    result = subprocess.run(cmd, capture_output=True, text=True, cwd=os.getcwd())
    return result.returncode == 0

def analyze_logs(logs):
    # Simple error detection
    errors = []
    if "Kotlin version" in logs and "compatibility" in logs:
        errors.append("kotlin_version")
    if "Could not resolve" in logs:
        errors.append("dependency_conflict")
    if "compileDebugKotlin" in logs and "error" in logs:
        errors.append("compilation")
    if "FAILED" in logs:
        errors.append("build_failed")
    return errors

def fix_kotlin_version():
    # Update Kotlin version in build.gradle.kts
    # This is a simplistic fix; we need to read and update.
    pass

def commit_and_push(message):
    subprocess.run(["git", "add", "."], check=False)
    subprocess.run(["git", "commit", "-m", message], check=False)
    subprocess.run(["git", "push"], check=False)

def main():
    print(f"Monitoring GitHub Actions run {RUN_ID} for repo {REPO}")
    print(f"Max attempts: {MAX_ATTEMPTS}, interval: {INTERVAL_SECONDS}s")
    
    for attempt in range(1, MAX_ATTEMPTS + 1):
        print(f"\n=== Attempt {attempt}/{MAX_ATTEMPTS} ===")
        status_data = get_run_status()
        if not status_data:
            print("Failed to get status, waiting...")
            time.sleep(INTERVAL_SECONDS)
            continue
        
        status = status_data.get("status")
        conclusion = status_data.get("conclusion")
        url = status_data.get("url")
        
        print(f"Status: {status}, Conclusion: {conclusion}")
        print(f"URL: {url}")
        
        if status == "completed":
            if conclusion == "success":
                print("Run succeeded!")
                # Download APK artifact
                if download_artifact("app-debug.apk"):
                    print("APK artifact downloaded.")
                    # Notify main session
                    print("SUCCESS: Build completed successfully.")
                    print(f"APK download link: {url}/artifacts")
                    sys.exit(0)
                else:
                    print("Failed to download artifact.")
            else:
                print("Run failed. Analyzing logs...")
                jobs = get_jobs()
                logs = ""
                for job in jobs:
                    job_id = job.get("databaseId")
                    if job_id:
                        log = get_job_logs(job_id)
                        if log:
                            logs += log
                errors = analyze_logs(logs)
                print(f"Detected errors: {errors}")
                # TODO: implement fixes based on errors
                # For now, just exit with failure
                print("No automatic fix implemented yet.")
                print("FAILURE: Build failed after maximum attempts.")
                sys.exit(1)
        else:
            print(f"Run still in progress. Waiting {INTERVAL_SECONDS} seconds...")
            time.sleep(INTERVAL_SECONDS)
    
    print("Maximum attempts reached without success.")
    sys.exit(1)

if __name__ == "__main__":
    main()