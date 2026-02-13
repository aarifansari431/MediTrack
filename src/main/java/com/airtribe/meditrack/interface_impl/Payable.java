package com.airtribe.meditrack.interface_impl;

/**
 * Interface for payable entities.
 * Represents entities that can generate bills and be paid.
 */
public interface Payable {
    
    /**
     * Calculate the total amount to be paid.
     *
     * @return the total amount
     */
    double calculateAmount();
    
    /**
     * Get the description of the payment.
     *
     * @return the payment description
     */
    String getPaymentDescription();
    
    /**
     * Get the payment type.
     *
     * @return the payment type
     */
    String getPaymentType();
}
