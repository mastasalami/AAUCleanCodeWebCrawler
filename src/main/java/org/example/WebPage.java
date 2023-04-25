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
    private static final String LINE_BREAK_SYMBOL                   = "\n";
    private static final String LINK_ATTRIBUTE                      = "href";
    private static final String EXTERNAL_WEBSITE_INDICATOR          = "http";

    private static final String WORKING_LINK_TEXT                   = "link to";
    private static final String BROKEN_LINK_TEXT                    = "broken link";
    private static final String OPEN_LINK_TAG                       = "<a>";
    private static final String CLOSE_LINK_TAG                      = "</a>";

    private final Document document;
    private List<Element> links     = new ArrayList<>();
    private final List<Element> headings  = new ArrayList<>();
    private final String url;
    private final int depth;

    public WebPage(Document document, String url, int depth) throws Exception {
        this.document = document;
        this.url = url;
        this.depth = depth;

        if (this.document != null)
            getElementsFromDocument();
    }

    public String getUrl() {
        return url;
    }

    private void getElementsFromDocument() throws Exception {
        if (this.document != null) {
            links = getElements(LINK_HTML_ELEMENT_NAME);
            getHeadings();
        }
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
        List<String> linkUrls = new ArrayList<>();
        for (Element link : links) {
            String linkUrl = getExternalLinkUrlFromElement(link);
            linkUrls.add(linkUrl);
        }

        return linkUrls;
    }

    private String getExternalLinkUrlFromElement(Element element) {
        String linkUrl = element.attr(LINK_ATTRIBUTE);

        if (isLinkForExternalWebsite(linkUrl))
            return linkUrl;
        return "";
    }

    private boolean isLinkForExternalWebsite(String linkUrl) {
        return (linkUrl.startsWith(EXTERNAL_WEBSITE_INDICATOR) && !linkUrl.contains(this.url));
    }

    public String getHeadingsToString() {
        StringBuilder headingsText = new StringBuilder();

        for (Element heading : headings) {
            headingsText.append(getElementText(heading));
        }

        if (!headingsText.isEmpty())
            headingsText.append(LINE_BREAK_SYMBOL + LINE_BREAK_SYMBOL);

        return headingsText.toString();
    }

    public String getLinkText() {
        if (isLinkBroken())                                                                                             //the text should be corrected for broken links
            return getLinkText(true);
        else
            return getLinkText(false);
    }

    private boolean isLinkBroken() {
        return document == null;
    }
    private String getLinkText(boolean isBrokenLink) {
        String linkText = getIndentationForDepth();
        if (isBrokenLink)
            linkText += BROKEN_LINK_TEXT;
        else
            linkText += WORKING_LINK_TEXT;

        linkText += " " + OPEN_LINK_TAG;
        linkText += this.url;
        linkText += CLOSE_LINK_TAG;
        linkText += LINE_BREAK_SYMBOL;

        return linkText;
    }

    public String getElementText(Element el) {
        String elementText = "";

        if (el.nodeName().contains(HEADING_HTML_ELEMENT_NAME))
            elementText += getIndentationForHeadingLevel(el);

        elementText += getIndentationForDepth();

        if (el.nodeName().contains(HEADING_HTML_ELEMENT_NAME))
            elementText += el.text();
        else
            elementText += el.attr(LINK_ATTRIBUTE);

        elementText += LINE_BREAK_SYMBOL;

        return elementText;
    }

    private String getIndentationForDepth() {
        StringBuilder indentationString = new StringBuilder();

        indentationString.append("--".repeat(Math.max(0, depth)));

        if (indentationString.length() > 0)
            indentationString.append("> ");

        return indentationString.toString();
    }

    private String getIndentationForHeadingLevel(Element el) {
        StringBuilder indentationString = new StringBuilder();

        indentationString.append("#".repeat(Math.max(0, getHeadingLevel(el) + 1)));

        if (indentationString.length() > 0)
            indentationString.append(" ");

        return indentationString.toString();
    }

    private int getHeadingLevel(Element headingElement) {
        String elementName = headingElement.nodeName();
        int headingLevel = 0;
        if (elementName.contains(HEADING_HTML_ELEMENT_NAME)) {
            String elementLevelText = elementName.replace(HEADING_HTML_ELEMENT_NAME, "");
            headingLevel = Integer.parseInt(elementLevelText);
        }
        return headingLevel;
    }
}
