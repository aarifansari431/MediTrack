package test;

import main.java.com.airtribe.meditrack.entity.Appointment;
import main.java.com.airtribe.meditrack.entity.Doctor;
import main.java.com.airtribe.meditrack.entity.Patient;
import main.java.com.airtribe.meditrack.entity.Specialization;

import java.time.LocalDate;

public class TestRunner {
    public static void main(String[] args) {
        Patient p1 = new Patient("P1", "Amit", 25, "Cold");
        Patient p2 = p1.clone();

        System.out.println(p1 != p2); // deep copy check

        Doctor d = new Doctor(
                "D1",
                "Dr. Sharma",
                45,
                Specialization.CARDIOLOGIST,
                800
        );

        Appointment a = new Appointment(p1, d, LocalDate.now());
        a.confirm();

        System.out.println("Appointment Status: " + a.getStatus());
    }
}
