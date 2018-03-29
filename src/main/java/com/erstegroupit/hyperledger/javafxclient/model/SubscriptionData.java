/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erstegroupit.hyperledger.javafxclient.model;

import java.time.LocalDate;

/**
 *
 * @author User
 */
public class SubscriptionData {
    private String subscriptionId;
    private String investorId;
    private String trancheId;
    private LocalDate initDate;
    private Integer targetAmount;
    private Double spreadLimit;

    public SubscriptionData(String subscriptionId, String investorId, String trancheId, LocalDate initDate, Integer targetAmount, Double spreadLimit) {
        this(investorId, trancheId, initDate, targetAmount, spreadLimit);
        this.subscriptionId = subscriptionId;
    }

    public SubscriptionData(String investorId, String trancheId, LocalDate initDate, Integer targetAmount, double spreadLimit) {
        this.investorId = investorId;
        this.trancheId = trancheId;
        this.initDate = initDate;
        this.targetAmount = targetAmount;
        this.spreadLimit = spreadLimit;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public String getInvestorId() {
        return investorId;
    }

    public String getTrancheId() {
        return trancheId;
    }

    public LocalDate getInitDate() {
        return initDate;
    }

    public Integer getTargetAmount() {
        return targetAmount;
    }

    public Double getSpreadLimit() {
        return spreadLimit;
    }
    
    
}
