@echo off
REM MediTrack 1.0 - Run Tests Script for Windows

if not exist build (
    echo Build directory not found. Please run build.bat first
    exit /b 1
)

echo Running MediTrack 1.0 Test Suite...
echo ====================================
echo.

REM Run the test runner
java -cp build com.airtribe.meditrack.test.TestRunner
