package org.example;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Logger logger = Logger.getInstance();
        String summary;
        try {
            ConsoleReader console = new ConsoleReader();
            console.writeInitialMessages();

            String  url     = console.readUrlFromConsole();
            int depth   = console.readSearchDepthsFromConsole();
            String language = console.readTargetLanguageFromConsole();

            WebCrawler crawler = new WebCrawler(depth);
            crawler.crawl(url);

            System.out.println("Crawling done!");

            GoogleTranslator translator = new GoogleTranslator();
            List<String> headingsList = crawler.getHeadingsAsList();

            String headingsTranslated = translator.translateMany(language,headingsList);
            String linkTexts = crawler.getLinksText();
            summary = headingsTranslated + linkTexts;
            summary += logger.getLoggedMessageSummary();
            createSummary(summary);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void createSummary(String summary) {
        try {
            SummaryCreator summaryCreator = new SummaryCreator(summary);
            summaryCreator.writeSummaryToFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}