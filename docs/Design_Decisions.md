# Design Decisions - MediTrack 1.0

## Architecture Overview

MediTrack 1.0 follows a **Layered Architecture** with clear separation of concerns:

```
┌─────────────────────────────────┐
│      Presentation Layer         │
│      (Main.java - Console UI)   │
└────────────────┬────────────────┘
                 │
┌─────────────────▼────────────────┐
│      Service Layer              │
│  (Business Logic & Validation)  │
│ - DoctorService                 │
│ - PatientService                │
│ - AppointmentService            │
└────────────────┬────────────────┘
                 │
┌─────────────────▼────────────────┐
│      Entity Layer               │
│      (Domain Models)            │
│ - Person, Doctor, Patient       │
│ - Appointment, Bill             │
└────────────────┬────────────────┘
                 │
┌─────────────────▼────────────────┐
│      Utility & Support Layer    │
│  - Validators, DateUtil         │
│  - IdGenerator, DataStore       │
│  - Exceptions, Interfaces       │
└─────────────────────────────────┘
```

---

## Detailed Design Decisions

### 1. Entity Design (Domain Models)

#### Person - Abstract Base Class
```
Decision: Use abstract class for Person
Rationale:
- Both Doctor and Patient share common attributes (name, email, phone, etc.)
- Prevents instantiation of incomplete entity
- Enforces inheritance contract
- Promotes code reuse and DRY principle
```

**Alternative Considered:** Interface  
**Rejected Because:** Interfaces cannot have constructor logic or field initialization

#### Doctor & Patient - Concrete Implementations
```
Decision: Two separate entity classes extending Person
Rationale:
- Each has unique domain requirements
- Doctor needs specialization, consultation fee, availability
- Patient needs medical history, blood group, age
- Separate classes maintain cohesion
```

#### Appointment - Standalone Entity
```
Decision: Appointment doesn't extend Person (composition over inheritance)
Rationale:
- Appointment represents a relationship, not an entity type
- Contains references to both Doctor and Patient IDs
- Independent lifecycle and validation rules
- Prevents unnecessary inheritance hierarchy
```

#### BillSummary - Immutable Class
```
Decision: Make BillSummary immutable with no setters
Rationale:
- Represents a snapshot of billing information
- Once created, summary shouldn't change
- Thread-safe without synchronization
- Easier to reason about state
Implementation:
- Final fields
- No setters
- Collections returned as unmodifiable views
- Constructor calculates all derived fields
```

**Pattern:** Immutable Object Pattern

---

### 2. Service Layer Design

#### Three Service Classes (Single Responsibility)

```
DoctorService
├─ Add/Get/Update/Delete Doctor
├─ Search Doctors
└─ Filter by Specialization/Availability

PatientService
├─ Add/Get/Update/Delete Patient
├─ Search Patients
└─ Filter by Attributes

AppointmentService
├─ Book/Get/Cancel Appointment
├─ Validation Logic
├─ Appointment Rescheduling
└─ Complex Business Rules
```

**Decision:** Separate services instead of one monolithic service  
**Rationale:**
- Each service has single responsibility
- Easier to test and maintain
- Aligns with SOLID principles
- Can be scaled independently

#### Validation in Service Layer

```
Decision: All validation happens in Service Layer
Rationale:
- Entities are "anemic models" (no business logic)
- Service validates before creating/updating entities
- Validation centralization prevents inconsistency
- Clear separation between presentation and business logic
```

---

### 3. Data Storage - Generic DataStore<T>

#### Generic Implementation
```java
public class DataStore<T> {
    private List<T> data;
    // CRUD operations
}
```

**Decision:** Generic class instead of separate storage classes  
**Rationale:**
- Works with any entity type
- Reduces code duplication
- Type-safe without casting
- Demonstrates Java Generics knowledge

#### Lambda-Based Filtering
```java
public List<T> filter(DataStorePredicate<T> predicate)
```

**Decision:** Use functional interface and lambdas  
**Rationale:**
- Enables flexible filtering without loops
- Functional programming approach
- Cleaner, more readable code
- Demonstrates Java Streams API understanding

#### Synchronized List
```java
Collections.synchronizedList(new ArrayList<>())
```

**Decision:** Synchronized collections for thread-safety  
**Rationale:**
- IdGenerator uses AtomicLong for thread-safe ID generation
- DataStore could be accessed from multiple threads
- Prevents concurrent modification exceptions
- Production-ready design

**Alternative Considered:** CopyOnWriteArrayList  
**Rejected Because:** Higher overhead for frequent reads

---

### 4. Exception Handling Strategy

#### Custom Exceptions
- `DoctorNotFoundException`
- `PatientNotFoundException`
- `AppointmentNotFoundException`
- `InvalidDataException`

