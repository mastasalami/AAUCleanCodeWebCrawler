package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleReader {
    BufferedReader reader = new BufferedReader(
            new InputStreamReader(System.in));

    public void writeInitialMessages() {
        System.out.println("WebCrawler starting!");
        System.out.println("You will now be asked to enter the parameter for this run.");
    }

    public String readUrlFromConsole() throws IOException {
        System.out.println("Enter the urls (different urls should be seperated by ',' symbols:");

        return reader.readLine();
    }

    public int readSearchDepthsFromConsole() throws IOException {
        System.out.println("Enter the search depths:");
        String searchDepths = reader.readLine();

        return Integer.parseInt(searchDepths);
    }

    public String readTargetLanguageFromConsole() throws IOException {
        System.out.println("Enter the target language. The target language can be one of the google languages (code or long text)" +
                "e.g. hungarian, it, tigrinya, english: ");

        return reader.readLine();
    }
}
