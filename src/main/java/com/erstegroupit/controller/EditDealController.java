/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erstegroupit.controller;

import com.erstegroupit.InjectorContext;
import com.erstegroupit.model.Deal;
import com.erstegroupit.model.DealData;
import com.erstegroupit.restclient.CreateDealResponse;
import com.erstegroupit.restclient.SSDRestClient;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
 *
 * @author H50UDBB
 */
public class EditDealController implements Initializable {

    private Boolean isUpdateDeal = false;

    @FXML
    private TextField dealId;

    @FXML
    private ComboBox ssdArrangeurNameList;

    @FXML
    private ComboBox ssdIssuerNameList;

    @FXML
    private DatePicker issueDateDatePicker;

    @FXML
    private DatePicker expiryDateDatePicker;

    @FXML
    private ComboBox ssdCurrency;

    @FXML
    private TextField amount;

    @FXML
    private TextField minSubscription;

    @FXML
    private StackPane stackPane;

    @FXML
    private Pane pane;

    @FXML
    private Button cancelButton;
    
    @FXML
    ImageView logoImage;
    
    private CommonController dataController;

    public EditDealController() {
        this.dataController = InjectorContext.getInjector().getInstance(CommonController.class);
    }
    
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

        CreateDealService service = new CreateDealService();
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateControls();
    }

    public void updateControls() {
        logoImage.setImage(new Image(dataController.getImageLogoPath()));
        
        if (isUpdateDeal) {
            ObservableValue<Deal> deal = dataController.getSelectedDeal();

            if (ssdArrangeurNameList.getItems() != null) {
                ssdArrangeurNameList.getItems().removeAll(ssdIssuerNameList.getItems());
            }
            ssdArrangeurNameList.getItems().addAll("Erste Group Bank", "UBS", "HSBC", "Merkantil Bank", "OTP Bank");

            if (ssdIssuerNameList.getItems() != null) {
                ssdIssuerNameList.getItems().removeAll(ssdIssuerNameList.getItems());
            }
            ssdIssuerNameList.getItems().addAll(dataController.getIssuers().get(Integer.parseInt(dataController.getClientId())));
            

            if (ssdCurrency.getItems() != null) {
                ssdCurrency.getItems().removeAll(ssdCurrency.getItems());
            }
            ssdCurrency.getItems().addAll("EUR", "USD", "GBP");            

            ssdArrangeurNameList.setValue(deal.getValue().getArranger());
            ssdIssuerNameList.setValue(dataController.getIssuers().get(deal.getValue().getIssuer()));
            ssdCurrency.setValue(deal.getValue().getCurrencyCd());                        
            issueDateDatePicker.setValue(deal.getValue().getIssueDate());
            expiryDateDatePicker.setValue(deal.getValue().getExpiryDate());
        } else {
            if (ssdArrangeurNameList.getItems() != null) {
                ssdArrangeurNameList.getItems().clear();
            }
            ssdArrangeurNameList.getItems().addAll("Erste Group Bank", "UBS", "HSBC", "Merkantil Bank", "OTP Bank");
            ssdArrangeurNameList.getSelectionModel().selectFirst();

            if (ssdIssuerNameList.getItems() != null) {
                ssdIssuerNameList.getItems().removeAll(ssdIssuerNameList.getItems());
            }
            ssdIssuerNameList.getItems().addAll(dataController.getIssuers().get(Integer.parseInt(dataController.getClientId())));
            ssdIssuerNameList.getSelectionModel().selectFirst();

            if (ssdCurrency.getItems() != null) {
                ssdCurrency.getItems().removeAll(ssdCurrency.getItems());
            }
            ssdCurrency.getItems().addAll("EUR", "USD", "GBP");
            ssdCurrency.getSelectionModel().selectFirst();

            issueDateDatePicker.setValue(LocalDate.now());
            expiryDateDatePicker.setValue(LocalDate.of(LocalDate.now().getYear() + 10, LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth()));
        }
    }

    private DealData createDealData() {
        String issuerName = ssdIssuerNameList.getSelectionModel().getSelectedItem().toString();
        Integer issuerId = dataController.getIssuerId(issuerName);
        Integer issuedAmount = Integer.parseInt(amount.getText());
        DealData dealData = new DealData(issuerName, issuedAmount, issueDateDatePicker.getValue());
        dealData.setIssuerId(issuerId);

        return dealData;
    }

    private void createDeal(String dealId) {
        this.dealId.setText(dealId);
        Integer issuerId = dataController.getIssuerId(ssdIssuerNameList.getSelectionModel().getSelectedItem().toString());
        LocalDate issueDate = issueDateDatePicker.getValue();
        LocalDate expiryDate = expiryDateDatePicker.getValue();
        Integer issuedAmount = Integer.parseInt(amount.getText());
        Integer minSubscriptionAmount = Integer.parseInt(minSubscription.getText());        
        DealData dealData = new DealData(dealId, issuerId, issuedAmount, issueDate, minSubscriptionAmount);
        dataController.getDeals().add(new Deal(dealData));
    }

    private void closeWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public Boolean getIsUpdateDeal() {
        return isUpdateDeal;
    }

    public void setIsUpdateDeal(Boolean isUpdateDeal) {
        this.isUpdateDeal = isUpdateDeal;
    }

    private class CreateDealService extends Service<Void> {

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    DealData dealData = createDealData();
                    SSDRestClient restc = InjectorContext.getInjector().getInstance(SSDRestClient.class);
                    
                    CreateDealResponse dealResp = restc.createDeal(dealData);
                    
                    String dealId = dealResp.getPayload().replaceAll("[{\"}]", "").split(":")[1];
                    System.out.println("Deal Id: " + dealId);
                    createDeal(dealId);
                                        
                    restc.readDeal(dealId);
                    return (Void) null;
                }
            };
        }
    }

}
