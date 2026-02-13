package main.java.com.airtribe.meditrack.entity;

public final class BillSummary {
    private final double baseAmount;
    private final double tax;
    private final double total;

    public BillSummary(double base, double tax) {
        this.baseAmount = base;
        this.tax = tax;
        this.total = base + tax;
    }

    public double getTotal() {
        return total;
    }
}
