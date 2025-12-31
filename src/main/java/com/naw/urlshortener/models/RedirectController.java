package com.naw.urlshortener;

import com.naw.urlshortener.domain.entites.ShortenedUrl;
import com.naw.urlshortener.domain.entites.respositories.ShortRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class RedirectController {

    private final ShortRepository shortRepository;

    public RedirectController(ShortRepository shortRepository) {
        this.shortRepository = shortRepository;
    }

    @GetMapping("/{shortKey}")
    public String redirect(@PathVariable String shortKey) {
        ShortenedUrl shortenedUrl = shortRepository.findByShortKey(shortKey)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Short URL not found"));

        return "redirect:" + shortenedUrl.getOriginalUrl();
    }
}
