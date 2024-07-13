package com.example.ZeBank.Dto.Request;

public class CreditCardPayingBillsRequest {
    private String cardNumber;
    private double invoiceAmount;

    public CreditCardPayingBillsRequest(String cardNumber, double invoiceAmount) {
        this.cardNumber = cardNumber;
        this.invoiceAmount = invoiceAmount;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public double getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(double invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }
}
