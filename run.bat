@echo off
REM MediTrack 1.0 - Run Application Script for Windows

if not exist build (
    echo Build directory not found. Please run build.bat first
    exit /b 1
)

echo Starting MediTrack 1.0...
echo =========================
echo.

REM Run the application
java -cp build com.airtribe.meditrack.Main
