package com.example.ZeBank.Dto.Response;

import com.example.ZeBank.EntityLayer.Enum.TransactionStatus;
import com.example.ZeBank.EntityLayer.Enum.TransactionType;

import java.util.Date;

public class TransactionResponseDto {
    private Long id;
    private Long accountId; // Account'a ait özelliklerden sadece adı örneğin
    private String transactionType;
    private double amount;
    private Date transactionDateTime;
    private String transactionStatus;
    private String toIban;
    private String fromIban;

    public TransactionResponseDto() {
    }

    public TransactionResponseDto(Long id, Long accountId, String transactionType, double amount, Date transactionDateTime, String transactionStatus, String toIban, String fromIban) {
        this.id = id;
        this.accountId = accountId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDateTime = transactionDateTime;
        this.transactionStatus = transactionStatus;
        this.toIban = toIban;
        this.fromIban = fromIban;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getTransactionDateTime() {
        return transactionDateTime;
    }

    public void setTransactionDateTime(Date transactionDateTime) {
        this.transactionDateTime = transactionDateTime;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
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
}