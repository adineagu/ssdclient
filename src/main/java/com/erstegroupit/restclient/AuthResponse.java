/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erstegroupit.restclient;

/**
 *
 * @author User
 */
public class AuthResponse {

    public boolean success;
    public String secret;
    public String message;
    public String token;

    public AuthResponse( boolean success, String secret, String message, String token) {
        this.success = success;
        this.secret = secret;
        this.message = message;
        this.token = token;
    }

    public AuthResponse() {
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
