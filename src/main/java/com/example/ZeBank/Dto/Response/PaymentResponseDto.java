package com.example.ZeBank.Dto.Response;

import com.example.ZeBank.EntityLayer.Enum.PaymentStatus;
import com.example.ZeBank.EntityLayer.Enum.PaymentType;

import java.util.Date;

public class PaymentResponseDto {
    private String informationMessage;
    private String paymentType;
    private Double paymentPrice;
    private String paymentStatus;
    private Date paymentDate;
    private int paymentId;

    public PaymentResponseDto() {
    }

    public PaymentResponseDto(String informationMessage, String paymentType, Double paymentPrice, String paymentStatus, Date paymentDate, int paymentId) {
        this.informationMessage = informationMessage;
        this.paymentType = paymentType;
        this.paymentPrice = paymentPrice;
        this.paymentStatus = paymentStatus;
        this.paymentDate = paymentDate;
        this.paymentId = paymentId;
    }

    public String getInformationMessage() {
        return informationMessage;
    }

    public void setInformationMessage(String informationMessage) {
        this.informationMessage = informationMessage;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Double getPaymentPrice() {
        return paymentPrice;
    }

    public void setPaymentPrice(Double paymentPrice) {
        this.paymentPrice = paymentPrice;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }
}
