package main.java.com.airtribe.meditrack.entity;

public class Person extends MedicalEntity {

    protected String name;
    protected int age;

    public Person(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    // Optional setters (CRUD support)
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public void displayInfo() {
        System.out.println(name + " (" + age + ")");
    }
}