/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erstegroupit.data;

import com.erstegroupit.model.Allocation;
import com.erstegroupit.model.AllocationData;
import com.erstegroupit.model.Deal;
import com.erstegroupit.model.DealData;
import com.erstegroupit.model.Subscription;
import com.erstegroupit.model.SubscriptionData;
import com.erstegroupit.model.Tranche;
import com.erstegroupit.model.TrancheData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.inject.Singleton;

/**
 *
 * @author User
 */
@Singleton
public class MockDataProvider implements DataProvider {
    public final Map<String, List<TrancheData>> tranchesData = new HashMap<>();
    
    private final List<DealData> dealsData = new ArrayList<>();
    private final List<TrancheData> trancheData = new ArrayList<>();
    private final List<SubscriptionData> subscriptionData = new ArrayList<>();
    private final List<AllocationData> allocationData = new ArrayList<>();
    
    private final ObservableList<Deal> deals = FXCollections.observableArrayList();
    private final ObservableList<Tranche> tranches = FXCollections.observableArrayList();
    private final ObservableList<Subscription> subscriptions = FXCollections.observableArrayList();
    private final ObservableList<Allocation> allocations = FXCollections.observableArrayList();

    
    @Override
    public List<DealData> getDealsData() {
        return dealsData;
    }

    @Override
    public List<TrancheData> getTranchesData(String dealId) {
        return tranchesData.get(dealId);
    }

    @Override
    public List<SubscriptionData> getSubscriptionData() {
        return subscriptionData;
    }

    @Override
    public ObservableList<Deal> getDealsObsList() {
        return deals;
    }

    @Override
    public ObservableList<Tranche> getTranchesObsList() {
        return tranches;
    }

    @Override
    public ObservableList<Subscription> getSubscriptionsObsList() {
        return subscriptions;
    }

    @Override
    public List<AllocationData> getAllocationData() {
        return allocationData;
    }

    @Override
    public ObservableList<Allocation> getAllocationsObsList() {
        return allocations;
    }
    
    @Override
    public void cleanData() {
        allocations.clear();
        subscriptions.clear();
        tranches.clear();
        deals.clear();
        
        allocationData.clear();
        subscriptionData.clear();
        tranchesData.clear();
        dealsData.clear();
    }
            
}
