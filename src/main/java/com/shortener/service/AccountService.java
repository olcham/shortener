package com.shortener.service;

import com.shortener.entity.UrlEntity;
import com.shortener.repository.UrlStorage;
import java.io.IOException;
import java.security.SecureRandom;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Singleton;
import javax.json.JsonObjectBuilder;
import org.apache.commons.lang.RandomStringUtils;
import sun.misc.BASE64Decoder;


/**
 * The class for work with account related logic
 * 
 */
@Singleton
public class AccountService {
    private final int randomStrLength = 8;
    private final Map<String, String> accounts = new ConcurrentHashMap<>();
    private final Map<String, UrlStorage> storages = new ConcurrentHashMap<>();

    private final char[] possibleCharacters =
            ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;\'\",<.>/?").toCharArray();

     public Response openAccount(String accountId) {
        JsonObject entity;
        if (accounts.containsKey(accountId)) {
            entity = Json.createObjectBuilder()
                    .add("success", "false")
                    .add("description", "Account with that ID already exists")
                    .build();
            return Response.status(Response.Status.CONFLICT).entity(entity).build();
        } else {
            String password = getNewPassword();
            accounts.putIfAbsent(accountId, password);
            storages.putIfAbsent(accountId, new UrlStorage(accountId));
            entity = Json.createObjectBuilder()
                    .add("success", "true")
                    .add("description", "'Your account " + accountId +" is opened")
                    .add("password", password)
                    .build();
            return Response.ok(entity, MediaType.APPLICATION_JSON).build();
        }

    }
    
    public Response deleteAccount(String accountId) {
        if (accounts.remove(accountId) != null) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

    }


    private String getNewPassword() {
        String randomStr = RandomStringUtils.random( randomStrLength, 0, possibleCharacters.length-1, false, false, possibleCharacters, new SecureRandom() );
        return randomStr;
    }
    
    public UrlStorage getStorage(String accountId) {
        return storages.get(accountId);
    }
    
    public Response getStatistic(String accountId) {
        UrlStorage storage = storages.get(accountId);
        JsonObjectBuilder entity = Json.createObjectBuilder();

        for (UrlEntity url : storage.getStatistic()) {
            entity.add(url.getUrl(), url.getVisitedCounter());
        }
        JsonObject jo = Json.createObjectBuilder()
            .add("visitedLinks", Json.createArrayBuilder()
                .add(entity)).build();
        
        return Response.ok(jo, MediaType.APPLICATION_JSON).build();
    }
    
   public String getAuthenticatedUser(String authString){
         
        String decodedAuth;
        // Header is in the format "Basic 5tyc0uiDat4"
        // We need to extract data before decoding it back to original string
        String[] authParts = authString.split("\\s+");
        String authInfo = authParts[1];
        
        // Decode the data back to original string
        byte[] bytes = null;
        try {
            bytes = new BASE64Decoder().decodeBuffer(authInfo);
        } catch (IOException e) {
            System.out.println("IOException");
            return null;
        }
        decodedAuth = new String(bytes);
         
        String[] parts = decodedAuth.split(":");      
        String pass = parts[1];
        if (pass.equals(accounts.get(parts[0]))) {
            return parts[0];
        } else {
            return null;
        }
    }
}
