/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erstegroupit.hyperledger.javafxclient.controller;

import com.erstegroupit.hyperledger.javafxclient.InjectorContext;
import com.erstegroupit.hyperledger.javafxclient.model.Allocation;
import com.erstegroupit.hyperledger.javafxclient.model.AllocationData;
import com.erstegroupit.hyperledger.javafxclient.model.Cashflow;
import com.erstegroupit.hyperledger.javafxclient.model.CashflowData;
import com.erstegroupit.hyperledger.javafxclient.model.DataModel;
import com.erstegroupit.hyperledger.javafxclient.model.Deal;
import com.erstegroupit.hyperledger.javafxclient.model.DealData;
import com.erstegroupit.hyperledger.javafxclient.model.Subscription;
import com.erstegroupit.hyperledger.javafxclient.model.SubscriptionData;
import com.erstegroupit.hyperledger.javafxclient.model.Tranche;
import com.erstegroupit.hyperledger.javafxclient.model.TrancheData;
import com.erstegroupit.hyperledger.javafxclient.restclient.CreateDealResponse;
import com.erstegroupit.hyperledger.javafxclient.restclient.SSDRestClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javax.inject.Singleton;

/**
 *
 * @author H50UDBB
 */
@Singleton
public class CommonController {

    private final DataModel dataModel;
    private final SSDRestClient restClient;

    public CommonController() {
        dataModel = InjectorContext.getInjector().getInstance(DataModel.class);
        restClient = InjectorContext.getInjector().getInstance(SSDRestClient.class);
    }

    public void authenticate(String user, String organization) {
        restClient.authenticate(user, organization);
    }

    public void deleteData() {
        dataModel.deleteData();
    }

    public void readDeals() {
        CreateDealResponse resp = restClient.readDeals();

        JsonParser parser = new JsonParser();
        JsonElement jsonTree = parser.parse(resp.getPayload());

        JsonArray elements = jsonTree.getAsJsonArray();
        for (JsonElement element : elements) {
            JsonObject object = element.getAsJsonObject();

            System.out.println(object.get("Key"));
            System.out.println(object.get("Record"));

            DealData dealData = createDealFromJson(object.getAsJsonObject("Record"));

            if (dataModel.getClientType().equals("ISSUER") && dataModel.getClientId().equals(dealData.getIssuerId().toString())
                    || !dataModel.getClientType().equals("ISSUER")) {
                dataModel.getDeals().add(new Deal(dealData));
            }
        }
    }

    private DealData createDealFromJson(JsonObject deal) {
        Integer minSubscriptionAmount = 10000;

        String dealId = deal.get("deal_uuid").getAsString();
        Integer issuerId = deal.get("issuer_uuid").getAsInt();
        LocalDate issueDate = LocalDate.parse(deal.get("init_date").getAsString(), DateTimeFormatter.ISO_DATE_TIME);
        Integer issuedAmount = deal.get("total_amount").getAsInt();
        JsonArray tranchesId = deal.getAsJsonArray("tranches");

        DealData dealData = new DealData(dealId, issuerId, issuedAmount, issueDate, minSubscriptionAmount);

        if (tranchesId != null && !tranchesId.isJsonNull() && tranchesId.size() > 0) {
            for (JsonElement trancheIdObj : tranchesId) {
                TrancheData trancheData = readTranche(trancheIdObj.getAsString(), dealId, issuerId);
                dealData.getTrancheData().add(trancheData);
            }
        }

        return dealData;
    }

