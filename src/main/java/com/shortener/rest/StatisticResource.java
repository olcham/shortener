package com.shortener.rest;

import com.shortener.service.AccountService;
import javax.inject.Inject;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 * Get statistic API
 */
@Path("statistic")
public class StatisticResource {

    
    @Inject
    private AccountService accountService;

    /**
     * Creates a new instance of StatisticResource
     */
    public StatisticResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStatistic(@HeaderParam("authorization") String authString) {
        if (authString == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        String accountId = accountService.getAuthenticatedUser(authString);
        System.out.println("STATISTICS");
        if (accountId == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
                       
        return accountService.getStatistic(accountId);
    }

}
