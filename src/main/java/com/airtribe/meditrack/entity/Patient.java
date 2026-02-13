package main.java.com.airtribe.meditrack.entity;

public class Patient extends Person implements Cloneable {
    private String ailment;

    public Patient(String id, String name, int age, String ailment) {
        super(id, name, age);
        this.ailment = ailment;
    }

    @Override
    public Patient clone() {
        return new Patient(this.id, this.name, this.age, this.ailment);
    }
}
