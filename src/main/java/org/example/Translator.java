package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Translator {
    private final String URI = "https://google-translator9.p.rapidapi.com/v2";
    private final String URI_DETECT = "/detect";
    private final String URI_LANGUAGES = "/languages";
    private final String HEADER_CONTENT_NAME = "content-type";
    private final String HEADER_CONTENT_VALUE = "application/Json";
    private final String HEADER_APIKEY_NAME = "X-RapidAPI-Key";
    private final String HEADER_APIKEY_VALUE = "1f5de7c1b6mshc346a13cd58af05p1a5df0jsne027c8bfa2cc";
    private final String HEADER_APIHOST_NAME = "X-RapidAPI-Host";
    private final String HEADER_APIHOST_VALUE = "google-translator9.p.rapidapi.com";


}
