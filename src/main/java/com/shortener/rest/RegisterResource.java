package com.shortener.rest;

import com.shortener.config.RedirectType;
import com.shortener.service.RegisterService;
import com.shortener.service.AccountService;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 * URL registration API
 */
@Path("register")
public class RegisterResource {
    
    @Inject    
    private AccountService accountService;

    /**
     * Creates a new instance of RegisterResource
     */
    public RegisterResource() {
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRegister() {
        Response.ResponseBuilder response = Response.status(Response.Status.OK);
        return response.build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerURL(@HeaderParam("authorization") String authString, InputStream is) {  
        Response response;
        
        if (authString == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        String accountId = accountService.getAuthenticatedUser(authString);
        if (accountId == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        
        String url;
        String redirectTypeStr;
        JsonReader parser = Json.createReader(is);
        try {
            JsonObject obj = parser.readObject();
            url = obj.getString("url");            
            redirectTypeStr = obj.getString("redirectType");

            is.close();
        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        try {
            response = RegisterService.registerUrl(accountService.getStorage(accountId), url, RedirectType.valueOf(redirectTypeStr));        
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return response;
    }
        
}
