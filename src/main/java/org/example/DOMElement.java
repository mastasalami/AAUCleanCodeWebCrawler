package org.example;
import org.jsoup.nodes.Element;

public class DOMElement {
    private static final String LINK_ATTRIBUTE                      = "href";
    private static final String EXTERNAL_WEBSITE_INDICATOR          = "http";

    private Element element;

    public DOMElement(Element element) {
        this.element = element;
    }

    public String getName() {
        return element.nodeName();
    }

    public String getAttribute(String attributeName) {
        return element.attr(attributeName);
    }

    public String getText() {
        return element.text();
    }

    public String getExternalLinkUrl(String baseUrl) {
        String linkUrl = element.attr(LINK_ATTRIBUTE);

        if (isLinkForExternalWebsite(linkUrl, baseUrl))
            return linkUrl;
        return "";
    }

    private boolean isLinkForExternalWebsite(String linkUrl, String baseUrl) {
        return (linkUrl.startsWith(EXTERNAL_WEBSITE_INDICATOR) && !linkUrl.contains(baseUrl));
    }
}
