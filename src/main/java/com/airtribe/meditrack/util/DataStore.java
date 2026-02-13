package com.airtribe.meditrack.util;

import java.io.*;
import java.util.*;

/**
 * Generic DataStore utility class for in-memory data storage and retrieval.
 * Provides CRUD operations for any entity type using generics.
 * 
 * @param <T> the type of entity stored
 */
public class DataStore<T> {
    
    private final List<T> data;
    private final String name;
    
    /**
     * Constructor for DataStore.
     *
     * @param name the name of the data store
     */
    public DataStore(String name) {
        this.data = Collections.synchronizedList(new ArrayList<>());
        this.name = name;
    }
    
    /**
     * Add an entity to the store.
     *
     * @param entity the entity to add
     */
    public void add(T entity) {
        if (entity != null) {
            data.add(entity);
        }
    }
    
    /**
     * Get entity by index.
     *
     * @param index the index of the entity
     * @return the entity at the given index
     * @throws IndexOutOfBoundsException if index is out of bounds
     */
    public T get(int index) {
        return data.get(index);
    }
    
    /**
     * Get all entities.
     *
     * @return unmodifiable list of all entities
     */
    public List<T> getAll() {
        return Collections.unmodifiableList(new ArrayList<>(data));
    }
    
    /**
     * Update an entity at index.
     *
     * @param index  the index of the entity to update
     * @param entity the new entity
     * @return true if update was successful, false otherwise
     */
    public boolean update(int index, T entity) {
        if (index >= 0 && index < data.size() && entity != null) {
            data.set(index, entity);
            return true;
        }
        return false;
    }
    
    /**
     * Remove an entity at index.
     *
     * @param index the index of the entity to remove
     * @return true if remove was successful, false otherwise
     */
    public boolean remove(int index) {
        if (index >= 0 && index < data.size()) {
            data.remove(index);
            return true;
        }
        return false;
    }
    
    /**
     * Check if the store contains an entity.
     *
     * @param entity the entity to check
     * @return true if entity is in the store, false otherwise
     */
    public boolean contains(T entity) {
        return data.contains(entity);
    }
    
    /**
     * Get the size of the store.
     *
     * @return the number of entities in the store
     */
    public int size() {
        return data.size();
    }
    
    /**
     * Check if the store is empty.
     *
     * @return true if the store is empty, false otherwise
     */
    public boolean isEmpty() {
        return data.isEmpty();
    }
    
    /**
     * Clear all entities from the store.
     */
    public void clear() {
        data.clear();
    }
    
    /**
     * Get a filtered list based on predicate.
     *
     * @param predicate the filter predicate
     * @return list of entities matching the predicate
     */
    public List<T> filter(DataStorePredicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T entity : data) {
            if (predicate.test(entity)) {
                result.add(entity);
            }
        }
        return result;
    }
    
    /**
     * Get the name of the data store.
     *
     * @return the store name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Functional interface for filtering predicates.
     *
     * @param <T> the type of entity
     */
    @FunctionalInterface
    public interface DataStorePredicate<T> {
        boolean test(T entity);
    }
}
