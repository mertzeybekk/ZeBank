package com.example.ZeBank.EntityLayer;


import com.example.ZeBank.EntityLayer.Enum.CreditCardStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "creditcard")
@Data
public class CreditCard extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_number", nullable = false)
    private String cardNumber;

    @Column(name = "card_holder_name", nullable = false)
    private String cardHolderName;

    @Column(name = "expiration_date", nullable = false)
    private Date expirationDate;

    @Column(name = "balance", nullable = false)
    private double balance;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "credit_card_status")
    private CreditCardStatus creditCardStatus;

    public CreditCard() {
    }

    public CreditCard(Long id, String cardNumber, String cardHolderName, Date expirationDate,
                      double balance, Customer customer, CreditCardStatus creditCardStatus) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expirationDate = expirationDate;
        this.balance = balance;
        this.customer = customer;
        this.creditCardStatus = creditCardStatus;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public CreditCardStatus getCreditCardStatus() {
        return creditCardStatus;
    }

    public void setCreditCardStatus(CreditCardStatus creditCardStatus) {
        this.creditCardStatus = creditCardStatus;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", cardHolderName='" + cardHolderName + '\'' +
                ", expirationDate=" + expirationDate +
                ", balance=" + balance +
                ", customer=" + customer +
                ", creditCardStatus=" + creditCardStatus +
                '}';
    }
}
