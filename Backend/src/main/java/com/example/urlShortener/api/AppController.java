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

    // Generates a new short URL from an original large URL, and saves it in the database.
    @PostMapping("/shortUrlPost")
    public URL shortUrlPost(@RequestBody URL myUrl) {

        // Generates a random string that's not already existing in the database.
        String randomString = "";
        do {
            randomString = appService.generateRandomString();
        } while (appRepository.existsByShortUrl(randomString));

        // todo: check first if the long URL already has a short URL, and return it if so.

        // Checks if the original large URL contains "https://" or "http://" at the beginning. If not, adds it.
        String existsHttps = myUrl.getLongUrl().substring(0, Math.min(myUrl.getLongUrl().length(), 8));
        if ( !(existsHttps.contains("https://") || existsHttps.contains("http://")) ) {
            myUrl.setLongUrl("https://" + myUrl.getLongUrl());
        }

        myUrl.setShortUrl(randomString);
        myUrl.setDate(appService.getTodaysDate());
        appRepository.save(myUrl);

        return myUrl;
    }

    // Takes you to the large URL associated with the short URL selected.
    @GetMapping("/{shortUrl}")
    public ModelAndView redirectShortUrlToLongUrl(@PathVariable String shortUrl) {

        // If the short URL exists, takes you to the associated large URL. If not, takes you to a determined error page.
        if (appRepository.existsByShortUrl(shortUrl)) {
            URL temp = appRepository.findByShortUrl(shortUrl);
            String redirectUrl = temp.getLongUrl();
            return new ModelAndView(new RedirectView(redirectUrl));
        } else { // When the short URL doesn't exist, handle properly
            String redirectUrl = "https://www.youtube.com/watch?v=dQw4w9WgXcQ&ab_channel=RickAstley";
            return new ModelAndView(new RedirectView(redirectUrl));
        }
    }
}
