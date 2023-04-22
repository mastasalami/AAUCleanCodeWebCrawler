package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Translator {
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
}