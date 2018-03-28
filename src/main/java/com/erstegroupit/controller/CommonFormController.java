/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erstegroupit.controller;

import com.erstegroupit.InjectorContext;
import com.erstegroupit.model.Allocation;
import com.erstegroupit.model.Deal;
import com.erstegroupit.model.Subscription;
import com.erstegroupit.model.Tranche;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author H50UDBB
 */
public class CommonFormController implements Initializable {

    @FXML
    protected TableView<Deal> dealTable;

    @FXML
    protected TableColumn<Deal, String> dealIdColumn;

    @FXML
    protected TableColumn<Deal, String> issuerColumn;

    @FXML
    protected TableColumn<Deal, LocalDate> issueDateColumn;

    @FXML
    protected TableColumn<Deal, String> typeColumn;

    @FXML
    protected TableColumn<Deal, String> purposeColumn;

    @FXML
    protected TableColumn<Deal, Integer> amountColumn;

    @FXML
    protected TableColumn<Deal, String> currencyColumn;

    @FXML
    protected TableColumn<Deal, Integer> minSubscriptionAmountColumn;

    @FXML
    protected TableView<Tranche> trancheTable;

    @FXML
    protected TableColumn<Tranche, String> trancheIdColumn;

    @FXML
    protected TableColumn<Tranche, Integer> trancheAmountColumn;

    @FXML
    protected TableColumn<Tranche, LocalDate> trancheDateColumn;

    @FXML
    protected TableColumn<Tranche, LocalDate> repaymentDateColumn;

    @FXML
    protected TableColumn<Tranche, String> referenceIndexColumn;

    @FXML
    protected TableColumn<Tranche, Double> marginColumn;

    @FXML
    protected TableView<Subscription> subscriptionTable;

    @FXML
    protected TableColumn<Subscription, String> subscriptionIdColumn;

    @FXML
    protected TableColumn<Subscription, String> investorNameColumn;

    @FXML
    protected TableColumn<Subscription, LocalDate> initialDateColumn;

    @FXML
    protected TableColumn<Subscription, Integer> targetAmountColumn;

    @FXML
    protected TableColumn<Subscription, Double> spreadLimitColumn;
    
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
    protected TableColumn<Allocation, Double> alocationStatusColumn;
    
    @FXML
    protected Button refreshButton;
    
    @FXML
    protected StackPane stackPane;

    @FXML
    protected GridPane pane;
    
    @FXML
    ImageView logoImage;

    protected final CommonController dataController;

    protected Deal prevSelectedDealRow;
    Date lastClickTime;

    public CommonFormController() {
        this.dataController = InjectorContext.getInjector().getInstance(CommonController.class);
    }

    @FXML 
    protected void handleRefreshDataButton(ActionEvent event) {
        refreshData();     
    }
    
    @FXML
    protected void handleCreateDealAction(ActionEvent event) {
        showDealEditor(false);
    }

    @FXML
    protected void handleCreateTrancheAction(ActionEvent event) {
        showTrancheEditor(false);
    }

    @FXML
    protected void handleCreateAllocationAction(ActionEvent event) {
        showAllocationsEditor(false);
    }
    
    @FXML
    protected void handleRowSelect() {
        Deal row = dealTable.getSelectionModel().getSelectedItem();
        if (row == null) {
            return;
        }
        if (row != prevSelectedDealRow) {
            prevSelectedDealRow = row;
            lastClickTime = new Date();
        } else if (row == prevSelectedDealRow) {
            Date now = new Date();
            long diff = now.getTime() - lastClickTime.getTime();
            if (diff < 500) {
                showDealEditor(true);
            } else {
                lastClickTime = new Date();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        logoImage.setImage(new Image(dataController.getImageLogoPath()));
        
        dealTable.setItems(dataController.getDeals());
        trancheTable.setItems(dataController.getTranches());   
        subscriptionTable.setItems(this.dataController.getSubscriptions());
        allocationTable.setItems(this.dataController.getAllocations());
        
        Tooltip refreshBtnToolTip = new Tooltip("Refresh");
        refreshButton.setTooltip(refreshBtnToolTip);        
        
        dealTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                newValue.setTranchesList(dataController.getTranches());
                dataController.getSubscriptions().clear();
                dataController.getAllocations().clear();
                dataController.setSelectedDeal(new SimpleObjectProperty<>(newValue));

                dataController.setDealIsSelected(Boolean.FALSE);
                dataController.setTrancheIsSelected(Boolean.TRUE);
                dataController.setSubscriptionIsSelected(Boolean.FALSE);
            }
        });
        
        trancheTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                newValue.setSubscriptionsList(dataController.getSubscriptions());
                newValue.setAllocationsList(dataController.getAllocations());
                dataController.setSelectedTranche(new SimpleObjectProperty<>(newValue));

