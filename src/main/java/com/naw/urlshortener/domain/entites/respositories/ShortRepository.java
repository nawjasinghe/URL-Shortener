package com.naw.urlshortener.domain.entites.respositories;

import com.naw.urlshortener.domain.entites.ShortenedUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortRepository extends JpaRepository<ShortenedUrl, Long> {
}
