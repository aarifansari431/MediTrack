package main.java.com.airtribe.meditrack.util;

import main.java.com.airtribe.meditrack.interfaces.BillingStrategy;

public class NoTaxBillingStrategy implements BillingStrategy {
    @Override
    public double calculate(double baseAmount) {
        return baseAmount;
    }
}
