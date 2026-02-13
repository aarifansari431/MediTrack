package main;

import main.java.com.airtribe.meditrack.entity.*;
import main.java.com.airtribe.meditrack.interfaces.BillingStrategy;
import main.java.com.airtribe.meditrack.service.AppointmentService;
import main.java.com.airtribe.meditrack.service.DoctorService;
import main.java.com.airtribe.meditrack.service.PatientService;
import main.java.com.airtribe.meditrack.util.IdGenerator;
import main.java.com.airtribe.meditrack.util.StandardBillingStrategy;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        DoctorService doctorService = new DoctorService();
        PatientService patientService = new PatientService();
        AppointmentService appointmentService = new AppointmentService();

        while (true) {
            System.out.println("\n--- MediTrack Menu ---");
            System.out.println("1. Add Doctor");
            System.out.println("2. Add Patient");
            System.out.println("3. Search Doctor");
            System.out.println("4. Create Appointment");
            System.out.println("5. View Appointments");
            System.out.println("6. Generate Bill");
            System.out.println("0. Exit");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1 -> {
                    String id = IdGenerator.getInstance().generateId();
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    System.out.print("Age: ");
                    int age = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Specialization: ");
                    Specialization sp =
                            Specialization.valueOf(sc.nextLine().toUpperCase());
                    System.out.print("Fee: ");
                    double fee = sc.nextDouble();

                    doctorService.addDoctor(
                            new Doctor(id, name, age, sp, fee)
                    );
                    System.out.println("Doctor added: " + id);
                }

                case 2 -> {
                    String id = IdGenerator.getInstance().generateId();
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    System.out.print("Age: ");
                    int age = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Ailment: ");
                    String ailment = sc.nextLine();

                    patientService.addPatient(
                            new Patient(id, name, age, ailment)
                    );
                    System.out.println("Patient added: " + id);
                }

                case 3 -> {
                    System.out.print("Search keyword: ");
                    String key = sc.nextLine();
                    doctorService.search(key)
                            .forEach(d -> System.out.println(d.getName()));
                }

                case 4 -> {
                    System.out.print("Patient ID: ");
                    Patient p = patientService.getPatient(sc.nextLine());

                    System.out.print("Doctor ID: ");
                    Doctor d = doctorService.getDoctor(sc.nextLine());
                    Appointment a1 = new Appointment(p,d, LocalDate.now());
                    Appointment a =
                            appointmentService.createAppointment(a1);

                    System.out.println("Appointment created. Status: "
                            + a.getStatus());
                }

                case 5 -> appointmentService.viewAppointments()
                        .forEach(a ->
                                System.out.println(a.getStatus()));

                case 6 -> {
                    System.out.print("Amount: ");
                    double amt = sc.nextDouble();

                    BillingStrategy strategy =
                            new StandardBillingStrategy();

                    Bill bill = new Bill(amt, strategy);
                    System.out.println("Total Bill: " +
                            bill.generateTotal());
                }

                case 0 -> {
                    System.out.println("Exiting MediTrack");
                    return;
                }
            }
        }
    }
}