package com.airtribe.meditrack;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.entity.*;
import com.airtribe.meditrack.exception.*;
import com.airtribe.meditrack.service.*;
import com.airtribe.meditrack.util.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for MediTrack application.
 * Console-based user interface for clinic management operations.
 */
public class Main {
    
    private static final Scanner scanner = new Scanner(System.in);
    private static final DoctorService doctorService = new DoctorService();
    private static final PatientService patientService = new PatientService();
    private static final AppointmentService appointmentService = 
            new AppointmentService(doctorService, patientService);
    private static boolean running = true;
    
    /**
     * Main method - entry point of the application.
     *
     * @param args command line arguments (unused)
     */
    public static void main(String[] args) {
        System.out.println("\n" + Constants.DOUBLE_DIVIDER);
        System.out.println("  Welcome to " + Constants.APP_NAME + " " + Constants.APP_VERSION);
        System.out.println("  " + Constants.APP_DESCRIPTION);
        System.out.println(Constants.DOUBLE_DIVIDER + "\n");
        
        loadSampleData();
        
        while (running) {
            displayMainMenu();
            int choice = getIntInput();
            handleMainMenuChoice(choice);
        }
        
        System.out.println("\nThank you for using " + Constants.APP_NAME + "!");
        scanner.close();
    }
    
    /**
     * Display the main menu.
     */
    private static void displayMainMenu() {
        System.out.println("\n" + Constants.MAIN_MENU_TITLE);
        System.out.println("1. Doctor Management");
        System.out.println("2. Patient Management");
        System.out.println("3. Appointment Management");
        System.out.println("4. Billing Management");
        System.out.println("5. Reports and Analytics");
        System.out.println("6. Exit");
        System.out.println(Constants.DIVIDER);
        System.out.print("Enter your choice: ");
    }
    
    /**
     * Handle main menu choices.
     *
     * @param choice the user's choice
     */
    private static void handleMainMenuChoice(int choice) {
        switch (choice) {
            case 1:
                doctorManagementMenu();
                break;
            case 2:
                patientManagementMenu();
                break;
            case 3:
                appointmentManagementMenu();
                break;
            case 4:
                billingManagementMenu();
                break;
            case 5:
                displayReports();
                break;
            case 6:
                running = false;
                break;
            default:
                System.out.println(Constants.ERROR_INVALID_INPUT);
        }
    }
    
    /**
     * Doctor Management Menu.
     */
    private static void doctorManagementMenu() {
        boolean docMenuRunning = true;
        
        while (docMenuRunning) {
            System.out.println("\n" + Constants.DOCTOR_MENU_TITLE);
            System.out.println("1. Add Doctor");
            System.out.println("2. View All Doctors");
            System.out.println("3. Search Doctor");
            System.out.println("4. Update Doctor");
            System.out.println("5. Delete Doctor");
            System.out.println("6. Back to Main Menu");
            System.out.println(Constants.DIVIDER);
            System.out.print("Enter your choice: ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    addDoctor();
                    break;
                case 2:
                    viewAllDoctors();
                    break;
                case 3:
                    searchDoctor();
                    break;
                case 4:
                    updateDoctor();
                    break;
                case 5:
                    deleteDoctor();
                    break;
                case 6:
                    docMenuRunning = false;
                    break;
                default:
                    System.out.println(Constants.ERROR_INVALID_INPUT);
            }
        }
    }
    
