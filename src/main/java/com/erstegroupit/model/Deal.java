/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erstegroupit.model;

import com.erstegroupit.InjectorContext;
import java.time.LocalDate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 *
 * @author H50UDBB
 */
public class Deal {
    
    private final DataModel dataModel = InjectorContext.getInjector().getInstance(DataModel.class);
    
    private StringProperty dealId;
    private IntegerProperty issuerId;
    private StringProperty issuerName;
    private ObjectProperty<LocalDate> issueDate;
    private ObjectProperty<LocalDate> expiryDate;    
    private StringProperty arrangerName; 
    private final StringProperty purpose;    
    private final StringProperty type;        
    private final IntegerProperty issuedAmount;    
    private final IntegerProperty subscriptionMinAmount;
    private final StringProperty currencyCd;
    private IntegerProperty paidOffAmount;
    
    private DealData dealData;

    public Deal(DealData data) {
        this.dealId = new SimpleStringProperty(data.getDealId());
        this.issuerId = new SimpleIntegerProperty(data.getIssuerId());
        this.issuerName = new SimpleStringProperty(dataModel.getIssuers().get(data.getIssuerId()));
        this.issueDate = new SimpleObjectProperty<>(data.getIssueDate());
        this.expiryDate = new SimpleObjectProperty<>(data.getExpiryDate());
        this.issuedAmount = new SimpleIntegerProperty(data.getIssuedAmount());
        this.subscriptionMinAmount = new SimpleIntegerProperty(data.getSubscriptionMinAmount());
        
        this.purpose = new SimpleStringProperty("Allgemeine Unternehmensfinanzierung und Refinanzierung");
        this.type = new SimpleStringProperty("Schuldschein Darlehen, durch Abtretung jederzeit Verfügbar");
        this.arrangerName = new SimpleStringProperty("Erste Group Bank");
        this.currencyCd  = new SimpleStringProperty("EUR");
        this.paidOffAmount = new SimpleIntegerProperty(0);
        this.dealData = data;
    }
    
    public Deal(String dealId, Integer issuerId, LocalDate issuerDate, LocalDate expiryDate, Integer issuedAmount, Integer subscriptionMinAmount) {
        this(issuerId, issuerDate, expiryDate, issuedAmount, subscriptionMinAmount);
        this.dealId = new SimpleStringProperty(dealId);
    }
    
    public Deal(Integer issuerId, LocalDate issuerDate, LocalDate expiryDate, Integer issuedAmount, Integer subscriptionMinAmount) {        
        this.issuerId = new SimpleIntegerProperty(issuerId);
        this.issuerName = new SimpleStringProperty(dataModel.getIssuers().get(issuerId));
        this.issueDate = new SimpleObjectProperty<>(issuerDate);
        this.expiryDate = new SimpleObjectProperty<>(expiryDate);
        this.issuedAmount = new SimpleIntegerProperty(issuedAmount);
        this.subscriptionMinAmount = new SimpleIntegerProperty(subscriptionMinAmount);
        
        this.purpose = new SimpleStringProperty("Allgemeine Unternehmensfinanzierung und Refinanzierung");
        this.type = new SimpleStringProperty("Schuldschein Darlehen, durch Abtretung jederzeit Verfügbar");
        this.arrangerName = new SimpleStringProperty("Erste Group Bank");
        this.currencyCd  = new SimpleStringProperty("EUR");
        this.paidOffAmount = new SimpleIntegerProperty(0);        
    }

    public StringProperty dealIdProperty() {
        return dealId;
    }
    
    public IntegerProperty issuerIdProperty() {
        return issuerId;
    }
    
    public StringProperty issuerNameProperty() {
        return issuerName;
    }    
    
    public ObjectProperty<LocalDate> issueDateProperty() {
        return issueDate;
    }

    public ObjectProperty<LocalDate> expiryDateProperty() {
        return expiryDate;
    }

    public String getType() {
        return type.get();
    }

    public void setType(String value) {
        type.set(value);
    }

    public StringProperty typeProperty() {
        return type;
    }    
    
    public String getPurpose() {
        return purpose.get();
    }

    public void setPurpose(String value) {
        purpose.set(value);
    }

    public StringProperty purposeProperty() {
        return purpose;
    }
    
    public int getIssuedAmount() {
        return issuedAmount.get();
    }

    public void setIssuedAmount(int value) {
        issuedAmount.set(value);
    }

    public IntegerProperty issuedAmountProperty() {
        return issuedAmount;
    }    
    
    public String getCurrencyCd() {
        return currencyCd.get();
    }

    public void setCurrencyCd(String value) {
        currencyCd.set(value);
    }

    public StringProperty currencyCdProperty() {
        return currencyCd;
    }
    
    public int getSubscriptionMinAmount() {
        return subscriptionMinAmount.get();
    }

    public void setSubscriptionMinAmount(int value) {
        subscriptionMinAmount.set(value);
    }

    public IntegerProperty subscriptionMinAmountProperty() {
        return subscriptionMinAmount;
    }    

    public StringProperty getDealId() {
        return dealId;
    }

    public void setDealId(StringProperty dealId) {
        this.dealId = dealId;
    }
    
    
    public String getIssuerId() {
        return dealId.get();
    }

    public void setIssuerId(String issuerId) {
        this.dealId.set(issuerId);
    }    

    public String getUsageType() {
        return purpose.get();
    }

    public void setUsageType(String usageType) {
        this.purpose.set(usageType);
    }

    public LocalDate getIssueDate() {
        return issueDate.get();
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate.set(issueDate);
    }

    public LocalDate getExpiryDate() {
        return expiryDate.get();
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate.set(expiryDate);
    }

    public Integer getIssuer() {
        return issuerId.get();
    }

    public void setIssuer(Integer issuer) {
        this.issuerId.set(issuer);
    }

    public String getArranger() {
        return arrangerName.get();
    }

    public void setArranger(String arranger) {
        this.arrangerName.set(arranger);
    }

    public void setSubscriptionMinAmount(Integer subscriptionMinAmount) {
        this.subscriptionMinAmount.set(subscriptionMinAmount);
    }

    public Integer getPaidOffAmount() {
        return paidOffAmount.get();
    }

    public void setPaidOffAmount(Integer paidOffAmount) {
        this.paidOffAmount.set(paidOffAmount);
    }

    public DealData getDealData() {
        return dealData;
    }

    public void setDealData(DealData dealData) {
        this.dealData = dealData;
    }
    
    public void setTranchesList(ObservableList<Tranche> list) {
        list.clear();
        
        if (dealData.getTrancheData() == null || dealData.getTrancheData().isEmpty()) {
            return;
        }
        
        for (TrancheData trancheData : dealData.getTrancheData()) {
            list.add(new Tranche(trancheData));
        }        
    }

    @Override
    public String toString() {
        return "Deal{" + "dealId=" + dealId + ", issuerId=" + issuerId + ", issuerName=" + issuerName + ", issueDate=" + issueDate + ", expiryDate=" + expiryDate + ", arrangerName=" + arrangerName + ", purpose=" + purpose + ", type=" + type + ", issuedAmount=" + issuedAmount + ", subscriptionMinAmount=" + subscriptionMinAmount + ", currencyCd=" + currencyCd + ", paidOffAmount=" + paidOffAmount + '}';
    }
    
    
    
}
