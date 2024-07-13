package com.example.ZeBank.Dto.Request;

import com.example.ZeBank.EntityLayer.Enum.InvoiceType;

import java.util.Date;

public class InvoiceRequestDto {
    private String invoiceNumber;
    private Long customerId;
    private Date invoiceDate;
    private double totalAmount;
    private String company;
    private InvoiceType invoiceType;

    public InvoiceRequestDto(String invoiceNumber, Long customerId, Date invoiceDate, double totalAmount, String company, InvoiceType invoiceType) {
        this.invoiceNumber = invoiceNumber;
        this.customerId = customerId;
        this.invoiceDate = invoiceDate;
        this.totalAmount = totalAmount;
        this.company = company;
        this.invoiceType = invoiceType;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public InvoiceType getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(InvoiceType invoiceType) {
        this.invoiceType = invoiceType;
    }
}
