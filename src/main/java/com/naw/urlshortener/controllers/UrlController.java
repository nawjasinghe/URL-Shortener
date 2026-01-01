package com.naw.urlshortener.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import com.naw.urlshortener.services.URLShortenService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping
public class UrlController{

    private final URLShortenService urlService;

    // service handling URL shortening logic
    public UrlController(URLShortenService urlService){
        this.urlService = urlService;
    }

    @GetMapping("/")
    public String home(Model model){
        // get all shortened URLs from the database and add to model
        model.addAttribute("shortenedUrls", urlService.getAllShortenedUrls());
        return "index";
    }

    @PostMapping("/shorten")
    public String shortenUrl(@RequestParam String originalUrl){
        //generate and save the shortened URL
        urlService.shortenURL(originalUrl);
        // redirect back to homepage to see the updated lisst
        return "redirect:/";
    }

    @PostMapping("/update/{shortKey}")
    public String updateUrl(@PathVariable String shortKey, @RequestParam String newOriginalUrl){
        urlService.updateUrl(shortKey, newOriginalUrl);
        return "redirect:/";
    }

    @PostMapping("/delete/{shortKey}")
    public String deleteUrl(@PathVariable String shortKey){
        urlService.deleteUrl(shortKey);
        return "redirect:/";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleInvalidUrl(IllegalArgumentException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
