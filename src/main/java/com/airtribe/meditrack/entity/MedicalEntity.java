package main.java.com.airtribe.meditrack.entity;

public abstract class MedicalEntity {
    protected String id;

    public String getId() {
        return id;
    }

    public abstract void displayInfo();
}