    /**
     * Add a new doctor.
     */
    private static void addDoctor() {
        System.out.println("\n--- Add New Doctor ---");
        try {
            System.out.print("Enter doctor name: ");
            String name = scanner.nextLine().trim();
            
            System.out.print("Enter doctor email: ");
            String email = scanner.nextLine().trim();
            
            System.out.print("Enter doctor phone: ");
            String phone = scanner.nextLine().trim();
            
            System.out.print("Enter doctor address: ");
            String address = scanner.nextLine().trim();
            
            System.out.print("Enter specialization: ");
            String specialization = scanner.nextLine().trim();
            
            System.out.print("Enter consultation fee: ");
            double consultationFee = getDoubleInput();
            
            System.out.print("Enter years of experience: ");
            int yearsOfExp = getIntInput();
            
            System.out.print("Enter available days (comma-separated, e.g., MONDAY,TUESDAY): ");
            String daysInput = scanner.nextLine().trim();
            List<String> availableDays = parseAvailableDays(daysInput);
            
            System.out.print("Enter available hours (HH:mm-HH:mm, e.g., 09:00-17:00): ");
            String availableHours = scanner.nextLine().trim();
            
            System.out.print("Enter max patients per day: ");
            int maxPatients = getIntInput();
            
            Doctor doctor = doctorService.addDoctor(name, email, phone, address,
                    specialization, consultationFee, yearsOfExp, availableDays, availableHours, maxPatients);
            
            System.out.println("\n✓ Doctor added successfully!");
            System.out.println("Doctor ID: " + doctor.getId());
            System.out.println(doctor);
            
        } catch (InvalidDataException e) {
            System.out.println("\n✗ Error: " + e.getMessage());
        }
    }
    
    /**
     * View all doctors.
     */
    private static void viewAllDoctors() {
        System.out.println("\n--- All Doctors ---");
        List<Doctor> doctors = doctorService.getAllDoctors();
        
        if (doctors.isEmpty()) {
            System.out.println("No doctors found.");
            return;
        }
        
        doctors.forEach(doctor -> {
            System.out.println(Constants.DIVIDER);
            System.out.println(doctor);
        });
    }
    
    /**
     * Search for a doctor.
     */
    private static void searchDoctor() {
        System.out.println("\n--- Search Doctor ---");
        System.out.print("Enter search criteria (name/email/phone/specialization): ");
        String criteria = scanner.nextLine().trim();
        
        List<Doctor> results = doctorService.searchDoctors(criteria);
        
        if (results.isEmpty()) {
            System.out.println("No doctors found matching: " + criteria);
            return;
        }
        
        System.out.println("\nSearch Results:");
        results.forEach(doctor -> {
            System.out.println(Constants.DIVIDER);
            System.out.println(doctor);
        });
    }
    
    /**
     * Update a doctor's information.
     */
    private static void updateDoctor() {
        System.out.println("\n--- Update Doctor ---");
        System.out.print("Enter doctor ID: ");
        long doctorId = getLongInput();
        
        try {
            Doctor doctor = doctorService.getDoctorById(doctorId);
            System.out.println("Current info: " + doctor);
            
            System.out.print("Enter new name (or press Enter to keep current): ");
            String name = scanner.nextLine().trim();
            if (!name.isEmpty()) doctor.setName(name);
            
            System.out.print("Enter new email (or press Enter to keep current): ");
            String email = scanner.nextLine().trim();
            if (!email.isEmpty()) doctor.setEmail(email);
            
            System.out.print("Enter new consultation fee (or -1 to keep current): ");
            double fee = getDoubleInput();
            if (fee > 0) doctor.setConsultationFee(fee);
            
            doctorService.updateDoctor(doctorId, doctor);
            System.out.println("✓ Doctor updated successfully!");
            
        } catch (DoctorNotFoundException e) {
            System.out.println("✗ " + e.getMessage());
        }
    }
    
    /**
     * Delete a doctor.
     */
    private static void deleteDoctor() {
        System.out.println("\n--- Delete Doctor ---");
        System.out.print("Enter doctor ID: ");
        long doctorId = getLongInput();
        
        try {
            doctorService.deleteDoctor(doctorId);
            System.out.println("✓ Doctor deleted successfully!");
        } catch (DoctorNotFoundException e) {
            System.out.println("✗ " + e.getMessage());
        }
    }
    
