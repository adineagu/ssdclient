/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erstegroupit.restclient;

/**
 *
 * @author User
 */
public class CreateDealRestParameters {
    private String issuerId;

    public CreateDealRestParameters(String issuerId) {
        this.issuerId = issuerId;
    }

    public String getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(String issuerId) {
        this.issuerId = issuerId;
    }
        
}
