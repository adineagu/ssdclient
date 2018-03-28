/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erstegroupit.model;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author User
 */
public class TrancheData {

    private String trancheId;
    private final String dealId;
    private final Integer issuerId;
    private final Integer trancheAmount;
    private final LocalDate trancheDate;
    private final LocalDate repaymentDate;
    private final String referenceIndex;
    private final double margin;
    private final String creationDate;
    
    private final List<SubscriptionData> subscriptionData = new ArrayList<>();
    private final List<AllocationData> allocationData = new ArrayList<>();

    public TrancheData(String dealId, Integer issuerId, Integer trancheAmount, LocalDate trancheDate, LocalDate repaymentDate, String referenceIndex, double margin) {
        this.dealId = dealId;
        this.issuerId = issuerId;
        this.trancheAmount = trancheAmount;
        this.trancheDate = trancheDate;
        this.repaymentDate = repaymentDate;        
        this.referenceIndex = referenceIndex;
        this.margin = margin;
        this.creationDate = getCrtTime();
            }

    public TrancheData(String trancheId, String dealId, Integer issuerId, Integer trancheAmount, LocalDate trancheDate, LocalDate repaymentDate, String referenceIndex, double margin) {
        this(dealId, issuerId, trancheAmount, trancheDate, repaymentDate, referenceIndex, margin);
        this.trancheId = trancheId;
    }

    public String getTrancheId() {
        return trancheId;
    }

    public void setTrancheId(String trancheId) {
        this.trancheId = trancheId;
    }

    public String getDealId() {
        return dealId;
    }

    public Integer getIssuerId() {
        return issuerId;
    }

    public Integer getTrancheAmount() {
        return trancheAmount;
    }

    public LocalDate getTrancheDate() {
        return trancheDate;
    }

    public LocalDate getRepaymentDate() {
        return repaymentDate;
    }

    public String getReferenceIndex() {
        return referenceIndex;
    }

    public double getMargin() {
        return margin;
    }

    public String getCreationDate() {
        return creationDate;
    }        
    
    private String getCrtTime() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        return sdfDate.format(now);
    }

    public List<SubscriptionData> getSubscriptionData() {
        return subscriptionData;
    }

    public List<AllocationData> getAllocationData() {
        return allocationData;
    }
}
