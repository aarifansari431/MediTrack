package com.airtribe.meditrack.entity;

import java.io.Serializable;
import java.util.*;

/**
 * Immutable BillSummary class representing a summary of bills for a patient.
 * This class demonstrates immutability principles in Java.
 */
public class BillSummary implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private final long patientId;
    private final List<Bill> bills;
    private final double totalBillAmount;
    private final double totalPaidAmount;
    private final double totalOutstandingAmount;
    private final int totalBillsCount;
    private final int paidBillsCount;
    private final int pendingBillsCount;
    
    /**
     * Constructor for BillSummary (Immutable).
     *
     * @param patientId the patient ID
     * @param bills     the list of bills
     */
    public BillSummary(long patientId, List<Bill> bills) {
        this.patientId = patientId;
        this.bills = Collections.unmodifiableList(new ArrayList<>(bills));
        this.totalBillsCount = bills.size();
        this.paidBillsCount = (int) bills.stream()
                .filter(b -> "PAID".equals(b.getPaymentStatus()))
                .count();
        this.pendingBillsCount = (int) bills.stream()
                .filter(b -> "PENDING".equals(b.getPaymentStatus()))
                .count();
        this.totalBillAmount = bills.stream()
                .mapToDouble(Bill::getTotalAmount)
                .sum();
        this.totalPaidAmount = bills.stream()
                .filter(b -> "PAID".equals(b.getPaymentStatus()))
                .mapToDouble(Bill::getTotalAmount)
                .sum();
        this.totalOutstandingAmount = totalBillAmount - totalPaidAmount;
    }
    
    // Getters (all return copies or immutable views)
    public long getPatientId() {
        return patientId;
    }
    
    public List<Bill> getBills() {
        return bills;
    }
    
    public double getTotalBillAmount() {
        return totalBillAmount;
    }
    
    public double getTotalPaidAmount() {
        return totalPaidAmount;
    }
    
    public double getTotalOutstandingAmount() {
        return totalOutstandingAmount;
    }
    
    public int getTotalBillsCount() {
        return totalBillsCount;
    }
    
    public int getPaidBillsCount() {
        return paidBillsCount;
    }
    
    public int getPendingBillsCount() {
        return pendingBillsCount;
    }
    
    /**
     * Get the payment completion percentage.
     *
     * @return the payment completion percentage
     */
    public double getPaymentCompletionPercentage() {
        if (totalBillAmount == 0) {
            return 0;
        }
        return (totalPaidAmount / totalBillAmount) * 100;
    }
    
    @Override
    public String toString() {
        return "BillSummary{" +
                "patientId=" + patientId +
                ", totalBillAmount=" + totalBillAmount +
                ", totalPaidAmount=" + totalPaidAmount +
                ", totalOutstandingAmount=" + totalOutstandingAmount +
                ", totalBillsCount=" + totalBillsCount +
                ", paidBillsCount=" + paidBillsCount +
                ", pendingBillsCount=" + pendingBillsCount +
                ", completionPercentage=" + String.format("%.2f", getPaymentCompletionPercentage()) +
                "%}";
    }
}
