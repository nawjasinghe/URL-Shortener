package com.naw.urlshortener.domain.entites;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "shortened_urls")
public class ShortenedUrl {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "original_url", nullable = false)
   private String originalUrl;

   @Column(name = "short_key", nullable = false, unique = true)
   private String shortKey;

   @Column(name = "is_private")
   private Boolean isPrivate = false;

   @Column(name = "created_at")
   private LocalDateTime createdAt = LocalDateTime.now();

   @Column(name = "expires_at")
   private LocalDateTime expiresAt;

   @Column(name = "click_count")
   private Long clickCount = 0L;

   public ShortenedUrl(){
   }
    // constructor for getting a shortened URL
   public ShortenedUrl(String originalUrl, String shortKey){
       this.originalUrl = originalUrl;
       this.shortKey = shortKey;
       this.createdAt = LocalDateTime.now();
   }

   // Getters and Setters
   public Long getId() {
        return id;
   }
   public void setId(Long id) {
        this.id = id;
   }

   public String getOriginalUrl() {
        return originalUrl;
   }
   public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
   }

   public String getShortKey() {
        return shortKey;
   }
   public void setShortKey(String shortKey) {
        this.shortKey = shortKey;
   }

   public Boolean getIsPrivate() {
        return isPrivate;
   }
   public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
   }

   public LocalDateTime getCreatedAt() {
        return createdAt;
   }
   public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
   }

   public LocalDateTime getExpiresAt() {
        return expiresAt;
   }
   public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
   }
}