    public TrancheData readTranche(String trancheId, String dealId, Integer issuerId) {
        CreateDealResponse resp = restClient.readTranche(trancheId);
        System.out.println("Tranche data payload is: " + resp.getPayload().getClass().getName());
        System.out.println("Tranche data is: " + resp.getPayload());

        JsonParser parser = new JsonParser();
        JsonElement jsonTree = parser.parse(resp.getPayload());

        JsonObject jsonTranche = jsonTree.getAsJsonObject();

        LocalDate issueDate = LocalDate.parse(jsonTranche.get("start_date").getAsString(), DateTimeFormatter.ISO_DATE_TIME);
        LocalDate repaymentDate = LocalDate.parse(jsonTranche.get("repayment_date").getAsString(), DateTimeFormatter.ISO_DATE_TIME);
        Integer issuedAmount = jsonTranche.get("amount").getAsInt();
        String conditions = jsonTranche.get("ref_index").getAsString();
        Double margin = jsonTranche.get("margin").getAsDouble();
        boolean signedByIssuer = jsonTranche.get("issuer_signature").getAsBoolean();
        boolean signedByInvestor = jsonTranche.get("investor_signature").getAsBoolean();
        boolean signedByArranger = jsonTranche.get("arranger_signature").getAsBoolean();

        JsonArray subscriptionsId = jsonTranche.getAsJsonArray("subscriptions");
        JsonArray allocationsId = jsonTranche.getAsJsonArray("allocations");
        JsonArray cashflowsId = jsonTranche.getAsJsonArray("cashflows");

        TrancheData trancheData = new TrancheData(trancheId, dealId, issuerId, issuedAmount, issueDate, repaymentDate, conditions, margin, signedByIssuer, signedByInvestor, signedByArranger);

        for (JsonElement subscriptionId : subscriptionsId) {
            SubscriptionData subscriptionData = readSubscription(subscriptionId.getAsString(), trancheId);

            if (dataModel.getClientType().equals("INVESTOR") && subscriptionData.getInvestorId().equals(dataModel.getClientId().toString())
                    || !dataModel.getClientType().equals("INVESTOR")) {
                trancheData.getSubscriptionData().add(subscriptionData);
            }
        }

        for (JsonElement allocationIdObj : allocationsId) {
            AllocationData allocationData = readAllocation(allocationIdObj.getAsString(), trancheId);

            if (dataModel.getClientType().equals("INVESTOR") && allocationData.getInvestorId().equals(dataModel.getClientId().toString())
                    || !dataModel.getClientType().equals("INVESTOR")) {
                trancheData.getAllocationData().add(allocationData);
            }
        }

        for (JsonElement cashflowIdObj : cashflowsId) {
            CashflowData cashflowData = readCashflow(cashflowIdObj.getAsString(), trancheId);

            if (!dataModel.getClientType().equals("INVESTOR") && cashflowData != null) {
                trancheData.getCashflowData().add(cashflowData);
            }
        }
        
        return trancheData;
    }

    public SubscriptionData readSubscription(String subscriptionId, String trancheId) {
        CreateDealResponse resp = restClient.readSubscription(subscriptionId);
        System.out.println("Subscrption data payload is: " + resp.getPayload().getClass().getName());
        System.out.println("Subscrption data is: " + resp.getPayload());

        JsonParser parser = new JsonParser();
        JsonElement jsonTree = parser.parse(resp.getPayload());

        JsonObject jsonTranche = jsonTree.getAsJsonObject();

        LocalDate issueDate = LocalDate.parse(jsonTranche.get("init_date").getAsString(), DateTimeFormatter.ISO_DATE_TIME);
        Integer issuedAmount = jsonTranche.get("amount").getAsInt();
        String investorId = jsonTranche.get("investor_id").getAsString();
        Double spreadLimit = jsonTranche.get("spread_limit").getAsDouble();

        return new SubscriptionData(subscriptionId, investorId, trancheId, issueDate, issuedAmount, spreadLimit);
    }

    public AllocationData readAllocation(String allocationId, String trancheId) {
        CreateDealResponse resp = restClient.readAllocation(allocationId);
        System.out.println("Allocation data payload is: " + resp.getPayload().getClass().getName());
        System.out.println("Allocation data is: " + resp.getPayload());

        JsonParser parser = new JsonParser();
        JsonElement jsonTree = parser.parse(resp.getPayload());

        JsonObject jsonTranche = jsonTree.getAsJsonObject();

        LocalDate issueDate = LocalDate.parse(jsonTranche.get("init_date").getAsString(), DateTimeFormatter.ISO_DATE_TIME);
        Integer issuedAmount = jsonTranche.get("allocation_amount").getAsInt();
        String investorId = jsonTranche.get("investor_id").getAsString();

        return new AllocationData(allocationId, investorId, trancheId, issueDate, issuedAmount, investorId);
    }
    
