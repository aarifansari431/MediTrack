package main.java.com.airtribe.meditrack.entity;

import main.java.com.airtribe.meditrack.constants.Constants;
import main.java.com.airtribe.meditrack.interfaces.BillingStrategy;
import main.java.com.airtribe.meditrack.interfaces.Payable;

public class Bill {
    private double baseAmount;
    private BillingStrategy strategy;

    public Bill(double baseAmount, BillingStrategy strategy) {
        this.baseAmount = baseAmount;
        this.strategy = strategy;
    }

    public double generateTotal() {
        return strategy.calculate(baseAmount);
    }
}
