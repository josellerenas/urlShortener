package com.example.urlShortener.repository;

import com.example.urlShortener.model.URL;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppRepository extends JpaRepository<URL, Long> {

    boolean existsByShortUrl(String shortUrl);
    URL findByShortUrl(String shortUrl);
}
