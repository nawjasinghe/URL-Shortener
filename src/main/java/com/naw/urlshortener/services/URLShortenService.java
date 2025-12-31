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
        // normalize the URL first
        String normalizedUrl = normalizeUrl(originalUrl);

        // logic to shorten the URL
        String shortKey = generateShortKey(normalizedUrl);

        //check for dupelicate short keys
        Optional<ShortenedUrl> existing = shortRepository.findByShortKey(shortKey);
        if (existing.isPresent()) {
            //return exisiting entity if key already exists
            if (existing.get().getOriginalUrl().equals(normalizedUrl)) {
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
    public String normalizeUrl(String url) {
        //normal/ blank inputs check
        if (url == null || url.isBlank()) {
            return url;
        }
        url = url.trim();
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            return "https://" + url;
        }
        return url;
    }

    public boolean isValidUrl(String url) {
        //normalize the URL first
        String normalizedUrl = normalizeUrl(url);

        //check for blank or null inputs
        if (normalizedUrl == null || normalizedUrl.isBlank()) {
            return false;
        }
        // basic URL validation
        return normalizedUrl.startsWith("http://") || normalizedUrl.startsWith("https://");
    }

    public Iterable<ShortenedUrl> getAllShortenedUrls() {
        // ftch all records from the shortened_urls table
        return shortRepository.findAll();
    }
}
