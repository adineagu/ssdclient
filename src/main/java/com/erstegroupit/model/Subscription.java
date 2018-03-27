/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erstegroupit.model;

import com.erstegroupit.InjectorContext;
import java.time.LocalDate;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author User
 */
public class Subscription {

    private final DataModel dataModel = InjectorContext.getInjector().getInstance(DataModel.class);
    
    private StringProperty subscriptionId;
    private StringProperty investorId;
    private StringProperty investorName;
    private StringProperty trancheId;
    private ObjectProperty<LocalDate> initDate;
    private IntegerProperty targetAmount;
    private DoubleProperty spreadLimit;

    public Subscription(SubscriptionData data) {
        this.subscriptionId = new SimpleStringProperty(data.getSubscriptionId());
        this.trancheId = new SimpleStringProperty(data.getTrancheId());
        this.investorId = new SimpleStringProperty(data.getInvestorId());
        this.investorName = new SimpleStringProperty(dataModel.getInvestors().get(Integer.parseInt(data.getInvestorId())));
        this.initDate = new SimpleObjectProperty<>(data.getInitDate());
        this.targetAmount = new SimpleIntegerProperty(data.getTargetAmount());
        this.spreadLimit = new SimpleDoubleProperty(data.getSpreadLimit());
    }

    public StringProperty getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(StringProperty subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public StringProperty getInvestorId() {
        return investorId;
    }

    public void setInvestorId(StringProperty investorId) {
        this.investorId = investorId;
    }

    public StringProperty getInvestorName() {
        return investorName;
    }

    public void setInvestorName(StringProperty investorName) {
        this.investorName = investorName;
    }

    public StringProperty getTrancheId() {
        return trancheId;
    }

    public void setTrancheId(StringProperty trancheId) {
        this.trancheId = trancheId;
    }

    public ObjectProperty<LocalDate> getInitDate() {
        return initDate;
    }

    public void setInitDate(ObjectProperty<LocalDate> initDate) {
        this.initDate = initDate;
    }

    public IntegerProperty getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(IntegerProperty targetAmount) {
        this.targetAmount = targetAmount;
    }

    public DoubleProperty getSpreadLimit() {
        return spreadLimit;
    }

    public void setSpreadLimit(DoubleProperty spreadLimit) {
        this.spreadLimit = spreadLimit;
    }

}
