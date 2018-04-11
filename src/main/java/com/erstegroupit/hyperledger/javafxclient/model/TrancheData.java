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
import java.util.Locale;

import com.erstegroupit.hyperledger.javafxclient.model.CashflowData.CashflowType;

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
    private final List<CashflowData> cashflowData = new ArrayList<>();

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

    public List<CashflowData> getCashflowData() {
        return cashflowData;
    }

    public String generateCashflow() {
    	
    	String actionStatus = "";
    	
    	if (!this.getCashflowData().isEmpty()) {
            actionStatus = "This tranche has already cashflows generated.";
            return actionStatus;
    	}
    	
    	boolean isFullySigned = this.isSignedByArranger() && this.isSignedByInvestor() && this.isSignedByIssuer();
    	if (!isFullySigned) {
            actionStatus = "This tranche is not fully signed and so cashflows can't be generated.";
            return actionStatus;
    	}

        cashflowData.clear();

        int startYear = this.getTrancheDate().getYear();

        int endMonth = this.getRepaymentDate().getMonthValue();
        int endYear = this.getRepaymentDate().getYear();

        double rate = 0.035;
        double principal = this.getTrancheAmount().doubleValue() / (endYear - startYear + 1);
        double interest = this.getTrancheAmount().doubleValue() * rate;

    	this.cashflowData.add(new CashflowData(this.getTrancheId(), this.getTrancheDate(), rate, CashflowType.INVESTMENT.toString(), "EUR", -1 * Double.parseDouble(String.format(Locale.ROOT, "%.3f", this.getTrancheAmount().doubleValue()))));
        
        for (int i = startYear; i <= endYear; i++) {
        	
            LocalDate adjustmentDate = LocalDate.of(i, endMonth, 28);
            this.cashflowData.add(new CashflowData(this.getTrancheId(), adjustmentDate, rate, CashflowType.PRINCIPAL_AND_INTEREST.toString(), "EUR", Double.parseDouble(String.format(Locale.ROOT, "%.3f", principal + interest))));
        	//this.cashflowData.add(new CashflowData(this.getTrancheId(), adjustmentDate, rate, CashflowType.PRINCIPAL.toString(), "EUR", Double.parseDouble(String.format(Locale.ROOT, "%.3f", principal))));
        	//this.cashflowData.add(new CashflowData(this.getTrancheId(), adjustmentDate, rate, CashflowType.INTEREST.toString(), "EUR", Double.parseDouble(String.format(Locale.ROOT, "%.3f", interest))));
        }
        
        actionStatus = "Cashflows have been generated.";
        
        return actionStatus;

    }

}
