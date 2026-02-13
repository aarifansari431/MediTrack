@echo off
REM MediTrack 1.0 - Simple Build Script for Windows
REM Compiles all Java source files

echo Building MediTrack 1.0...
echo ==========================

REM Create build directory
if not exist build mkdir build

REM Compile Java files
echo Compiling Java source files...
setlocal enabledelayedexpansion
set "files="
for /r src %%f in (*.java) do (
    set "files=!files! %%f"
)

javac -d build %files%

REM Check compilation status
if %ERRORLEVEL% equ 0 (
    echo Build successful!
    echo Output: build\
    echo.
    echo To run the application, execute: run.bat
    echo To run tests, execute: test.bat
) else (
    echo Build failed!
    exit /b 1
)
