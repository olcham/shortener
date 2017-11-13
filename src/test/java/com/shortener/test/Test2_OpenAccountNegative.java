/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shortener.test;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.junit.Assert;
import org.junit.Test;

public class Test2_OpenAccountNegative {
    
    private final String accountId = "MyAccountId";    
    private final Client client = ClientBuilder.newClient();
    private final String basePath = "http://localhost:8080";
    
    @Test
    public void test02_openAccount_Negative() {
        System.out.println("test2 run");
        WebTarget target = client.target(basePath + "/shortener/services/account");
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);
       
        JsonObject jsonObject = Json.createObjectBuilder()
                    .add("AccountId", accountId)
                    .build();
        
        invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.post(Entity.json(jsonObject.toString())); 
        System.out.println("jsonObject.toString()=" + jsonObject.toString());
        
        System.out.println("test2 response.getStatus()=" + response.getStatus());
        Assert.assertEquals(response.getStatus(), 409);    
    }
    
}
