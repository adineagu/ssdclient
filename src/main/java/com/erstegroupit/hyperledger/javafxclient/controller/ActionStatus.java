package com.erstegroupit.hyperledger.javafxclient.controller;

import javafx.scene.control.Alert.AlertType;

public class ActionStatus {

	private final AlertType alertType;
	private final String message;

	public ActionStatus(AlertType alertType, String message) {
		super();
		this.alertType = alertType;
		this.message = message;
	}

	public AlertType getAlertType() {
		return alertType;
	}

	public String getMessage() {
		return message;
	}

}