package com.shortener.rest;

import com.shortener.service.AccountService;
import java.io.InputStream;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 * Open account API
 */
@Path("account")
public class AccountResource { 

    @Inject
    private AccountService accountService;
   
    public AccountResource() {
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response openAccount(InputStream is) {
        JsonReader parser = Json.createReader(is);
        String accountId = null;
        try {
            JsonObject obj = parser.readObject();
            accountId= obj.getString("AccountId");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Response response = accountService.openAccount(accountId);
        return response;
    }

}
