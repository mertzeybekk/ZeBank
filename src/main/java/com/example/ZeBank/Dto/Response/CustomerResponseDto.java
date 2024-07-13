package com.example.ZeBank.Dto.Response;

import com.example.ZeBank.EntityLayer.BaseEntity;

import java.util.Date;

public class CustomerResponseDto extends BaseEntity {
    private String informationMessage;
    private Date createdDate;
    private String customerNumber;
    private Integer creditScore;

    public CustomerResponseDto(String informationMessage, Date createdDate, String customerNumber, Integer creditScore) {
        this.informationMessage = informationMessage;
        this.createdDate = createdDate;
        this.customerNumber = customerNumber;
        this.creditScore = creditScore;
    }

    public CustomerResponseDto() {
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getInformationMessage() {
        return informationMessage;
    }

    public void setInformationMessage(String informationMessage) {
        this.informationMessage = informationMessage;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public Integer getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(Integer creditScore) {
        this.creditScore = creditScore;
    }
}