    /**
     * Patient Management Menu.
     */
    private static void patientManagementMenu() {
        boolean patMenuRunning = true;
        
        while (patMenuRunning) {
            System.out.println("\n" + Constants.PATIENT_MENU_TITLE);
            System.out.println("1. Add Patient");
            System.out.println("2. View All Patients");
            System.out.println("3. Search Patient");
            System.out.println("4. Update Patient");
            System.out.println("5. Delete Patient");
            System.out.println("6. Back to Main Menu");
            System.out.println(Constants.DIVIDER);
            System.out.print("Enter your choice: ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    addPatient();
                    break;
                case 2:
                    viewAllPatients();
                    break;
                case 3:
                    searchPatient();
                    break;
                case 4:
                    updatePatient();
                    break;
                case 5:
                    deletePatient();
                    break;
                case 6:
                    patMenuRunning = false;
                    break;
                default:
                    System.out.println(Constants.ERROR_INVALID_INPUT);
            }
        }
    }
    
    /**
     * Add a new patient.
     */
    private static void addPatient() {
        System.out.println("\n--- Add New Patient ---");
        try {
            System.out.print("Enter patient name: ");
            String name = scanner.nextLine().trim();
            
            System.out.print("Enter patient email: ");
            String email = scanner.nextLine().trim();
            
            System.out.print("Enter patient phone: ");
            String phone = scanner.nextLine().trim();
            
            System.out.print("Enter patient address: ");
            String address = scanner.nextLine().trim();
            
            System.out.print("Enter patient age: ");
            int age = getIntInput();
            
            System.out.print("Enter patient gender (MALE/FEMALE/OTHER): ");
            String gender = scanner.nextLine().trim().toUpperCase();
            
            System.out.print("Enter blood group (A+/A-/B+/B-/AB+/AB-/O+/O-): ");
            String bloodGroup = scanner.nextLine().trim().toUpperCase();
            
            System.out.print("Enter medical history (or press Enter to skip): ");
            String medicalHistory = scanner.nextLine().trim();
            
            System.out.print("Is senior citizen (Y/N): ");
            boolean isSeniorCitizen = scanner.nextLine().trim().equalsIgnoreCase("Y");
            
            Patient patient = patientService.addPatient(name, email, phone, address,
                    age, gender, bloodGroup, medicalHistory, isSeniorCitizen);
            
            System.out.println("\n✓ Patient added successfully!");
            System.out.println("Patient ID: " + patient.getId());
            System.out.println(patient);
            
        } catch (InvalidDataException e) {
            System.out.println("\n✗ Error: " + e.getMessage());
        }
    }
    
    /**
     * View all patients.
     */
    private static void viewAllPatients() {
        System.out.println("\n--- All Patients ---");
        List<Patient> patients = patientService.getAllPatients();
        
        if (patients.isEmpty()) {
            System.out.println("No patients found.");
            return;
        }
        
        patients.forEach(patient -> {
            System.out.println(Constants.DIVIDER);
            System.out.println(patient);
        });
    }
    
    /**
     * Search for a patient.
     */
    private static void searchPatient() {
        System.out.println("\n--- Search Patient ---");
        System.out.print("Enter search criteria (name/email/phone/blood group): ");
        String criteria = scanner.nextLine().trim();
        
        List<Patient> results = patientService.searchPatients(criteria);
        
        if (results.isEmpty()) {
            System.out.println("No patients found matching: " + criteria);
            return;
        }
        
        System.out.println("\nSearch Results:");
        results.forEach(patient -> {
            System.out.println(Constants.DIVIDER);
            System.out.println(patient);
        });
    }
    
    /**
     * Update a patient's information.
     */
    private static void updatePatient() {
        System.out.println("\n--- Update Patient ---");
        System.out.print("Enter patient ID: ");
        long patientId = getLongInput();
        
        try {
            Patient patient = patientService.getPatientById(patientId);
            System.out.println("Current info: " + patient);
            
            System.out.print("Enter new name (or press Enter to keep current): ");
            String name = scanner.nextLine().trim();
            if (!name.isEmpty()) patient.setName(name);
            
            System.out.print("Enter new email (or press Enter to keep current): ");
            String email = scanner.nextLine().trim();
            if (!email.isEmpty()) patient.setEmail(email);
            
            System.out.print("Enter new medical history (or press Enter to keep current): ");
            String medicalHistory = scanner.nextLine().trim();
            if (!medicalHistory.isEmpty()) patient.setMedicalHistory(medicalHistory);
            
            patientService.updatePatient(patientId, patient);
            System.out.println("✓ Patient updated successfully!");
            
        } catch (PatientNotFoundException e) {
            System.out.println("✗ " + e.getMessage());
        }
    }
    
