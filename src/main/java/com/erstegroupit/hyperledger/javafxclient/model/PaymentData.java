package com.erstegroupit.hyperledger.javafxclient.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.erstegroupit.hyperledger.javafxclient.InjectorContext;

/**
*
* @author H502QOB
*/
public class PaymentData {
	
    private final DataModel dataModel = InjectorContext.getInjector().getInstance(DataModel.class);
	
    private String paymentId; 
    private LocalDate paymentDate;  
    private String currency;    
    private Double amount;
    private Integer issuerId;
    private Integer investorId;
    
    private static Integer idCounter = 0;
    
    private final Map<AllocationData, List<CashflowData>> allocationCashflowMap = new HashMap<AllocationData, List<CashflowData>>(); //TODO: replace AllocationData with BallanceData
    
	public PaymentData(String paymentId, LocalDate paymentDate, String currency, Double amount, Integer issuerId,
			Integer investorId) {
		super();
		this.paymentId = paymentId;
		this.paymentDate = paymentDate;
		this.currency = currency;
		this.amount = amount;
		this.issuerId = issuerId;
		this.investorId = investorId;
	}

	public PaymentData(CashflowData cashflowData, AllocationData allocationData, TrancheData trancheData) {
		idCounter = idCounter + 1;
		this.paymentId = idCounter.toString();
		this.paymentDate = cashflowData.getAdjustedDate();
		this.currency = cashflowData.getCurrency();
		this.issuerId = trancheData.getIssuerId();
		this.investorId = Integer.parseInt(allocationData.getInvestorId());
		
		Double stake = allocationData.getAllocationAmount().doubleValue() / trancheData.getTrancheAmount().doubleValue();
		Integer paymentDirection = 1;
	    if (dataModel.getClientType().equals("ISSUER")) {
	    	paymentDirection = -1;
	    }
		this.amount = cashflowData.getAmount().doubleValue() * stake * paymentDirection.doubleValue();
		
		List<CashflowData> cdList = new ArrayList<CashflowData>();
		cdList.add(cashflowData);
		allocationCashflowMap.put(allocationData, cdList);
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

	public Map<AllocationData, List<CashflowData>> getCashflowAllocationMap() {
		return allocationCashflowMap;
	}    
    
}
