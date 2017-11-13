package com.shortener.config;

/**
 * Redirect type enum
 */
public enum RedirectType {
    PERMANENTLY(301),
    TEMPORARILY(302);
    
    private final int value;

    RedirectType(int value) {
        this.value = value;
    }            
}
