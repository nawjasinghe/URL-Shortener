package com.naw.urlshortener.services;

import com.naw.urlshortener.domain.entites.ShortenedUrl;
import com.naw.urlshortener.domain.entites.respositories.ShortRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class URLShortenService{
    private final ShortRepository shortRepository;

    public URLShortenService(ShortRepository shortRepository){
        this.shortRepository = shortRepository;
    }

    public String generateShortKey(String originalUrl){
        // generating a short key using first N chars of the hashcode
        return Integer.toString(Math.abs(originalUrl.hashCode()), 36).substring(0, 6);
    }

    public ShortenedUrl shortenURL(String originalUrl){
        // validate URL first
        if (!isValidUrl(originalUrl)){
            throw new IllegalArgumentException("Invalid URL provided");
        }

        // normalize the URL
        String normalizedUrl = normalizeUrl(originalUrl);

        // logic to shorten the URL
        String shortKey = generateShortKey(normalizedUrl);

        //check for dupelicate short keys
        Optional<ShortenedUrl> existing = shortRepository.findByShortKey(shortKey);
        if(existing.isPresent()){
            //return exisiting entity if key already exists
            if(existing.get().getOriginalUrl().equals(normalizedUrl)){
                return existing.get();
            }
            throw new IllegalStateException("Short key collision occurred. Please try again.");
        }

        // create new ShortenedUrl entity with the normalized URL and generated key
        ShortenedUrl shortenedUrl = new ShortenedUrl(normalizedUrl, shortKey);

        //add to database and return the saved entity
        shortRepository.save(shortenedUrl);
        return shortenedUrl;
    }
    // normalize URL by ensuring it starts with http:// or https:// even if user inputs without it
    public String normalizeUrl(String url){
        //normal/ blank inputs check
        if(url == null || url.isBlank()){
            return url;
        }
        url = url.trim();
        if(!url.startsWith("http://") && !url.startsWith("https://")){
            return "https://" + url;
        }
        return url;
    }

    public boolean isValidUrl(String url){
        if(url == null || url.isBlank()){
            return false;
        }
        String normalizedUrl = normalizeUrl(url);
        try{
            java.net.URI uri = new java.net.URI(normalizedUrl);
            String host = uri.getHost();
            // check host exists and contains at least one dot (basic domain check)
            return host != null && host.contains(".");
        }catch (Exception e){
            return false;
        }
    }

    //ipdates the original URL for an existing shortened URL entry
    // normalizes the new URL before saving to keep format
    public void updateUrl(String shortKey, String newOriginalUrl){
        // validate the new url before updating
        if(!isValidUrl(newOriginalUrl)){
            throw new IllegalArgumentException("Invalid URL provided");
        }
        ShortenedUrl url = shortRepository.findByShortKey(shortKey).orElseThrow(() -> new RuntimeException("URL not found"));
        url.setOriginalUrl(normalizeUrl(newOriginalUrl));
        shortRepository.save(url);
    }

    // deletes a shortened URL entry from the database using its short key
    // throws exception if the short key doesn't exist
    public void deleteUrl(String shortKey){
        ShortenedUrl url = shortRepository.findByShortKey(shortKey).orElseThrow(() -> new RuntimeException("URL not found"));
        shortRepository.delete(url);
    }

    public Optional<ShortenedUrl> findByShortKey(String shortKey){
        return shortRepository.findByShortKey(shortKey);
    }

    public Iterable<ShortenedUrl> getAllShortenedUrls(){
        // ftch all records from the shortened_urls table
        return shortRepository.findAll();
    }
}