**Decision:** Checked exceptions for domain-specific errors  
**Rationale:**
- Developers must handle these explicitly
- Clear communication of failure modes
- Allows proper error recovery
- Better than generic Exception

#### Exception Hierarchy
```
Exception
├── DoctorNotFoundException
├── PatientNotFoundException
├── AppointmentNotFoundException
└── InvalidDataException
```

**Decision:** Flat hierarchy instead of parent exception  
**Rationale:**
- Each exception is distinct and handled differently
- Simpler than creating NotFoundException base class
- Clear intent in catch blocks

---

### 5. Validation Design

#### Centralized Validator Class

**Decision:** Static methods in Validator utility  
**Rationale:**
- No instance needed (stateless utility)
- Consistent validation across application
- Easy to test validation logic
- Single source of truth for rules

```java
Validator.validateEmail(email);      // Throws if invalid
Validator.validatePhone(phone);      // Throws if invalid
Validator.validateAge(age);          // Throws if invalid
```

#### Validation Patterns

**Fail-Fast Approach:**
```
Decision: Throw exception immediately on invalid input
Rationale:
- Prevents partial updates
- Clear error reporting
- No silent failures
- User knows exactly what went wrong
```

---

### 6. Date/Time Handling

#### DateUtil with Specific Formatters

**Decision:** Use java.time API (LocalDate, LocalDateTime)  
**Rationale:**
- Modern standard (LocalDate is thread-safe)
- Old Date/Calendar classes are problematic
- Better for date calculations
- Timezone-aware capabilities

**Format Strings:**
- Date: `dd/MM/yyyy`
- Time: `HH:mm`
- DateTime: `dd/MM/yyyy HH:mm`

**Decision:** Fixed format strings  
**Rationale:**
- Consistent user experience
- Easy validation and parsing
- Prevents ambiguity

---

### 7. Appointment Booking Logic

#### Smart Scheduling with Multiple Validations

```
Appointment Validation Flow:
1. Check if datetime is in future
2. Verify doctor is active
3. Verify patient is active
4. Check doctor availability for day
5. Check doctor's max slots for day
6. Prevent duplicate appointments
7. Create appointment
```

**Decision:** Layered validation approach  
**Rationale:**
- Catches errors early
- Clear error messages
- Prevents invalid state
- Production-ready robustness

#### Slot Management
```
Decision: Store appointments with date, track daily count
Rationale:
- Simple slot checking based on appointment count
- No need for calendar/schedule classes
- Sufficient for demonstration
```

---

### 8. ID Generation Strategy

#### Thread-Safe ID Generation with AtomicLong

```java
private static final AtomicLong doctorIdCounter = new AtomicLong(1000L);
```

**Decision:** Static counters starting at different bases  
**Rationale:**
- Thread-safe without locks (uses CAS)
- Demonstrates concurrent programming
- Easy to distinguish entity types (1000+, 2000+, etc.)
- Incrementing ensures unique IDs

**ID Ranges:**
- Doctors: 1000+
- Patients: 2000+
- Appointments: 3000+
- Bills: 4000+

---

### 9. Interfaces & Contracts

#### Searchable Interface
```java
public interface Searchable {
    boolean matches(String criteria);
    long getId();
}
```

**Implementation:** Doctor, Patient

**Decision:** Separate interface instead of base method  
**Rationale:**
- Not all entities are searchable
- Clear contract for searchable behavior
- Interface segregation (SOLID)
- Can be extended for advanced searching

#### Payable Interface
```java
public interface Payable {
    double calculateAmount();
    String getPaymentDescription();
    String getPaymentType();
}
```

**Implementation:** Bill

**Decision:** Interface for billing-related operations  
**Rationale:**
- Abstracts billing behavior
- Enables polymorphism in future (different bill types)
- Clear separation from other interfaces
- Extensible for payment processors

---

### 10. Constants Management

#### Centralized Constants Class

**Decision:** All constants in one class  
**Rationale:**
- Single source of truth
- Easy to modify application behavior
- No magic numbers scattered in code
- Professional codebase appearance

**Categories:**
- Application info
- Date/Time formats
- Validation rules
- File paths
- Menu labels
- Status constants
- Error/Success messages

---

### 11. Console UI Design (Main.java)

#### Menu-Driven Architecture

```
Main Menu
├── Doctor Management Submenu
├── Patient Management Submenu
├── Appointment Management Submenu
├── Billing Management Submenu
├── Reports & Analytics
└── Exit
```

**Decision:** Hierarchical menu structure  
**Rationale:**
- Intuitive navigation
- Easy to add new features
- Organized grouping of operations
- User-friendly

#### Input Handling

