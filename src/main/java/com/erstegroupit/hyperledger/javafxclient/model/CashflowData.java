/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erstegroupit.hyperledger.javafxclient.model;

import java.time.LocalDate;

/**
 *
 * @author H50UDBB
 */
public class CashflowData {
	
	public enum CashflowType {
		INVESTMENT, PRINCIPAL, INTEREST;		
	}
   
    private String cashflowId;    
    private String trancheId;    
    private LocalDate adjustedDate;    
    private Double rate;    
    private String cashflowType;    
    private String currency;    
    private Double amount;

    public CashflowData(String trancheId, LocalDate adjustedDate, Double rate, String cashflowType, String currency, Double amount) {
        this.trancheId = trancheId;
        this.adjustedDate = adjustedDate;
        this.rate = rate;
        this.cashflowType = cashflowType;
        this.currency = currency;
        this.amount = amount;
    }

    public CashflowData(String cashflowId, String trancheId, LocalDate adjustedDate, Double rate, String cashflowType, String currency, Double amount) {
        this(trancheId, adjustedDate, rate, cashflowType, currency, amount);
        this.cashflowId = cashflowId;        
    }    
    
    /**
     * Get the value of amount
     *
     * @return the value of amount
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * Set the value of amount
     *
     * @param amount new value of amount
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }


    /**
     * Get the value of currency
     *
     * @return the value of currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Set the value of currency
     *
     * @param currency new value of currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * Get the value of cashflowType
     *
     * @return the value of cashflowType
     */
    public String getCashflowType() {
        return cashflowType;
    }

    /**
     * Set the value of cashflowType
     *
     * @param cashflowType new value of cashflowType
     */
    public void setCashflowType(String cashflowType) {
        this.cashflowType = cashflowType;
    }


    /**
     * Get the value of rate
     *
     * @return the value of rate
     */
    public Double getRate() {
        return rate;
    }

    /**
     * Set the value of rate
     *
     * @param rate new value of rate
     */
    public void setRate(Double rate) {
        this.rate = rate;
    }


    /**
     * Get the value of adjustedDate
     *
     * @return the value of adjustedDate
     */
    public LocalDate getAdjustedDate() {
        return adjustedDate;
    }

    /**
     * Set the value of adjustedDate
     *
     * @param adjustedDate new value of adjustedDate
     */
    public void setAdjustedDate(LocalDate adjustedDate) {
        this.adjustedDate = adjustedDate;
    }


    /**
     * Get the value of trancheId
     *
     * @return the value of trancheId
     */
    public String getTrancheId() {
        return trancheId;
    }

    /**
     * Set the value of trancheId
     *
     * @param trancheId new value of trancheId
     */
    public void setTrancheId(String trancheId) {
        this.trancheId = trancheId;
    }


    /**
     * Get the value of cashflowId
     *
     * @return the value of cashflowId
     */
    public String getCashflowId() {
        return cashflowId;
    }

    /**
     * Set the value of cashflowId
     *
     * @param cashflowId new value of cashflowId
     */
    public void setCashflowId(String cashflowId) {
        this.cashflowId = cashflowId;
    }
 
}
