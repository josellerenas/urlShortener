package com.example.urlShortener.api;

import com.example.urlShortener.model.URL;
import com.example.urlShortener.repository.AppRepository;
import org.springframework.web.bind.annotation.*;

@RestController
public class AppController {

    private AppRepository appRepository;

    public AppController(AppRepository appRepository) {
        this.appRepository = appRepository;
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
    public URL shortUrlPost(@RequestBody URL myUrl) {
        appRepository.save(myUrl);
        return myUrl;
    }
}
