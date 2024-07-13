package com.example.ZeBank.Dto.Request;

import com.example.ZeBank.EntityLayer.BaseEntity;

import java.util.Date;

public class CustomerRequestDto extends BaseEntity {
    private String name;
    private String address;
    private String contactInformation;
    private Date dateOfBirth;
    private String password;
    private String phoneNumber;
    private String roles;
    private Integer creditScore;

    // Getters and setters
    public CustomerRequestDto() {

    }

    public CustomerRequestDto(String name, String address, String contactInformation, Date dateOfBirth, String password, String phoneNumber, String roles) {
        this.name = name;
        this.address = address;
        this.contactInformation = contactInformation;
        this.dateOfBirth = dateOfBirth;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.roles = roles;
    }

    public Integer getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(Integer creditScore) {
        this.creditScore = creditScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
