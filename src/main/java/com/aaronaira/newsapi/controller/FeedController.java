package com.aaronaira.newsapi.controller;

import com.aaronaira.newsapi.model.Feed;
import com.aaronaira.newsapi.repository.FeedRepository;
import com.rometools.rome.io.FeedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/news")
public class FeedController {
    private final FeedRepository feedRepository;

    public FeedController(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @GetMapping("")
    public ResponseEntity<List<Feed>> getAll() {
        return feedRepository.getAll();
    }
}
