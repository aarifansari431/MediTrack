# MediTrack — Clinic & Appointment Management System

MediTrack is a console-based Java application that manages doctors, patients,
appointments, and billing using Core Java and OOP principles.

## Features
- CRUD operations for Doctors and Patients
- Appointment creation, cancellation, rescheduling
- Enum-based appointment status
- Billing with Strategy Pattern
- Observer-based appointment notifications
- CSV persistence
- Streams & Lambdas for search
- Generic DataStore

## Tech Stack
- Java 17
- Core Java (No frameworks)
- Collections, Streams, I/O

## How to Run
1. Compile:
   javac com/airtribe/meditrack/Main.java
2. Run:
   java com.airtribe.meditrack.Main

## Sample Output
--- MediTrack Menu ---
1. Add Doctor
2. Add Patient
3. Create Appointment
4. Reschedule Appointment
5. Generate Bill
0. Exit

Appointment created. Status: PENDING
🔔 Appointment update: Status = CONFIRMED
