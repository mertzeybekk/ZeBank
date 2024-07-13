package com.example.ZeBank.Dto.Response;

import com.example.ZeBank.EntityLayer.Enum.InvoiceType;

import java.util.Date;

public class InvoiceResponseDto {
    private String informationMessage;
    private double invoiceAmount;
    private Date invoicetDate;
    private String invoiceType;
    private String company;
    private String invoiceNumber;

    public InvoiceResponseDto(String informationMessage, double invoiceAmount,
                              Date invoicetDate, String invoiceType, String company,String invoiceNumber) {
        this.informationMessage = informationMessage;
        this.invoiceAmount = invoiceAmount;
        this.invoicetDate = invoicetDate;
        this.invoiceType = invoiceType;
        this.company = company;
        this.invoiceNumber = invoiceNumber;
    }

    public InvoiceResponseDto() {
    }

    public String getInformationMessage() {
        return informationMessage;
    }

    public void setInformationMessage(String informationMessage) {
        this.informationMessage = informationMessage;
    }

    public double getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(double invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public Date getInvoicetDate() {
        return invoicetDate;
    }

    public void setInvoicetDate(Date invoicetDate) {
        this.invoicetDate = invoicetDate;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }
}
