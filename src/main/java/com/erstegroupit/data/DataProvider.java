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
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author User
 */
public interface DataProvider {
    public List<DealData> getDealsData();
    public List<TrancheData> getTranchesData(String dealId);
    public List<SubscriptionData> getSubscriptionData();
    public List<AllocationData> getAllocationData();
    
    public ObservableList<Deal> getDealsObsList();
    public ObservableList<Tranche> getTranchesObsList();
    public ObservableList<Subscription> getSubscriptionsObsList();
    public ObservableList<Allocation> getAllocationsObsList();
    
    public void cleanData();
}
