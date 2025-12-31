package com.naw.urlshortener;

import com.naw.urlshortener.domain.entites.ShortenedUrl;
import com.naw.urlshortener.domain.entites.respositories.ShortRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class HomeController {

    private final ShortRepository shortRepository;

    public HomeController(ShortRepository shortRepository) {
        this.shortRepository = shortRepository;
    }
    @GetMapping("/")
    public String home(Model model) {
        List<ShortenedUrl> shortenedUrls = shortRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        model.addAttribute("shortenedUrls", shortenedUrls);
        return "index";
    }


}
