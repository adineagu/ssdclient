/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erstegroupit.restclient;

import org.apache.commons.lang.StringUtils;

/**
 *
 * @author User
 */
public class CreateDeal {

    private String fcn;
    private String[] args;

    public CreateDeal(String fcn, String[] args) {
        this.fcn = fcn;
        this.args = args;
    }

    public String getFcn() {
        return fcn;
    }

    public void setFcn(String fcn) {
        this.fcn = fcn;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return "CreateDeal{" + "fcn=" + fcn + ", args=" + StringUtils.join(args, ", ") + '}';
    }
    
    
    
}
