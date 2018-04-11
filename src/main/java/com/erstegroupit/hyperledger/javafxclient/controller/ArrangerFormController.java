/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erstegroupit.hyperledger.javafxclient.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.erstegroupit.hyperledger.javafxclient.InjectorContext;
import com.erstegroupit.hyperledger.javafxclient.model.Tranche;
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
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;

/**
 *
 * @author H50UDBB
 */
public class ArrangerFormController extends CommonFormController {

    public ArrangerFormController() {
        super();
    }
    
    @FXML
    private Button signBtn;
    
    @FXML
    private TabPane paymentsTabPane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {   
        
        Tooltip signBtnToolTip = new Tooltip("Sign");
        signBtn.setTooltip(signBtnToolTip);
        
        Tooltip refreshBtnToolTip = new Tooltip("Refresh");
        refreshButton.setTooltip(refreshBtnToolTip);    
        
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
 
    
    public class SignTrancheService extends Service<Void> {
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    signTrancheByArranger();
                    return (Void) null;
                }
            };
        }
    }
    
    private void signTrancheByArranger() {
        SSDRestClient restc = InjectorContext.getInjector().getInstance(SSDRestClient.class);

        Tranche tranche = this.dataController.getSelectedTranche().getValue();
        if (tranche != null) {
        	restc.signTrancheByArranger(tranche.getTrancheId(), this.dataController.getClientId());
        	tranche.setSignedByArranger(true);
        }
              
    }
    
	  public TabPane getPaymentsTabPane() {
	        return paymentsTabPane;
	  }
	
	  public void setPaymentsTabPane(TabPane paymentsTabPane) {
	        this.paymentsTabPane = paymentsTabPane;
	  }  
}
