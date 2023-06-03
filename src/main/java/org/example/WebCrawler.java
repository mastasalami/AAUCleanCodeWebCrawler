package org.example;

import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WebCrawler {
    private final int maxDepth;
    private final CrawlerConnection connection;
    private final List<WebPage> webPages = new ArrayList<>();
    private final Set<String> visitedUrls = new HashSet<>();

    private Logger logger = Logger.getInstance();

    public WebCrawler(int maxDepth) {
        this.maxDepth = maxDepth;
        connection = new CrawlerConnection();
    }

    private void createNewConnection(String url) {
        connection.createNewConnection(url);
    }

    private DOMDocument getDocumentFromConnection() throws IOException {
        return connection.loadDocument();
    }

    public void crawl(String initialUrl) {
        crawl(initialUrl, 0);
    }
    private void crawl(String initialUrl, int depth) {
        createNewConnection(initialUrl);
        try {
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
        catch (IOException e) {
            visitedUrls.add(connection.getUrl());
            logger.log("URL:" + connection.getUrl() + " could not be fetched!");
        }
    }

    private boolean shouldCrawlUrl(String url) {
        return (!url.isEmpty() && !visitedUrls.contains(url));
    }

    private WebPage getWebPageFromConnection(int depth) throws IOException {
        DOMDocument document = getDocumentFromConnection();

        if (document == null)                                                                                           //This means the call to get the page failed for some reason (Timeout, NoAccess,...)
            return createNotReachableWebPage(depth);
        else
            return createReachableWebPage(document, depth);
    }

    private WebPage createNotReachableWebPage(int depth) {
        WebPage page = new WebPage(null, connection.getUrl(), depth);
        addWebPageToList(page);
        return page;
    }

    private WebPage createReachableWebPage(DOMDocument document, int depth) {
        WebPage page = new WebPage(document, connection.getUrl(), depth);
        page.loadElementsFromDocument();
        addWebPageToList(page);
        return page;
    }

    private void addWebPageToList(WebPage page) {
        webPages.add(page);
        visitedUrls.add(page.getUrl());
    }

    public String getHeadingsText() {
        StringBuilder headingsText = new StringBuilder();

        for (WebPage page : webPages) {
            headingsText.append(page.getHeadingsToString());
        }

        return headingsText.toString();
    }

    public List<String> getHeadingsAsList(){
        List<String> headingsList = new ArrayList<>();
        for (WebPage page: webPages) {
            headingsList.add(page.getHeadingsToString());
        }

        return headingsList;
    }

    public String getLinksText() {
        StringBuilder headingsText = new StringBuilder();
        for (WebPage page : webPages) {
            headingsText.append(page.getLinkText());
        }

        return headingsText.toString();
    }
}
