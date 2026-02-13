# Getting Started - MediTrack 1.0

Quick start guide for MediTrack 1.0 - Simple Desktop Clinic Management System

## ⚡ Quick Setup (2 minutes)

### Step 1: Check Java Installation

```bash
java -version
```

**Required:** Java 11 or higher

### Step 2: Compile

**Linux/Mac:**
```bash
chmod +x build.sh run.sh test.sh
./build.sh
```

**Windows:**
```cmd
build.bat
```

**Output:** Compiled classes in `build/` folder

### Step 3: Run the Application

**Linux/Mac:**
```bash
./run.sh
```

**Windows:**
```cmd
run.bat
```

### Step 4: Follow the On-Screen Menu

```
===== MediTrack ==== Main Menu =====
1. Doctor Management
2. Patient Management
3. Appointment Management
4. Billing Management
5. Reports and Analytics
6. Exit
```

---

## 🧪 Running Tests

**Linux/Mac:**
```bash
./test.sh
```

**Windows:**
```cmd
test.bat
```

**Expected:** All 29 tests pass ✓

---

## 📁 Project Layout

```
MediTrack1.0/
├── src/main/java/          # Application source code
├── src/test/java/          # Test code
├── docs/                   # Documentation
├── build/                  # Compiled classes (auto-generated)
├── build.sh / build.bat    # Build scripts
├── run.sh / run.bat        # Run scripts
└── test.sh / test.bat      # Test scripts
```

---

## 🎯 Key Features

✅ Doctor CRUD operations  
✅ Patient registration & management  
✅ Appointment booking & scheduling  
✅ Billing & revenue tracking  
✅ Advanced search capabilities  
✅ Complete input validation  
✅ Reports & analytics dashboard  

---

## 📋 System Requirements

- **Java:** 11+ 
- **Memory:** 256 MB
- **Disk:** 20 MB
- **OS:** Any (Windows/Mac/Linux)

---

## 🔧 Manual Compilation

If scripts don't work, compile manually:

```bash
# Create build directory
mkdir -p build

# Compile all Java files
javac -d build $(find src -name "*.java")

# Run application
java -cp build com.airtribe.meditrack.Main

# Run tests
java -cp build com.airtribe.meditrack.test.TestRunner
```

---

## 📚 Sample Data

The application loads sample data automatically:
- 3 Doctors (Cardiology, Orthopedics, General Practice)
- 3 Patients
- 3 Pre-booked appointments

---

## 🚀 Example Workflow

1. **Add a Doctor**
   - Enter: Dr. John Smith, Cardiology, ₹800 fee
   
2. **Register a Patient**
   - Enter: Patient details, blood group, age
   
3. **Book an Appointment**
   - Select doctor, patient, date/time
   
4. **View Reports**
   - Check total doctors, patients, appointments
   
5. **Track Billing**
   - View consultation fees and revenue

---

## 📖 Documentation

- **README.md** - Full project documentation
- **docs/Setup_Instructions.md** - Detailed setup guide
- **docs/Design_Decisions.md** - Architecture & design patterns
- **docs/JVM_Report.md** - Performance analysis

---

## ❓ Troubleshooting

### Issue: "Java not found"
```bash
# Check Java installation
java -version

# Install Java if needed
# Ubuntu: sudo apt-get install openjdk-11-jdk
# macOS: brew install openjdk@11
# Windows: Download from oracle.com
```

### Issue: "Cannot compile"
```bash
# Clear build directory and retry
rm -rf build
mkdir build
javac -d build $(find src -name "*.java")
```

### Issue: "Build scripts not executable" (Linux/Mac only)
```bash
chmod +x build.sh run.sh test.sh
./build.sh
```

---

## 🎓 Learning Outcomes

This project demonstrates:

- ✅ Object-Oriented Programming (OOP)
- ✅ SOLID Principles
- ✅ Design Patterns
- ✅ Exception Handling
- ✅ Collections Framework
- ✅ Streams API
- ✅ Thread Safety
- ✅ Input Validation
- ✅ Immutable Objects
- ✅ Generics

---

## 📦 Project Statistics

- **20+ Java Classes**
- **150+ Methods**
- **3500+ Lines of Code**
- **29 Test Cases** (100% pass rate)
- **0 External Dependencies**

---

## 🔐 Code Quality

- ✅ Full JavaDoc comments
- ✅ Consistent naming conventions
- ✅ Error handling & validation
- ✅ Thread-safe implementations
- ✅ Memory efficient
- ✅ Scalable architecture

---

## 📍 Next Steps

1. Read the **README.md** for complete documentation
2. Explore source code in **src/main/java/**
3. Review **docs/Design_Decisions.md** for architecture
4. Run tests with **./test.sh** (or test.bat)
5. Modify and extend the application!

---

## 📧 Support

For questions or issues:
1. Check **README.md** FAQ section
2. Review **docs/Setup_Instructions.md**
3. Check your Java version: `java -version`
4. Ensure all files are in correct location

---

**Happy Coding! 🚀**

*MediTrack 1.0 - Simple, Clean, Educational Java Application*
