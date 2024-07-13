package com.example.ZeBank.Dto.Request;

import com.example.ZeBank.EntityLayer.Enum.AccountStatus;
import com.example.ZeBank.EntityLayer.Enum.AccountType;
import com.example.ZeBank.EntityLayer.Loan;

import java.util.Date;

public class AccountRequestDto {
    private Long customerId;
    private String accountType;
    private double balance;
    private String accountStatus;
    private Date accountOpeningDate;
    private String accountNumber;

    public AccountRequestDto(Long customerId, String accountType, double balance, String accountStatus, Date accountOpeningDate, String accountNumber) {
        this.customerId = customerId;
        this.accountType = accountType;
        this.balance = balance;
        this.accountStatus = accountStatus;
        this.accountOpeningDate = accountOpeningDate;
        this.accountNumber = accountNumber;
    }
    public AccountRequestDto(){}

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Date getAccountOpeningDate() {
        return accountOpeningDate;
    }

    public void setAccountOpeningDate(Date accountOpeningDate) {
        this.accountOpeningDate = accountOpeningDate;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}

