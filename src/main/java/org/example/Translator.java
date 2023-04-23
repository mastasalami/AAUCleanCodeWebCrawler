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

    public String getSourceLanguage() {
        return sourceLanguage;
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }

    public void setSourceLanguage(String sourceLanguage) {
        this.sourceLanguage = sourceLanguage;
    }

    public void setTargetLanguage(String targetLanguage) {
        this.targetLanguage = targetLanguage;
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
        int jsonArrayStartIndex = responseBody.indexOf('[');
        String extracted = responseBody.substring(jsonArrayStartIndex);
        return new JSONArray(extracted);
    }


}