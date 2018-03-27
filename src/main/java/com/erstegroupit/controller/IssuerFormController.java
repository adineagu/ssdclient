/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erstegroupit.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

/**
 *
 * @author H50UDBB
 */
public class IssuerFormController extends CommonFormController {

    public IssuerFormController() {
        super();
    }

    @FXML
    private Button createDealBtn;
    
    @FXML
    private Button createTrancheBtn;

    @FXML
    private Button createAllocationsBtn;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Tooltip dealBtnToolTip = new Tooltip("Create Deal...");
        createDealBtn.setTooltip(dealBtnToolTip);  

        Tooltip trancheBtnToolTip = new Tooltip("Create Tranche...");
        createTrancheBtn.setTooltip(trancheBtnToolTip);  
        
        Tooltip allocBtnToolTip = new Tooltip("Create Allocations...");
        createAllocationsBtn.setTooltip(allocBtnToolTip);          
        
        createTrancheBtn.disableProperty().bind(dataController.getDealIsSelected());
        super.initialize(url, rb);        
    }
    
}
