/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erstegroupit.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class DealData {
    private String dealId;
    private Integer issuerId;
    private String issuerName;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private String arrangerName; 
    private String purpose;    
    private String type;        
    private Integer issuedAmount;    
    private Integer subscriptionMinAmount;
    private String currencyCd;
    private Integer paidOffAmount;
    
    private final List<TrancheData> trancheData = new ArrayList<>();

    public DealData(String dealId, Integer issuerId, Integer issuedAmount, LocalDate issueDate, LocalDate expiryDate, Integer subscriptionMinAmount) {
        this(issuerId, issuedAmount, issueDate, expiryDate, subscriptionMinAmount);
        this.dealId = dealId;
    }
    
    public DealData(Integer issuerId, Integer issuedAmount, LocalDate issueDate, LocalDate expiryDate, Integer subscriptionMinAmount) {
        this.issuerId = issuerId;
        this.issuedAmount = issuedAmount;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
        this.subscriptionMinAmount = subscriptionMinAmount;   
        
        this.purpose = "Allgemeine Unternehmensfinanzierung und Refinanzierung";
        this.type = "Schuldschein Darlehen, durch Abtretung jederzeit Verfügbar";
        this.arrangerName = "Erste Group Bank";
        this.currencyCd  = "EUR";
        this.paidOffAmount = 0;
    }

    
    public DealData(String issuerName, Integer issuedAmount, LocalDate issueDate, LocalDate expiryDate) {
        this.issuerName = issuerName;
        this.issuedAmount = issuedAmount;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
    }

    
    public String getDealId() {
        return dealId;
    }

    public void setDealId(String dealId) {
        this.dealId = dealId;
    }

    public Integer getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(Integer issuerId) {
        this.issuerId = issuerId;
    }

    public String getIssuerName() {
        return issuerName;
    }

    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getArrangerName() {
        return arrangerName;
    }

    public void setArrangerName(String arrangerName) {
        this.arrangerName = arrangerName;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getIssuedAmount() {
        return issuedAmount;
    }

    public void setIssuedAmount(Integer issuedAmount) {
        this.issuedAmount = issuedAmount;
    }

    public Integer getSubscriptionMinAmount() {
        return subscriptionMinAmount;
    }

    public void setSubscriptionMinAmount(Integer subscriptionMinAmount) {
        this.subscriptionMinAmount = subscriptionMinAmount;
    }

    public String getCurrencyCd() {
        return currencyCd;
    }

    public void setCurrencyCd(String currencyCd) {
        this.currencyCd = currencyCd;
    }

    public Integer getPaidOffAmount() {
        return paidOffAmount;
    }

    public void setPaidOffAmount(Integer paidOffAmount) {
        this.paidOffAmount = paidOffAmount;
    }

    public List<TrancheData> getTrancheData() {
        return trancheData;
    }

}
