package com.erstegroupit.hyperledger.javafxclient.model;

import java.time.LocalDate;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

public class Payment {
	
    private final StringProperty paymentId;
    private final ObjectProperty<LocalDate> paymentDate;
    private final StringProperty currency;
    private final DoubleProperty amount;  
    private final StringProperty issuerId;
    private final StringProperty investorId;
    private final IntegerProperty paymentDirection;
    
	public Payment(StringProperty paymentId, ObjectProperty<LocalDate> paymentDate, StringProperty currency,
			DoubleProperty amount, StringProperty issuerId, StringProperty investorId,
			IntegerProperty paymentDirection) {
		super();
		this.paymentId = paymentId;
		this.paymentDate = paymentDate;
		this.currency = currency;
		this.amount = amount;
		this.issuerId = issuerId;
		this.investorId = investorId;
		this.paymentDirection = paymentDirection;
	}

	public StringProperty getPaymentIdProperty() {
		 return paymentId;
	}
	
	public ObjectProperty<LocalDate> getPaymentDateProperty() {
		return paymentDate;
	}
	
	public StringProperty getCurrencyProperty() {
		return currency;
	}
	
	public DoubleProperty getAmountProperty() {
		return amount;
	}
	
	public StringProperty getIssuerIdProperty() {
		return issuerId;
	}
	
	public StringProperty getInvestorIdProperty() {
		return investorId;
	}
	
	public IntegerProperty getPaymentDirectionProperty() {
		return paymentDirection;
	}
	
	public String getPaymentId() {
		return paymentId.get();
	}
	
	public LocalDate getPaymentDate() {
		return paymentDate.get();
	}
	
	public String getCurrency() {
		return currency.get();
	}
	
	public double getAmount() {
		return amount.get();
	}
	
	public String getIssuerId() {
		return issuerId.get();
	}
	
	public String getInvestorId() {
		return investorId.get();
	}
	
	public int getPaymentDirection() {
		return paymentDirection.get();
	}
	
	
	public void setPaymentId(String value) {
		 paymentId.set(value);
	}
	
	public void setPaymentDate(LocalDate value) {
		 paymentDate.set(value);
	}
	
	public void setCurrency(String value) {
		 currency.set(value);
	}
	
	public void setAmount(Double value) {
		 amount.set(value);
	}
	
	public void setIssuerId(String value) {
		 issuerId.set(value);
	}
	
	public void setInvestorId(String value) {
		 investorId.set(value);
	}
	
	public void setPaymentDirection(Integer value) {
		 paymentDirection.set(value);
	}
	
    


}
