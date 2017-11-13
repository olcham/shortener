package com.shortener.test;

import java.io.InputStream;
import java.math.BigDecimal;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.junit.Assert;
import org.junit.Test;

public class Test1_OpenAccount {
    
    private final String accountId = "MyAccountId";        
    private final Client client = ClientBuilder.newClient();
    private final String basePath = "http://localhost:8080";
    
    private static String password;       
            
    @Test
    public void test01_openAccount(){
        System.out.println("test1 run");
        WebTarget target = client.target(basePath + "/shortener/services/account");
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);
       
        JsonObject jsonObject = Json.createObjectBuilder()
                    .add("AccountId", accountId)
                        .build();
        
        invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);
        
        Response response = invocationBuilder.post(Entity.json(jsonObject.toString())); 
                
        System.out.println("test1 response.getStatus()=" + response.getStatus());
        Assert.assertEquals(200, response.getStatus());
        InputStream is = (InputStream)response.getEntity();
        
        JsonReader parser = Json.createReader(is);
        password = null;
        try {
            JsonObject obj = parser.readObject();
            password = obj.getString("password");
            System.out.println("password=" + password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertEquals(8, password.length());
    }       
}
