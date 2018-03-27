/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erstegroupit;

import com.erstegroupit.injection.Module;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 *
 * @author User
 */
public class InjectorContext {
    private static final Injector injector = Guice.createInjector(new Module());

    public static Injector getInjector() {
        return injector;
    }
}
