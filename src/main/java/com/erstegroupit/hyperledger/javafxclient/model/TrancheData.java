/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erstegroupit.hyperledger.javafxclient.model;

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
    private boolean signedByIssuer;
    private boolean signedByInvestor;
    private boolean signedByArranger;

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
        this.signedByIssuer = false;
        this.signedByInvestor = false;
        this.signedByArranger = false;
    }

    public TrancheData(String trancheId, String dealId, Integer issuerId, Integer trancheAmount, LocalDate trancheDate, LocalDate repaymentDate, String referenceIndex, double margin,
            boolean signedByIssuer, boolean signedByInvestor, boolean signedByArranger) {
        this(dealId, issuerId, trancheAmount, trancheDate, repaymentDate, referenceIndex, margin);
        this.signedByArranger = signedByArranger;
        this.signedByIssuer = signedByIssuer;
        this.signedByInvestor = signedByInvestor;
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

    public boolean isSignedByIssuer() {
        return signedByIssuer;
    }

    public void setSignedByIssuer(boolean signedByIssuer) {
        this.signedByIssuer = signedByIssuer;
    }

    public boolean isSignedByInvestor() {
        return signedByInvestor;
    }

    public void setSignedByInvestor(boolean signedByInvestor) {
        this.signedByInvestor = signedByInvestor;
    }

    public boolean isSignedByArranger() {
        return signedByArranger;
    }

    public void setSignedByArranger(boolean signedByArranger) {
        this.signedByArranger = signedByArranger;
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
