package com.airtribe.meditrack.entity;

import java.io.Serializable;

/**
 * Abstract base class representing a Person in the system.
 * Common attributes for Doctor and Patient.
 */
public abstract class Person implements Serializable {
    
    private static final long serialVersionUID = 1L;
    protected long id;
    protected String name;
    protected String email;
    protected String phone;
    protected String address;
    protected String status;
    
    /**
     * Constructor for Person.
     *
     * @param id      the unique identifier
     * @param name    the person's name
     * @param email   the person's email
     * @param phone   the person's phone number
     * @param address the person's address
     * @param status  the person's status
     */
    protected Person(long id, String name, String email, String phone, String address, String status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.status = status;
    }
    
    // Getters and Setters
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id;
    }
    
    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }
}
