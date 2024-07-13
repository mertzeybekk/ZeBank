package com.example.ZeBank.EntityLayer;

import com.example.ZeBank.EntityLayer.Enum.LoanStatus;
import com.example.ZeBank.EntityLayer.Enum.LoanType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "loans")
@Data
public class Loan extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "loan_type", nullable = false)
    private LoanType loanType;

    @Column(name = "loan_amount", nullable = false)
    private double loanAmount;

    @Column(name = "interest_rate", nullable = false)
    private double interestRate;
    @Column(name = "payment_start", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date paymentStart;

    @Column(name = "payment_end", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date paymentEnd;

    @Column(name = "loan_duration_months", nullable = false)
    private int loanDurationMonths;
    @Column(name = "loan_status", nullable = false)
    private LoanStatus loanStatus;
    @Column(name = "remaining_amount", nullable = false)
    private double remainingAmount;

    public Loan() {
    }

    public Loan(Customer customer, LoanType loanType, double loanAmount, double interestRate,
                Date paymentStart, Date paymentEnd, LoanStatus loanStatus, int loanDurationMonths, double remainingAmount) {
        this.customer = customer;
        this.loanType = loanType;
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.paymentStart = paymentStart;
        this.paymentEnd = paymentEnd;
        this.loanDurationMonths = loanDurationMonths;
        this.loanStatus = loanStatus;
        this.remainingAmount = remainingAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public LoanType getLoanType() {
        return loanType;
    }

    public void setLoanType(LoanType loanType) {
        this.loanType = loanType;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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

    public int getLoanDurationMonths() {
        return loanDurationMonths;
    }

    public void setLoanDurationMonths(int loanDurationMonths) {
        this.loanDurationMonths = loanDurationMonths;
    }

    public LoanStatus getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(LoanStatus loanStatus) {
        this.loanStatus = loanStatus;
    }

    public double getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(double remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
// Getters and setters...
}