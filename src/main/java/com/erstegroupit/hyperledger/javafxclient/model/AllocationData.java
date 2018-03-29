/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erstegroupit.hyperledger.javafxclient.model;

import java.time.LocalDate;

/**
 *
 * @author User
 */
public class AllocationData {
    
    private String allocationId;
    private String investorId;
    private String trancheId;
    private LocalDate initDate;
    private Integer allocationAmount;    
    private String status;

    /**
     * Get the value of status
     *
     * @return the value of status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Set the value of status
     *
     * @param status new value of status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    public AllocationData(String allocationId, String investorId, String trancheId, LocalDate initDate, Integer targetAmount, String status) {
        this(investorId, trancheId, initDate, targetAmount, status);
        this.allocationId = allocationId;
    }

    public AllocationData(String investorId, String trancheId, LocalDate initDate, Integer targetAmount, String status) {
        this.investorId = investorId;
        this.trancheId = trancheId;
        this.initDate = initDate;
        this.allocationAmount = targetAmount;
        this.status = status;
    }

    public void setAllocationId(String allocationId) {
        this.allocationId = allocationId;
    }

    public String getAllocationId() {
        return allocationId;
    }

    public String getInvestorId() {
        return investorId;
    }

    public String getTrancheId() {
        return trancheId;
    }

    public LocalDate getInitDate() {
        return initDate;
    }

    public Integer getAllocationAmount() {
        return allocationAmount;
    }    

    public void setInvestorId(String investorId) {
        this.investorId = investorId;
    }

    public void setTrancheId(String trancheId) {
        this.trancheId = trancheId;
    }

    public void setInitDate(LocalDate initDate) {
        this.initDate = initDate;
    }

    public void setAllocationAmount(Integer allocationAmount) {
        this.allocationAmount = allocationAmount;
    }
        
}
