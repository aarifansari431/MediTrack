package com.airtribe.meditrack.test;

import com.airtribe.meditrack.entity.*;
import com.airtribe.meditrack.exception.*;
import com.airtribe.meditrack.service.*;
import com.airtribe.meditrack.util.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * TestRunner for manual testing of MediTrack application.
 * Demonstrates core functionality without using JUnit framework.
 */
public class TestRunner {
    
    private static int testsPassed = 0;
    private static int testsFailed = 0;
    
    /**
     * Main method to run all tests.
     *
     * @param args command line arguments (unused)
     */
    public static void main(String[] args) {
        System.out.println("===================================");
        System.out.println("  MediTrack - Manual Test Runner");
        System.out.println("===================================\n");
        
        // Initialize services
        DoctorService doctorService = new DoctorService();
        PatientService patientService = new PatientService();
        AppointmentService appointmentService = new AppointmentService(doctorService, patientService);
        
        // Run all tests
        testDoctorService(doctorService);
        testPatientService(patientService);
        testAppointmentService(appointmentService, doctorService, patientService);
        testUtilityClasses();
        testExceptions();
        
        // Print summary
        printTestSummary();
    }
    
    /**
     * Test Doctor Service operations.
     *
     * @param doctorService the doctor service to test
     */
    private static void testDoctorService(DoctorService doctorService) {
        System.out.println("\n--- Testing Doctor Service ---");
        
        try {
            // Test 1: Add Doctor
            List<String> availableDays = new ArrayList<>();
            availableDays.add("MONDAY");
            availableDays.add("WEDNESDAY");
            availableDays.add("FRIDAY");
            
            Doctor doctor = doctorService.addDoctor("Dr. Test", "test@clinic.com", "9876543210",
                    "123 Street", "Cardiology", 800, 10, availableDays, "09:00-17:00", 20);
            
            printTest("Add Doctor", doctor != null && doctor.getId() > 0);
            
            // Test 2: Get Doctor by ID
            Doctor retrieved = doctorService.getDoctorById(doctor.getId());
            printTest("Get Doctor by ID", retrieved.getName().equals("Dr. Test"));
            
            // Test 3: Get All Doctors
            List<Doctor> allDoctors = doctorService.getAllDoctors();
            printTest("Get All Doctors", allDoctors.size() > 0);
            
            // Test 4: Search Doctor
            List<Doctor> searchResults = doctorService.searchDoctors("Cardiology");
            printTest("Search Doctor", searchResults.size() > 0);
            
            // Test 5: Update Doctor
            doctor.setConsultationFee(900);
            doctorService.updateDoctor(doctor.getId(), doctor);
            Doctor updated = doctorService.getDoctorById(doctor.getId());
            printTest("Update Doctor", updated.getConsultationFee() == 900);
            
            // Test 6: Get Doctors by Specialization
            List<Doctor> docs = doctorService.getDoctorsBySpecialization("Cardiology");
            printTest("Get Doctors by Specialization", docs.size() > 0);
            
        } catch (Exception e) {
            printTest("Doctor Service Tests", false);
            System.out.println("  Error: " + e.getMessage());
        }
    }
    
    /**
     * Test Patient Service operations.
     *
     * @param patientService the patient service to test
     */
    private static void testPatientService(PatientService patientService) {
        System.out.println("\n--- Testing Patient Service ---");
        
        try {
            // Test 1: Add Patient
            Patient patient = patientService.addPatient("John Doe", "john@email.com",
                    "9988776655", "101 Lane", 45, "MALE", "O+", "Hypertension", false);
            
            printTest("Add Patient", patient != null && patient.getId() > 0);
            
            // Test 2: Get Patient by ID
            Patient retrieved = patientService.getPatientById(patient.getId());
            printTest("Get Patient by ID", retrieved.getName().equals("John Doe"));
            
            // Test 3: Get All Patients
            List<Patient> allPatients = patientService.getAllPatients();
            printTest("Get All Patients", allPatients.size() > 0);
            
            // Test 4: Search Patient
            List<Patient> searchResults = patientService.searchPatients("John");
            printTest("Search Patient", searchResults.size() > 0);
            
            // Test 5: Update Patient
            patient.setAge(46);
            patientService.updatePatient(patient.getId(), patient);
            Patient updated = patientService.getPatientById(patient.getId());
            printTest("Update Patient", updated.getAge() == 46);
            
            // Test 6: Get Patients by Blood Group
            List<Patient> patients = patientService.getPatientsByBloodGroup("O+");
            printTest("Get Patients by Blood Group", patients.size() > 0);
            
            // Test 7: Clone Patient
            Patient cloned = patientService.clonePatient(patient.getId());
            printTest("Clone Patient", cloned.getId() == patient.getId());
            
        } catch (Exception e) {
            printTest("Patient Service Tests", false);
            System.out.println("  Error: " + e.getMessage());
        }
    }
    
