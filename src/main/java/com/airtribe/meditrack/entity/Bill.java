package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.interface_impl.Payable;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Bill entity representing an invoice for a patient's appointment.
 * Implements Payable interface for billing functionality.
 */
public class Bill implements Payable, Serializable {
    
    private static final long serialVersionUID = 1L;
    private long id;
    private long appointmentId;
    private long patientId;
    private double consultationFee;
    private double taxAmount;
    private double discountAmount;
    private double totalAmount;
    private LocalDateTime billDate;
    private String paymentStatus; // PENDING, PAID, CANCELLED
    private String paymentMethod;
    
    /**
     * Constructor for Bill.
     *
     * @param id               the unique identifier
     * @param appointmentId    the appointment ID
     * @param patientId        the patient ID
     * @param consultationFee  the consultation fee
     * @param taxAmount        the tax amount
     * @param discountAmount   the discount amount
     * @param billDate         the bill date
     * @param paymentStatus    the payment status
     */
    public Bill(long id, long appointmentId, long patientId, double consultationFee,
                double taxAmount, double discountAmount, LocalDateTime billDate, String paymentStatus) {
        this.id = id;
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.consultationFee = consultationFee;
        this.taxAmount = taxAmount;
        this.discountAmount = discountAmount;
        this.billDate = billDate;
        this.paymentStatus = paymentStatus;
        this.totalAmount = calculateAmount();
        this.paymentMethod = "";
    }
    
    // Getters and Setters
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public long getAppointmentId() {
        return appointmentId;
    }
    
    public void setAppointmentId(long appointmentId) {
        this.appointmentId = appointmentId;
    }
    
    public long getPatientId() {
        return patientId;
    }
    
    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }
    
    public double getConsultationFee() {
        return consultationFee;
    }
    
    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
        this.totalAmount = calculateAmount();
    }
    
    public double getTaxAmount() {
        return taxAmount;
    }
    
    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
        this.totalAmount = calculateAmount();
    }
    
    public double getDiscountAmount() {
        return discountAmount;
    }
    
    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
        this.totalAmount = calculateAmount();
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
    
    public LocalDateTime getBillDate() {
        return billDate;
    }
    
    public void setBillDate(LocalDateTime billDate) {
        this.billDate = billDate;
    }
    
    public String getPaymentStatus() {
        return paymentStatus;
    }
    
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    @Override
    public double calculateAmount() {
        return consultationFee + taxAmount - discountAmount;
    }
    
    @Override
    public String getPaymentDescription() {
        return "Bill for Appointment ID: " + appointmentId + " | Patient ID: " + patientId;
    }
    
    @Override
    public String getPaymentType() {
        return "CONSULTATION_FEE";
    }
    
    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", appointmentId=" + appointmentId +
                ", patientId=" + patientId +
                ", consultationFee=" + consultationFee +
                ", taxAmount=" + taxAmount +
                ", discountAmount=" + discountAmount +
                ", totalAmount=" + totalAmount +
                ", billDate=" + billDate +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bill bill = (Bill) o;
        return id == bill.id;
    }
    
    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }
}
