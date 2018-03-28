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

/**
 *
 * @author H50UDBB
 */
public class Allocation {

    private final DataModel dataModel = InjectorContext.getInjector().getInstance(DataModel.class);  
    private final StringProperty allocationId;
    private final StringProperty investorId;
    private final StringProperty investorName;
    private final StringProperty trancheId;
    private final ObjectProperty<LocalDate> initDate;
    private final IntegerProperty allocatedAmount;
    private final StringProperty status;

    public Allocation(AllocationData data) {
        this.allocationId = new SimpleStringProperty(data.getAllocationId());
        this.trancheId = new SimpleStringProperty(data.getTrancheId());
        this.investorId = new SimpleStringProperty(data.getInvestorId());
        this.investorName = new SimpleStringProperty(dataModel.getInvestors().get(Integer.parseInt(data.getInvestorId())));
        this.initDate = new SimpleObjectProperty<>(data.getInitDate());
        this.allocatedAmount = new SimpleIntegerProperty(data.getAllocationAmount());
        this.status = new SimpleStringProperty("");
    }
        
    public String getStatus() {
        return status.get();
    }

    public void setStatus(String value) {
        status.set(value);
    }

    public StringProperty statusProperty() {
        return status;
    }    

    public int getAllocatedAmount() {
        return allocatedAmount.get();
    }

    public void setAllocatedAmount(int value) {
        allocatedAmount.set(value);
    }

    public IntegerProperty allocatedAmountProperty() {
        return allocatedAmount;
    }    

    public LocalDate getInitDate() {
        return initDate.get();
    }

    public void setInitDate(LocalDate value) {
        initDate.set(value);
    }

    public ObjectProperty initDateProperty() {
        return initDate;
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

    public String getInvestorName() {
        return investorName.get();
    }

    public void setInvestorName(String value) {
        investorName.set(value);
    }

    public StringProperty investorNameProperty() {
        return investorName;
    }    

    public String getInvestorId() {
        return investorId.get();
    }

    public void setInvestorId(String value) {
        investorId.set(value);
    }

    public StringProperty investorIdProperty() {
        return investorId;
    }    

    public String getAllocationId() {
        return allocationId.get();
    }

    public void setAllocationId(String value) {
        allocationId.set(value);
    }

    public StringProperty allocationIdProperty() {
        return allocationId;
    }
    
    public AllocationData getAllocationData() {
        String investorId = this.investorId.getValue();
        String trancheId = this.trancheId.getValue();
        LocalDate initDate = (LocalDate) this.initDateProperty().getValue();
        Integer amount =  allocatedAmountProperty().getValue();
        String status = this.status.getValue();
        return new AllocationData(investorId, trancheId, initDate, amount, status);
    }    
    
}
