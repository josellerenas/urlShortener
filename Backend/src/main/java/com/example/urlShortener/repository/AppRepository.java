package com.example.urlShortener.repository;

import com.example.urlShortener.model.URL;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRepository extends JpaRepository<URL, Long> {
}