    /**
     * Test Appointment Service operations.
     *
     * @param appointmentService the appointment service to test
     * @param doctorService the doctor service to test
     * @param patientService the patient service to test
     */
    private static void testAppointmentService(AppointmentService appointmentService,
                                              DoctorService doctorService, PatientService patientService) {
        System.out.println("\n--- Testing Appointment Service ---");
        
        try {
            // Setup: Add doctor and patient
            List<String> availableDays = new ArrayList<>();
            availableDays.add("MONDAY");
            availableDays.add("WEDNESDAY");
            availableDays.add("FRIDAY");
            
            Doctor doctor = doctorService.addDoctor("Dr. Appointment", "apt@clinic.com", "9876543220",
                    "456 Street", "Surgery", 1000, 15, availableDays, "09:00-17:00", 10);
            
            Patient patient = patientService.addPatient("Jane Doe", "jane@email.com",
                    "9988776666", "202 Lane", 35, "FEMALE", "A+", "None", false);
            
            // Test 1: Book Appointment
            LocalDateTime apptTime = LocalDateTime.now().plusDays(3).withHour(10).withMinute(0);
            Appointment appointment = appointmentService.bookAppointment(
                    doctor.getId(), patient.getId(), apptTime, "Checkup");
            
            printTest("Book Appointment", appointment != null && appointment.getId() > 0);
            
            // Test 2: Get Appointment by ID
            Appointment retrieved = appointmentService.getAppointmentById(appointment.getId());
            printTest("Get Appointment by ID", retrieved.getReason().equals("Checkup"));
            
            // Test 3: Get Appointments by Patient
            List<Appointment> patientAppts = appointmentService.getAppointmentsByPatient(patient.getId());
            printTest("Get Appointments by Patient", patientAppts.size() > 0);
            
            // Test 4: Get Appointments by Doctor
            List<Appointment> doctorAppts = appointmentService.getAppointmentsByDoctor(doctor.getId());
            printTest("Get Appointments by Doctor", doctorAppts.size() > 0);
            
            // Test 5: Complete Appointment
            appointmentService.completeAppointment(appointment.getId(), "Completed successfully");
            Appointment completed = appointmentService.getAppointmentById(appointment.getId());
            printTest("Complete Appointment", "COMPLETED".equals(completed.getStatus()));
            
        } catch (Exception e) {
            printTest("Appointment Service Tests", false);
            System.out.println("  Error: " + e.getMessage());
        }
    }
    
    /**
     * Test utility classes.
     */
    private static void testUtilityClasses() {
        System.out.println("\n--- Testing Utility Classes ---");
        
        try {
            // Test 1: IdGenerator
            long doctorId = IdGenerator.generateDoctorId();
            printTest("IdGenerator - Generate Doctor ID", doctorId > 0);
            
            long patientId = IdGenerator.generatePatientId();
            printTest("IdGenerator - Generate Patient ID", patientId > 0);
            
            // Test 2: Validator
            try {
                Validator.validateEmail("test@email.com");
                printTest("Validator - Valid Email", true);
            } catch (InvalidDataException e) {
                printTest("Validator - Valid Email", false);
            }
            
            try {
                Validator.validateEmail("invalid-email");
                printTest("Validator - Invalid Email Detection", false);
            } catch (InvalidDataException e) {
                printTest("Validator - Invalid Email Detection", true);
            }
            
            // Test 3: DateUtil
            String formattedDate = DateUtil.formatDateTime(LocalDateTime.now());
            printTest("DateUtil - Format DateTime", formattedDate != null && !formattedDate.isEmpty());
            
            // Test 4: DataStore
            DataStore<String> store = new DataStore<>("TestStore");
            store.add("Item1");
            store.add("Item2");
            printTest("DataStore - Add Items", store.size() == 2);
            
            List<String> items = store.getAll();
            printTest("DataStore - Get All Items", items.size() == 2);
            
            // Test 5: CSVUtil
            try {
                CSVUtil.createFileIfNotExists("data/test.csv");
                printTest("CSVUtil - Create File", true);
            } catch (Exception e) {
                printTest("CSVUtil - Create File", false);
            }
            
        } catch (Exception e) {
            printTest("Utility Classes Tests", false);
            System.out.println("  Error: " + e.getMessage());
        }
    }
    
    /**
     * Test exception handling.
     */
    private static void testExceptions() {
        System.out.println("\n--- Testing Exceptions ---");
        
        try {
            // Test 1: DoctorNotFoundException
            DoctorService doctorService = new DoctorService();
            try {
                doctorService.getDoctorById(9999);
                printTest("DoctorNotFoundException - Throw", false);
            } catch (DoctorNotFoundException e) {
                printTest("DoctorNotFoundException - Throw", true);
            }
            
            // Test 2: PatientNotFoundException
            PatientService patientService = new PatientService();
            try {
                patientService.getPatientById(9999);
                printTest("PatientNotFoundException - Throw", false);
            } catch (PatientNotFoundException e) {
                printTest("PatientNotFoundException - Throw", true);
            }
            
            // Test 3: InvalidDataException
            try {
                Validator.validateEmail("invalid-email");
                printTest("InvalidDataException - Throw", false);
            } catch (InvalidDataException e) {
                printTest("InvalidDataException - Throw", true);
            }
            
        } catch (Exception e) {
            printTest("Exception Tests", false);
            System.out.println("  Error: " + e.getMessage());
        }
    }
    
    /**
     * Print test result.
     *
     * @param testName the name of the test
     * @param passed whether the test passed
     */
    private static void printTest(String testName, boolean passed) {
        if (passed) {
            System.out.println("✓ " + testName);
            testsPassed++;
        } else {
            System.out.println("✗ " + testName);
            testsFailed++;
        }
    }
    
    /**
     * Print test summary.
     */
    private static void printTestSummary() {
        System.out.println("\n===================================");
        System.out.println("          Test Summary");
        System.out.println("===================================");
        System.out.println("Tests Passed: " + testsPassed);
        System.out.println("Tests Failed: " + testsFailed);
        System.out.println("Total Tests: " + (testsPassed + testsFailed));
        
        double passPercentage = (testsPassed * 100.0) / (testsPassed + testsFailed);
        System.out.println("Pass Rate: " + String.format("%.2f", passPercentage) + "%");
        System.out.println("===================================\n");
    }
}
