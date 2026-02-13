package main.java.com.airtribe.meditrack.util;

import main.java.com.airtribe.meditrack.entity.Appointment;
import main.java.com.airtribe.meditrack.interfaces.AppointmentObserver;

public class ConsoleNotificationObserver implements AppointmentObserver {

    @Override
    public void notify(Appointment appointment) {
        System.out.println(
                "🔔 Appointment update: Status = " + appointment.getStatus()
        );
    }
}
