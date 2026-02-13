package main.java.com.airtribe.meditrack.entity;

import main.java.com.airtribe.meditrack.interfaces.Searchable;

public class Doctor extends Person implements Searchable {

    private Specialization specialization;
    private double consultationFee;

    public Doctor(String id, String name, int age,
                  Specialization specialization, double consultationFee) {
        super(id, name, age);
        this.specialization = specialization;
        this.consultationFee = consultationFee;
    }

    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    @Override
    public boolean matches(String keyword) {
        return name.toLowerCase().contains(keyword.toLowerCase()) ||
                specialization.name().equalsIgnoreCase(keyword);
    }
}
