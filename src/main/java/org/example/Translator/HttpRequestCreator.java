package org.example.Translator;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;

public class HttpRequestCreator {
    private final String URI_TRANSLATE = "https://google-translator9.p.rapidapi.com/v2";
    private final String URI_DETECT = "https://google-translator9.p.rapidapi.com/v2/detect";
    private final String HEADER_CONTENT_NAME = "content-type";
    private final String HEADER_CONTENT_VALUE = "application/json";
    private final String HEADER_APIKEY_NAME = "X-RapidAPI-Key";
    private final String HEADER_APIKEY_VALUE = "aadb5d6ff7msh13b2cb8721a86c9p18710ajsn2027bc2ca5c9";
    private final String HEADER_APIHOST_NAME = "X-RapidAPI-Host";
    private final String HEADER_APIHOST_VALUE = "google-translator9.p.rapidapi.com";
    private final String SEND_DATA = "POST";
    private final int MIN_CHARACTER_LIMIT = 3000;

    private DOMHttpRequestBuilder currentRequestToBuild;

    private List<String> currentToFormat;

    private List<String> formatted;

    private enum HttpRequestType {
        DETECTLANGUAGE,
        TRANSLATE
    }
    public DOMHttpRequest buildDetectLanguageHttpRequest(String textExampleOfLanguageToDetect) {
        buildHttpRequest(HttpRequestType.DETECTLANGUAGE);
        setMethodForDetectRequest(textExampleOfLanguageToDetect);

        DOMHttpRequest detectRequest = currentRequestToBuild.build();

        return detectRequest;
    }

    public DOMHttpRequest buildTranslateLanguageHttpRequest(String toTranslate, String sourceLanguage, String targetlanguage) {
        buildHttpRequest(HttpRequestType.TRANSLATE);
        
        setMethodForTranslateRequest(toTranslate, sourceLanguage, targetlanguage);

        DOMHttpRequest translateRequest = currentRequestToBuild.build();

        return translateRequest;

    }
    
    private void setMethodForDetectRequest(String textExampleOfLanguageToDetect){
        currentRequestToBuild.setMethod(SEND_DATA,"{\r\"q\": \""
                + textExampleOfLanguageToDetect + "\"\r}");
    }
    
    private void setMethodForTranslateRequest(String toTranslate, String sourceLanguage, String targetlanguage){
        currentRequestToBuild.setMethod(SEND_DATA,"{\r\"q\": \""
                + toTranslate + "\",\r\"source\": \""
                + sourceLanguage + "\",\r\"target\": \""
                + targetlanguage + "\",\r\"format\": \"text\"\r}");
    }

    private void buildHttpRequest(HttpRequestType requestType) {
        initCurrentRequestToBuild();
        setDetectOrTranslateRequestURI(requestType);
        addHeaders();
    }

    private void initCurrentRequestToBuild(){
        currentRequestToBuild = new DOMHttpRequestBuilder();
    }

    private void setDetectOrTranslateRequestURI(HttpRequestType requestType){
        if (isDetectLanguageRequest(requestType)) {
            currentRequestToBuild.setUri(URI_DETECT);
        } else {
            currentRequestToBuild.setUri(URI_TRANSLATE);
        }
    }

    private boolean isDetectLanguageRequest(HttpRequestType requestType){
        return HttpRequestType.DETECTLANGUAGE == requestType;
    }

    private void addHeaders(){
        currentRequestToBuild.addHeader(HEADER_CONTENT_NAME, HEADER_CONTENT_VALUE);
        currentRequestToBuild.addHeader(HEADER_APIKEY_NAME, HEADER_APIKEY_VALUE);
        currentRequestToBuild.addHeader(HEADER_APIHOST_NAME, HEADER_APIHOST_VALUE);
    }

    public List<String> formatForHttpRequest(List<String> toFormat){
        setUpFormat(toFormat);

        format();

        return formatted;
    }

    private void setUpFormat(List<String> toFormat){
        currentToFormat = toFormat;
        formatted = new ArrayList<>();
    }

    private void format(){
        StringBuilder putTogether = new StringBuilder();

        for (String elementFromList : currentToFormat){
            putTogether.append(elementFromList);
            if(hasMinCharacterLimitBeenSurpassed(putTogether.length())){
                formatted.add(putTogether.toString());
                putTogether.setLength(0);
            }
        }

       if(!putTogether.isEmpty()) formatted.add(putTogether.toString());
    }

    private boolean hasMinCharacterLimitBeenSurpassed(int length){
        return length > MIN_CHARACTER_LIMIT;
    }




}
