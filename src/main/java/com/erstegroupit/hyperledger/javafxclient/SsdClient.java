/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erstegroupit.hyperledger.javafxclient;

import com.erstegroupit.hyperledger.javafxclient.controller.CommonController;
import com.erstegroupit.hyperledger.javafxclient.controller.InvestorFormController;
import com.erstegroupit.hyperledger.javafxclient.controller.IssuerFormController;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author H50UDBB
 */
public class SsdClient extends Application {

    private static ClientType clientType = ClientType.ISSUER;

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader();

        stage.getIcons().add(new Image(getClass().getResource("/com/erstegroupit/hyperledger/javafxclient/view/window-logo.png").toString()));

        switch (clientType) {
        case ISSUER: 	startIssuer(stage);
        				break;
        case INVESTOR:  startInvestor(stage);
        				break;
        case ARRANGER:  startInvestor(stage);
        				break;
        }
        
        if (ClientType.ISSUER.equals(clientType)) {
            startIssuer(stage);
        } else {
            startInvestor(stage);
        }

    }

    private void startIssuer(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/com/erstegroupit/hyperledger/javafxclient/view/IssuerForm.fxml"));
        stage.setTitle("SSD - Issuer");

        Parent root = loader.load();
        Scene scene = new Scene(root);

        IssuerFormController controller = loader.getController();

        stage.addEventHandler(WindowEvent.WINDOW_SHOWN, new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent window) {
                controller.authenticate("Jim", "org1");
            }
        });

        stage.setScene(scene);
        stage.show();
    }

    private void startInvestor(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/com/erstegroupit/hyperledger/javafxclient/view/InvestorForm.fxml"));
        stage.setTitle("SSD - Investor");

        Parent root = loader.load();
        Scene scene = new Scene(root);

        InvestorFormController controller = loader.getController();

        stage.addEventHandler(WindowEvent.WINDOW_SHOWN, new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent window) {
                controller.authenticate("Barry", "org2");
            }
        });

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String id;

        if (args == null || args.length != 2) {
            System.out.println("Invalid command line. Use \"java -jar SSDClientMaven-4.0.0.jar ISSUER <issuerId>\"");
            System.out.println("or");
            System.out.println("Invalid command line. Use \"java -jar SSDClientMaven-4.0.0.jar INVESTOR <investorId>\"");
            System.out.println("or");
            System.out.println("Invalid command line. Use \"java -jar SSDClientMaven-4.0.0.jar ARRANGER <arrangerId>\"");
            return;
        } else {
        	try {
        		clientType = ClientType.valueOf(args[0]);
        	} catch (IllegalArgumentException e) {
                System.out.println("Invalid member type. It must be ISSUER or INVESTOR or ARRANGER");
        	}
            id = args[1];
        }

        CommonController commonController = InjectorContext.getInjector().getInstance(CommonController.class);

        commonController.setClientType(clientType);
        commonController.setClientId(id);

        launch(args);
    }

}
