/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erstegroupit.model;

import com.erstegroupit.data.DataProvider;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 *
 * @author H50UDBB
 */
@Singleton 
public class DataModel {
        
    private static final Map<Integer, String> issuerMap = new HashMap<>();
    private static final Map<Integer, String> investorMap = new HashMap<>();
            
    private ObservableValue<Deal> selectedDeal;
    private ObservableValue<Tranche> selectedTranche;
    private ObservableValue<Subscription> selectedSubscription;
    private ObservableValue<Allocation> selectedAllocation;
    
    private final SimpleBooleanProperty dealIsSelected = new SimpleBooleanProperty(true);
    private final SimpleBooleanProperty trancheIsSelected = new SimpleBooleanProperty(true);
    private final SimpleBooleanProperty subscriptionIsSelected = new SimpleBooleanProperty(true);
    private final SimpleBooleanProperty allocationIsSelected = new SimpleBooleanProperty(true);     
    
    @Inject
    private DataProvider dataProvider;
        
    public DataModel() {
        initIssuers();
        initInvestors();
    }
    
    public void deleteData() {
        dataProvider.cleanData();
    }
                    
    public ObservableList<Deal> getDeals() {
        return dataProvider.getDealsObsList();
    }

    public ObservableList<Tranche> getTranches() {
        return dataProvider.getTranchesObsList();
    }
    
    public ObservableList<Subscription> getSubscriptions() {
        return dataProvider.getSubscriptionsObsList();
    }
    
    public ObservableList<Allocation> getAllocations() {
        return dataProvider.getAllocationsObsList();
    }
    
    public Map<Integer, String> getIssuers() {
        return issuerMap;
    }
    
    public Map<Integer, String> getInvestors() {
        return investorMap;
    }    
    
    private void initIssuers() {
        issuerMap.put(1001, "Deutsche Bahn");
        issuerMap.put(1002, "Billa Group");
        issuerMap.put(1003, "OMV");
    }
    
    private void initInvestors() {
        investorMap.put(1001, "JPMorgan Intrepid European");
        investorMap.put(1002, "BlackRock EuroFund");
        investorMap.put(1003, "Pioneer");
    }
    
    public ObservableValue<Deal> getSelectedDeal() {
        return selectedDeal;
    }

    public void setSelectedDeal(ObservableValue<Deal> selectedDeal) {
        this.selectedDeal = selectedDeal;
    }

    public ObservableValue<Tranche> getSelectedTranche() {
        return selectedTranche;
    }

    public void setSelectedTranche(ObservableValue<Tranche> selectedTranche) {
        this.selectedTranche = selectedTranche;
    }

    public ObservableValue<Subscription> getSelectedSubscription() {
        return selectedSubscription;
    }

    public void setSelectedSubscription(ObservableValue<Subscription> selectedSubscription) {
        this.selectedSubscription = selectedSubscription;
    }
        
    public ObservableValue<Boolean> getDealIsSelected() {
        return dealIsSelected;
    }

    public void setDealIsSelected(Boolean status) {
        this.dealIsSelected.set(status);
    }

    public SimpleBooleanProperty getTrancheIsSelected() {
        return trancheIsSelected;
    }
    
    public void setTrancheIsSelected(Boolean status) {
        this.trancheIsSelected.set(status);
    }

    public SimpleBooleanProperty getSubscriptionIsSelected() {
        return subscriptionIsSelected;
    }
    
    public void setSubscriptionIsSelected(Boolean status) {
        this.subscriptionIsSelected.set(status);
    }   
    
    public Integer getIssuerId(String issuerName) {
        for (Integer issuerId : issuerMap.keySet()) {
            if (issuerMap.get(issuerId).equals(issuerName)) {
                return issuerId;
            }
        }

        return 999;
    }          
 
    public Integer getInvestorId(String investorName) {
        for (Integer issuerId : investorMap.keySet()) {
            if (investorMap.get(issuerId).equals(investorName)) {
                return issuerId;
            }
        }

        return 999;
    }  
  
}
