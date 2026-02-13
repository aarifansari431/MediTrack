package main.java.com.airtribe.meditrack.service;

import main.java.com.airtribe.meditrack.entity.Patient;
import main.java.com.airtribe.meditrack.exception.InvalidDataException;
import main.java.com.airtribe.meditrack.util.DataStore;

import java.util.List;
import java.util.stream.Collectors;

public class PatientService {

    private DataStore<Patient> store = new DataStore<>();

    // CREATE
    public void addPatient(Patient p) {
        store.save(p.getId(), p);
    }

    // READ
    public Patient getPatient(String id) {
        Patient p = store.get(id);
        if (p == null) {
            throw new InvalidDataException("Patient not found: " + id);
        }
        return p;
    }

    // UPDATE (EDIT)
    public void updatePatient(String id, String name, int age) {
        Patient p = getPatient(id);
        p.setName(name);
        p.setAge(age);
    }

    // DELETE
    public void deletePatient(String id) {
        if (store.get(id) == null) {
            throw new InvalidDataException("Cannot delete. Patient not found");
        }
        store.delete(id);
    }

    // SEARCH overloads
    public List<Patient> search(String name) {
        return store.getAll().stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }

    public List<Patient> search(int age) {
        return store.getAll().stream()
                .filter(p -> p.getAge() == age)
                .collect(Collectors.toList());
    }
}
