package main.java.com.airtribe.meditrack.interfaces;

public interface Searchable {
    boolean matches(String keyword);

    default boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }
}
