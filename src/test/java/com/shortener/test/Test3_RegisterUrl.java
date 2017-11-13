/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shortener.test;

import com.shortener.test.utils.Authenticator;
import com.shortener.config.RedirectType;
import java.io.InputStream;
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

public class Test3_RegisterUrl {
    private final String accountId = "MyAccountId2";    
    private final String testUrl   = "http://stackoverflow.com/questions/1567929/website-safe-dataaccess-architecture-question?rq=1";
    private Client client = ClientBuilder.newClient();
    private final String basePath = "http://localhost:8080";
    
    private static String shortUrl;
    private static String password;
    
    public static String getPassword() {
        return password;
    }
    
    public static String getShortUrl() {
        return shortUrl;
    }
        
    @Test
    public void test03_registerUrl() {
        WebTarget target = client.target(basePath + "/shortener/services/account");
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);
       
        JsonObject jsonObject = Json.createObjectBuilder()
                    .add("AccountId", accountId)
                        .build();
        
        invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.post(Entity.json(jsonObject.toString())); 
        
        InputStream is = (InputStream)response.getEntity();
        JsonReader parser = Json.createReader(is);
        password = null;
        try {
            JsonObject obj = parser.readObject();
            password= obj.getString("password");
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
                        
        client = ClientBuilder.newClient().register(new Authenticator(accountId, password));
        target = client.target(basePath + "/shortener/services/register");        
        invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);
       
        jsonObject = Json.createObjectBuilder()
                    .add("url", testUrl)
                    .add("redirectType", RedirectType.PERMANENTLY.name())
                    .build(); 

        System.out.println("accountId=" + accountId);
        System.out.println("password=" + password);
        System.out.println("path=" + (basePath + "/shortener/services/register"));
        response = invocationBuilder.post(Entity.json(jsonObject.toString()));        
                
        System.out.println("test3 response.getStatus()=" + response.getStatus());
        Assert.assertEquals(response.getStatus(), 200);
        
        is = (InputStream)response.getEntity();
        
        parser = Json.createReader(is);
        try {
            JsonObject obj = parser.readObject();
            shortUrl = obj.getString("shortUrl");
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }        
        
        System.out.println("shortUrl=" + shortUrl);
        
        Assert.assertNotEquals(shortUrl.length(), 0);
    }
    
}
