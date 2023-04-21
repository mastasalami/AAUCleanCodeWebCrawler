package org.example;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class WebPage {
    private static final int    HEADING_MIN_LEVEL                   = 1;
    private static final int    HEADING_MAX_LEVEL                   = 6;
    private static final String LINK_HTML_ELEMENT_NAME              = "a";
    private static final String HEADING_HTML_ELEMENT_NAME           = "h";
    private static final String RELATIVE_LINK_TO_SAME_PAGE_SYMBOL   = "/";

    private Document document;
    private List<Element> links     = new ArrayList<>();
    private List<Element> headings  = new ArrayList<>();
    private String url;

    public WebPage(Document document, String url) throws Exception {
        this.document = document;
        this.url = url;

        if (this.document != null)
            getElementsFromDocument();
    }

    public String getUrl() {
        return url;
    }

    public void getElementsFromDocument() throws Exception {
        links = getElements(LINK_HTML_ELEMENT_NAME);
        getHeadings();
    }

    private void getHeadings() throws Exception {
        for (int level = HEADING_MIN_LEVEL; level < HEADING_MAX_LEVEL; level++) {
            getHeadingsByLevel(level);
        }
    }

    private void getHeadingsByLevel(int level) throws Exception {
        if (level < HEADING_MIN_LEVEL || level > HEADING_MAX_LEVEL)
            throw new Exception("No HTML element with the tag " + HEADING_HTML_ELEMENT_NAME + level + " exists!");

        String headingSelector = HEADING_HTML_ELEMENT_NAME + level;
        List<Element> headingsOfLevel = getElements(headingSelector);
        headings.addAll(headingsOfLevel);
    }

    private List<Element> getElements(String tagName) {
        return document.getElementsByTag(tagName);
    }

    public List<String> getLinkUrls() {
        List<String> linkUrls = new ArrayList<String>();
        for (Element link : links) {
            String linkUrl = getExternalLinkUrlFromElement(link);
            linkUrls.add(linkUrl);
        }

        return linkUrls;
    }

    private String getExternalLinkUrlFromElement(Element element) {
        String linkUrl = element.attr("href");

        if (linkUrl.startsWith("http") && !linkUrl.contains(this.url))
            return linkUrl;
        return new String();
    }
}
