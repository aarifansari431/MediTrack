package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.exception.DoctorNotFoundException;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.exception.PatientNotFoundException;
import com.airtribe.meditrack.util.*;
import com.airtribe.meditrack.constants.Constants;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * AppointmentService handles all appointment-related operations.
 * Implements CRUD operations, validation, and scheduling logic.
 */
public class AppointmentService {
    
    private final DataStore<Appointment> appointmentStore;
    private final DoctorService doctorService;
    private final PatientService patientService;
    
    /**
     * Constructor for AppointmentService.
     *
     * @param doctorService  the doctor service
     * @param patientService the patient service
     */
    public AppointmentService(DoctorService doctorService, PatientService patientService) {
        this.appointmentStore = new DataStore<>("AppointmentStore");
        this.doctorService = doctorService;
        this.patientService = patientService;
    }
    
    /**
     * Book a new appointment.
     *
     * @param doctorId          the doctor's ID
     * @param patientId         the patient's ID
     * @param appointmentDateTime the appointment date and time
     * @param reason            the reason for the appointment
     * @return the booked appointment
     * @throws InvalidDataException if any validation fails
     * @throws DoctorNotFoundException if doctor not found
     * @throws PatientNotFoundException if patient not found
     */
    public Appointment bookAppointment(long doctorId, long patientId, LocalDateTime appointmentDateTime,
                                       String reason) throws InvalidDataException, DoctorNotFoundException,
                                       PatientNotFoundException {
        
        // Validate inputs
        if (Validator.isNullOrEmpty(reason)) {
            throw new InvalidDataException("Appointment reason cannot be empty");
        }
        
        // Check if datetime is in the future
        if (DateUtil.isDateTimeInPast(appointmentDateTime)) {
            throw new InvalidDataException("Appointment cannot be scheduled in the past");
        }
        
        // Get doctor and validate
        Doctor doctor = doctorService.getDoctorById(doctorId);
        if (!"ACTIVE".equals(doctor.getStatus())) {
            throw new InvalidDataException("Doctor is not active");
        }
        
        // Get patient and validate
        Patient patient = patientService.getPatientById(patientId);
        if (!"ACTIVE".equals(patient.getStatus())) {
            throw new InvalidDataException("Patient is not active");
        }
        
        // Check if doctor is available on the day
        String dayName = getDayName(appointmentDateTime);
        if (!doctor.isAvailableOnDay(dayName)) {
            throw new InvalidDataException("Doctor is not available on " + dayName);
        }
        
        // Check for slot availability
        int appointmentsOnDay = getAppointmentsCountOnDay(doctorId, appointmentDateTime);
        if (appointmentsOnDay >= doctor.getMaxPatientsPerDay()) {
            throw new InvalidDataException("No available slots for this doctor on the selected date");
        }
        
        // Check for duplicate appointment
        if (hasExistingAppointment(doctorId, patientId, appointmentDateTime)) {
            throw new InvalidDataException("Patient already has an appointment with this doctor at this time");
        }
        
        long appointmentId = IdGenerator.generateAppointmentId();
        Appointment appointment = new Appointment(appointmentId, doctorId, patientId,
                appointmentDateTime, reason, Constants.APPOINTMENT_STATUS_SCHEDULED,
                doctor.getConsultationFee());
        
        appointmentStore.add(appointment);
        return appointment;
    }
    
    /**
     * Get an appointment by ID.
     *
     * @param appointmentId the appointment's ID
     * @return the appointment
     * @throws AppointmentNotFoundException if appointment not found
     */
    public Appointment getAppointmentById(long appointmentId) throws AppointmentNotFoundException {
        for (Appointment appointment : appointmentStore.getAll()) {
            if (appointment.getId() == appointmentId) {
                return appointment;
            }
        }
        throw new AppointmentNotFoundException("Appointment with ID " + appointmentId + " not found");
    }
    
    /**
     * Get all appointments.
     *
     * @return list of all appointments
     */
    public List<Appointment> getAllAppointments() {
        return appointmentStore.getAll();
    }
    
    /**
     * Get appointments for a patient.
     *
     * @param patientId the patient's ID
     * @return list of appointments for the patient
     * @throws PatientNotFoundException if patient not found
     */
    public List<Appointment> getAppointmentsByPatient(long patientId) throws PatientNotFoundException {
        patientService.getPatientById(patientId); // Validate patient exists
        return appointmentStore.filter(a -> a.getPatientId() == patientId);
    }
    
    /**
     * Get appointments for a doctor.
     *
     * @param doctorId the doctor's ID
     * @return list of appointments for the doctor
     * @throws DoctorNotFoundException if doctor not found
     */
    public List<Appointment> getAppointmentsByDoctor(long doctorId) throws DoctorNotFoundException {
        doctorService.getDoctorById(doctorId); // Validate doctor exists
        return appointmentStore.filter(a -> a.getDoctorId() == doctorId);
    }
    
