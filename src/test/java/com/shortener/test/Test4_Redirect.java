/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shortener.test;

import com.shortener.test.utils.Authenticator;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.junit.Assert;
import org.junit.Test;

public class Test4_Redirect {
    private final String accountId = "MyAccountId2";        
    private final Client client = ClientBuilder.newClient().register(new Authenticator(accountId, Test3_RegisterUrl.getPassword()));
    private final String basePath = Test3_RegisterUrl.getShortUrl();
    
    @Test
    public void test04_redirect(){
        System.out.println("test4 run");
        WebTarget target = client.target(basePath);        
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);
        
        System.out.println("basePath=" + basePath);
        System.out.println("password=" + Test3_RegisterUrl.getPassword());
        invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);        
        Response response = invocationBuilder.get();
        
        Assert.assertEquals(301, response.getStatus());        
    }
}
