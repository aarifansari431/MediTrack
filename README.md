# MediTrack 1.0 - Clinic & Appointment Management System

A comprehensive, object-oriented clinic management system implemented in **Core Java** demonstrating strong OOP design, SOLID principles, and Java fundamentals.

## Features

✅ **Doctor Management** - CRUD operations for healthcare professionals  
✅ **Patient Management** - Complete patient profiles with medical history  
✅ **Appointment Scheduling** - Smart appointment booking with slot management  
✅ **Billing System** - Consultation fee management and revenue tracking  
✅ **Advanced Search** - Search doctors and patients by multiple criteria  
✅ **Validation** - Comprehensive input validation and error handling  
✅ **Reports & Analytics** - Dashboard with key metrics and insights  

## Project Structure

```
MediTrack1.0/
├── src/
│   ├── main/java/com/airtribe/meditrack/
│   │   ├── Main.java                    # Entry point with console menus
│   │   ├── constants/
│   │   │   └── Constants.java            # Application-wide constants
│   │   ├── entity/
│   │   │   ├── Person.java               # Abstract base class
│   │   │   ├── Doctor.java               # Doctor entity
│   │   │   ├── Patient.java              # Patient entity (with cloning)
│   │   │   ├── Appointment.java          # Appointment entity
│   │   │   ├── Bill.java                 # Bill entity (implements Payable)
│   │   │   └── BillSummary.java          # Immutable bill summary
│   │   ├── service/
│   │   │   ├── DoctorService.java        # Doctor business logic
│   │   │   ├── PatientService.java       # Patient business logic
│   │   │   └── AppointmentService.java   # Appointment business logic
│   │   ├── util/
│   │   │   ├── IdGenerator.java          # Thread-safe ID generation
│   │   │   ├── Validator.java            # Input validation
│   │   │   ├── DateUtil.java             # Date/time utilities
│   │   │   ├── DataStore.java            # Generic data store with filtering
│   │   │   ├── CSVUtil.java              # CSV file operations
│   │   │   └── AIHelper.java             # AI recommendations (optional)
│   │   ├── exception/
│   │   │   ├── DoctorNotFoundException.java
│   │   │   ├── PatientNotFoundException.java
│   │   │   ├── AppointmentNotFoundException.java
│   │   │   └── InvalidDataException.java
│   │   └── interface_impl/
│   │       ├── Searchable.java           # Interface for searchable entities
│   │       └── Payable.java              # Interface for payable entities
│   └── test/java/com/airtribe/meditrack/
│       └── TestRunner.java               # Manual test suite
├── docs/
│   ├── JVM_Report.md                     # JVM analysis
│   ├── Setup_Instructions.md             # Setup guide
│   └── Design_Decisions.md               # Architecture decisions
├── build.sh / build.bat                  # Build scripts
├── run.sh / run.bat                      # Run application scripts
├── test.sh / test.bat                    # Run tests scripts
├── build/                                # Compiled classes (generated)
├── .gitignore                            # Git ignore rules
└── README.md                             # This file
```

## Quick Start

### Prerequisites
- Java 11 or higher
- Git (optional)
- Linux/Mac: bash shell, OR Windows: Command Prompt

### Compilation & Execution

#### For Linux/Mac:

**1. Compile the project:**
```bash
chmod +x build.sh run.sh test.sh
./build.sh
```

**2. Run the application:**
```bash
./run.sh
```

**3. Run tests:**
```bash
./test.sh
```

#### For Windows:

**1. Compile the project:**
```cmd
build.bat
```

**2. Run the application:**
```cmd
run.bat
```

**3. Run tests:**
```cmd
test.bat
```

#### Manual Compilation (All Platforms):

```bash
# Create build directory
mkdir build

# Compile all Java files
javac -d build $(find src -name "*.java")

# Run the application
java -cp build com.airtribe.meditrack.Main

# Run tests
java -cp build com.airtribe.meditrack.test.TestRunner
```

## Usage Guide

### Main Menu Options

1. **Doctor Management**
   - Add new doctors
   - View all doctors
   - Search doctors by name/specialization/email
   - Update doctor information
   - Delete doctors (mark inactive)

2. **Patient Management**
   - Register new patients
   - View patient records
   - Search patients by name/email/blood group
   - Update patient information
   - Delete patients (mark inactive)

3. **Appointment Management**
   - Book appointments
   - View availability
   - Complete appointments
   - Cancel appointments
   - Reschedule appointments

4. **Billing Management**
   - View consultation fees
   - Calculate monthly revenue
   - Track payment status

5. **Reports & Analytics**
   - Dashboard with key metrics
   - Upcoming appointments
   - Revenue analysis

### Example Workflow

```
1. Add a Doctor (Cardiology, ₹800 consultation fee)
2. Register a Patient 
3. Book an Appointment (MONDAY, 10:00 AM)
4. Complete Appointment
5. View Billing Summary
```

## Sample Data

The application comes pre-loaded with sample data:
- **3 Sample Doctors** (Cardiology, Orthopedics, General Practice)
- **3 Sample Patients** (Including senior citizen)
- **3 Sample Appointments** (Booked for upcoming days)

Remove lines 420-470 in Main.java if you want to start with empty data.

## Key OOP Concepts Demonstrated