    /**
     * Delete a patient.
     */
    private static void deletePatient() {
        System.out.println("\n--- Delete Patient ---");
        System.out.print("Enter patient ID: ");
        long patientId = getLongInput();
        
        try {
            patientService.deletePatient(patientId);
            System.out.println("✓ Patient deleted successfully!");
        } catch (PatientNotFoundException e) {
            System.out.println("✗ " + e.getMessage());
        }
    }
    
    /**
     * Appointment Management Menu.
     */
    private static void appointmentManagementMenu() {
        boolean appMenuRunning = true;
        
        while (appMenuRunning) {
            System.out.println("\n" + Constants.APPOINTMENT_MENU_TITLE);
            System.out.println("1. Book Appointment");
            System.out.println("2. View All Appointments");
            System.out.println("3. View Appointments by Doctor");
            System.out.println("4. View Appointments by Patient");
            System.out.println("5. Complete Appointment");
            System.out.println("6. Cancel Appointment");
            System.out.println("7. Reschedule Appointment");
            System.out.println("8. Back to Main Menu");
            System.out.println(Constants.DIVIDER);
            System.out.print("Enter your choice: ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    bookAppointment();
                    break;
                case 2:
                    viewAllAppointments();
                    break;
                case 3:
                    viewAppointmentsByDoctor();
                    break;
                case 4:
                    viewAppointmentsByPatient();
                    break;
                case 5:
                    completeAppointment();
                    break;
                case 6:
                    cancelAppointment();
                    break;
                case 7:
                    rescheduleAppointment();
                    break;
                case 8:
                    appMenuRunning = false;
                    break;
                default:
                    System.out.println(Constants.ERROR_INVALID_INPUT);
            }
        }
    }
    
    /**
     * Book a new appointment.
     */
    private static void bookAppointment() {
        System.out.println("\n--- Book Appointment ---");
        try {
            System.out.print("Enter doctor ID: ");
            long doctorId = getLongInput();
            
            System.out.print("Enter patient ID: ");
            long patientId = getLongInput();
            
            System.out.print("Enter appointment date (dd/MM/yyyy): ");
            String dateString = scanner.nextLine().trim();
            
            System.out.print("Enter appointment time (HH:mm): ");
            String timeString = scanner.nextLine().trim();
            
            LocalDateTime appointmentDateTime = DateUtil.combineDateAndTime(dateString, timeString);
            
            System.out.print("Enter reason for appointment: ");
            String reason = scanner.nextLine().trim();
            
            Appointment appointment = appointmentService.bookAppointment(
                    doctorId, patientId, appointmentDateTime, reason);
            
            System.out.println("\n✓ Appointment booked successfully!");
            System.out.println("Appointment ID: " + appointment.getId());
            System.out.println("Scheduled for: " + DateUtil.formatDateTime(appointment.getAppointmentDateTime()));
            System.out.println("Consultation Fee: ₹" + appointment.getConsultationFee());
            
        } catch (DateTimeParseException | InvalidDataException | DoctorNotFoundException | 
                 PatientNotFoundException e) {
            System.out.println("\n✗ Error: " + e.getMessage());
        }
    }
    
    /**
     * View all appointments.
     */
    private static void viewAllAppointments() {
        System.out.println("\n--- All Appointments ---");
        List<Appointment> appointments = appointmentService.getAllAppointments();
        
        if (appointments.isEmpty()) {
            System.out.println("No appointments found.");
            return;
        }
        
        appointments.forEach(appointment -> {
            System.out.println(Constants.DIVIDER);
            System.out.println(appointment);
        });
    }
    
