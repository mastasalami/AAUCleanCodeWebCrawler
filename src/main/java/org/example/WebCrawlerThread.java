package org.example;

import org.example.Translator.GoogleTranslator;
import org.example.Translator.TranslationFailedException;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

public class WebCrawlerThread implements Callable<String> {
    private WebCrawler crawler;
    private int depth;
    private String url;
    private String summary;
    private String language;
    private Logger logger = Logger.getInstance();

    public WebCrawlerThread(String url, int depth, String language) {
        this.depth = depth;
        this.url = url;
        this.language = language;
    }

    @Override
    public String call() {
        crawler = new WebCrawler(depth);
        crawler.crawl(url);

        List<String> headings = crawler.getHeadingsAsList();
        String headingsTranslated = translateHeadings(headings);

        String linkTexts = crawler.getLinksText();
        summary = headingsTranslated + linkTexts;

        return summary;
    }

    //Todo: Improve Error handling from translation (maybe different messages per exception type)
    private String translateHeadings(List<String> headings) {
        String headingsTranslated;
        try {
            GoogleTranslator translator = new GoogleTranslator();
            headingsTranslated = translator.translateMany(language, headings);
        } catch (TranslationFailedException e) {
            logger.log("Translation failed:" + e);
            headingsTranslated = crawler.getHeadingsText();
        }
        return headingsTranslated;
    }
}
