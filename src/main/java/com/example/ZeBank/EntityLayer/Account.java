package com.example.ZeBank.EntityLayer;

import com.example.ZeBank.EntityLayer.Enum.AccountStatus;
import com.example.ZeBank.EntityLayer.Enum.AccountType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "accounts")
public class Account extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "account_type", nullable = false)
    private AccountType accountType;

    @Column(name = "balance")
    private double balance;

    @Column(name = "account_opening_date")
    @Temporal(TemporalType.DATE)
    private Date accountOpeningDate;

    @Column(name = "account_status")
    private AccountStatus accountStatus;
    @Column(name = "account_number")
    private String accountNumber;

    public Account() {
    }

    public Account(Customer customer, AccountType accountType, double balance, Date accountOpeningDate, AccountStatus accountStatus, String accountNumber) {
        this.customer = customer;
        this.accountType = accountType;
        this.balance = balance;
        this.accountOpeningDate = accountOpeningDate;
        this.accountStatus = accountStatus;
        this.accountNumber = accountNumber;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Date getAccountOpeningDate() {
        return accountOpeningDate;
    }

    public void setAccountOpeningDate(Date accountOpeningDate) {
        this.accountOpeningDate = accountOpeningDate;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    // Getters and setters...
}
