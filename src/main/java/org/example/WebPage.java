package org.example;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

public class WebPage {
    private static final int    HEADING_MIN_LEVEL                   = 1;
    private static final int    HEADING_MAX_LEVEL                   = 6;
    private static final String LINK_HTML_ELEMENT_NAME              = "a";
    private static final String HEADING_HTML_ELEMENT_NAME           = "h";
    private static final String LINE_BREAK_SYMBOL                   = "\n";
    private static final String LINK_ATTRIBUTE                      = "href";

    private static final String WORKING_LINK_TEXT                   = "link to";
    private static final String BROKEN_LINK_TEXT                    = "broken link";
    private static final String OPEN_LINK_TAG                       = "<a>";
    private static final String CLOSE_LINK_TAG                      = "</a>";

    private DOMDocument document;
    private List<DOMElement> links                                  = new ArrayList<>();
    private final List<DOMElement> headings                         = new ArrayList<>();
    private final String url;
    private final int depth;

    public WebPage(DOMDocument document, String url, int depth) {
        if (document != null)
            this.document = document;
        this.url = url;
        this.depth = depth;
    }

    public void loadElementsFromDocument() {
        if (document != null)
            getElementsFromDocument();
    }

    public String getUrl() {
        return url;
    }

    private void getElementsFromDocument() {
        if (document != null) {
            links = document.loadElementsByTagName(LINK_HTML_ELEMENT_NAME);
            loadHeadings();
        }
    }

    private void loadHeadings() {
        for (int level = HEADING_MIN_LEVEL; level < HEADING_MAX_LEVEL; level++) {
            loadHeadingsByLevel(level);
        }
    }

    private void loadHeadingsByLevel(int level) {
        if (level < HEADING_MIN_LEVEL || level > HEADING_MAX_LEVEL)
            throw new IllegalArgumentException("No HTML element with the tag " + HEADING_HTML_ELEMENT_NAME + level + " exists!");

        String headingSelector = HEADING_HTML_ELEMENT_NAME + level;
        List<DOMElement> headingsOfLevel = document.loadElementsByTagName(headingSelector);
        headings.addAll(headingsOfLevel);
    }

    public List<String> getLinkUrls() {
        List<String> linkUrls = new ArrayList<>();
        for (DOMElement link : links) {
            String linkUrl = link.getExternalLinkUrl(this.url);
            linkUrls.add(linkUrl);
        }

        return linkUrls;
    }

    public String getHeadingsToString() {
        StringBuilder headingsText = new StringBuilder();
        for (DOMElement heading : headings) {
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

    public String getElementText(DOMElement el) {
        String elementText = "";

        if (el.getName().contains(HEADING_HTML_ELEMENT_NAME))
            elementText += getIndentationForHeadingLevel(el);

        elementText += getIndentationForDepth();

        if (el.getName().contains(HEADING_HTML_ELEMENT_NAME))
            elementText += el.getText();
        else
            elementText += el.getAttribute(LINK_ATTRIBUTE);

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

    private String getIndentationForHeadingLevel(DOMElement el) {
        StringBuilder indentationString = new StringBuilder();

        indentationString.append("#".repeat(Math.max(0, getHeadingLevel(el) + 1)));

        if (indentationString.length() > 0)
            indentationString.append(" ");

        return indentationString.toString();
    }

    private int getHeadingLevel(DOMElement headingElement) {
        String elementName = headingElement.getName();
        int headingLevel = 0;
        if (elementName.contains(HEADING_HTML_ELEMENT_NAME)) {
            String elementLevelText = elementName.replace(HEADING_HTML_ELEMENT_NAME, "");
            headingLevel = Integer.parseInt(elementLevelText);
        }
        return headingLevel;
    }
}
