package com.example.ZeBank.Dto.Response;

import java.time.LocalDate;
import java.util.Date;

public class CreditCardResponseDto {
    private String cardNumber;
    private Date expirationDate;
    private double balance;
    private String cardHolderName;
    private String informationMessage;

    public CreditCardResponseDto(String cardNumber, Date expirationDate, double balance, String cardHolderName,
                                 String informationMessage) {
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.balance = balance;
        this.cardHolderName = cardHolderName;
        this.informationMessage = informationMessage;
    }

    public CreditCardResponseDto() {

    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getInformationMessage() {
        return informationMessage;
    }

    public void setInformationMessage(String informationMessage) {
        this.informationMessage = informationMessage;
    }
}