    /**
     * View appointments for a doctor.
     */
    private static void viewAppointmentsByDoctor() {
        System.out.println("\n--- View Appointments by Doctor ---");
        System.out.print("Enter doctor ID: ");
        long doctorId = getLongInput();
        
        try {
            List<Appointment> appointments = appointmentService.getAppointmentsByDoctor(doctorId);
            
            if (appointments.isEmpty()) {
                System.out.println("No appointments found for this doctor.");
                return;
            }
            
            System.out.println("\nAppointments for Doctor ID: " + doctorId);
            appointments.forEach(appointment -> {
                System.out.println(Constants.DIVIDER);
                System.out.println(appointment);
            });
            
        } catch (DoctorNotFoundException e) {
            System.out.println("✗ " + e.getMessage());
        }
    }
    
    /**
     * View appointments for a patient.
     */
    private static void viewAppointmentsByPatient() {
        System.out.println("\n--- View Appointments by Patient ---");
        System.out.print("Enter patient ID: ");
        long patientId = getLongInput();
        
        try {
            List<Appointment> appointments = appointmentService.getAppointmentsByPatient(patientId);
            
            if (appointments.isEmpty()) {
                System.out.println("No appointments found for this patient.");
                return;
            }
            
            System.out.println("\nAppointments for Patient ID: " + patientId);
            appointments.forEach(appointment -> {
                System.out.println(Constants.DIVIDER);
                System.out.println(appointment);
            });
            
        } catch (PatientNotFoundException e) {
            System.out.println("✗ " + e.getMessage());
        }
    }
    
    /**
     * Complete an appointment.
     */
    private static void completeAppointment() {
        System.out.println("\n--- Complete Appointment ---");
        System.out.print("Enter appointment ID: ");
        long appointmentId = getLongInput();
        
        try {
            System.out.print("Enter notes (or press Enter to skip): ");
            String notes = scanner.nextLine().trim();
            
            appointmentService.completeAppointment(appointmentId, notes);
            System.out.println("✓ Appointment marked as completed!");
            
        } catch (AppointmentNotFoundException e) {
            System.out.println("✗ " + e.getMessage());
        }
    }
    
    /**
     * Cancel an appointment.
     */
    private static void cancelAppointment() {
        System.out.println("\n--- Cancel Appointment ---");
        System.out.print("Enter appointment ID: ");
        long appointmentId = getLongInput();
        
        try {
            appointmentService.cancelAppointment(appointmentId);
            System.out.println("✓ Appointment cancelled successfully!");
        } catch (AppointmentNotFoundException e) {
            System.out.println("✗ " + e.getMessage());
        }
    }
    
    /**
     * Reschedule an appointment.
     */
    private static void rescheduleAppointment() {
        System.out.println("\n--- Reschedule Appointment ---");
        System.out.print("Enter appointment ID: ");
        long appointmentId = getLongInput();
        
        try {
            System.out.print("Enter new appointment date (dd/MM/yyyy): ");
            String dateString = scanner.nextLine().trim();
            
            System.out.print("Enter new appointment time (HH:mm): ");
            String timeString = scanner.nextLine().trim();
            
            LocalDateTime newDateTime = DateUtil.combineDateAndTime(dateString, timeString);
            appointmentService.rescheduleAppointment(appointmentId, newDateTime);
            
            System.out.println("✓ Appointment rescheduled successfully!");
            System.out.println("New time: " + DateUtil.formatDateTime(newDateTime));
            
        } catch (DateTimeParseException | InvalidDataException | AppointmentNotFoundException e) {
            System.out.println("✗ " + e.getMessage());
        }
    }
    
