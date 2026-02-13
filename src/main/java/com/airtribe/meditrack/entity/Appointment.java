package main.java.com.airtribe.meditrack.entity;

import java.time.LocalDate;

public class Appointment implements Cloneable {

    private Patient patient;
    private Doctor doctor;
    private LocalDate appointmentDate;
    private AppointmentStatus status;

    public Appointment(Patient patient, Doctor doctor, LocalDate date) {
        this.patient = patient.clone(); // deep copy
        this.doctor = doctor;
        this.appointmentDate = date;
        this.status = AppointmentStatus.PENDING;
    }

    public void confirm() {
        this.status = AppointmentStatus.CONFIRMED;
    }

    public void cancel() {
        this.status = AppointmentStatus.CANCELLED;
    }

    // ✅ RESCHEDULE
    public void reschedule(LocalDate newDate) {
        if (status == AppointmentStatus.CANCELLED) {
            throw new IllegalStateException("Cannot reschedule cancelled appointment");
        }
        this.appointmentDate = newDate;
        this.status = AppointmentStatus.PENDING;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }
}
