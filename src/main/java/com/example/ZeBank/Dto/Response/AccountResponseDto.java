package com.example.ZeBank.Dto.Response;

import java.util.Date;

public class AccountResponseDto {
    private Long accountId;
    private String accountType;
    private double balance;
    private String accountOpeningDate;
    private String accountStatus;
    private Long customerId;
    private String accountNumber;

    public AccountResponseDto(Long accountId, String accountType, double balance, String accountOpeningDate, String accountStatus, Long customerId, String accountNumber) {
        this.accountId = accountId;
        this.accountType = accountType;
        this.balance = balance;
        this.accountOpeningDate = accountOpeningDate;
        this.accountStatus = accountStatus;
        this.customerId = customerId;
        this.accountNumber = accountNumber;
    }

    public AccountResponseDto() {
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountOpeningDate() {
        return accountOpeningDate;
    }

    public void setAccountOpeningDate(String accountOpeningDate) {
        this.accountOpeningDate = accountOpeningDate;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    // Getters and Setters
}

