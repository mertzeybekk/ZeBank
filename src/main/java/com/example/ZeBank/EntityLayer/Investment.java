package com.example.ZeBank.EntityLayer;

import com.example.ZeBank.EntityLayer.Enum.InvestmentType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "investments")
@Data
public class Investment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "investment_type", nullable = false)
    private InvestmentType investmentType;

    @Column(name = "purchase_price", nullable = false)
    private double purchasePrice;

    @Column(name = "current_market_value", nullable = false)
    private double currentMarketValue;

    @Column(name = "investment_date")
    @Temporal(TemporalType.DATE)
    private Date investmentDate;

    public Investment() {
    }

    public Investment(Customer customer, InvestmentType investmentType, double purchasePrice, double currentMarketValue, Date investmentDate) {
        this.customer = customer;
        this.investmentType = investmentType;
        this.purchasePrice = purchasePrice;
        this.currentMarketValue = currentMarketValue;
        this.investmentDate = investmentDate;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public InvestmentType getInvestmentType() {
        return investmentType;
    }

    public void setInvestmentType(InvestmentType investmentType) {
        this.investmentType = investmentType;
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

    public Date getInvestmentDate() {
        return investmentDate;
    }

    public void setInvestmentDate(Date investmentDate) {
        this.investmentDate = investmentDate;
    }
}

