package com.example.ZeBank.Dto.Request;

import java.util.Date;

public class InvestmentRequestDto {
    private long customerId;
    private String investmentType;
    private Double purchasePrice;
    private Double currentMarketValue;
    private Date investmentDate;

    public InvestmentRequestDto(long customerId, String investmentType, Double purchasePrice, Double currentMarketValue, Date investmentDate) {
        this.customerId = customerId;
        this.investmentType = investmentType;
        this.purchasePrice = purchasePrice;
        this.currentMarketValue = currentMarketValue;
        this.investmentDate = investmentDate;
    }

    public InvestmentRequestDto() {
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getInvestmentType() {
        return investmentType;
    }

    public void setInvestmentType(String investmentType) {
        this.investmentType = investmentType;
    }

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Double getCurrentMarketValue() {
        return currentMarketValue;
    }

    public void setCurrentMarketValue(Double currentMarketValue) {
        this.currentMarketValue = currentMarketValue;
    }

    public Date getInvestmentDate() {
        return investmentDate;
    }

    public void setInvestmentDate(Date investmentDate) {
        this.investmentDate = investmentDate;
    }
}
