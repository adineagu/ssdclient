/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erstegroupit.hyperledger.javafxclient.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

import com.erstegroupit.hyperledger.javafxclient.InjectorContext;
import com.erstegroupit.hyperledger.javafxclient.model.Allocation;
import com.erstegroupit.hyperledger.javafxclient.model.Cashflow;
import com.erstegroupit.hyperledger.javafxclient.model.DataModel;
import com.erstegroupit.hyperledger.javafxclient.model.Deal;
import com.erstegroupit.hyperledger.javafxclient.model.Payment;
import com.erstegroupit.hyperledger.javafxclient.model.PaymentCashflow;
import com.erstegroupit.hyperledger.javafxclient.model.Subscription;
import com.erstegroupit.hyperledger.javafxclient.model.Tranche;

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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
    protected TableColumn<Tranche, Boolean> issuerSignedColumn;
    
    @FXML
    protected TableColumn<Tranche, Boolean> investorSignedColumn;
    
    @FXML
    protected TableColumn<Tranche, Boolean> arrangerSignedColumn;

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
    protected TableColumn<Allocation, Boolean> alocationStatusColumn;

    @FXML
    protected TableView<Cashflow> cashflowTable;

    @FXML
    protected TableColumn<Cashflow, String> cashflowIdColumn;

    @FXML
    protected TableColumn<Cashflow, LocalDate> ajustedDateColumn;

    @FXML
    protected TableColumn<Cashflow, Double> rateColumn;

    @FXML
    protected TableColumn<Cashflow, String> cashflowTypeColumn;

    @FXML
    protected TableColumn<Cashflow, String> currencyCdColumn;

    @FXML
    protected TableColumn<Cashflow, Double> cfAmountColumn;
    
    
    @FXML
    protected TableView<Payment> paymentTable;
    
    @FXML
    protected TableColumn<Payment, String> paymentIdColumn;
    
    @FXML
    protected TableColumn<Payment, LocalDate> paymentDateColumn;
    
    @FXML
    protected TableColumn<Payment, Double> paymentAmountColumn;
    
    @FXML
    protected TableColumn<Payment, String> paymentCurrencyColumn;
    
    @FXML
    protected TableColumn<Payment, String> paymentIssuerNameColumn;
    
    @FXML
    protected TableColumn<Payment, String> paymentInvestorNameColumn;
    
    @FXML
    protected TableView<PaymentCashflow> paymentCashflowTable;
    
    @FXML
    protected TableColumn<PaymentCashflow, String> pcfCashflowIdColumn;

    @FXML
    protected TableColumn<PaymentCashflow, LocalDate> pcfAjustedDateColumn;

    @FXML
    protected TableColumn<PaymentCashflow, Double> pcfRateColumn;

    @FXML
    protected TableColumn<PaymentCashflow, String> pcfCashflowTypeColumn;

    @FXML
    protected TableColumn<PaymentCashflow, String> pcfCurrencyCdColumn;

    @FXML
    protected TableColumn<PaymentCashflow, Double> pcfCashflowAmountColumn;
    
    @FXML
    protected TableColumn<PaymentCashflow, String> pcfAllocationIdColumn;
    
    @FXML
    protected TableColumn<PaymentCashflow, Double> pcfTrancheStakeColumn;
    
    @FXML
    protected Button refreshButton;

    @FXML
    protected StackPane stackPane;

    @FXML
    protected GridPane pane;

    @FXML
    ImageView logoImage;

    private final DataModel dataModel = InjectorContext.getInjector().getInstance(DataModel.class);
    
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

        cashflowTable.setItems(this.dataController.getCashflows());
		paymentTable.setItems(dataController.getPayments());
		paymentCashflowTable.setItems(dataController.getPaymentCashflows());


        dealTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                newValue.setTranchesList(dataController.getTranches());
                dataController.getSubscriptions().clear();
                dataController.getAllocations().clear();
                dataController.getCashflows().clear();
                
                dataController.setSelectedDeal(new SimpleObjectProperty<>(newValue));

                dataController.setDealIsSelected(Boolean.FALSE);
                dataController.setTrancheIsSelected(Boolean.TRUE);
                dataController.setSubscriptionIsSelected(Boolean.FALSE);
            }
        });

        trancheTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                
                /*
                The following three lines of code synchronize the content of Subscription, Allocations and Cashflow tables with
                selected Tranche
                */
                newValue.setSubscriptionsList(dataController.getSubscriptions());
                newValue.setAllocationsList(dataController.getAllocations());
                newValue.setCashflowsList(dataController.getCashflows());
                
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
        
        allocationTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                dataController.setSelectedAllocation(new SimpleObjectProperty<>(newValue));
                dataController.setDealIsSelected(Boolean.TRUE);
                dataController.setTrancheIsSelected(Boolean.TRUE);
                dataController.setSubscriptionIsSelected(Boolean.TRUE);
            }
        });
        
        
        paymentTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                newValue.setPaymentCashflowsList(dataController.getPaymentCashflows());
              
                dataController.setSelectedPayment(new SimpleObjectProperty<>(newValue));
            }
        });

		setPaymentAmountFormatting();
		setSignedStatusFormatting();
        
		
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
	    issuerSignedColumn.setCellValueFactory(cellData -> cellData.getValue().signedByIssuerProperty().asObject());
	    investorSignedColumn.setCellValueFactory(cellData -> cellData.getValue().signedByInvestorProperty().asObject());
	    arrangerSignedColumn.setCellValueFactory(cellData -> cellData.getValue().signedByInvestorProperty().asObject());


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
        alocationStatusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty().asObject());
        
	    	
        //bind for cashflow
        cashflowIdColumn.setCellValueFactory(cellData -> cellData.getValue().cashflowIdProperty());
        ajustedDateColumn.setCellValueFactory(cellData -> cellData.getValue().adjustmentDateProperty());
        rateColumn.setCellValueFactory(cellData -> cellData.getValue().rateProperty().asObject());
        cashflowTypeColumn.setCellValueFactory(cellData -> cellData.getValue().cashflowTypeProperty());
        currencyCdColumn.setCellValueFactory(cellData -> cellData.getValue().currencyProperty());
        cfAmountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
    
        // bind for payments
        paymentIdColumn.setCellValueFactory(cellData -> cellData.getValue().getPaymentIdProperty() );
        paymentDateColumn.setCellValueFactory(cellData -> cellData.getValue().getPaymentDateProperty());
        paymentAmountColumn.setCellValueFactory(cellData -> cellData.getValue().getAmountProperty().asObject());
        paymentCurrencyColumn.setCellValueFactory(cellData -> cellData.getValue().getCurrencyProperty());
        paymentIssuerNameColumn.setCellValueFactory(cellData -> cellData.getValue().getIssuerNameProperty());
        paymentInvestorNameColumn.setCellValueFactory(cellData -> cellData.getValue().getInvestorNameProperty());
        
        // bind for payment cashflow      
        pcfCashflowIdColumn.setCellValueFactory(cellData -> cellData.getValue().cashflowIdProperty());
        pcfAjustedDateColumn.setCellValueFactory(cellData -> cellData.getValue().adjustmentDateProperty());
        pcfRateColumn.setCellValueFactory(cellData -> cellData.getValue().rateProperty().asObject());
        pcfCashflowTypeColumn.setCellValueFactory(cellData -> cellData.getValue().cashflowTypeProperty());
        pcfCurrencyCdColumn.setCellValueFactory(cellData -> cellData.getValue().currencyProperty());
        pcfCashflowAmountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        pcfAllocationIdColumn.setCellValueFactory(cellData -> cellData.getValue().getAllocationIdProperty());
        pcfTrancheStakeColumn.setCellValueFactory(cellData -> cellData.getValue().getTrancheStakeProperty().asObject());
       
    }

    private void setPaymentAmountFormatting() {
		paymentAmountColumn.setCellFactory(column -> {
		    return new TableCell<Payment, Double>() {
		    	
		        @Override
		        protected void updateItem(Double amount, boolean empty) {
		            super.updateItem(amount, empty);

		            if (amount == null || empty) {
		                setText(null);
		                setStyle("");
		                return;
		            } 
		            
		            setText(amount.toString());
		            
		            if (amount > 0d) {
		            	setTextFill(Color.BLUE);
		            } else {
		                 setTextFill(Color.RED);
		            }    
		        }
		    };
		});
    }
    
    private void setSignedStatusFormatting() {
    	issuerSignedColumn.setCellFactory(column -> {return new SignedStatusTableCell<Tranche, Boolean>();});
    	investorSignedColumn.setCellFactory(column -> {return new SignedStatusTableCell<Tranche, Boolean>();});
    	arrangerSignedColumn.setCellFactory(column -> {return new SignedStatusTableCell<Tranche, Boolean>();});
    	alocationStatusColumn.setCellFactory(column -> {return new SignedStatusTableCell<Allocation, Boolean>();});
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
            loader.setLocation(getClass().getResource("/com/erstegroupit/hyperledger/javafxclient/view/EditDeal.fxml"));
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
            loader.setLocation(getClass().getResource("/com/erstegroupit/hyperledger/javafxclient/view/EditTranche.fxml"));
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
            loader.setLocation(getClass().getResource("/com/erstegroupit/hyperledger/javafxclient/view/EditSubscription.fxml"));
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
            loader.setLocation(getClass().getResource("/com/erstegroupit/hyperledger/javafxclient/view/EditAllocations.fxml"));
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
    
//    protected void showPopupMessage(String message) {
//    	
//    	Popup popup = new Popup(); popup.setX(300); popup.setY(200);
//
//        Button okBtn = new Button("Ok");
//        okBtn.setOnAction(new EventHandler<ActionEvent>() {
//          @Override public void handle(ActionEvent event) {
//            popup.hide();
//          }
//        });
//
//
//        popup.show(arg0);
//        HBox layout = new HBox(10);
//        layout.setStyle("-fx-background-color: cornsilk; -fx-padding: 10;");
//        layout.getChildren().addAll(show, hide);
//        primaryStage.setScene(new Scene(layout));
//        primaryStage.show();
//    	
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("/com/erstegroupit/hyperledger/javafxclient/view/EditAllocations.fxml"));
//            Parent root = loader.load();
//
//            EditAllocationController controller = loader.getController();
//            controller.setIsUpdate(isUpdate);
//            controller.updateControls();
//
//            Scene sceneIssuer = new Scene(root);
//            Stage stage = new Stage(StageStyle.UTILITY);
//            stage.initModality(Modality.APPLICATION_MODAL);
//            stage.setScene(sceneIssuer);
//            stage.show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private class RefreshDataService extends Service<Void> {

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    dataController.deleteData();
                    dataController.readDeals();
                    dataController.readPayments();
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
                    dataController.readPayments();
                    return (Void) null;
                }
            };
        }
    }
    
    private class SignedStatusTableCell<Object, Boolean> extends TableCell<Object, Boolean>  {
    	
        @Override
        protected void updateItem(Boolean signedStatus, boolean empty) {
            super.updateItem(signedStatus, empty);

            if (signedStatus == null || empty) {
                setText(null);
                setStyle("");
                return;
            } 
            
            setText(signedStatus.toString());
            
            if ((boolean) signedStatus) {
            	setTextFill(Color.GREEN);
            } else {
                 setTextFill(Color.RED);
            }    
        }
    };

}
