/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erstegroupit.hyperledger.javafxclient.data;

import com.erstegroupit.hyperledger.javafxclient.model.Allocation;
import com.erstegroupit.hyperledger.javafxclient.model.AllocationData;
import com.erstegroupit.hyperledger.javafxclient.model.Cashflow;
import com.erstegroupit.hyperledger.javafxclient.model.Deal;
import com.erstegroupit.hyperledger.javafxclient.model.DealData;
import com.erstegroupit.hyperledger.javafxclient.model.Subscription;
import com.erstegroupit.hyperledger.javafxclient.model.SubscriptionData;
import com.erstegroupit.hyperledger.javafxclient.model.Tranche;
import com.erstegroupit.hyperledger.javafxclient.model.TrancheData;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author User
 */
public interface DataProvider {
      
    public ObservableList<Deal> getDealsObsList();
    public ObservableList<Tranche> getTranchesObsList();
    public ObservableList<Subscription> getSubscriptionsObsList();
    public ObservableList<Allocation> getAllocationsObsList();
    public ObservableList<Cashflow> getCashflowsObsList();
    
    public void cleanData();
}
