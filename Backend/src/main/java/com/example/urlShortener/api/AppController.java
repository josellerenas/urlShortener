package com.example.urlShortener.api;

import com.example.urlShortener.model.URL;
import com.example.urlShortener.repository.AppRepository;
import com.example.urlShortener.service.AppService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class AppController {

    private AppRepository appRepository;
    private AppService appService;

    public AppController(AppRepository appRepository, AppService appService) {
        this.appRepository = appRepository;
        this.appService = appService;
    }

    @GetMapping("/hello-world")
    public String helloWorld() {
        return "Hello world from the API";
    }

    @GetMapping("/shortUrl/{myUrl}")
    public String shortUrl(@PathVariable String myUrl) {
        return "Do you want to short this Url? " + myUrl;
    }

    @PostMapping("/shortUrlPost")
    public void shortUrlPost(@RequestBody URL myUrl) {
        String randomString = "";
        do {
            randomString = appService.generateRandomString();
        } while (appRepository.existsByShortUrl(randomString));

        myUrl.setShortUrl(randomString);
        myUrl.setDate(appService.getTodaysDate());
        appRepository.save(myUrl);
    }

//    @GetMapping("/{shortUrl}")
//    public void redirectShortUrlToLongUrl(@PathVariable String shortUrl) {
//        URL temp = appRepository.findByShortUrl(shortUrl);
//        System.out.println(temp);
//        System.out.println(temp.getLongUrl());
//    }

    @GetMapping("/{shortUrl}")
    public ModelAndView redirectShortUrlToLongUrl(@PathVariable String shortUrl) {

        if (appRepository.existsByShortUrl(shortUrl)) {
            URL temp = appRepository.findByShortUrl(shortUrl);
            // todo: fix this "https" thing. Check if the original url already has it.
            String redirectUrl = "https://" + temp.getLongUrl();
            return new ModelAndView(new RedirectView(redirectUrl));
        } else { // When the short URL doesn't exist, handle properly
            String redirectUrl = "https://www.youtube.com/watch?v=dQw4w9WgXcQ&ab_channel=RickAstley";
            return new ModelAndView(new RedirectView(redirectUrl));
        }
    }
}
