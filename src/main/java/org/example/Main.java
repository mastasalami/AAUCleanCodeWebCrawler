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
        System.out.println("Hello world!");
        System.out.println("Enter the URL you would like to analyse:");

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        try {
            String url = reader.readLine();

            System.out.println("Enter the search depth:");
            String depth = reader.readLine();

            System.out.println("Enter the language:");
            String languageCode = reader.readLine();

            Connection connection = Jsoup.connect(url);
            Document document = connection.execute().parse();

            List<Element> links = document.getElementsByTag("a");

            for (Element link : links) {
                String linkTo = link.attr("href");
                String linkText = link.text();

                System.out.println("linkText:" + linkText);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}