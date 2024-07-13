package com.example.ZeBank.Dto.Request;

import com.example.ZeBank.EntityLayer.Enum.PaymentStatus;
import com.example.ZeBank.EntityLayer.Enum.PaymentType;

import java.util.Date;

public class PaymentRequestDto {
    private int customerId;
    private String paymentType;
    private Double amount;
    private Date paymentDateTime;
    private int accountId;
    private Date paymentStart;

    public PaymentRequestDto(String paymentType, Double amount, Date paymentDateTime, int accountId, Date paymentStart) {
        this.paymentType = paymentType;
        this.amount = amount;
        this.paymentDateTime = paymentDateTime;
        this.accountId = accountId;
        this.paymentStart = paymentStart;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getPaymentDateTime() {
        return paymentDateTime;
    }

    public void setPaymentDateTime(Date paymentDateTime) {
        this.paymentDateTime = paymentDateTime;
    }


    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public Date getPaymentStart() {
        return paymentStart;
    }

    public void setPaymentStart(Date paymentStart) {
        this.paymentStart = paymentStart;
    }
}
