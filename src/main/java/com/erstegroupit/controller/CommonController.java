/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erstegroupit.controller;

import com.erstegroupit.InjectorContext;
import com.erstegroupit.model.Allocation;
import com.erstegroupit.model.DataModel;
import com.erstegroupit.model.Deal;
import com.erstegroupit.model.DealData;
import com.erstegroupit.model.Subscription;
import com.erstegroupit.model.SubscriptionData;
import com.erstegroupit.model.Tranche;
import com.erstegroupit.model.TrancheData;
import com.erstegroupit.restclient.CreateDealResponse;
import com.erstegroupit.restclient.SSDRestClient;
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

    public void authenticate() {
        restClient.authenticate();
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
            dataModel.getDeals().add(new Deal(dealData));
        }        
    }

    private DealData createDealFromJson(JsonObject deal) {
        Integer minSubscriptionAmount = 10000;

        String dealId = deal.get("deal_uuid").getAsString();
        String issuerName = deal.get("issuer").getAsString();
        Integer issuerId = dataModel.getIssuerId(issuerName);
        LocalDate issueDate = LocalDate.parse(deal.get("StartDate").getAsString(), DateTimeFormatter.ISO_DATE_TIME);
        LocalDate expiryDate = LocalDate.parse(deal.get("expiry_date").getAsString(), DateTimeFormatter.ISO_DATE_TIME);
        Integer issuedAmount = deal.get("amount").getAsInt();
        JsonArray tranchesId = deal.getAsJsonArray("tranches");

        DealData dealData = new DealData(dealId, issuerId, issuedAmount, issueDate, expiryDate, minSubscriptionAmount);

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
        Integer issuedAmount = jsonTranche.get("amount").getAsInt();
        String conditions = jsonTranche.get("conditions").getAsString();
        
        JsonArray subscriptionsId = jsonTranche.getAsJsonArray("subscriptions");
        
        TrancheData trancheData = new TrancheData(trancheId, dealId, issuerId, issuedAmount, issueDate, issueDate, conditions, 0);
        
        for (JsonElement subscriptionId : subscriptionsId) {
            SubscriptionData subscriptionData = readSubscription(subscriptionId.getAsString(), trancheId);
            trancheData.getSubscriptionData().add(subscriptionData);
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

}
