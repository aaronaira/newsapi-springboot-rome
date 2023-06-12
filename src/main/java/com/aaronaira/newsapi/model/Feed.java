package com.aaronaira.newsapi.model;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Feed {

    final String title;
    final String link;
    final String description;
    final Date pubDate;
    final String source;


    public Feed(String title, String link, String description, Date pubDate, String source) {
        this.title = title;
        this.link = link;
        this.description = description;;
        this.pubDate = pubDate;
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public String getSource() {return source; }

}