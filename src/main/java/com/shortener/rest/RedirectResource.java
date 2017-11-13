/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shortener.rest;

import com.shortener.service.AccountService;
import com.shortener.entity.UrlEntity;
import com.shortener.repository.UrlStorage;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import javax.inject.Inject;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 * URL redirection API
 */
@Path("redirect")
public class RedirectResource {

    @Inject
    private AccountService accountService;
    
    /**
     * Creates a new instance of RedirectResource
     */
    public RedirectResource() {
    }

    /**
     * Retrieves representation of an instance of com.shortener.rest.RedirectResource
     * @return an instance of java.lang.String
     */    
    @GET    
    @Path("{urlPart}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRedirection(@HeaderParam("authorization") String authString, @PathParam("urlPart") String urlPart) {
        
        String urlString = UrlStorage.shortUrlBase + urlPart;
        
        if (authString == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        
        String accountId = accountService.getAuthenticatedUser(authString);        
        if (accountId == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        
        UrlStorage storage = accountService.getStorage(accountId);        
        UrlEntity urlEntity = storage.getStorageEntity(urlString);        
                
        if (urlEntity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }             
        
        urlEntity.visited();
        
        URL url;
                
        switch (urlEntity.getType()) {
            case PERMANENTLY:
                try {
                    url = new URL(urlEntity.getUrl()); 
                    return Response.seeOther(url.toURI()).build();
                } catch (URISyntaxException ex) {
                    System.out.println("URISyntaxException");
                    return Response.status(Response.Status.BAD_REQUEST).build();
                } catch (MalformedURLException ex) {
                    System.out.println("MalformedURLException");
                    return Response.status(Response.Status.BAD_REQUEST).build();
                }
            case TEMPORARILY:
                try {
                    url = new URL(urlEntity.getUrl()); 
                    return Response.temporaryRedirect(url.toURI()).build();
                } catch (URISyntaxException ex) {
                    System.out.println("URISyntaxException");
                    return Response.status(Response.Status.BAD_REQUEST).build();
                } catch (MalformedURLException ex) {
                    System.out.println("MalformedURLException");
                    return Response.status(Response.Status.BAD_REQUEST).build();
                }
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
        
    }
    
}
