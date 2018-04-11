/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erstegroupit.hyperledger.javafxclient.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.erstegroupit.hyperledger.javafxclient.InjectorContext;
import com.erstegroupit.hyperledger.javafxclient.model.Cashflow;
import com.erstegroupit.hyperledger.javafxclient.model.CashflowData;
import com.erstegroupit.hyperledger.javafxclient.model.Tranche;
import com.erstegroupit.hyperledger.javafxclient.restclient.CreateDealResponse;
import com.erstegroupit.hyperledger.javafxclient.restclient.SSDRestClient;

import javafx.collections.ObservableList;
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

    @FXML
    private Button createCashflowBtn;
    
    @FXML
    private Button signBtn;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Tooltip dealBtnToolTip = new Tooltip("Create Deal...");
        createDealBtn.setTooltip(dealBtnToolTip);  

        Tooltip trancheBtnToolTip = new Tooltip("Create Tranche...");
        createTrancheBtn.setTooltip(trancheBtnToolTip);  
        
        Tooltip allocBtnToolTip = new Tooltip("Create Allocations...");
        createAllocationsBtn.setTooltip(allocBtnToolTip);          

        Tooltip cashflowBtnToolTip = new Tooltip("Create Cashflow");
        createCashflowBtn.setTooltip(cashflowBtnToolTip);    
        
        Tooltip signBtnToolTip = new Tooltip("Sign");
        signBtn.setTooltip(signBtnToolTip);
        
        Tooltip refreshBtnToolTip = new Tooltip("Refresh");
        refreshButton.setTooltip(refreshBtnToolTip);
        
        createTrancheBtn.disableProperty().bind(dataController.getDealIsSelected());
        super.initialize(url, rb);        
    }
        
    @FXML
    protected void handleCreateCashflowAction(ActionEvent event) {
        

        ProgressIndicator pi = new ProgressIndicator();
        VBox box = new VBox();
        box.getChildren().add(pi);
        box.setAlignment(Pos.CENTER);
        pane.setDisable(true);
        stackPane.getChildren().add(box);

        CreateCashflowService service = new CreateCashflowService();
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
    
    
    @FXML
    protected void handleSignAction(ActionEvent event) {
    	
        ProgressIndicator pi = new ProgressIndicator();
        VBox box = new VBox();
        box.getChildren().add(pi);
        box.setAlignment(Pos.CENTER);
        pane.setDisable(true);
        stackPane.getChildren().add(box);

        SignTrancheService service = new SignTrancheService();
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
  
    private void createCashflowData() {
        SSDRestClient restc = InjectorContext.getInjector().getInstance(SSDRestClient.class);
        
        ObservableList<Cashflow> displayCashflowRecords = dataController.getCashflows();
        Tranche tranche = this.dataController.getSelectedTranche().getValue();
        
        String actionStatus = tranche.getTrancheData().generateCashflow();
        
        displayCashflowRecords.clear();
        
        for (CashflowData trancheData : tranche.getTrancheData().getCashflowData()) {
            CreateDealResponse resp = restc.createCashflow(trancheData);
            
            String cashflowId = resp.getPayload().replaceAll("[{\"}]", "").split(":")[1];
            System.out.println("Cashflow Id: " + cashflowId);
            
            trancheData.setCashflowId(cashflowId);
                    
            displayCashflowRecords.add(new Cashflow(trancheData));            
            
        }       
        
        showPopupMessage(actionStatus);
    }
    
    private class CreateCashflowService extends Service<Void> {
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    createCashflowData();
                    return (Void) null;
                }
            };
        }
    }
    
    public class SignTrancheService extends Service<Void> {
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    signTrancheByIssuer();
                    return (Void) null;
                }
            };
        }
    }
    
    private void signTrancheByIssuer() {
        SSDRestClient restc = InjectorContext.getInjector().getInstance(SSDRestClient.class);

        Tranche tranche = this.dataController.getSelectedTranche().getValue();
        if (tranche != null) {
        	CreateDealResponse response = restc.signTrancheByIssuer(tranche.getTrancheId());
        	this.
        	showPopupMessage("Answer is: " + response);
        }
              
    }
    
}
