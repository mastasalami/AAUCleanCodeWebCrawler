package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Translator {
    private final static String JSONOBJECT_DETECTED_KEY = "language";
    private final static String JSONOBJECT_TRANSLATED_KEY = "translatedText";
    private String sourceLanguage;
    private String targetLanguage;
    private final HttpRequestCreator httpRequestCreator;
    private final LanguageTransformer languageTransformer;

    public Translator() {
        httpRequestCreator = HttpRequestCreator.getHttpRequestCreator();
        languageTransformer = LanguageTransformer.getLanguageTransformer();
    }
    public String translate(String targetLanguage, String toTranslate) throws IOException, InterruptedException {
        setTargetLanguage(targetLanguage);
        String detectedLanguage = detectSourceLanguage(toTranslate);
        setSourceLanguage(detectedLanguage);
        String translatedText = doTranslation(toTranslate);
        return translatedText;
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
        String detectedLanguage = parseHttpResponse(detectResponse,JSONOBJECT_DETECTED_KEY);
        return detectedLanguage;
    }

    private String doTranslation(String toTranslate) throws IOException, InterruptedException {
        HttpRequest translateRequest = httpRequestCreator.buildTranslateLanguageHttpRequest(toTranslate,this.sourceLanguage,this.targetLanguage);
        HttpResponse<String> translateResponse = sendHttpRequest(translateRequest);
        String translatedText = parseHttpResponse(translateResponse,JSONOBJECT_TRANSLATED_KEY);
        return translatedText;
    }


    private HttpResponse<String> sendHttpRequest(HttpRequest request) throws IOException, InterruptedException {
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    private String parseHttpResponse(HttpResponse<String> response, String jsonObjectKey){
        JSONArray responseJson = extractJsonArrayFromHttpResponse(response);
        StringBuilder parsedResponse = new StringBuilder();

        for (int i = 0; i < responseJson.length(); i++) {
            JSONObject responseObject = responseJson.getJSONObject(i);
            String parsed = responseObject.getString(jsonObjectKey);
            parsedResponse.append(parsed);
        }
        String parsedString = parsedResponse.toString();
        return parsedString;
    }

    private JSONArray extractJsonArrayFromHttpResponse(HttpResponse<String> response){
        String responseBody = response.body();
        int jsonArrayStartIndex = responseBody.lastIndexOf('[');
        String extracted = responseBody.substring(jsonArrayStartIndex);
        return new JSONArray(extracted);
    }


}