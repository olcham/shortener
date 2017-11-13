package com.shortener.config;

import com.shortener.service.AccountService;
import javax.ws.rs.ApplicationPath;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Application configuration
 */
@ApplicationPath("services")
public class ApplicationConfig extends ResourceConfig {

    public ApplicationConfig() {
        register(new MyApplicationBinder());
        packages("com.shortener.rest");        
    }

    private static class MyApplicationBinder  extends AbstractBinder  {
        @Override
        protected void configure() {
            bind(new AccountService()).to(AccountService.class);
        }
    }
    
}
