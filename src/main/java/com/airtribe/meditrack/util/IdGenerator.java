package main.java.com.airtribe.meditrack.util;

public class IdGenerator {
    private static IdGenerator instance;
    private int counter = 1000;

    private IdGenerator() {}

    public static synchronized IdGenerator getInstance() {
        if (instance == null) {
            instance = new IdGenerator();
        }
        return instance;
    }

    public String generateId() {
        return "ID-" + counter++;
    }
}
