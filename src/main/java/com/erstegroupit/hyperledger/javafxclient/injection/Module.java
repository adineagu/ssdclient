/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erstegroupit.hyperledger.javafxclient.injection;

import com.erstegroupit.hyperledger.javafxclient.data.DataProvider;
import com.erstegroupit.hyperledger.javafxclient.data.MockDataProvider;
import com.google.inject.AbstractModule;

/**
 *
 * @author User
 */
public class Module extends AbstractModule {

    @Override
    protected void configure() {
        bind(DataProvider.class).to(MockDataProvider.class);
    }
    
}
