#!/bin/bash

# MediTrack 1.0 - Run Application Script for Linux/Mac

if [ ! -d "build" ]; then
    echo "Build directory not found. Please run ./build.sh first"
    exit 1
fi

echo "Starting MediTrack 1.0..."
echo "========================="
echo ""

# Run the application
java -cp build com.airtribe.meditrack.Main
