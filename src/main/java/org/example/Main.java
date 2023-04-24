package org.example;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        try {
            //Aufgaben:
            //1. Output / Input für URL / depth / language
            //2. Suche mit jsoup (Rekursiv bis depth limit)
            //3. Ergebnisse in irgendeiner Form zwischen speichern
            //4. Ergebnisse übersetzen mit API
            //5. Übersetzung in .md Format speichern
            //6. Unit Tests schreiben
            //7. ???
            //8. Profit

            //Klassen:
            //Formatter
            //Suche
            //Übersetzung

            ConsoleReader console = new ConsoleReader();
            console.writeInitialMessages();

            String  url     = console.readUrlFromConsole();
            Integer depth   = console.readSearchDepthsFromConsole();
            String language = console.readTargetLanguageFromConsole();

            WebCrawler crawler = new WebCrawler(url, depth);
            crawler.crawl();

            System.out.println("Crawling done!");

            Translator translator = new Translator();

            List<String> headingsList = crawler.getHeadingsAsList();

            String headingsTranslated = translator.translateList(language,headingsList);
            String linkTexts = crawler.getLinksText();

            String summary = headingsTranslated + linkTexts;

            SummaryCreator summaryCreator = new SummaryCreator(summary);
            summaryCreator.writeSummaryToFile();
            System.out.println("Summary:" + summary);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}