                dataController.setDealIsSelected(Boolean.TRUE);
                dataController.setTrancheIsSelected(Boolean.FALSE);
                dataController.setSubscriptionIsSelected(Boolean.TRUE);
            }
        });
        
        subscriptionTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                dataController.setSelectedSubscription(new SimpleObjectProperty<>(newValue));
                dataController.setDealIsSelected(Boolean.TRUE);
                dataController.setTrancheIsSelected(Boolean.TRUE);
                dataController.setSubscriptionIsSelected(Boolean.FALSE);
            }
        });        

        // bind for deals
        dealIdColumn.setCellValueFactory(cellData -> cellData.getValue().dealIdProperty());
        issuerColumn.setCellValueFactory(cellData -> cellData.getValue().issuerNameProperty());
        issueDateColumn.setCellValueFactory(cellData -> cellData.getValue().issueDateProperty());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        purposeColumn.setCellValueFactory(cellData -> cellData.getValue().purposeProperty());
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().issuedAmountProperty().asObject());
        currencyColumn.setCellValueFactory(cellData -> cellData.getValue().currencyCdProperty());
        minSubscriptionAmountColumn.setCellValueFactory(cellData -> cellData.getValue().subscriptionMinAmountProperty().asObject());

        // bind for tranches
        trancheIdColumn.setCellValueFactory(cellData -> cellData.getValue().trancheIdProperty());
        trancheDateColumn.setCellValueFactory(cellData -> cellData.getValue().trancheDateProperty());
        repaymentDateColumn.setCellValueFactory(cellData -> cellData.getValue().repaymentDateProperty());
        trancheAmountColumn.setCellValueFactory(cellData -> cellData.getValue().trancheAmountProperty().asObject());
        referenceIndexColumn.setCellValueFactory(cellData -> cellData.getValue().referenceIndexProperty());
        marginColumn.setCellValueFactory(cellData -> cellData.getValue().marginColumnProperty().asObject());
        
        // bind for subscriptions
        subscriptionIdColumn.setCellValueFactory(cellData -> cellData.getValue().getSubscriptionId());
        investorNameColumn.setCellValueFactory(cellData -> cellData.getValue().getInvestorName());
        initialDateColumn.setCellValueFactory(cellData -> cellData.getValue().getInitDate());
        targetAmountColumn.setCellValueFactory(cellData -> cellData.getValue().getTargetAmount().asObject());
        spreadLimitColumn.setCellValueFactory(cellData -> cellData.getValue().getSpreadLimit().asObject());   
        
        // bind for allocations
        allocationIdColumn.setCellValueFactory(cellData -> cellData.getValue().allocationIdProperty());
        allocationInvestorNameColumn.setCellValueFactory(cellData -> cellData.getValue().investorNameProperty());
        allocationInitialDateColumn.setCellValueFactory(cellData -> cellData.getValue().initDateProperty());
        allocationAmountColumn.setCellValueFactory(cellData -> cellData.getValue().allocatedAmountProperty().asObject());
    }
    
    public void authenticate(String user, String organization) {
        ProgressIndicator pi = new ProgressIndicator();
        VBox box = new VBox(pi);
        box.setAlignment(Pos.CENTER);
        pane.setDisable(true);
        stackPane.getChildren().add(box);

        AuthenticateService service = new AuthenticateService(user, organization);
        service.start();

        service.setOnFailed(new EventHandler<WorkerStateEvent>() {

            @Override
            public void handle(WorkerStateEvent t) {
                Throwable ouch = service.getException();
                ouch.printStackTrace();
                pane.setDisable(false);
                stackPane.getChildren().remove(box);

                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText(ouch.getMessage());

                alert.showAndWait();
            }
        });

        service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

            @Override
            public void handle(WorkerStateEvent t) {
                pane.setDisable(false);
                stackPane.getChildren().remove(box);
            }
        });
    }

    public void refreshData() {
        ProgressIndicator pi = new ProgressIndicator();
        VBox box = new VBox(pi);
        box.setAlignment(Pos.CENTER);
        pane.setDisable(true);
        stackPane.getChildren().add(box);

        RefreshDataService service = new RefreshDataService();
        service.start();

        service.setOnFailed(new EventHandler<WorkerStateEvent>() {

            @Override
            public void handle(WorkerStateEvent t) {
                Throwable ouch = service.getException();
                ouch.printStackTrace();
                pane.setDisable(false);
                stackPane.getChildren().remove(box);

                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText(ouch.getMessage());

                alert.showAndWait();
            }
        });

        service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

            @Override
            public void handle(WorkerStateEvent t) {
                pane.setDisable(false);
                stackPane.getChildren().remove(box);
            }
        });

    }

    private void showDealEditor(Boolean isUpdate) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/erstegroupit/view/EditDeal.fxml"));
            Parent root = loader.load();

            EditDealController controller = loader.getController();
            controller.setIsUpdateDeal(isUpdate);
            controller.updateControls();

            Scene sceneIssuer = new Scene(root);
            Stage stage = new Stage(StageStyle.UTILITY);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(sceneIssuer);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showTrancheEditor(Boolean isUpdate) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/erstegroupit/view/EditTranche.fxml"));
            Parent root = loader.load();

            EditTrancheController controller = loader.getController();
            controller.setIsUpdate(isUpdate);
            controller.updateControls();

            Scene sceneIssuer = new Scene(root);
            Stage stage = new Stage(StageStyle.UTILITY);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(sceneIssuer);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void showSubscriptionEditor(Boolean isUpdate) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/erstegroupit/view/EditSubscription.fxml"));
            Parent root = loader.load();

            EditSubscriptionController controller = loader.getController();
            controller.setIsUpdate(isUpdate);
            controller.updateControls();

            Scene sceneIssuer = new Scene(root);
            Stage stage = new Stage(StageStyle.UTILITY);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(sceneIssuer);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void showAllocationsEditor(Boolean isUpdate) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/erstegroupit/view/EditAllocations.fxml"));
            Parent root = loader.load();

            EditAllocationController controller = loader.getController();
            controller.setIsUpdate(isUpdate);
            controller.updateControls();

            Scene sceneIssuer = new Scene(root);
            Stage stage = new Stage(StageStyle.UTILITY);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(sceneIssuer);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private class RefreshDataService extends Service<Void> {
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    dataController.deleteData();
                    dataController.readDeals();
                    return (Void) null;
                }
            };
        }
    }

    private class AuthenticateService extends Service<Void> {

        private final String user;
        private final String organization;

        public AuthenticateService(String user, String organization) {
            this.user = user;
            this.organization = organization;
        }
        
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    dataController.authenticate(user, organization);                    
                    dataController.readDeals();                                        
                    return (Void) null;
                }
            };
        }
    }

}
