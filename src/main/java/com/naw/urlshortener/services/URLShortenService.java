package com.naw.urlshortener.services;

import com.naw.urlshortener.domain.entites.ShortenedUrl;
import com.naw.urlshortener.domain.entites.respositories.ShortRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class URLShortenService {
    private final ShortRepository shortRepository;

    public URLShortenService(ShortRepository shortRepository) {
        this.shortRepository = shortRepository;
    }

    public String generateShortKey(String originalUrl) {
        // generating a short key using first N chars of the hashcode
        return Integer.toString(Math.abs(originalUrl.hashCode()), 36).substring(0, 6);
    }

    public ShortenedUrl shortenURL(String originalUrl) {
        // logic to shorten the URL
        String shortKey = generateShortKey(originalUrl);

        //check for dupelicate short keys
        Optional<ShortenedUrl> existing = shortRepository.findByShortKey(shortKey);
        if (existing.isPresent()) {
            //return exisiting entity if key already exists
            if (existing.get().getOriginalUrl().equals(originalUrl)) {
                return existing.get();
            }
            throw new IllegalStateException("Short key collision occurred. Please try again.");
        }

        // create new ShortenedUrl entity with the original URL and generated key
        ShortenedUrl shortenedUrl = new ShortenedUrl(originalUrl, shortKey);

        //add to database and return the saved entity
        shortRepository.save(shortenedUrl);
        return shortenedUrl;
    }

    public boolean isValidUrl(String url) {
        // basic URL validation
        return url.startsWith("http://") || url.startsWith("https://");
    }

    public Iterable<ShortenedUrl> getAllShortenedUrls() {
        // ftch all records from the shortened_urls table
        return shortRepository.findAll();
    }
}
