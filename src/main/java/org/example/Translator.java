package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Translator {
    private final String URI_TRANSLATE = "https://google-translator9.p.rapidapi.com/v2";
    private final String URI_DETECT = "/detect";
    private final String URI_LANGUAGES = "/languages";
    private final String HEADER_CONTENT_NAME = "content-type";
    private final String HEADER_CONTENT_VALUE = "application/Json";
    private final String HEADER_APIKEY_NAME = "X-RapidAPI-Key";
    private final String HEADER_APIKEY_VALUE = "1f5de7c1b6mshc346a13cd58af05p1a5df0jsne027c8bfa2cc";
    private final String HEADER_APIHOST_NAME = "X-RapidAPI-Host";
    private final String HEADER_APIHOST_VALUE = "google-translator9.p.rapidapi.com";


    private HttpRequest.Builder buildHttpRequest(HttpRequestType requestType){
        HttpRequest.Builder buildRequest = HttpRequest.newBuilder();
        if(requestType == HttpRequestType.DETECTLANGUAGE) {
            buildRequest.uri(URI.create(URI_DETECT))
                    .header(HEADER_CONTENT_NAME, HEADER_CONTENT_VALUE);
        } else if (requestType == HttpRequestType.TRANSLATE) {
            buildRequest.uri(URI.create(URI_TRANSLATE))
                    .header(HEADER_CONTENT_NAME, HEADER_CONTENT_VALUE);
        } else{
            buildRequest.uri(URI.create(URI_LANGUAGES));
        }
                buildRequest.header(HEADER_APIKEY_NAME, HEADER_APIKEY_VALUE)
                .header(HEADER_APIHOST_NAME, HEADER_APIHOST_VALUE);
        return buildRequest;
    }

    private HttpRequest buildDetectLanguageHttpRequest(String headerSample){
        HttpRequest.Builder requestBuild = buildHttpRequest(HttpRequestType.DETECTLANGUAGE);

        requestBuild.method("POST", HttpRequest.BodyPublishers.ofString("{\r\"q\": \"" + headerSample + "\"\r}"));

        HttpRequest request = requestBuild.build();

        return request;
    }

    private HttpRequest buildTranslateLanguageHttpRequest(String Headers, String sourceLanguage, String targetlanguage){
        HttpRequest.Builder requestBuild = buildHttpRequest(HttpRequestType.TRANSLATE);
        requestBuild.method("POST", HttpRequest.BodyPublishers.ofString("{\r\"q\": \""
                + Headers + "\",\r\"source\": \""
                + sourceLanguage + "\",\r\"target\": \""
                + targetlanguage + "\",\r\"format\": \"text\"\r}"));

        HttpRequest request = requestBuild.build();

        return request;

    }







}

enum HttpRequestType {
    DETECTLANGUAGE,
    TRANSLATE,
    GETLANGUAGES;

}