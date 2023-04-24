package org.example;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WebCrawler {
    private Connection connection;
    private String url              = "https://www.aau.at/";
    private int maxDepth            = 2;
    private List<WebPage> webPages  = new ArrayList<>();
    private Set<String> visitedUrls = new HashSet<>();

    public WebCrawler(String url, int maxDepth) throws IOException {
        this.maxDepth = maxDepth;
        init(url);
    }

    private void init(String url) throws IOException {
        this.url = url;
        connection = Jsoup.connect(url);
    }

    private Document getDocumentFromConnection() throws IOException {
        return connection.execute().parse();
    }

    public void crawl() throws Exception {
        crawl(this.url, 0);
    }
    private void crawl(String initialUrl, int depth) throws Exception {
        init(initialUrl);
        System.out.println("Crawling url:" + initialUrl);

        WebPage initialPage = getWebPageFromConnection(depth);
        if (depth < maxDepth) {
            depth++;
            List<String> linkUrls = initialPage.getLinkUrls();
            for (String url : linkUrls) {
                if (shouldCrawlUrl(url))
                    crawl(url, depth);
            }
        }
    }

    private boolean shouldCrawlUrl(String url) {
        return (!url.isEmpty() && !visitedUrls.contains(url));
    }

    private WebPage getWebPageFromConnection(int depth) throws Exception {
        Document document = null;
        try {
            document = getDocumentFromConnection();
        } catch (IOException e) {                               //This means the call to get the page failed for some reason (Timeout, NoAccess,...)
            return createNotReachableWebPage(depth);
        }

        return createReachableWebPage(document, depth);
    }

    private WebPage createNotReachableWebPage(int depth) throws Exception {
        WebPage page = new WebPage(null, this.url, depth);
        addWebPageToList(page);
        return page;
    }

    private WebPage createReachableWebPage(Document document, int depth) throws Exception {
        WebPage page = new WebPage(document, this.url, depth);
        addWebPageToList(page);
        return page;
    }

    private void addWebPageToList(WebPage page) {
        webPages.add(page);
        visitedUrls.add(page.getUrl());
    }

    public String getHeadingsText() {
        String headingsText = "";
        for (WebPage page : webPages) {
            headingsText += page.getHeadingsToString();
        }

        return headingsText;
    }

    public String getLinksText() {
        String headingsText = "";
        for (WebPage page : webPages) {
            headingsText += page.getLinkText();
        }

        return headingsText;
    }
}
