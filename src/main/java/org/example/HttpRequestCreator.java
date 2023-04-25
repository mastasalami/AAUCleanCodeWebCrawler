package org.example;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;

public class HttpRequestCreator {

    private static HttpRequestCreator requestCreator;
    private final String URI_TRANSLATE = "https://google-translator9.p.rapidapi.com/v2";
    private final String URI_DETECT = "https://google-translator9.p.rapidapi.com/v2/detect";
    private final String HEADER_CONTENT_NAME = "content-type";
    private final String HEADER_CONTENT_VALUE = "application/json";
    private final String HEADER_APIKEY_NAME = "X-RapidAPI-Key";
    private final String HEADER_APIKEY_VALUE = "1f5de7c1b6mshc346a13cd58af05p1a5df0jsne027c8bfa2cc";
    private final String HEADER_APIHOST_NAME = "X-RapidAPI-Host";
    private final String HEADER_APIHOST_VALUE = "google-translator9.p.rapidapi.com";
    private final String SEND_DATA = "POST";
    private final int CHARACTER_LIMIT = 3000;

    private enum HttpRequestType {
        DETECTLANGUAGE,
        TRANSLATE
    }


    public static HttpRequestCreator getHttpRequestCreator() {
        if (requestCreator == null) {
            requestCreator = new HttpRequestCreator();
        }

        return requestCreator;
    }


    public HttpRequest buildDetectLanguageHttpRequest(String textExampleOfLanguageToDetect) {
        HttpRequest.Builder requestBuild = buildHttpRequest(HttpRequestType.DETECTLANGUAGE);

        requestBuild.method(SEND_DATA, HttpRequest.BodyPublishers.ofString("{\r\"q\": \""
                + textExampleOfLanguageToDetect + "\"\r}"));

        HttpRequest detectRequest = requestBuild.build();

        return detectRequest;
    }

    public HttpRequest buildTranslateLanguageHttpRequest(String toTranslate, String sourceLanguage, String targetlanguage) {
        HttpRequest.Builder requestBuild = buildHttpRequest(HttpRequestType.TRANSLATE);
        requestBuild.method(SEND_DATA, HttpRequest.BodyPublishers.ofString("{\r\"q\": \""
                + toTranslate + "\",\r\"source\": \""
                + sourceLanguage + "\",\r\"target\": \""
                + targetlanguage + "\",\r\"format\": \"text\"\r}"));

        HttpRequest translateRequest = requestBuild.build();

        return translateRequest;

    }
    //This Method is for potential future use
    public List<HttpRequest> buildManyTranslateLanguageHttpRequest(List<String> toTranslate, String sourceLanguage, String targetlanguage){
        List<String> formattedToTranslate = formatForHttpRequest(toTranslate);
        List<HttpRequest> translateRequests = new ArrayList<>();

        for (String translate: formattedToTranslate) {
            HttpRequest translateRequest = buildTranslateLanguageHttpRequest(translate,sourceLanguage,targetlanguage);
            translateRequests.add(translateRequest);
        }
        return translateRequests;
    }
    private HttpRequest.Builder buildHttpRequest(HttpRequestType requestType) {
        HttpRequest.Builder requestBuild = HttpRequest.newBuilder();
        if (isDetectLanguageRequest(requestType)) {
            requestBuild.uri(URI.create(URI_DETECT));
        } else {
            requestBuild.uri(URI.create(URI_TRANSLATE));
        }
        requestBuild.header(HEADER_CONTENT_NAME, HEADER_CONTENT_VALUE)
                .header(HEADER_APIKEY_NAME, HEADER_APIKEY_VALUE)
                .header(HEADER_APIHOST_NAME, HEADER_APIHOST_VALUE);
        return requestBuild;
    }
    private boolean isDetectLanguageRequest(HttpRequestType requestType){
        return HttpRequestType.DETECTLANGUAGE == requestType;
    }
    public List<String> formatForHttpRequest(List<String> toFormat){
        List<String> formattedList = new ArrayList<>();
        StringBuilder putTogether = new StringBuilder();

        for (int i = 0; i < toFormat.size(); i++) {
            String element = toFormat.get(i);
            putTogether.append(element);
            if(putTogether.length() > CHARACTER_LIMIT || i == toFormat.size() -1){
                formattedList.add(putTogether.toString());
                putTogether = new StringBuilder();

            }
        }
        return formattedList;
    }
}
