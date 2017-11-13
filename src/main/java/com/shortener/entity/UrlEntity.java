package com.shortener.entity;

import com.shortener.config.RedirectType;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Incapsulate URL redirection data
 */
public class UrlEntity {
    
    private final String url;
    private final String shortUrl;
    private final RedirectType type;
    private AtomicInteger visitedCounter = new AtomicInteger(0);    
    
    public UrlEntity(String url, String shortUrl, RedirectType type) {
        this.url = url;
        this.shortUrl = shortUrl;
        this.type = type;
    }
    
    public String getUrl() {
        return url;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public RedirectType getType() {
        return type;
    }
    
    public int getVisitedCounter() {
        return visitedCounter.get();
    }
    
    public void visited() {
        visitedCounter.incrementAndGet();
    }
    
}