| Concept | Implementation |
|---------|-----------------|
| **Inheritance** | Person → Doctor, Patient |
| **Encapsulation** | Private fields with getters/setters |
| **Polymorphism** | Interfaces (Searchable, Payable) |
| **Abstraction** | Abstract Person class |
| **Interfaces** | Searchable, Payable contracts |
| **Generics** | DataStore<T> |
| **Collections** | ArrayList, Collections, Streams |
| **Exception Handling** | Custom exceptions with meaningful messages |
| **Immutability** | BillSummary class |
| **Cloning** | Patient implements Cloneable |
| **Enums** | Day enums in appointment |
| **Streams API** | Filtering and aggregating data |

## SOLID Principles

✅ **Single Responsibility** - Each service handles one domain  
✅ **Open/Closed** - Open for extension via inheritance  
✅ **Liskov Substitution** - Patient/Doctor substitute for Person  
✅ **Interface Segregation** - Specific interfaces (Searchable, Payable)  
✅ **Dependency Injection** - Services injected into constructors  

## Advanced Features

### 1. Thread-Safe ID Generation
```java
IdGenerator.generateDoctorId()     // ✓ Thread-safe using AtomicLong
```

### 2. Generic DataStore with Lambda Filtering
```java
DataStore<Doctor> store = new DataStore<>("Doctors");
store.filter(d -> d.getSpecialization().equals("Cardiology"));
```

### 3. Immutable BillSummary
```java
BillSummary summary = new BillSummary(patientId, bills);
// No setters - truly immutable
```

### 4. Smart Appointment Validation
- Prevents booking in the past
- Checks doctor availability
- Validates slot availability per day
- Prevents duplicate bookings

### 5. AI Helper Recommendations
```java
AIHelper.getAppointmentRecommendation(medicalHistory, daysSinceVisit);
AIHelper.suggestConsultationFee(yearsOfExperience, specialization);
```

## Validation Rules

**Names:** 2-100 characters  
**Phone:** 10-15 digits  
**Age:** 0-150 years  
**Blood Group:** A+/A-/B+/B-/AB+/AB-/O+/O-  
**Gender:** MALE/FEMALE/OTHER  
**Email:** Valid email format  
**Appointments:** Cannot be in past, must have available slot  

## Error Handling

All operations throw appropriate exceptions:
- `DoctorNotFoundException` - Doctor not found
- `PatientNotFoundException` - Patient not found
- `AppointmentNotFoundException` - Appointment not found
- `InvalidDataException` - Validation failures

## Output Example

```
===================================
  Welcome to MediTrack 1.0
  Clinic & Appointment Management System
===================================

Loading sample data...
✓ Sample data loaded successfully!

Sample Doctors added: 3
Sample Patients added: 3
Sample Appointments booked: 3

===== MediTrack ==== Main Menu =====
1. Doctor Management
2. Patient Management
3. Appointment Management
4. Billing Management
5. Reports and Analytics
6. Exit
================================
Enter your choice: 1

======== Doctor Management ========
1. Add Doctor
2. View All Doctors
3. Search Doctor
4. Update Doctor
5. Delete Doctor
6. Back to Main Menu
```

## Testing

The project includes a comprehensive manual test suite:
```bash
java -cp target/classes com.airtribe.meditrack.test.TestRunner
```

Test Coverage:
- Doctor Service (Add, Get, Update, Delete, Search)
- Patient Service (Add, Get, Update, Delete, Search, Clone)
- Appointment Service (Book, Get, Complete, Cancel, Reschedule)
- Utility Classes (Validators, DateUtil, IdGenerator, DataStore)
- Exception Handling

## Data Persistence (Future Enhancement)

Currently, data is stored in memory. For production:
- Implement CSV export/import via CSVUtil
- Add database integration (Spring Data JPA)
- Implement serialization

## Development Standards

- **Code Style:** Java conventions
- **Naming:** Clear, descriptive variable names
- **Comments:** JavaDoc for all public methods
- **Error Messages:** User-friendly and actionable
- **Validation:** Input validation at service level

## Dependencies

**None** - Pure Core Java implementation using:
- Java Collections Framework
- Java Time API (java.time)
- Java Streams API
- Custom exception handling
- Standard input/output

No external libraries or frameworks required!

## System Requirements

- **JDK:** Java 11 or higher
- **Memory:** 256 MB minimum
- **Disk:** 20 MB for source code
- **OS:** Windows/Mac/Linux (any OS with Java support)

## Future Enhancements

1. Database Integration (MySQL/PostgreSQL)
2. Web UI (Spring Boot + Thymeleaf)
3. RESTful API (Spring REST)
4. Authentication & Authorization
5. Email notifications
6. SMS notifications
7. Mobile app integration
8. Advanced reporting with charts
9. Multi-clinic support
10. Prescription management

## Troubleshooting

**Issue:** "Main class not found"  
**Solution:** Ensure Maven is installed and run `mvn compile`

**Issue:** "Invalid input" errors  
**Solution:** Follow the prompted format (dates as dd/MM/yyyy, phone as 10-15 digits)

**Issue:** "Appointment not available"  
**Solution:** Check doctor availability and max slots per day

## Project Statistics

- **Total Lines of Code:** ~3500+
- **Java Classes:** 20+
- **Methods:** 150+
- **Exception Classes:** 4
- **Interfaces:** 2
- **Test Cases:** 25+

## Author

Developed by: Airtribe  
Version: 1.0  
Release Date: 2024  

## License

This project is for educational purposes.

---

**For more information, see:**
- [Setup Instructions](docs/Setup_Instructions.md)
- [Design Decisions](docs/Design_Decisions.md)
- [JVM Report](docs/JVM_Report.md)
