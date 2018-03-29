/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erstegroupit.hyperledger.javafxclient.restclient;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author User
 */
public class BlockchainDealInfo {
    @JsonProperty("issuer")
    private String issuer;
    
    @JsonProperty("amount")
    private String amount;
    
    @JsonProperty("StartDate")
    private String startDate;
    
    @JsonProperty("expiry_date")
    private String expiryDate;
    
    @JsonProperty("deal_uuid")
    private String dealId;
    
    @JsonProperty("description")
    private String description;

    public BlockchainDealInfo() {
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getDealId() {
        return dealId;
    }

    public void setDealId(String dealId) {
        this.dealId = dealId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
    
}
