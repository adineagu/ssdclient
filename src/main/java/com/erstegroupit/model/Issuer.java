/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erstegroupit.model;

/**
 *
 * @author H50UDBB
 */
public class Issuer {
    private final String issuerId;
    private final String issuerName;

    public Issuer(String issuerId, String issuerName) {
        this.issuerId = issuerId;
        this.issuerName = issuerName;
    }     

    public String getIssuerId() {
        return issuerId;
    }

    public String getIssuerName() {
        return issuerName;
    }
        
}
