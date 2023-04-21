package org.example;

import java.net.URI;
import java.net.http.HttpRequest;

public class HttpRequestCreator {

    private static HttpRequestCreator instance;
    private final String URI_TRANSLATE = "https://google-translator9.p.rapidapi.com/v2";
    private final String URI_DETECT = "https://google-translator9.p.rapidapi.com/v2/detect";
    private final String HEADER_CONTENT_NAME = "content-type";
    private final String HEADER_CONTENT_VALUE = "application/json";
    private final String HEADER_APIKEY_NAME = "X-RapidAPI-Key";
    private final String HEADER_APIKEY_VALUE = "1f5de7c1b6mshc346a13cd58af05p1a5df0jsne027c8bfa2cc";
    private final String HEADER_APIHOST_NAME = "X-RapidAPI-Host";
    private final String HEADER_APIHOST_VALUE = "google-translator9.p.rapidapi.com";
    private final String SEND_DATA = "POST";

    private enum HttpRequestType {
        DETECTLANGUAGE,
        TRANSLATE
    }


    public static HttpRequestCreator gethttpRequestCreator() {
        if (instance == null) {
            instance = new HttpRequestCreator();
        }

        return instance;
    }


    public HttpRequest buildDetectLanguageHttpRequest(String headerSample) {
        HttpRequest.Builder requestBuild = buildHttpRequest(HttpRequestType.DETECTLANGUAGE);

        requestBuild.method(SEND_DATA, HttpRequest.BodyPublishers.ofString("{\r\"q\": \""
                + headerSample + "\"\r}"));

        HttpRequest request = requestBuild.build();

        return request;
    }

    public HttpRequest buildTranslateLanguageHttpRequest(String Headers, String sourceLanguage, String targetlanguage) {
        HttpRequest.Builder requestBuild = buildHttpRequest(HttpRequestType.TRANSLATE);
        requestBuild.method(SEND_DATA, HttpRequest.BodyPublishers.ofString("{\r\"q\": \""
                + Headers + "\",\r\"source\": \""
                + sourceLanguage + "\",\r\"target\": \""
                + targetlanguage + "\",\r\"format\": \"text\"\r}"));

        HttpRequest request = requestBuild.build();

        return request;

    }

    private HttpRequest.Builder buildHttpRequest(HttpRequestType requestType) {
        HttpRequest.Builder buildRequest = HttpRequest.newBuilder();
        if (requestType == HttpRequestType.DETECTLANGUAGE) {
            buildRequest.uri(URI.create(URI_DETECT))
                    .header(HEADER_CONTENT_NAME, HEADER_CONTENT_VALUE);
        } else {
            buildRequest.uri(URI.create(URI_TRANSLATE))
                    .header(HEADER_CONTENT_NAME, HEADER_CONTENT_VALUE);
        }
        buildRequest.header(HEADER_APIKEY_NAME, HEADER_APIKEY_VALUE)
                .header(HEADER_APIHOST_NAME, HEADER_APIHOST_VALUE);
        return buildRequest;
    }
}
