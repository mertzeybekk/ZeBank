package com.example.ZeBank.Dto.Response;

import java.util.Date;

public class LoanResponseDto {
    private String informationMessage;
    private double loanPrice;
    private Date paymentStart;
    private Date paymentEnd;
    private Double monthlyAmount;
    private int loanDurationMonths;
    private String loanType;
    private String loanStatus;
    private double remainingAmount;
    private Long loanId;

    public LoanResponseDto(String informationMessage, double loanPrice, Date paymentStart, Date paymentEnd, Double monthlyAmount,
                           String loanType, int loanDurationMonths, String loanStatus, double remainingAmount, Long loanId) {
        this.informationMessage = informationMessage;
        this.loanPrice = loanPrice;
        this.paymentStart = paymentStart;
        this.paymentEnd = paymentEnd;
        this.monthlyAmount = monthlyAmount;
        this.loanDurationMonths = loanDurationMonths;
        this.loanType = loanType;
        this.loanStatus = loanStatus;
        this.remainingAmount = remainingAmount;
        this.loanId = loanId;
    }

    public LoanResponseDto(String informationMessage) {
        this.informationMessage = informationMessage;
    }

    public LoanResponseDto() {
    }

    public String getInformationMessage() {
        return informationMessage;
    }

    public void setInformationMessage(String informationMessage) {
        this.informationMessage = informationMessage;
    }

    public double getLoanPrice() {
        return loanPrice;
    }

    public void setLoanPrice(double loanPrice) {
        this.loanPrice = loanPrice;
    }

    public Date getPaymentStart() {
        return paymentStart;
    }

    public void setPaymentStart(Date paymentStart) {
        this.paymentStart = paymentStart;
    }

    public Date getPaymentEnd() {
        return paymentEnd;
    }

    public void setPaymentEnd(Date paymentEnd) {
        this.paymentEnd = paymentEnd;
    }

    public Double getMonthlyAmount() {
        return monthlyAmount;
    }

    public void setMonthlyAmount(Double monthlyAmount) {
        this.monthlyAmount = monthlyAmount;
    }

    public int getLoanDurationMonths() {
        return loanDurationMonths;
    }

    public void setLoanDurationMonths(int loanDurationMonths) {
        this.loanDurationMonths = loanDurationMonths;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public String getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(String loanStatus) {
        this.loanStatus = loanStatus;
    }

    public double getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(double remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }
}
