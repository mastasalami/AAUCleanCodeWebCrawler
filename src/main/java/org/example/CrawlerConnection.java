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

    public Document getDocumentFromConnection() throws IOException {
        return connection.execute().parse();
    }

}
