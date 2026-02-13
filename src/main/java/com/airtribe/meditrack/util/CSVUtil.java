package main.java.com.airtribe.meditrack.util;

import main.java.com.airtribe.meditrack.entity.Patient;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVUtil {

    public static void savePatients(List<Patient> patients, String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Patient p : patients) {
                bw.write(p.getId() + "," + p.getName() + "," + p.getAge());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Patient> loadPatients(String filePath) {
        List<Patient> patients = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                patients.add(new Patient(
                        data[0],
                        data[1],
                        Integer.parseInt(data[2]),
                        "N/A"
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return patients;
    }
}
