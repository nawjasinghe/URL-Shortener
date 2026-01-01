package com.naw.urlshortener.domain.entites.respositories;
import java.util.Optional;

import com.naw.urlshortener.domain.entites.ShortenedUrl;
import org.springframework.data.jpa.repository.JpaRepository;

// spring Data JPA repository for database ops on shortened URLs (save, retrieve, lookup by short key)
public interface ShortRepository extends JpaRepository<ShortenedUrl, Long>{
    Optional<ShortenedUrl> findByShortKey(String shortKey);
}
