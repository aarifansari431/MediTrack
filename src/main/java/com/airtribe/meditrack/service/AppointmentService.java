package main.java.com.airtribe.meditrack.service;

import main.java.com.airtribe.meditrack.entity.Appointment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AppointmentService {

    private List<Appointment> appointments = new ArrayList<>();

    public Appointment createAppointment(Appointment a) {
        appointments.add(a);
        return a;
    }

    public List<Appointment> viewAppointments() {
        return appointments;
    }

    public void cancelAppointment(Appointment a) {
        a.cancel();
    }

    // ✅ UPDATE / RESCHEDULE
    public void rescheduleAppointment(Appointment a, LocalDate newDate) {
        a.reschedule(newDate);
    }
}
