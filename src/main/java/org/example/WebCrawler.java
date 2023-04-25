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
    private String url;
    private int maxDepth;
    private Connection connection;
    private List<WebPage> webPages = new ArrayList<>();
    private Set<String> visitedUrls = new HashSet<>();

    public WebCrawler(String url, int maxDepth) throws IOException {
        this.maxDepth = maxDepth;
        createNewConnection(url);        //For potential future methods
    }

    private void createNewConnection(String url) {
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
        createNewConnection(initialUrl);
        System.out.println("Crawling url:" + initialUrl);

        WebPage initialPage = getWebPageFromConnection(depth);
        if (depth < maxDepth) {
            depth++;
            List<String> linkUrls = initialPage.getLinkUrls();
            for (String url : linkUrls) {
                if (!url.isEmpty() && !visitedUrls.contains(url))
                    crawl(url, depth);
            }
        }
    }

    private WebPage getWebPageFromConnection(int depth) throws Exception {
        Document document = null;
        try {
            document = getDocumentFromConnection();
        } catch (IOException e) {                               //This means the call to get the page failed for some reason (Timeout, NoAccess,...)
            WebPage page = new WebPage(null, this.url, depth);
            addWebPageToList(page);
            return page;
        }

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
            headingsText += page.getHeadingsToText();
        }

        return headingsText;
    }
    //
    public List<String> getHeadingsAsList(){
        List<String> headingsList = new ArrayList<>();
        StringBuilder headingText = new StringBuilder();
        for (WebPage page: webPages) {
            headingText.append(page.getHeadingsToText());
        }

        return headingsList;
    }

    public String getLinksText() {
        String headingsText = "";
        for (WebPage page : webPages) {
            headingsText += page.getLinkText();
        }

        return headingsText;
    }
}
