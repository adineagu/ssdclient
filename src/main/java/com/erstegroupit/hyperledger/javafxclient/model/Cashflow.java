/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erstegroupit.hyperledger.javafxclient.model;

import java.time.LocalDate;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author H50UDBB
 */
public class Cashflow {

    private final StringProperty cashflowId;
    private final StringProperty trancheId;
    private final ObjectProperty<LocalDate> adjustmentDate;
    private final DoubleProperty rate;
    private final StringProperty cashflowType;
    private final StringProperty currency;
    private final DoubleProperty amount;    
    private final CashflowData data;

    /**
     * Get the value of data
     *
     * @return the value of data
     */
    public CashflowData getData() {
        return data;
    }

    public Cashflow(CashflowData data) {        
        this.data = data;
        
        this.cashflowId = new SimpleStringProperty(data.getCashflowId());
        this.trancheId = new SimpleStringProperty(data.getTrancheId());
        this.adjustmentDate = new SimpleObjectProperty<>(data.getAdjustedDate());
        this.rate = new SimpleDoubleProperty(data.getRate());
        this.cashflowType = new SimpleStringProperty(data.getCashflowType());
        this.currency = new SimpleStringProperty(data.getCurrency());
        this.amount = new SimpleDoubleProperty(data.getAmount());
    }
        
    public double getAmount() {
        return amount.get();
    }

    public void setAmount(double value) {
        amount.set(value);
    }

    public DoubleProperty amountProperty() {
        return amount;
    }
    

    public String getCurrency() {
        return currency.get();
    }

    public void setCurrency(String value) {
        currency.set(value);
    }

    public StringProperty currencyProperty() {
        return currency;
    }
    

    public String getCashflowType() {
        return cashflowType.get();
    }

    public void setCashflowType(String value) {
        cashflowType.set(value);
    }

    public StringProperty cashflowTypeProperty() {
        return cashflowType;
    }
    

    public double getRate() {
        return rate.get();
    }

    public void setRate(double value) {
        rate.set(value);
    }

    public DoubleProperty rateProperty() {
        return rate;
    }
    

    public LocalDate getAdjustmentDate() {
        return adjustmentDate.get();
    }

    public void setAdjustmentDate(LocalDate value) {
        adjustmentDate.set(value);
    }

    public ObjectProperty adjustmentDateProperty() {
        return adjustmentDate;
    }
    
    

    public String getTrancheId() {
        return trancheId.get();
    }

    public void setTrancheId(String value) {
        trancheId.set(value);
    }

    public StringProperty trancheIdProperty() {
        return trancheId;
    }
    

    public String getCashflowId() {
        return cashflowId.get();
    }

    public void setCashflowId(String value) {
        cashflowId.set(value);
    }

    public StringProperty cashflowIdProperty() {
        return cashflowId;
    }
        
}
