/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erstegroupit.hyperledger.javafxclient.model;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
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

    public static List<CashflowData> getCashflow(TrancheData trancheData) {
        List<CashflowData> cashflowRecords = new ArrayList<>();

        int startMonth = trancheData.getTrancheDate().getMonthValue();
        int startYear = trancheData.getTrancheDate().getYear();

        int endMonth = trancheData.getRepaymentDate().getMonthValue();
        int endYear = trancheData.getRepaymentDate().getYear();

        double rate = 3.5;
        double principal = trancheData.getTrancheAmount().doubleValue() / (endYear - startYear + 1);
                
        for (int i = startYear; i <= endYear; i++) {
            if (i == startYear) {
                // we have only a record in cashflow
                
                double amount = 0; 
                if (startYear == endYear) {
                    amount = trancheData.getTrancheAmount().doubleValue() * (1 + 1 / (12 - trancheData.getTrancheDate().getMonthValue() + 1)) * rate;
                } else {
                    amount =  trancheData.getTrancheAmount().doubleValue() * (12 - trancheData.getTrancheDate().getMonthValue() + 1) * rate;
                }
                cashflowRecords.add(new CashflowData(trancheData.getTrancheId(),
                        trancheData.getTrancheDate().withMonth(12).withDayOfMonth(31), rate, "principal", "EUR", amount));
            } else if (i > startYear && i < endYear) {
                // add complete years
            } else if (i == endYear && i != startYear) {
                // add incomplete last year
                //double amount = trancheData.getTrancheAmount().doubleValue() * (1 + 1 / (trancheData.getRepaymentDate().getMonthValue()) * rate;
                cashflowRecords.add(new CashflowData(trancheData.getTrancheId(),
                        trancheData.getTrancheDate().withMonth(12).withDayOfMonth(31), rate, "principal", "EUR", principal));
                
            }
        }
        Period tranchePeriod = Period.between(trancheData.getTrancheDate(), trancheData.getRepaymentDate());

        tranchePeriod.getYears();

        return cashflowRecords;
    }

}
