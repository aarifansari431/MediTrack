# Setup Instructions - MediTrack 1.0

## Prerequisites

Before you begin, ensure you have the following installed on your system:

### 1. Java Development Kit (JDK)

**Minimum Version:** Java 11  
**Recommended Version:** Java 17+

#### Check Java Installation:
```bash
java -version
```

Expected output:
```
java version "11.0.x" 2021-xx-xx LTS
Java(TM) SE Runtime Environment 18.9 (build 11.0.x+x)
Java HotSpot(TM) 64-Bit Server VM (build 11.0.x+x, mixed mode)
```

#### Install Java:

**Windows:**
- Download from [oracle.com/java](https://www.oracle.com/java/technologies/downloads/) or [openjdk.java.net](https://openjdk.java.net/)
- Run the installer and follow the wizard
- Add JAVA_HOME to environment variables

**macOS:**
```bash
brew install openjdk@11
```

**Linux:**
```bash
sudo apt-get update
sudo apt-get install openjdk-11-jdk
```

### 2. Maven

**Version:** 3.6.0 or higher

#### Check Maven Installation:
```bash
mvn -version
```

Expected output:
```
Apache Maven 3.6.x
Maven home: /path/to/maven
...
```

#### Install Maven:

**Windows:**
1. Download from [maven.apache.org](https://maven.apache.org/)
2. Extract to a directory (e.g., C:\maven)
3. Add MAVEN_HOME to environment variables
4. Add %MAVEN_HOME%\bin to PATH

**macOS:**
```bash
brew install maven
```

**Linux:**
```bash
sudo apt-get install maven
```

### 3. Git (Optional)

For cloning the repository.

```bash
git --version
```

---

## Step-by-Step Setup

### Step 1: Obtain the Project

**Option A: Clone from GitHub**
```bash
git clone https://github.com/aarifansari431/TestMeditrak.git
cd TestMeditrak/MediTrack1.0
```

**Option B: Manual Download**
1. Download the project zip file
2. Extract to your preferred location
3. Navigate to the `MediTrack1.0` directory

### Step 2: Verify Project Structure

Ensure you have the following structure:
```
MediTrack1.0/
├── src/
│   ├── main/java/com/airtribe/meditrack/
│   └── test/java/com/airtribe/meditrack/
├── docs/
├── pom.xml
└── README.md
```

### Step 3: Set Up Environment Variables

#### Set JAVA_HOME:

**Windows:**
```cmd
set JAVA_HOME=C:\Program Files\Java\jdk-11.0.x
```

**macOS/Linux:**
```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 11)
```

#### Set MAVEN_HOME:

**Windows:**
```cmd
set MAVEN_HOME=C:\maven
set PATH=%PATH%;%MAVEN_HOME%\bin
```

**macOS/Linux:**
```bash
export MAVEN_HOME=/usr/local/opt/maven
export PATH=$PATH:$MAVEN_HOME/bin
```

### Step 4: Build the Project

Navigate to the project directory and run:

```bash
cd MediTrack1.0
mvn clean compile
```

Expected output (last lines):
```
[INFO] BUILD SUCCESS
[INFO] Total time: x.xxx s
[INFO] Finished at: 2024-XX-XXTXX:XX:XX+XX:XX
```

---

## Running the Application

### Option 1: Using Maven Exec Plugin

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.Main"
```

### Option 2: Using JAR File

```bash
# Create JAR
mvn clean package

# Run JAR
java -jar target/meditrack-1.0.jar
```

### Option 3: Running from IDE

**IntelliJ IDEA:**
1. Open File → Open Project
2. Select MediTrack1.0 folder
3. Mark src/main/java as Sources Root
4. Right-click Main.java → Run 'Main.main()'

**Eclipse:**
1. File → Import → Existing Maven Projects
2. Select MediTrack1.0 folder
3. Right-click Main.java → Run As → Java Application

**VS Code:**
1. Install Extension Pack for Java
2. Open Command Palette (Ctrl+Shift+P)
3. Search "Java: Run" and select Main.java

---

## Running Tests

### Run Manual Test Suite

```bash
# Compile the project first
mvn clean compile

# Run the test suite
java -cp target/classes com.airtribe.meditrack.test.TestRunner
```

Expected output:
```
===================================
  MediTrack - Manual Test Runner
===================================

--- Testing Doctor Service ---
✓ Add Doctor
✓ Get Doctor by ID
✓ Get All Doctors
...

===================================
          Test Summary
===================================
Tests Passed: 25
Tests Failed: 0
Total Tests: 25
Pass Rate: 100.00%
===================================
```

---

## Generating JavaDoc

```bash
mvn javadoc:javadoc
```

JavaDoc will be generated in:
```
target/site/apidocs/index.html
```

Open the HTML file in your browser to view API documentation.

---

## Troubleshooting

### Issue 1: "Java not found" or "Not recognized as internal command"

**Solution:**
- Verify Java installation: `java -version`
- Check JAVA_HOME: `echo %JAVA_HOME%` (Windows) or `echo $JAVA_HOME` (Unix)
- Add JDK bin directory to PATH

### Issue 2: "Maven not recognized"

**Solution:**
- Verify Maven installation: `mvn -version`
- Check MAVEN_HOME setup
- Restart terminal after setting environment variables

### Issue 3: "BUILD FAILURE: Cannot find symbol"

**Solution:**
```bash
# Clear cache and rebuild
mvn clean
mvn compile

# Refresh IDE dependencies
# IntelliJ: File → Invalidate Caches
# Eclipse: Project → Clean
```

### Issue 4: "Port already in use"

**Solution:**
- The console app doesn't use ports - this shouldn't occur
- Clear any terminal processes if needed

### Issue 5: "Compilation errors in Main.java"

**Solution:**
```bash
mvn clean compile -U
# -U forces update of dependencies
```

---

## Project Configuration

### pom.xml Configuration

The project uses the following key configurations:

```xml
<properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>11</maven.compiler.source>
    <maven.compiler.target>11</maven.compiler.target>
</properties>
```

### File Structure Notes

- **Source Files:** `src/main/java/com/airtribe/meditrack/`
- **Test Files:** `src/test/java/com/airtribe/meditrack/`
- **Resources:** `src/main/resources/` (created on demand)
- **Built Classes:** `target/classes/`
- **Generated Docs:** `target/site/apidocs/`

---

## Development Workflow

### 1. Create New Feature

```bash
mvn clean compile
# Edit files in src/main/java/...
mvn compile
```

### 2. Test Your Changes

```bash
java -cp target/classes com.airtribe.meditrack.test.TestRunner
```

### 3. Run Main Application

```bash
mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.Main"
```

---

## Environment Setup Checklist

- [ ] Java JDK 11+ installed and JAVA_HOME set
- [ ] Maven 3.6+ installed and MAVEN_HOME set
- [ ] PATH environment variable includes Java and Maven bin directories
- [ ] Project structure verified
- [ ] `mvn clean compile` executes successfully
- [ ] `mvn exec:java` runs the application
- [ ] Test runner executes all tests successfully

---

## Next Steps

1. **Read the README.md** for project overview
2. **Review Design_Decisions.md** for architecture details
3. **Run the Test Suite** to verify installation
4. **Launch the Application** and explore features
5. **Review Source Code** in `src/main/java/`

---

## System Requirements Summary

| Component | Minimum | Recommended |
|-----------|---------|-------------|
| Java | 11 | 17+ |
| Maven | 3.6.0 | 3.8.0+ |
| RAM | 256 MB | 512 MB+ |
| Disk | 100 MB | 500 MB+ |
| OS | Any | Linux/macOS |

---

## Support

For issues during setup:
1. Check Java and Maven versions: `java -version` and `mvn -version`
2. Verify JAVA_HOME and MAVEN_HOME paths
3. Review project README.md
4. Check mavens documentation: [maven.apache.org](https://maven.apache.org/)
5. Check Java documentation: [docs.oracle.com](https://docs.oracle.com/)

---

**Happy Coding! 🚀**
