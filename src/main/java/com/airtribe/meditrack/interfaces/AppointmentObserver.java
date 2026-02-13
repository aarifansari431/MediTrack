package main.java.com.airtribe.meditrack.interfaces;

import main.java.com.airtribe.meditrack.entity.Appointment;

public interface AppointmentObserver {
    void notify(Appointment appointment);
}
