/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erstegroupit.restclient;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author User
 */
public class BlockchainDeal {
    @JsonProperty("Key")
    private String key;
    
    @JsonProperty("Record")
    private BlockchainDealInfo record;

    public BlockchainDeal() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public BlockchainDealInfo getRecord() {
        return record;
    }

    public void setRecord(BlockchainDealInfo record) {
        this.record = record;
    }
}
