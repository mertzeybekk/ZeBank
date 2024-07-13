package com.example.ZeBank.Dto.Request;

public class CreditCardRequestDto {
    private int customerId;
    private double creditCardLimit;

    public CreditCardRequestDto(int customerId, double creditCardLimit) {
        this.customerId = customerId;
        this.creditCardLimit = creditCardLimit;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public double getCreditCardLimit() {
        return creditCardLimit;
    }

    public void setCreditCardLimit(double creditCardLimit) {
        this.creditCardLimit = creditCardLimit;
    }
}