    /**
     * Billing Management Menu.
     */
    private static void billingManagementMenu() {
        System.out.println("\n" + Constants.BILLING_MENU_TITLE);
        System.out.println("Note: Billing is automatically generated from appointments.");
        System.out.println("Consultation fees are shown when appointments are booked.");
        System.out.println("\n1. View Upcoming Appointments (with fees)");
        System.out.println("2. Calculate Estimated Monthly Revenue");
        System.out.println("3. Back to Main Menu");
        System.out.println(Constants.DIVIDER);
        System.out.print("Enter your choice: ");
        
        int choice = getIntInput();
        
        switch (choice) {
            case 1:
                viewUpcomingAppointmentsWithFees();
                break;
            case 2:
                calculateMonthlyRevenue();
                break;
            case 3:
                break;
            default:
                System.out.println(Constants.ERROR_INVALID_INPUT);
        }
    }
    
    /**
     * View upcoming appointments with fees.
     */
    private static void viewUpcomingAppointmentsWithFees() {
        System.out.println("\n--- Upcoming Appointments with Fees ---");
        List<Appointment> appointments = appointmentService.getUpcomingAppointments();
        
        if (appointments.isEmpty()) {
            System.out.println("No upcoming appointments.");
            return;
        }
        
        double totalFees = 0;
        for (Appointment appointment : appointments) {
            System.out.println(Constants.DIVIDER);
            System.out.println("Appointment ID: " + appointment.getId());
            System.out.println("Doctor ID: " + appointment.getDoctorId());
            System.out.println("Patient ID: " + appointment.getPatientId());
            System.out.println("Date & Time: " + DateUtil.formatDateTime(appointment.getAppointmentDateTime()));
            System.out.println("Reason: " + appointment.getReason());
            System.out.println("Consultation Fee: ₹" + appointment.getConsultationFee());
            totalFees += appointment.getConsultationFee();
        }
        
        System.out.println(Constants.DIVIDER);
        System.out.println("Total Expected Revenue: ₹" + totalFees);
    }
    
    /**
     * Calculate monthly revenue.
     */
    private static void calculateMonthlyRevenue() {
        System.out.println("\n--- Monthly Revenue Calculation ---");
        List<Appointment> allAppointments = appointmentService.getAllAppointments();
        
        double completedTotal = allAppointments.stream()
                .filter(a -> Constants.APPOINTMENT_STATUS_COMPLETED.equals(a.getStatus()))
                .mapToDouble(Appointment::getConsultationFee)
                .sum();
        
        double scheduledTotal = allAppointments.stream()
                .filter(a -> Constants.APPOINTMENT_STATUS_SCHEDULED.equals(a.getStatus()))
                .mapToDouble(Appointment::getConsultationFee)
                .sum();
        
        System.out.println("Completed Appointments Revenue: ₹" + completedTotal);
        System.out.println("Scheduled Appointments (Expected): ₹" + scheduledTotal);
        System.out.println("Total Expected Revenue: ₹" + (completedTotal + scheduledTotal));
    }
    
    /**
     * Display reports and analytics.
     */
    private static void displayReports() {
        System.out.println("\n--- System Reports & Analytics ---");
        System.out.println(Constants.DIVIDER);
        System.out.println("Total Doctors: " + doctorService.getTotalDoctorsCount());
        System.out.println("Total Patients: " + patientService.getTotalPatientsCount());
        System.out.println("Total Appointments: " + appointmentService.getTotalAppointmentsCount());
        System.out.println(Constants.DIVIDER);
        
        List<Appointment> upcomingAppointments = appointmentService.getUpcomingAppointments();
        System.out.println("Upcoming Appointments: " + upcomingAppointments.size());
        
        if (!upcomingAppointments.isEmpty()) {
            System.out.println("\nNext 5 Upcoming Appointments:");
            upcomingAppointments.stream()
                    .limit(5)
                    .forEach(app -> System.out.println("  - Appointment ID: " + app.getId() + 
                            " | Date: " + DateUtil.formatDateTime(app.getAppointmentDateTime())));
        }
    }
    
