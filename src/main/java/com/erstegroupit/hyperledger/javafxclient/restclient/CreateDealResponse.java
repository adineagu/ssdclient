/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erstegroupit.hyperledger.javafxclient.restclient;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author User
 */
public class CreateDealResponse {
    @JsonProperty("tx_id")
    public String txId;
    @JsonProperty("payload")
    public String payload;

    public CreateDealResponse() {
    }

    public CreateDealResponse(String txId, String payload) {
        this.txId = txId;
        this.payload = payload;
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "CreateDealResponse{" + "txId=" + txId + ", payload=" + payload + '}';
    }    
    
}
