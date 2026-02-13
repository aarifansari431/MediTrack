package main.java.com.airtribe.meditrack.service;

import main.java.com.airtribe.meditrack.entity.Doctor;
import main.java.com.airtribe.meditrack.exception.InvalidDataException;
import main.java.com.airtribe.meditrack.util.DataStore;

import java.util.List;
import java.util.stream.Collectors;

public class DoctorService {

    private DataStore<Doctor> store = new DataStore<>();

    // CREATE
    public void addDoctor(Doctor d) {
        store.save(d.getId(), d);
    }

    // READ
    public Doctor getDoctor(String id) {
        Doctor d = store.get(id);
        if (d == null) {
            throw new InvalidDataException("Doctor not found: " + id);
        }
        return d;
    }

    // UPDATE (EDIT)
    public void updateDoctor(String id, String name, double fee) {
        Doctor d = getDoctor(id);
        d.setName(name);
        d.setConsultationFee(fee);
    }

    // DELETE
    public void deleteDoctor(String id) {
        if (store.get(id) == null) {
            throw new InvalidDataException("Cannot delete. Doctor not found");
        }
        store.delete(id);
    }

    // SEARCH (overloaded)
    public List<Doctor> search(String keyword) {
        return store.getAll().stream()
                .filter(d -> d.matches(keyword))
                .collect(Collectors.toList());
    }

    public List<Doctor> searchBySpecialization(String specialization) {
        return store.getAll().stream()
                .filter(d ->
                        d.getSpecialization().name()
                                .equalsIgnoreCase(specialization))
                .collect(Collectors.toList());
    }
}