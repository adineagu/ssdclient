/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erstegroupit.hyperledger.javafxclient.model;

import java.time.LocalDate;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 *
 * @author User
 */
public class Tranche {

    private StringProperty trancheId = new SimpleStringProperty();
    private StringProperty dealId = new SimpleStringProperty();
    private IntegerProperty issuerId = new SimpleIntegerProperty();
    private IntegerProperty trancheAmount = new SimpleIntegerProperty();
    private ObjectProperty<LocalDate> trancheDate = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDate> repaymentDate = new SimpleObjectProperty<>();
    private StringProperty referenceIndexColumn = new SimpleStringProperty();
    private DoubleProperty marginColumn = new SimpleDoubleProperty();
    private StringProperty creationDate = new SimpleStringProperty();
    private BooleanProperty signedByIssuer = new SimpleBooleanProperty();
    private BooleanProperty signedByInvestor = new SimpleBooleanProperty();
    private BooleanProperty signedByArranger = new SimpleBooleanProperty();

    public boolean isSignedByArranger() {
        return signedByArranger.get();
    }

    public void setSignedByArranger(boolean value) {
        signedByArranger.set(value);
    }

    public BooleanProperty signedByArrangerProperty() {
        return signedByArranger;
    }    

    public boolean isSignedByInvestor() {
        return signedByInvestor.get();
    }

    public void setSignedByInvestor(boolean value) {
        signedByInvestor.set(value);
    }

    public BooleanProperty signedByInvestorProperty() {
        return signedByInvestor;
    }
    

    public boolean isSignedByIssuer() {
        return signedByIssuer.get();
    }

    public void setSignedByIssuer(boolean value) {
        signedByIssuer.set(value);
    }

    public BooleanProperty signedByIssuerProperty() {
        return signedByIssuer;
    }
    
    private final TrancheData trancheData;

    public Tranche(TrancheData data) {
        this.trancheData = data;
        
        this.trancheId = new SimpleStringProperty(data.getTrancheId());
        this.dealId = new SimpleStringProperty(data.getDealId());
        this.issuerId = new SimpleIntegerProperty(data.getIssuerId());
        this.trancheDate = new SimpleObjectProperty(data.getTrancheDate());
        this.repaymentDate = new SimpleObjectProperty(data.getRepaymentDate());
        this.trancheAmount = new SimpleIntegerProperty(data.getTrancheAmount());
        this.referenceIndexColumn = new SimpleStringProperty(data.getReferenceIndex());
        this.marginColumn = new SimpleDoubleProperty(data.getMargin());
        this.creationDate = new SimpleStringProperty(data.getCreationDate());
        this.signedByIssuer = new SimpleBooleanProperty(data.isSignedByIssuer());
        this.signedByInvestor = new SimpleBooleanProperty(data.isSignedByInvestor());
        this.signedByArranger = new SimpleBooleanProperty(data.isSignedByArranger());
    }        

    public LocalDate getRepaymentDate() {
        return repaymentDate.get();
    }

    public void setRepaymentDate(LocalDate value) {
        repaymentDate.set(value);
    }

    public ObjectProperty repaymentDateProperty() {
        return repaymentDate;
    }

    
    public double getMarginColumn() {
        return marginColumn.get();
    }

    public void setMarginColumn(double value) {
        marginColumn.set(value);
    }

    public DoubleProperty marginColumnProperty() {
        return marginColumn;
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
    
    public String getCreationDate() {
        return creationDate.get();
    }

    public void setCreationDate(String value) {
        creationDate.set(value);
    }

    public StringProperty creationDateProperty() {
        return creationDate;
    }
    
    public String getReferenceIndex() {
        return referenceIndexColumn.get();
    }

    public void setReferenceIndex(String value) {
        referenceIndexColumn.set(value);
    }

    public StringProperty referenceIndexProperty() {
        return referenceIndexColumn;
    }
    

    public int getTrancheAmount() {
        return trancheAmount.get();
    }

    public void setTrancheAmount(int value) {
        trancheAmount.set(value);
    }

    public IntegerProperty trancheAmountProperty() {
        return trancheAmount;
    }
    

    public LocalDate getTrancheDate() {
        return trancheDate.get();
    }

    public void setTrancheDate(LocalDate value) {
        trancheDate.set(value);
    }

    public ObjectProperty trancheDateProperty() {
        return trancheDate;
    }
        
    public int getIssuerId() {
        return issuerId.get();
    }

    public void setIssuerId(int value) {
        issuerId.set(value);
    }

    public IntegerProperty issuerIdProperty() {
        return issuerId;
    }

    public String getDealId() {
        return dealId.get();
    }

    public void setDealId(String value) {
        dealId.set(value);
    }

    public StringProperty dealIdProperty() {
        return dealId;
    }

    public TrancheData getTrancheData() {
        return trancheData;
    }
    
    public void setSubscriptionsList(ObservableList<Subscription> list) {
        list.clear();
        
        if (trancheData.getSubscriptionData() == null || trancheData.getSubscriptionData().isEmpty()) {
            return;
        }
        
        for (SubscriptionData subsData : trancheData.getSubscriptionData()) {
            list.add(new Subscription(subsData));
        }        
    }
    
    public void setAllocationsList(ObservableList<Allocation> list) {
        list.clear();
        
        if (trancheData.getAllocationData() == null || trancheData.getAllocationData().isEmpty()) {
            return;
        }
        
        for (AllocationData allocData : trancheData.getAllocationData()) {
            list.add(new Allocation(allocData));
        }        
    }
}
