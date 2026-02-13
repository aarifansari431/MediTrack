package com.airtribe.meditrack.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Appointment entity representing a scheduled appointment between doctor and patient.
 */
public class Appointment implements Serializable, Comparable<Appointment> {
    
    private static final long serialVersionUID = 1L;
    private long id;
    private long doctorId;
    private long patientId;
    private LocalDateTime appointmentDateTime;
    private String reason;
    private String status; // SCHEDULED, COMPLETED, CANCELLED
    private String notes;
    private double consultationFee;
    
    /**
     * Constructor for Appointment.
     *
     * @param id                    the unique identifier
     * @param doctorId              the doctor's ID
     * @param patientId             the patient's ID
     * @param appointmentDateTime   the appointment date and time
     * @param reason                the reason for the appointment
     * @param status                the appointment status
     * @param consultationFee       the consultation fee
     */
    public Appointment(long id, long doctorId, long patientId, LocalDateTime appointmentDateTime,
                       String reason, String status, double consultationFee) {
        this.id = id;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.appointmentDateTime = appointmentDateTime;
        this.reason = reason;
        this.status = status;
        this.consultationFee = consultationFee;
        this.notes = "";
    }
    
    // Getters and Setters
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public long getDoctorId() {
        return doctorId;
    }
    
    public void setDoctorId(long doctorId) {
        this.doctorId = doctorId;
    }
    
    public long getPatientId() {
        return patientId;
    }
    
    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }
    
    public LocalDateTime getAppointmentDateTime() {
        return appointmentDateTime;
    }
    
    public void setAppointmentDateTime(LocalDateTime appointmentDateTime) {
        this.appointmentDateTime = appointmentDateTime;
    }
    
    public String getReason() {
        return reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public double getConsultationFee() {
        return consultationFee;
    }
    
    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
    }
    
    @Override
    public int compareTo(Appointment other) {
        return this.appointmentDateTime.compareTo(other.appointmentDateTime);
    }
    
    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", doctorId=" + doctorId +
                ", patientId=" + patientId +
                ", appointmentDateTime=" + appointmentDateTime +
                ", reason='" + reason + '\'' +
                ", status='" + status + '\'' +
                ", consultationFee=" + consultationFee +
                ", notes='" + notes + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return id == that.id;
    }
    
    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }
}
