package com.example.ZeBank.Dto.Request;

import com.example.ZeBank.EntityLayer.Enum.TransactionStatus;
import com.example.ZeBank.EntityLayer.Enum.TransactionType;

import java.util.Date;

public class TransactionRequestDto {
    private Long accountId;
    private String transactionType;
    private Double amount;
    private String toIban;
    private String fromIban;
    private String transactionStatus;

    public TransactionRequestDto(Long accountId, String transactionType, double amount, Date transactionDateTime, String toIban, String fromIban, String transactionStatus) {
        this.accountId = accountId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.toIban = toIban;
        this.fromIban = fromIban;
        this.transactionStatus = transactionStatus;
    }

    public TransactionRequestDto() {
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getToIban() {
        return toIban;
    }

    public void setToIban(String toIban) {
        this.toIban = toIban;
    }

    public String getFromIban() {
        return fromIban;
    }

    public void setFromIban(String fromIban) {
        this.fromIban = fromIban;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }
}
