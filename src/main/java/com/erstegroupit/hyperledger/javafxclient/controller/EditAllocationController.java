/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erstegroupit.hyperledger.javafxclient.controller;

import com.erstegroupit.hyperledger.javafxclient.InjectorContext;
import com.erstegroupit.hyperledger.javafxclient.model.Allocation;
import com.erstegroupit.hyperledger.javafxclient.model.AllocationData;
import com.erstegroupit.hyperledger.javafxclient.model.Subscription;
import com.erstegroupit.hyperledger.javafxclient.restclient.CreateDealResponse;
import com.erstegroupit.hyperledger.javafxclient.restclient.SSDRestClient;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LocalDateStringConverter;
import javafx.util.converter.NumberStringConverter;

/**
 *
 * @author H50UDBB
 */
public class EditAllocationController implements Initializable {

    private Boolean isUpdate = true;

    @FXML
    protected TableView<Allocation> allocationTable;

    @FXML
    protected TableColumn<Allocation, String> allocationIdColumn;

    @FXML
    protected TableColumn<Allocation, String> allocationInvestorNameColumn;

    @FXML
    protected TableColumn<Allocation, LocalDate> allocationInitialDateColumn;

    @FXML
    protected TableColumn<Allocation, Integer> allocationAmountColumn;

    @FXML
    protected TableColumn<Allocation, String> alocationStatusColumn;

    @FXML
    private Button cancelButton;

    @FXML
    private StackPane stackPane;

    @FXML
    private Pane pane;
    
    @FXML
    ImageView logoImage;

    @FXML
    private void handleSubmitAction(ActionEvent event) {
        ProgressIndicator pi = new ProgressIndicator();
        VBox box = new VBox();
        box.getChildren().add(pi);
        box.setAlignment(Pos.CENTER);
        pane.setDisable(true);
        stackPane.getChildren().add(box);

        CreateAllocationsService service = new CreateAllocationsService();
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

    @FXML
    private void handleCancelAction(ActionEvent event) {
        closeWindow();
    }

    private CommonController dataController;

    public EditAllocationController() {
        this.dataController = InjectorContext.getInjector().getInstance(CommonController.class);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        logoImage.setImage(new Image(dataController.getImageLogoPath()));
        
        createAllocationsFromSubscriptions();

        allocationTable.setItems(dataController.getAllocations());
        allocationTable.setEditable(true);

        allocationAmountColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        allocationAmountColumn.setOnEditCommit(
                new EventHandler<CellEditEvent<Allocation, Integer>>() {
            @Override
            public void handle(CellEditEvent<Allocation, Integer> t) {
                ((Allocation) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setAllocatedAmount(t.getNewValue());
            }
        }
        );

        allocationInitialDateColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
        allocationInitialDateColumn.setOnEditCommit(
                new EventHandler<CellEditEvent<Allocation, LocalDate>>() {
            @Override
            public void handle(CellEditEvent<Allocation, LocalDate> t) {
                ((Allocation) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setInitDate(t.getNewValue());
            }
        }
        );

        // bind for allocations
        allocationIdColumn.setCellValueFactory(cellData -> cellData.getValue().allocationIdProperty());
        allocationInvestorNameColumn.setCellValueFactory(cellData -> cellData.getValue().investorNameProperty());
        allocationInitialDateColumn.setCellValueFactory(cellData -> cellData.getValue().initDateProperty());
        allocationAmountColumn.setCellValueFactory(cellData -> cellData.getValue().allocatedAmountProperty().asObject());
        alocationStatusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

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

    public void updateControls() {

    }

    public void createAllocationsFromSubscriptions() {
        dataController.getAllocations().clear();
        for (Subscription subscription : dataController.getSubscriptions()) {

            String investorId = subscription.getInvestorId().getValue();
            String trancheId = subscription.getTrancheId().getValue();
            LocalDate initDate = subscription.getInitDate().getValue();
            Integer targetAmount = subscription.getTargetAmount().getValue();

            AllocationData allocationData = new AllocationData(investorId, trancheId, initDate, targetAmount, "false");
            Allocation allocation = new Allocation(allocationData);

            dataController.getAllocations().add(allocation);
        }
    }

    private class CreateAllocationsService extends Service<Void> {

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    for (Allocation allocation : dataController.getAllocations()) {
                        AllocationData allocationData = allocation.getAllocationData();
                        SSDRestClient restc = InjectorContext.getInjector().getInstance(SSDRestClient.class);

                        CreateDealResponse dealResp = restc.createAllocation(allocationData);

                        String allocationId = dealResp.getPayload().replaceAll("[{\"}]", "").split(":")[1];
                        System.out.println("Allocation Id: " + allocationId);
                        
                        allocationData.setAllocationId(allocationId);
                        allocation.allocationIdProperty().setValue(allocationId);
                    }
                    return (Void) null;
                }
            };
        }
    }
}
