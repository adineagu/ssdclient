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
    private String issuerId;
    private String investorId;
    private Integer paymentDirection; /* +1 = payment from issuer to investor, -1 payment from investor to issuer */
    
    private final Map<CashflowData, AllocationData> trancheData = new HashMap<CashflowData, AllocationData>(); //TODO: replace AllocationData with BallanceData
    
	public PaymentData(String paymentId, LocalDate paymentDate, String currency, Double amount, String issuerId,
			String investorId, Integer paymentDirection) {
		super();
		this.paymentId = paymentId;
		this.paymentDate = paymentDate;
		this.currency = currency;
		this.amount = amount;
		this.issuerId = issuerId;
		this.investorId = investorId;
		this.paymentDirection = paymentDirection;
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

	public String getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(String issuerId) {
		this.issuerId = issuerId;
	}

	public String getInvestorId() {
		return investorId;
	}

	public void setInvestorId(String investorId) {
		this.investorId = investorId;
	}

	public Integer getPaymentDirection() {
		return paymentDirection;
	}

	public void setPaymentDirection(Integer paymentDirection) {
		this.paymentDirection = paymentDirection;
	}

	public Map<CashflowData, AllocationData> getTrancheData() {
		return trancheData;
	}    
    
}
