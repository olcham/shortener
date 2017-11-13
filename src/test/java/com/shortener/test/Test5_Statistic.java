package com.shortener.test;

import com.shortener.test.utils.Authenticator;
import java.io.InputStream;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.junit.Assert;
import org.junit.Test;

public class Test5_Statistic {
    private final String accountId = "MyAccountId2";        
    private final Client client = ClientBuilder.newClient().register(new Authenticator(accountId, Test3_RegisterUrl.getPassword()));;
    private final String basePath = "http://localhost:8080";
    
    @Test
    public void test05_statistic(){
        System.out.println("test5 run");
        WebTarget target = client.target( basePath + "/shortener/services/statistic");        
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);
                
        invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);        
        Response response = invocationBuilder.get();
        
        Assert.assertEquals(response.getStatus(), 200);        
        
        InputStream is = (InputStream)response.getEntity();
        
        JsonReader parser = Json.createReader(is);
        try {
            JsonObject obj = parser.readObject();
            JsonArray array = obj.getJsonArray("visitedLinks");
            
            for (int i = 0; i < array.size(); i++) {
                System.out.println(array.get(i));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