    public CashflowData readCashflow(String cashflowId, String trancheId) {
        CreateDealResponse resp = restClient.readAllocation(cashflowId);
        System.out.println("Cashflow data payload is: " + resp.getPayload().getClass().getName());
        System.out.println("Cashflow data is: " + resp.getPayload());

        JsonParser parser = new JsonParser();
        JsonElement jsonTree = parser.parse(resp.getPayload());

        JsonObject jsonCashflow = jsonTree.getAsJsonObject();
        
        
        LocalDate adjustedDate = LocalDate.parse(jsonCashflow.get("adjusted_date").getAsString(), DateTimeFormatter.ISO_DATE_TIME);
        Double rate = jsonCashflow.get("rate").getAsDouble();
        String type = jsonCashflow.get("cashflow_type").getAsString();
        String currency = jsonCashflow.get("currency").getAsString();
        Double amount = jsonCashflow.get("amount").getAsDouble();

        return new CashflowData(cashflowId, trancheId, adjustedDate, rate, type, currency, amount);
        
    }

    public ObservableValue<Deal> getSelectedDeal() {
        return dataModel.getSelectedDeal();
    }

    public void setSelectedDeal(ObservableValue<Deal> selectedDeal) {
        dataModel.setSelectedDeal(selectedDeal);
    }

    public ObservableValue<Tranche> getSelectedTranche() {
        return dataModel.getSelectedTranche();
    }

    public void setSelectedTranche(ObservableValue<Tranche> selectedTranche) {
        dataModel.setSelectedTranche(selectedTranche);
    }

    public ObservableValue<Subscription> getSelectedSubscription() {
        return dataModel.getSelectedSubscription();
    }

    public void setSelectedSubscription(ObservableValue<Subscription> selectedSubscription) {
        dataModel.setSelectedSubscription(selectedSubscription);
    }

    public Map<Integer, String> getIssuers() {
        return dataModel.getIssuers();
    }

    public Map<Integer, String> getInvestors() {
        return dataModel.getInvestors();
    }

    public Integer getIssuerId(String issuerName) {
        return dataModel.getIssuerId(issuerName);
    }

    public Integer getInvestorId(String investorName) {
        return dataModel.getInvestorId(investorName);
    }

    public ObservableList<Deal> getDeals() {
        return dataModel.getDeals();
    }

    public ObservableList<Tranche> getTranches() {
        return dataModel.getTranches();
    }

    public ObservableList<Subscription> getSubscriptions() {
        return dataModel.getSubscriptions();
    }

    public ObservableList<Allocation> getAllocations() {
        return dataModel.getAllocations();
    }

    public ObservableList<Cashflow> getCashflows() {
        return dataModel.getCashflows();
    }
    
    public void setDealIsSelected(Boolean status) {
        dataModel.setDealIsSelected(status);
    }

    public void setSubscriptionIsSelected(Boolean status) {
        dataModel.setSubscriptionIsSelected(status);
    }

    public void setTrancheIsSelected(Boolean status) {
        dataModel.setTrancheIsSelected(status);
    }

    public ObservableValue<Boolean> getDealIsSelected() {
        return dataModel.getDealIsSelected();
    }

    public SimpleBooleanProperty getTrancheIsSelected() {
        return dataModel.getTrancheIsSelected();
    }

    public String getClientId() {
        return dataModel.getClientId();
    }

    public void setClientId(String clientId) {
        dataModel.setClientId(clientId);
    }

    public String getClientType() {
        return dataModel.getClientType();
    }

    public void setClientType(String clientType) {
        dataModel.setClientType(clientType);
    }

    public String getImageLogoPath() {
        return dataModel.getImageLogoPath();
    }
    
}
