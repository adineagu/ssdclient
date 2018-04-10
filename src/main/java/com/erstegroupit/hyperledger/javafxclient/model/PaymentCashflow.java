package com.erstegroupit.hyperledger.javafxclient.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
*
* @author H502QOB
* 
* GUI only component to display cashflow and portion of this cashflow belonging to given investor (tranche stake) 
*/
public class PaymentCashflow extends Cashflow{

    private final StringProperty allocationId;
    private final DoubleProperty trancheStake;
	
	public PaymentCashflow(CashflowData cashflowData, AllocationData allocationData, TrancheData trancheData) {
		super(cashflowData);
        this.allocationId = new SimpleStringProperty(allocationData.getAllocationId());
        this.trancheStake = new SimpleDoubleProperty(allocationData.getAllocationAmount().doubleValue() / trancheData.getTrancheAmount().doubleValue());
	}

	public StringProperty getAllocationIdProperty() {
		return allocationId;
	}

	public DoubleProperty getTrancheStakeProperty() {
		return trancheStake;
	}

	public String getAllocationId() {
		return allocationId.get();
	}
	
	public double getTrancheStake() {
		return trancheStake.get();
	}

	public void setAllocationId(String value) {
		allocationId.set(value);
	}
	
	public void setTrancheStake(Double value) {
		 trancheStake.set(value);
	}
	
}
