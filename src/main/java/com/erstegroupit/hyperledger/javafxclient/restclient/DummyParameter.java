/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erstegroupit.hyperledger.javafxclient.restclient;

/**
 *
 * @author User
 */
public class DummyParameter {
    private String title;
    private String body;
    private int userId;   

    public DummyParameter(String title, String body, int userId) {
        this.title = title;
        this.body = body;
        this.userId = userId;
    }
    
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "DummyObject{" + "userId=" + userId + ", title=" + title + ", body=" + body + '}';
    }    
}
