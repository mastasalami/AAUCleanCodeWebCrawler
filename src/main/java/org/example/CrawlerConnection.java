package org.example;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class CrawlerConnection {
    private String url;
    private Connection connection;

    public CrawlerConnection() {
    }

    public String getUrl() {
        return url;
    }

    public void createNewConnection(String url) {
        this.url = url;
        connection = Jsoup.connect(url);
    }

    public Document loadDocument() throws IOException {
        return connection != null ? connection.execute().parse() : null;
    }

    public Connection getConnection() {
        return connection;
    }
}
