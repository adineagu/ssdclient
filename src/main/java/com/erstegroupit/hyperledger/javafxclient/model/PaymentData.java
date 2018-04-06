package com.erstegroupit.hyperledger.javafxclient.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
*
* @author H502QOB
*/
public class PaymentData {
	
    private String paymentId; 
    private LocalDate paymentDate;  
    private String currency;    
    private Double amount;
    private Integer issuerId;
    private Integer investorId;
    private Integer paymentDirection; /* +1 = payment from issuer to investor, -1 payment from investor to issuer */
    
    private static Integer idCounter = 0;
    
    private final Map<AllocationData, CashflowData> allocationCashflowMap = new HashMap<AllocationData, CashflowData>(); //TODO: replace AllocationData with BallanceData
    
	public PaymentData(String paymentId, LocalDate paymentDate, String currency, Double amount, Integer issuerId,
			Integer investorId, Integer paymentDirection) {
		super();
		this.paymentId = paymentId;
		this.paymentDate = paymentDate;
		this.currency = currency;
		this.amount = amount;
		this.issuerId = issuerId;
		this.investorId = investorId;
		this.paymentDirection = paymentDirection;
	}

	public PaymentData(CashflowData cashflowData, AllocationData allocationData, TrancheData trancheData) {
		idCounter = idCounter + 1;
		this.paymentId = idCounter.toString();
		this.paymentDate = cashflowData.getAdjustedDate();
		this.currency = cashflowData.getCurrency();
		Double stake = allocationData.getAllocationAmount().doubleValue() / trancheData.getTrancheAmount().doubleValue();
		this.amount = cashflowData.getAmount().doubleValue() * stake;
		this.issuerId = trancheData.getIssuerId();
		this.investorId = Integer.parseInt(allocationData.getInvestorId());
		this.paymentDirection = 1;
		
		allocationCashflowMap.put(allocationData, cashflowData);
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public LocalDate getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Integer getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(Integer issuerId) {
		this.issuerId = issuerId;
	}

	public Integer getInvestorId() {
		return investorId;
	}

	public void setInvestorId(Integer investorId) {
		this.investorId = investorId;
	}

	public Integer getPaymentDirection() {
		return paymentDirection;
	}

	public void setPaymentDirection(Integer paymentDirection) {
		this.paymentDirection = paymentDirection;
	}

	public Map<AllocationData, CashflowData> getCashflowAllocationMap() {
		return allocationCashflowMap;
	}    
    
}
