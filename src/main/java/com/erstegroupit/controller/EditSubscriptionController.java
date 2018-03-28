/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erstegroupit.controller;

import com.erstegroupit.InjectorContext;
import com.erstegroupit.model.DataModel;
import com.erstegroupit.model.Subscription;
import com.erstegroupit.model.SubscriptionData;
import com.erstegroupit.model.Tranche;
import com.erstegroupit.model.TrancheData;
import com.erstegroupit.restclient.CreateDealResponse;
import com.erstegroupit.restclient.SSDRestClient;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class EditSubscriptionController implements Initializable {

    private final DataModel dataModel;

    private Boolean isUpdate = false;

    @FXML
    private TextField subscriptionId;

    @FXML
    private TextField dealId;

    @FXML
    private TextField trancheId;

    @FXML
    private ComboBox investorNameList;

    @FXML
    private DatePicker initDatePicker;

    @FXML
    private TextField targetAmount;

    @FXML
    private TextField spreadLimit;

    @FXML
    private Button cancelButton;

    @FXML
    private StackPane stackPane;

    @FXML
    private Pane pane;
    
    @FXML
    ImageView logoImage;

    @FXML
    private void handleCancelAction(ActionEvent event) {
        closeWindow();
    }

    @FXML
    private void handleSubmitAction(ActionEvent event) {

        ProgressIndicator pi = new ProgressIndicator();
        VBox box = new VBox();
        box.getChildren().add(pi);
        box.setAlignment(Pos.CENTER);
        pane.setDisable(true);
        stackPane.getChildren().add(box);

        CreateSubscriptionService service = new CreateSubscriptionService();
        service.start();

        service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

            @Override
            public void handle(WorkerStateEvent t) {
                pane.setDisable(false);
                stackPane.getChildren().remove(box);
                closeWindow();
            }
        });

        service.setOnFailed(new EventHandler<WorkerStateEvent>() {

            @Override
            public void handle(WorkerStateEvent t) {
                Throwable ouch = service.getException();
                //System.out.println(ouch.getClass().getName() + " -> " + ouch.getMessage());
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

    public EditSubscriptionController() {
        this.dataModel = InjectorContext.getInjector().getInstance(DataModel.class);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO       
    }

    private SubscriptionData createSubscriptionData() {
        Integer investorId = dataModel.getInvestorId(investorNameList.getSelectionModel().getSelectedItem().toString());
        String lDealId = dealId.getText();
        String lTrancheId = trancheId.getText();
        Integer lTargetAmount = Integer.parseInt(targetAmount.getText());
        Double lSpreadLimit = Double.parseDouble(spreadLimit.getText());
        SubscriptionData subscriptionData = new SubscriptionData(Integer.toString(investorId), lTrancheId, initDatePicker.getValue(), lTargetAmount, lSpreadLimit);

        return subscriptionData;
    }

    public void updateControls() {

        logoImage.setImage(new Image(dataModel.getImageLogoPath()));
        
        if (investorNameList.getItems() != null) {
            investorNameList.getItems().removeAll(investorNameList.getItems());
        }
        //investorNameList.getItems().addAll(dataModel.getInvestors().values());
        investorNameList.getItems().addAll(dataModel.getInvestors().get(Integer.parseInt(dataModel.getClientId())));

        if (isUpdate) {
            ObservableValue<Tranche> tranche = dataModel.getSelectedTranche();
            dealId.setText(tranche.getValue().getDealId());
            trancheId.setText(tranche.getValue().getTrancheId());
        } else {
            ObservableValue<Tranche> tranche = dataModel.getSelectedTranche();
            dealId.setText(tranche.getValue().getDealId());
            trancheId.setText(tranche.getValue().getTrancheId());
            investorNameList.getSelectionModel().selectFirst();            
            initDatePicker.setValue(dataModel.getSelectedTranche().getValue().getTrancheDate());
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public Boolean getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(Boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

    private class CreateSubscriptionService extends Service<Void> {

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    TrancheData trancheData = dataModel.getSelectedTranche().getValue().getTrancheData();
                    
                    SubscriptionData subscriptionData = createSubscriptionData();
                    SSDRestClient restc = InjectorContext.getInjector().getInstance(SSDRestClient.class);
                    CreateDealResponse dealResp = restc.createSubscription(subscriptionData);
                    String subscriptionId = dealResp.getPayload().replaceAll("[{\"}]", "").split(":")[1];
                    System.out.println("Subscription Id: " + subscriptionId);
                    subscriptionData.setSubscriptionId(subscriptionId);

                    trancheData.getSubscriptionData().add(subscriptionData);
                    dataModel.getSubscriptions().add(new Subscription(subscriptionData));
                    
                    return (Void) null;
                }
            };
        }
    }
}
