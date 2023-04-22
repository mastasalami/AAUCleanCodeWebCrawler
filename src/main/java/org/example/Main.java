package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

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

            String summary = crawler.getSummary();
            System.out.println("Summary:" + summary);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}