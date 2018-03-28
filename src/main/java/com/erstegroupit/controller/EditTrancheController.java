/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erstegroupit.controller;

import com.erstegroupit.InjectorContext;
import com.erstegroupit.model.DataModel;
import com.erstegroupit.model.DealData;
import com.erstegroupit.model.Tranche;
import com.erstegroupit.model.TrancheData;
import com.erstegroupit.restclient.CreateDealResponse;
import com.erstegroupit.restclient.SSDRestClient;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.inject.Inject;

/**
 * FXML Controller class
 *
 * @author User
 */
public class EditTrancheController implements Initializable {
    
    @Inject
    private CreateTrancheService service;

    private DataModel dataModel;

    private Boolean isUpdate = false;

    @FXML
    private TextField dealId;

    @FXML
    private TextField trancheId;

    @FXML
    private TextField trancheAmount;
    
    @FXML
    private DatePicker trancheDate;
    
    @FXML
    private DatePicker repaymentDate;         

    @FXML
    private TextField referenceIndex;
    
    @FXML
    private TextField margin;      

    @FXML
    private StackPane stackPane;

    @FXML
    private GridPane pane;

    @FXML
    private Button cancelButton;

    @FXML
    ImageView logoImage;
    
    public EditTrancheController() {
        this.dataModel = InjectorContext.getInjector().getInstance(DataModel.class);
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
    }

    public void setModel(DataModel dataModel) {
        this.dataModel = dataModel;
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

        CreateTrancheService service = new CreateTrancheService();
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
                ouch.printStackTrace();
                pane.setDisable(false);
                stackPane.getChildren().remove(box);

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText(ouch.getMessage());

                alert.showAndWait();
            }
        });
    }

    public void updateControls() {
        logoImage.setImage(new Image(dataModel.getImageLogoPath()));
        
        if (isUpdate) {
            ObservableValue<Tranche> tranche = dataModel.getSelectedTranche();
            dealId.setText(tranche.getValue().getDealId());
            trancheDate.setValue(tranche.getValue().getTrancheDate());            
        } else {
            dealId.setText(dataModel.getSelectedDeal().getValue().dealIdProperty().getValue());
            trancheDate.setValue(LocalDate.now());
            repaymentDate.setValue(LocalDate.now());
        }
    }

    private TrancheData createTrancheData() {
        TrancheData trancheData = new TrancheData(this.dealId.getText(), 1003, Integer.parseInt(trancheAmount.getText()), this.trancheDate.getValue(), 
                this.repaymentDate.getValue(), referenceIndex.getText(), Double.parseDouble(margin.getText()));
        return trancheData;
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

    private class CreateTrancheService extends Service<Void> {

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    DealData dealData = dataModel.getSelectedDeal().getValue().getDealData();
                    
                    TrancheData trancheData = createTrancheData();
                    SSDRestClient restc = InjectorContext.getInjector().getInstance(SSDRestClient.class);
                    CreateDealResponse trancheIdStr = restc.createTranche(trancheData);
                    trancheId.setText(trancheIdStr.getPayload());
                    trancheData.setTrancheId(trancheIdStr.getPayload().replaceAll("[{\"}]", "").split(":")[1].split(" ")[0]);

                    dealData.getTrancheData().add(trancheData);
                    dataModel.getTranches().add(new Tranche(trancheData));

                    return (Void) null;
                }
            };
        }
    }

}
