package com.aaronaira.newsapi.repository;


import com.aaronaira.newsapi.model.Feed;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;


@Repository
public class FeedRepository {

    private static final List<Feed> feeds = new ArrayList<>();
    private static final String newspaperSource = "\\src\\main\\resources\\newspaper.properties";

    public FeedRepository() {

    }

    public ResponseEntity<List<Feed>> getAll() {
           return parseFeeds()
                   .map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.noContent().build());
    }

    private static Optional<Properties> loadFeedsUrl() {
        String newspaperProperties = System.getProperty("user.dir") + newspaperSource;

        try (InputStream input = new FileInputStream(newspaperProperties)) {

            Properties prop = new Properties();
            prop.load(input);

            return Optional.of(prop);

        } catch (IOException io) {
            System.out.println(io.getMessage());
        }

        return Optional.empty();
    }

    private static Optional<List<Feed>> parseFeeds()  {

        if(loadFeedsUrl().isEmpty()) {
            return Optional.empty();
        } else {
            loadFeedsUrl().ifPresent(item -> item.forEach((newspaper, feedUrl) -> {
                URL feedSource = null;

                try {
                    feedSource = new URL(feedUrl.toString());
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
                SyndFeedInput input = new SyndFeedInput();
                SyndFeed feedReader = null;

                try {
                    feedReader = input.build(new XmlReader(feedSource));
                    feedReader.getEntries().stream().limit(5).forEach(feed -> feeds.add(mapToFeed(feed, newspaper.toString())));
                } catch (FeedException | IOException e) {
                    throw new RuntimeException(e);
                }
            }));
        }

        return Optional.of(feeds);
    }

    private static Feed mapToFeed(SyndEntry syndEntry, String newspaper) {
       return new Feed(syndEntry.getTitle(), syndEntry.getLink(), syndEntry.getDescription().getValue(), syndEntry.getPublishedDate(), newspaper);
    }
}


