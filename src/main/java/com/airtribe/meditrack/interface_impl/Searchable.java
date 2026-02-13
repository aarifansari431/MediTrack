package com.airtribe.meditrack.interface_impl;

/**
 * Interface for searchable entities.
 * Allows filtering and searching functionality across entities.
 */
public interface Searchable {
    
    /**
     * Check if the entity matches a given search criteria.
     *
     * @param criteria the search criteria
     * @return true if the entity matches the criteria, false otherwise
     */
    boolean matches(String criteria);
    
    /**
     * Get the unique identifier of the entity.
     *
     * @return the ID
     */
    long getId();
}
