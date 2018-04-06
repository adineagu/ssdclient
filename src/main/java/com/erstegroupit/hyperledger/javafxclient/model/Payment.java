package com.erstegroupit.hyperledger.javafxclient.model;

import java.time.LocalDate;

import com.erstegroupit.hyperledger.javafxclient.InjectorContext;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Payment {
	
    private final DataModel dataModel = InjectorContext.getInjector().getInstance(DataModel.class);
	
    private final StringProperty paymentId;
    private final ObjectProperty<LocalDate> paymentDate;
    private final StringProperty currency;
    private final DoubleProperty amount;  
    private final StringProperty issuerName;
    private final StringProperty investorName;
    private final IntegerProperty paymentDirection;
    
    public Payment(PaymentData data) {
    	this.paymentId = new SimpleStringProperty(data.getPaymentId());
        this.paymentDate = new SimpleObjectProperty<>(data.getPaymentDate());
        this.currency = new SimpleStringProperty(data.getCurrency());
        this.amount = new SimpleDoubleProperty(data.getAmount());
        this.issuerName = new SimpleStringProperty(dataModel.getIssuers().get(data.getIssuerId()));	
        this.investorName = new SimpleStringProperty(dataModel.getInvestors().get(data.getInvestorId()));	
        this.paymentDirection = new SimpleIntegerProperty(data.getPaymentDirection());
    }
    
	public Payment(StringProperty paymentId, ObjectProperty<LocalDate> paymentDate, StringProperty currency,
			DoubleProperty amount, StringProperty issuerName, StringProperty investorName,
			IntegerProperty paymentDirection) {
		super();
		this.paymentId = paymentId;
		this.paymentDate = paymentDate;
		this.currency = currency;
		this.amount = amount;
		this.issuerName = issuerName;
		this.investorName = investorName;
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
	
	public StringProperty getIssuerNameProperty() {
		return issuerName;
	}
	
	public StringProperty getInvestorNameProperty() {
		return investorName;
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
	
	public String getIssuerName() {
		return issuerName.get();
	}
	
	public String getInvestorName() {
		return investorName.get();
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
	
	public void setIssuerName(String value) {
		 issuerName.set(value);
	}
	
	public void setInvestorName(String value) {
		 investorName.set(value);
	}
	
	public void setPaymentDirection(Integer value) {
		 paymentDirection.set(value);
	}
	
    


}
