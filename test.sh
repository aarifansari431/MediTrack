#!/bin/bash

# MediTrack 1.0 - Run Tests Script for Linux/Mac

if [ ! -d "build" ]; then
    echo "Build directory not found. Please run ./build.sh first"
    exit 1
fi

echo "Running MediTrack 1.0 Test Suite..."
echo "===================================="
echo ""

# Run the test runner
java -cp build com.airtribe.meditrack.test.TestRunner
