/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shortener.repository;

import com.shortener.config.RedirectType;
import com.shortener.entity.UrlEntity;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * URL data storage
 */
public class UrlStorage {
    
    private final String accountId;
    private final Map <String, UrlEntity> storage = new ConcurrentHashMap<>();    
    private final AtomicInteger shortUrlChange = new AtomicInteger(new Random().nextInt());
    
    public static final String shortUrlBase = "http://localhost:8080/shortener/services/redirect/";
    
    public UrlStorage(String accountId) {
        this.accountId = accountId;
    }
    
    public String registerUrl(String url, RedirectType type) {
        String shortUrl = generateShortUrl(url);
        storage.putIfAbsent(shortUrl, new UrlEntity(url, shortUrl, type));
        return shortUrl;
    }
    
    private String generateShortUrl(String url) {
        shortUrlChange.incrementAndGet();
        String shortUrl = shortUrlBase + shortUrlChange.toString();
        return shortUrl;
    }
    
    public Collection<UrlEntity> getStatistic() {
        return storage.values();
    }
    
    public UrlEntity getStorageEntity(String key) {
        return storage.get(key);
    }   
        
}
