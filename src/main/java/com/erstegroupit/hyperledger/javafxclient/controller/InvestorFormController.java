/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erstegroupit.hyperledger.javafxclient.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.erstegroupit.hyperledger.javafxclient.InjectorContext;
import com.erstegroupit.hyperledger.javafxclient.model.Allocation;
import com.erstegroupit.hyperledger.javafxclient.model.AllocationData.AllocationStatus;
import com.erstegroupit.hyperledger.javafxclient.restclient.SSDRestClient;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;

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
    
    @FXML
    private Button signBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Tooltip signBtnToolTip = new Tooltip("Sign");
        signBtn.setTooltip(signBtnToolTip);
    	
        createSubscriptionBtn.disableProperty().bind(dataController.getTrancheIsSelected());
        super.initialize(url, rb);
    }
    
    @FXML
    protected void handleSignAction(ActionEvent event) {
    	
        ProgressIndicator pi = new ProgressIndicator();
        VBox box = new VBox();
        box.getChildren().add(pi);
        box.setAlignment(Pos.CENTER);
        pane.setDisable(true);
        stackPane.getChildren().add(box);

        SignAllocationService service = new SignAllocationService();
        service.start();

        service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

            @Override
            public void handle(WorkerStateEvent t) {
                pane.setDisable(false);
                stackPane.getChildren().remove(box);
            }
        });

        service.setOnFailed(new EventHandler<WorkerStateEvent>() {

            @Override
            public void handle(WorkerStateEvent t) {
                Throwable ouch = service.getException();
                pane.setDisable(false);
                stackPane.getChildren().remove(box);

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText(ouch.getMessage());
                ouch.printStackTrace();

                alert.showAndWait();
            }
        });
    	
    }
    
    public class SignAllocationService extends Service<Void> {
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    signAllocationByInvestor();
                    return (Void) null;
                }
            };
        }
    }
    
    private void signAllocationByInvestor() {
        SSDRestClient restc = InjectorContext.getInjector().getInstance(SSDRestClient.class);

        Allocation allocation = this.dataController.getSelectedAllocation().getValue();
        if (allocation != null) {
        	restc.signAllocationByInvestor(allocation.getAllocationId());
        	allocation.setStatus(AllocationStatus.TRUE.toString());
        }
              
    }

}
