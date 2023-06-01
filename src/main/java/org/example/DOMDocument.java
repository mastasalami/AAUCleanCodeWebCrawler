package org.example;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class DOMDocument {
    private Document document;

    public DOMDocument(Document document) {
        this.document = document;
    }
    public List<DOMElement> loadElementsByTagName(String tagName) {
        List<Element> jsoupElements = document.getElementsByTag(tagName);
        List<DOMElement> domElements = new ArrayList<>();

        for (Element jsoupElement : jsoupElements) {
            DOMElement element = new DOMElement(jsoupElement);
            domElements.add(element);
        }

        return domElements;
    }
}
