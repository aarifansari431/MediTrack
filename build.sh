#!/bin/bash

# MediTrack 1.0 - Simple Build Script for Linux/Mac
# Compiles all Java source files

echo "Building MediTrack 1.0..."
echo "=========================="

# Create build directory
mkdir -p build

# Find all Java files and compile
echo "Compiling Java source files..."
find src -name "*.java" -print0 | xargs -0 javac -d build

# Check compilation status
if [ $? -eq 0 ]; then
    echo "✓ Build successful!"
    echo "Output: build/"
    echo ""
    echo "To run the application, execute: ./run.sh"
    echo "To run tests, execute: ./test.sh"
else
    echo "✗ Build failed!"
    exit 1
fi
