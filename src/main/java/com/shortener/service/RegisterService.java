package com.shortener.service;

import com.shortener.config.RedirectType;
import com.shortener.repository.UrlStorage;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * The class for work with URL registration
 */
public class RegisterService {
    
    private RegisterService() {}
    
    public static Response registerUrl(UrlStorage storage, String url, RedirectType type) {               
        JsonObject entity;                
        
        if (storage != null) {                    
            entity = Json.createObjectBuilder()
                    .add("shortUrl", storage.registerUrl(url, type))
                        .build();
            return Response.ok(entity, MediaType.APPLICATION_JSON).build();
        } else {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }
                
}
