/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erstegroupit;

import com.erstegroupit.controller.CommonController;
import com.erstegroupit.controller.InvestorFormController;
import com.erstegroupit.controller.IssuerFormController;
import com.sun.xml.internal.ws.util.StringUtils;
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
public class IssuerClient extends Application {

    private static String clientType = "ISSUER";

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader();

        stage.getIcons().add(new Image(getClass().getResource("/com/erstegroupit/view/window-logo.png").toString()));

        if ("ISSUER".equalsIgnoreCase(clientType)) {
            startIssuer(stage);
        } else {
            startInvestor(stage);
        }

    }

    private void startIssuer(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/com/erstegroupit/view/IssuerForm.fxml"));
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

        loader.setLocation(getClass().getResource("/com/erstegroupit/view/InvestorForm.fxml"));
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
            return;
        } else {
            clientType = args[0];
            id = args[1];
        }

        CommonController commonController = InjectorContext.getInjector().getInstance(CommonController.class);

        commonController.setClientType(clientType);
        commonController.setClientId(id);

        if ("INVESTOR".equals(clientType)) {

        } else if ("ISSUER".equals(clientType)) {

        } else {
            System.out.println("Invalid member type. It must be ISSUER or INVESTOR");
            return;
        }

        System.out.println("Launch " + StringUtils.capitalize(clientType) + " interface");

        launch(args);
    }

}
