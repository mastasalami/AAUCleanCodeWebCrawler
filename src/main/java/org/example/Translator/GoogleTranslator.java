package org.example.Translator;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class GoogleTranslator implements Translator {
    private String sourceLanguage;
    private String targetLanguage;
    private String toTranslate;
    private final HttpRequestCreator httpRequestCreator;
    private final LanguageTransformer languageTransformer;
    private final HttpParser httpParser;

    public GoogleTranslator() {
        httpRequestCreator = HttpRequestCreator.getHttpRequestCreator();
        languageTransformer = LanguageTransformer.getLanguageTransformer();
        httpParser = HttpParser.getHttpRequestParser();
    }

    @Override
    public String translate(String targetLanguage, String toTranslate) throws TranslationFailedException {
        setUpTranslation(targetLanguage, toTranslate);

        if (sourceLanguage.equals(targetLanguage)) return targetLanguage;

        String translatedText = doTranslation();
        return translatedText;
    }

    @Override
    public String translateMany(String targetLanguage, List<String> toTranslate) throws TranslationFailedException{
        List<String> formattedToTranslate = httpRequestCreator.formatForHttpRequest(toTranslate);
        StringBuilder translatedText = new StringBuilder();

        for (String translate : formattedToTranslate) {
            String translated = translate(targetLanguage, translate);
            translatedText.append(translated);
        }
        return translatedText.toString();
    }

    private void setUpTranslation(String targetLanguage, String toTranslate) throws TranslationFailedException {
        setTargetLanguage(targetLanguage);
        String detectedLanguage = detectSourceLanguage(toTranslate);
        setSourceLanguage(detectedLanguage);
        setToTranslate(toTranslate);
    }

    private void setTargetLanguage(String targetLanguage) {
        String languageCode = languageTransformer.getLanguageCode(targetLanguage);
        this.targetLanguage = languageCode;
    }

    private void setSourceLanguage(String sourceLanguage) {
        this.sourceLanguage = sourceLanguage;
    }

    private void setToTranslate(String toTranslate) {
        this.toTranslate = toTranslate;
    }

    private String detectSourceLanguage(String toTranslate) throws TranslationFailedException {
        DOMHttpRequest detectRequest = httpRequestCreator.buildDetectLanguageHttpRequest(toTranslate);
        DOMHttpResponse detectResponse = sendHttpRequest(detectRequest);
        String detectedLanguage = httpParser.parseDetectResponse(detectResponse);
        return detectedLanguage;
    }

    private String doTranslation() throws TranslationFailedException {
        DOMHttpRequest translateRequest = httpRequestCreator.buildTranslateLanguageHttpRequest(toTranslate, this.sourceLanguage, this.targetLanguage);
        DOMHttpResponse translateResponse = sendHttpRequest(translateRequest);
        String translatedText = httpParser.parseTranslateResponse(translateResponse);
        return translatedText;
    }


    private DOMHttpResponse sendHttpRequest(DOMHttpRequest request) throws TranslationFailedException {
        DOMHttpResponse response = null;
        try {
            response = request.send();
            return response;
        } catch (IOException | InterruptedException e) {
            throw new TranslationFailedException("An error occured while trying to send a Httprequest to the translation API");
        }
    }
}