    /**
     * Load sample data for demonstration.
     */
    private static void loadSampleData() {
        try {
            System.out.println("Loading sample data...\n");
            
            // Add sample doctors
            List<String> monWedFri = new ArrayList<>();
            monWedFri.add("MONDAY");
            monWedFri.add("WEDNESDAY");
            monWedFri.add("FRIDAY");
            
            List<String> tueThuSat = new ArrayList<>();
            tueThuSat.add("TUESDAY");
            tueThuSat.add("THURSDAY");
            tueThuSat.add("SATURDAY");
            
            Doctor doctor1 = doctorService.addDoctor("Dr. Rajesh Kumar", "rajesh@clinic.com", 
                    "9876543210", "123 Medical Plaza", "Cardiology", 800, 15, monWedFri, "09:00-17:00", 20);
            
            Doctor doctor2 = doctorService.addDoctor("Dr. Priya Sharma", "priya@clinic.com", 
                    "9876543211", "456 Health Center", "Orthopedics", 600, 10, tueThuSat, "10:00-18:00", 15);
            
            Doctor doctor3 = doctorService.addDoctor("Dr. Anil Patel", "anil@clinic.com", 
                    "9876543212", "789 Care Hospital", "General Practice", 500, 8, monWedFri, "08:00-16:00", 25);
            
            // Add sample patients
            Patient patient1 = patientService.addPatient("Amit Singh", "amit@email.com", 
                    "9988776655", "101 Park Avenue", 45, "MALE", "O+", "Hypertension", false);
            
            Patient patient2 = patientService.addPatient("Neha Verma", "neha@email.com", 
                    "9988776656", "202 Garden Lane", 38, "FEMALE", "B+", "None", false);
            
            Patient patient3 = patientService.addPatient("Rajesh Gupta", "rajesh.g@email.com", 
                    "9988776657", "303 Lake View", 72, "MALE", "AB+", "Diabetes", true);
            
            // Add sample appointments
            LocalDateTime apptTime1 = LocalDateTime.now().plusDays(3).withHour(10).withMinute(0);
            LocalDateTime apptTime2 = LocalDateTime.now().plusDays(5).withHour(14).withMinute(30);
            LocalDateTime apptTime3 = LocalDateTime.now().plusDays(7).withHour(11).withMinute(15);
            
            Appointment appt1 = appointmentService.bookAppointment(doctor1.getId(), patient1.getId(), 
                    apptTime1, "Cardiac checkup");
            
            Appointment appt2 = appointmentService.bookAppointment(doctor2.getId(), patient2.getId(), 
                    apptTime2, "Knee pain consultation");
            
            Appointment appt3 = appointmentService.bookAppointment(doctor3.getId(), patient3.getId(), 
                    apptTime3, "Regular checkup");
            
            System.out.println("✓ Sample data loaded successfully!\n");
            System.out.println("Sample Doctors added: " + doctorService.getTotalDoctorsCount());
            System.out.println("Sample Patients added: " + patientService.getTotalPatientsCount());
            System.out.println("Sample Appointments booked: " + appointmentService.getTotalAppointmentsCount());
            System.out.println();
            
        } catch (Exception e) {
            System.out.println("Error loading sample data: " + e.getMessage());
        }
    }
    
    /**
     * Parse available days from user input.
     *
     * @param input comma-separated days
     * @return list of days
     */
    private static List<String> parseAvailableDays(String input) {
        List<String> days = new ArrayList<>();
        if (!input.isEmpty()) {
            String[] dayArray = input.split(",");
            for (String day : dayArray) {
                days.add(day.trim().toUpperCase());
            }
        }
        return days;
    }
    
    /**
     * Get integer input from user.
     *
     * @return the integer value
     */
    private static int getIntInput() {
        try {
            int value = Integer.parseInt(scanner.nextLine().trim());
            return value;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid integer.");
            return -1;
        }
    }
    
    /**
     * Get long input from user.
     *
     * @return the long value
     */
    private static long getLongInput() {
        try {
            return Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid ID.");
            return -1;
        }
    }
    
    /**
     * Get double input from user.
     *
     * @return the double value
     */
    private static double getDoubleInput() {
        try {
            return Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            return -1.0;
        }
    }
}
