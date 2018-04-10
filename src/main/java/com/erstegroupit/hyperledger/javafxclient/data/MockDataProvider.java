/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erstegroupit.hyperledger.javafxclient.data;

import com.erstegroupit.hyperledger.javafxclient.model.Allocation;
import com.erstegroupit.hyperledger.javafxclient.model.Cashflow;
import com.erstegroupit.hyperledger.javafxclient.model.Deal;
import com.erstegroupit.hyperledger.javafxclient.model.Payment;
import com.erstegroupit.hyperledger.javafxclient.model.PaymentCashflow;
import com.erstegroupit.hyperledger.javafxclient.model.Subscription;
import com.erstegroupit.hyperledger.javafxclient.model.Tranche;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.inject.Singleton;

/**
 *
 * @author User
 */
@Singleton
public class MockDataProvider implements DataProvider {
    
    private final ObservableList<Deal> deals = FXCollections.observableArrayList();
    private final ObservableList<Tranche> tranches = FXCollections.observableArrayList();
    private final ObservableList<Subscription> subscriptions = FXCollections.observableArrayList();
    private final ObservableList<Allocation> allocations = FXCollections.observableArrayList();
    private final ObservableList<Cashflow> cashflows = FXCollections.observableArrayList();
    private final ObservableList<Payment> payments = FXCollections.observableArrayList();
    private final ObservableList<PaymentCashflow> paymentCashflows = FXCollections.observableArrayList();

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
    public ObservableList<Allocation> getAllocationsObsList() {
        return allocations;
    }
    
    @Override
    public ObservableList<Cashflow> getCashflowsObsList() {
        return cashflows;
    }    
    
	@Override
	public ObservableList<Payment> getPaymentsObsList() {
		return payments;
	}
	
	@Override
	public ObservableList<PaymentCashflow> getPaymentCashflowsObsList() {
		return paymentCashflows;
	}
    
    @Override
    public void cleanData() {
        cashflows.clear();
        allocations.clear();
        subscriptions.clear();
        tranches.clear();
        deals.clear();
        payments.clear();
        paymentCashflows.clear();
    }

            
}
