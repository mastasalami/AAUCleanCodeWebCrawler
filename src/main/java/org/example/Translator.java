package org.example;

import org.json.JSONArray;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class Translator {
    private String sourceLanguage;
    private String targetLanguage;
    private final HttpRequestCreator httpRequestCreator;
    private final LanguageTransformer languageTransformer;
    private final HttpParser httpParser;

    public Translator() {
        httpRequestCreator = HttpRequestCreator.getHttpRequestCreator();
        languageTransformer = LanguageTransformer.getLanguageTransformer();
        httpParser = HttpParser.getHttpRequestParser();
    }
    public String translate(String targetLanguage, String toTranslate) throws IOException, InterruptedException {
        setTargetLanguage(targetLanguage);
        String detectedLanguage = detectSourceLanguage(toTranslate);
        setSourceLanguage(detectedLanguage);
        String translatedText = doTranslation(toTranslate);
        return translatedText;
    }

    public String translateList(String targetLanguage, List<String> toTranslate) throws IOException, InterruptedException {
        setTargetLanguage(targetLanguage);
        String detectedLanguage = detectSourceLanguage(toTranslate.get(0));
        setSourceLanguage(detectedLanguage);
        StringBuilder translatedText = new StringBuilder();

        for (String translate: toTranslate) {
            translatedText.append(doTranslation(translate));
        }

        return translatedText.toString();
    }

    private void setTargetLanguage(String targetLanguage){
        String languageCode = languageTransformer.getLanguageCode(targetLanguage);
        this.targetLanguage = languageCode;
    }
    private void setSourceLanguage(String sourceLanguage){
        this.sourceLanguage = sourceLanguage;
    }
    private String detectSourceLanguage(String toTranslate) throws IOException, InterruptedException {
        HttpRequest detectRequest = httpRequestCreator.buildDetectLanguageHttpRequest(toTranslate);
        HttpResponse<String> detectResponse = sendHttpRequest(detectRequest);
        String detectedLanguage = httpParser.parseHttpResponse(detectResponse, HttpParser.JSONOBJECT_DETECTED_KEY);
        return detectedLanguage;
    }

    private String doTranslation(String toTranslate) throws IOException, InterruptedException {
        HttpRequest translateRequest = httpRequestCreator.buildTranslateLanguageHttpRequest(toTranslate,this.sourceLanguage,this.targetLanguage);
        HttpResponse<String> translateResponse = sendHttpRequest(translateRequest);
        String translatedText = httpParser.parseHttpResponse(translateResponse, HttpParser.JSONOBJECT_TRANSLATED_KEY);
        return translatedText;
    }


    private HttpResponse<String> sendHttpRequest(HttpRequest request) throws IOException, InterruptedException {
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    private String parseHttpResponse(HttpResponse<String> response, String jsonObjectKey){
        //Maybe extract method
        return httpParser.parseHttpResponse(response, jsonObjectKey);
    }

    private JSONArray extractJsonArrayFromHttpResponse(HttpResponse<String> response){
        //TODO write comment
        return httpParser.extractJsonArrayFromHttpResponse(response);
    }


}