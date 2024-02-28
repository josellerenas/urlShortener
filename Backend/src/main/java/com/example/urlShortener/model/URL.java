package com.example.urlShortener.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class URL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String urlDirection;

    public URL() {
    }

    public URL(String urlDirection) {
        this.urlDirection = urlDirection;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrlDirection() {
        return urlDirection;
    }

    public void setUrlDirection(String urlDirection) {
        this.urlDirection = urlDirection;
    }

    @Override
    public String toString() {
        return "URL{" +
                "id=" + id +
                ", UrlDirection='" + urlDirection + '\'' +
                '}';
    }
}