    /**
     * Update appointment status to completed.
     *
     * @param appointmentId the appointment's ID
     * @param notes         notes about the appointment
     * @throws AppointmentNotFoundException if appointment not found
     */
    public void completeAppointment(long appointmentId, String notes) throws AppointmentNotFoundException {
        Appointment appointment = getAppointmentById(appointmentId);
        appointment.setStatus(Constants.APPOINTMENT_STATUS_COMPLETED);
        if (notes != null && !notes.isEmpty()) {
            appointment.setNotes(notes);
        }
    }
    
    /**
     * Cancel an appointment.
     *
     * @param appointmentId the appointment's ID
     * @throws AppointmentNotFoundException if appointment not found
     */
    public void cancelAppointment(long appointmentId) throws AppointmentNotFoundException {
        Appointment appointment = getAppointmentById(appointmentId);
        if (Constants.APPOINTMENT_STATUS_COMPLETED.equals(appointment.getStatus())) {
            throw new AppointmentNotFoundException("Cannot cancel a completed appointment");
        }
        appointment.setStatus(Constants.APPOINTMENT_STATUS_CANCELLED);
    }
    
    /**
     * Reschedule an appointment.
     *
     * @param appointmentId      the appointment's ID
     * @param newAppointmentDateTime the new appointment date and time
     * @throws AppointmentNotFoundException if appointment not found
     * @throws InvalidDataException if rescheduling fails
     */
    public void rescheduleAppointment(long appointmentId, LocalDateTime newAppointmentDateTime)
            throws AppointmentNotFoundException, InvalidDataException {
        
        Appointment appointment = getAppointmentById(appointmentId);
        
        if (Constants.APPOINTMENT_STATUS_COMPLETED.equals(appointment.getStatus()) ||
            Constants.APPOINTMENT_STATUS_CANCELLED.equals(appointment.getStatus())) {
            throw new InvalidDataException("Cannot reschedule a " + appointment.getStatus() + " appointment");
        }
        
        if (DateUtil.isDateTimeInPast(newAppointmentDateTime)) {
            throw new InvalidDataException("Cannot reschedule to a past date");
        }
        
        appointment.setAppointmentDateTime(newAppointmentDateTime);
    }
    
    /**
     * Get all upcoming appointments.
     *
     * @return sorted list of upcoming appointments
     */
    public List<Appointment> getUpcomingAppointments() {
        return appointmentStore.filter(a -> !a.getAppointmentDateTime().isBefore(LocalDateTime.now()) 
                && Constants.APPOINTMENT_STATUS_SCHEDULED.equals(a.getStatus()))
                .stream()
                .sorted()
                .collect(Collectors.toList());
    }
    
    /**
     * Check if there are existing appointments on a specific time slot.
     *
     * @param doctorId the doctor's ID
     * @param patientId the patient's ID
     * @param appointmentDateTime the appointment time
     * @return true if appointment exists, false otherwise
     */
    private boolean hasExistingAppointment(long doctorId, long patientId, LocalDateTime appointmentDateTime) {
        return appointmentStore.getAll().stream()
                .anyMatch(a -> a.getDoctorId() == doctorId && 
                         a.getPatientId() == patientId &&
                         !Constants.APPOINTMENT_STATUS_CANCELLED.equals(a.getStatus()) &&
                         a.getAppointmentDateTime().equals(appointmentDateTime));
    }
    
    /**
     * Get appointment count for a doctor on a specific day.
     *
     * @param doctorId the doctor's ID
     * @param dateTime the day to check
     * @return the count of appointments for that day
     */
    private int getAppointmentsCountOnDay(long doctorId, LocalDateTime dateTime) {
        LocalDateTime startOfDay = dateTime.withHour(0).withMinute(0);
        LocalDateTime endOfDay = dateTime.withHour(23).withMinute(59);
        
        return (int) appointmentStore.getAll().stream()
                .filter(a -> a.getDoctorId() == doctorId &&
                        a.getAppointmentDateTime().isAfter(startOfDay) &&
                        a.getAppointmentDateTime().isBefore(endOfDay) &&
                        !Constants.APPOINTMENT_STATUS_CANCELLED.equals(a.getStatus()))
                .count();
    }
    
    /**
     * Get the day name from appointment datetime.
     *
     * @param dateTime the datetime
     * @return the day name (e.g., MONDAY)
     */
    private String getDayName(LocalDateTime dateTime) {
        return dateTime.getDayOfWeek().toString();
    }
    
    /**
     * Get total appointments count.
     *
     * @return the count of all appointments
     */
    public int getTotalAppointmentsCount() {
        return appointmentStore.getAll().size();
    }
}
