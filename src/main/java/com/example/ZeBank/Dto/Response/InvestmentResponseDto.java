package com.example.ZeBank.Dto.Response;

import com.example.ZeBank.EntityLayer.Enum.InvestmentType;

import java.util.Date;

public class InvestmentResponseDto {
    private String informationMessage;
    private Date investmentDate;
    private double purchasePrice;
    private double currentMarketValue;
    private String investmentType;
    private int id;
    private double difference;

    public InvestmentResponseDto(String informationMessage, Date investmentDate, double purchasePrice,
                                 double currentMarketValue, String investmentType, int id, double difference) {
        this.informationMessage = informationMessage;
        this.investmentDate = investmentDate;
        this.purchasePrice = purchasePrice;
        this.currentMarketValue = currentMarketValue;
        this.investmentType = investmentType;
        this.id = id;
        this.difference = difference;
    }

    public InvestmentResponseDto() {
    }

    public String getInformationMessage() {
        return informationMessage;
    }

    public void setInformationMessage(String informationMessage) {
        this.informationMessage = informationMessage;
    }

    public Date getInvestmentDate() {
        return investmentDate;
    }

    public void setInvestmentDate(Date investmentDate) {
        this.investmentDate = investmentDate;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public double getCurrentMarketValue() {
        return currentMarketValue;
    }

    public void setCurrentMarketValue(double currentMarketValue) {
        this.currentMarketValue = currentMarketValue;
    }

    public String getInvestmentType() {
        return investmentType;
    }

    public void setInvestmentType(String investmentType) {
        this.investmentType = investmentType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getDifference() {
        return difference;
    }

    public void setDifference(double difference) {
        this.difference = difference;
    }
}
