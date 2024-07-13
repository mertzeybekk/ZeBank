package com.example.ZeBank.Dto.Request;

import com.example.ZeBank.EntityLayer.Enum.LoanStatus;

import java.util.Date;

public class LoanRequestDto {
    private Long customerId;
    private String loanType;
    private Double loanAmount;
    private Double interestRate;
    private Date paymentStart;
    private Date paymentEnd;
    private Integer loanDurationMonths;
    private String loanStatus;

    public LoanRequestDto(Long customerId, String loanType, Double loanAmount, Double interestRate,
                          Date paymentStart, Date paymentEnd, Integer loanDurationMonths, String loanStatus) {
        this.customerId = customerId;
        this.loanType = loanType;
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.paymentStart = paymentStart;
        this.paymentEnd = paymentEnd;
        this.loanDurationMonths = loanDurationMonths;
        this.loanStatus = loanStatus;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
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

    public Integer getLoanDurationMonths() {
        return loanDurationMonths;
    }

    public void setLoanDurationMonths(Integer loanDurationMonths) {
        this.loanDurationMonths = loanDurationMonths;
    }

    public String getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(String loanStatus) {
        this.loanStatus = loanStatus;
    }
}
