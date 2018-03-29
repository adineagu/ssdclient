/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erstegroupit.hyperledger.javafxclient.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 *
 * @author H50UDBB
 */
public class InvestorFormController extends CommonFormController {

    public InvestorFormController() {
        super();
    }

    @FXML
    private void handleCreateSubscriptionAction(ActionEvent event) {
        showSubscriptionEditor(false);
    }

    @FXML
    private Button createSubscriptionBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createSubscriptionBtn.disableProperty().bind(dataController.getTrancheIsSelected());
        super.initialize(url, rb);
    }

}
