/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erstegroupit.hyperledger.javafxclient.model;

import com.erstegroupit.hyperledger.javafxclient.data.DataProvider;
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

    private String clientId;
    private String clientType;

    private static final Map<Integer, String> issuerMap = new HashMap<>();
    private static final Map<Integer, String> investorMap = new HashMap<>();
    
    private final Map<String, DealData> dealMap = new HashMap<>();
    private final Map<String, TrancheData> trancheMap = new HashMap<>();
    private final Map<String, SubscriptionData> subscriptionMap = new HashMap<>();
    private final Map<String, AllocationData> allocationMap = new HashMap<>();
    private final Map<String, CashflowData> cashflowMap = new HashMap<>();
    private final Map<String, PaymentData> paymentMap = new HashMap<>();

    private ObservableValue<Deal> selectedDeal;
    private ObservableValue<Tranche> selectedTranche;
    private ObservableValue<Subscription> selectedSubscription;
    private ObservableValue<Allocation> selectedAllocation;
    private ObservableValue<Payment> selectedPayment;

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
        cleanObjectMaps();
    }

	private void cleanObjectMaps() {
		dealMap.clear();
        trancheMap.clear();
        subscriptionMap.clear();
        allocationMap.clear();
        cashflowMap.clear();
	}

    public ObservableList<Deal> getDeals() {
        return dataProvider.getDealsObsList();
    }

	public ObservableList<Payment> getPayments() {
        return dataProvider.getPaymentsObsList();
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

    public ObservableList<Cashflow> getCashflows() {
        return dataProvider.getCashflowsObsList();
    }
    
    public ObservableList<PaymentCashflow> getPaymentCashflows() {
        return dataProvider.getPaymentCashflowsObsList();
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
    
    public void setSelectedPayment(ObservableValue<Payment> selectedPayment) {
        this.selectedPayment = selectedPayment;
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

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public Map<String, DealData> getDealMap() {
		return dealMap;
	}

	public Map<String, TrancheData> getTrancheMap() {
		return trancheMap;
	}

	public Map<String, SubscriptionData> getSubscriptionMap() {
		return subscriptionMap;
	}

	public Map<String, AllocationData> getAllocationMap() {
		return allocationMap;
	}

	public Map<String, CashflowData> getCashflowMap() {
		return cashflowMap;
	}
	
	public Map<String, PaymentData> getPaymentMap() {
		return paymentMap;
	}

	public String getImageLogoPath() {
        String path = "";
        if (getClientType().equals("ISSUER")) {
            if ("1001".equals(getClientId())) {
                path = getClass().getResource("/com/erstegroupit/hyperledger/javafxclient/view/dbahn.png").toString();
            } else if ("1002".equals(getClientId())) {
                path = getClass().getResource("/com/erstegroupit/hyperledger/javafxclient/view/billa.png").toString();
            } else if ("1003".equals(getClientId())) {
                path = getClass().getResource("/com/erstegroupit/hyperledger/javafxclient/view/omv.png").toString();
            }
        } else if (getClientType().equals("INVESTOR")) {
            if ("1001".equals(getClientId())) {
                path = getClass().getResource("/com/erstegroupit/hyperledger/javafxclient/view/jpm.jpg").toString();
            } else if ("1002".equals(getClientId())) {
                path = getClass().getResource("/com/erstegroupit/hyperledger/javafxclient/view/blackrock.png").toString();
            } else if ("1003".equals(getClientId())) {
                path = getClass().getResource("/com/erstegroupit/hyperledger/javafxclient/view/pioneer.png").toString();
            }
        }

        return path;
    }
        
}
