package main.java.com.airtribe.meditrack.util;

import main.java.com.airtribe.meditrack.constants.Constants;
import main.java.com.airtribe.meditrack.interfaces.BillingStrategy;

public class StandardBillingStrategy implements BillingStrategy {
    @Override
    public double calculate(double baseAmount) {
        return baseAmount + (baseAmount * Constants.TAX_RATE);
    }
}