**Decision:** Dedicated input methods (getIntInput, getLongInput, etc.)  
**Rationale:**
- Centralized error handling
- Consistent exception handling
- Reusable across menus
- Clear separation of concerns

---

### 12. Testing Strategy

#### Manual Test Runner (No JUnit)

**Decision:** Custom TestRunner instead of JUnit  
**Rationale:**
- Demonstrates manual testing capability
- Shows understanding of core Java
- No external dependency
- Can be run with java -cp

#### Test Coverage

```
✓ DoctorService (6 tests)
✓ PatientService (7 tests)
✓ AppointmentService (5 tests)
✓ Utility Classes (5 tests)
✓ Exception Handling (3 tests)
Total: 26 tests
```

---

### 13. String Building & Formatting

#### StringBuilder for Concatenation
```java
StringBuilder content = new StringBuilder();
content.append(line).append(NEWLINE);
```

**Decision:** StringBuilder instead of string concatenation  
**Rationale:**
- Better performance (immutability avoidance)
- Professional Java practice
- Handles large strings efficiently

#### String.format() for Consistency
```java
String.format("%.2f", amount)  // 2 decimal places
```

---

### 14. Cloning Pattern

#### Patient Cloning Implementation

**Decision:** Patient implements Cloneable  
**Rationale:**
- Demonstrates cloning pattern
- Needed for creating patient copies
- Shows knowledge of clone() method
- Shallow clone sufficient for demo

```java
public Object clone() throws CloneNotSupportedException {
    return super.clone();
}
```

---

### 15. Optional Features

#### AIHelper Class

**Decision:** AI-like recommendations (optional advanced feature)  
**Rationale:**
- Demonstrates advanced features
- Not required for core functionality
- Can be easily extended
- Shows modern development thinking

Methods:
- Appointment recommendations based on history
- Doctor performance analysis
- Billing insights
- Dynamic pricing suggestions

---

## Design Patterns Used

| Pattern | Location | Purpose |
|---------|----------|---------|
| **Singleton** | IdGenerator | Centralized ID generation |
| **Immutable Object** | BillSummary | Thread-safe snapshots |
| **Strategy** | Validator | Flexible validation |
| **Facade** | Service Classes | Simplified complex operations |
| **Generic** | DataStore<T> | Type-safe containers |
| **DAO Pattern** | DataStore<T> | Data access abstraction |
| **Factory** | Services | Create entities |

---

## Trade-offs & Justifications

| Decision | Pro | Con | Justification |
|----------|-----|-----|---------------|
| **In-Memory Storage** | Fast, Simple | Not Persistent | Demo purposes - DB added in v2.0 |
| **Static Services** | Singleton pattern | Not injectable | Demonstrates Module pattern |
| **Custom Exceptions** | Clear intent | More classes | Better error handling |
| **No REST API** | Simpler | Not scalable | Core Java focus - API in v2.0 |
| **Console UI** | Pure Java | UX limited | Educational - Web UI in v2.0 |
| **Thread-safe DataStore** | Production ready | Slight overhead | Scalability consideration |

---

## Future Architecture Enhancements

### Version 2.0 (Planned)

1. **Database Layer**
   - JPA/Hibernate implementation
   - Replace DataStore with repository pattern

2. **Web Layer**
   - Spring Boot REST API
   - Angular/React frontend

3. **Authentication**
   - Spring Security
   - Role-based access control

4. **Messaging**
   - Email notifications
   - SMS alerts

5. **Monitoring**
   - Logging with SLF4J
   - Metrics with Micrometer

---

## Code Quality Standards

### Formatting
- Consistent indentation (4 spaces)
- Proper capitalization conventions
- Meaningful variable names

### Documentation
- JavaDoc for all public methods
- Inline comments for complex logic
- File-level comments explaining purpose

### Best Practices
- No hardcoded values (use Constants)
- Proper exception handling
- Collection usage patterns
- Null safety via defensive checks

---

## Naming Conventions

- **Classes:** PascalCase (Doctor, Patient, AppointmentService)
- **Methods:** camelCase (getDoctorById, addPatient)
- **Variables:** camelCase (doctorId, patientName)
- **Constants:** UPPER_CASE (CONSULTATION_FEE, DATE_FORMAT)
- **Packages:** lowercase (com.airtribe.meditrack)

---

## Conclusion

MediTrack 1.0 demonstrates:
- ✅ Strong OOP principles (Inheritance, Polymorphism, Encapsulation)
- ✅ SOLID design principles
- ✅ Clean code practices
- ✅ Professional Java patterns
- ✅ Scalable architecture
- ✅ Production-ready considerations

The architecture is designed to be:
- **Maintainable** - Clear separation of concerns
- **Scalable** - Layered design allows easy extension
- **Testable** - Dependency injection ready
- **Extensible** - Interfaces and abstractions for future features
