package org.example.Translator;

import java.io.IOException;
import java.util.List;

public class GoogleTranslator implements Translator {
    private String sourceLanguage;
    private String targetLanguage;
    private List<String> toTranslate;
    private final HttpRequestCreator httpRequestCreator;
    private final LanguageTransformer languageTransformer;
    private final HttpResponseHandler httpResponseHandler;

    public GoogleTranslator() {
        httpRequestCreator = HttpRequestCreator.getHttpRequestCreator();
        languageTransformer = LanguageTransformer.getLanguageTransformer();
        httpResponseHandler = HttpResponseHandler.getHttpRequestParser();
    }
    @Override
    public String translate(String targetLanguage, List<String> toTranslate) throws TranslationFailedException{
        setUpTranslation(targetLanguage, toTranslate);

        if (sourceLanguage.equals(targetLanguage)) return toTranslate.toString();

        return doManyTranslation();
    }

    private String doManyTranslation() throws TranslationFailedException {
        StringBuilder translatedText = new StringBuilder();

        for (String translate : toTranslate) {
           String translated = doTranslation(translate);
            translatedText.append(translated);
        }

        return translatedText.toString();
    }

    private void setUpTranslation(String targetLanguage, List<String> toTranslate) throws TranslationFailedException {
        setUpToTranslate(toTranslate);
        setTargetLanguage(targetLanguage);
        String detectedLanguage = detectSourceLanguage();
        setSourceLanguage(detectedLanguage);
    }

    private void setTargetLanguage(String targetLanguage) {
        String languageCode = languageTransformer.getLanguageCode(targetLanguage);
        this.targetLanguage = languageCode;
    }

    private void setSourceLanguage(String sourceLanguage) {
        this.sourceLanguage = sourceLanguage;
    }

    private void setUpToTranslate(List<String> toTranslate) {
        this.toTranslate = httpRequestCreator.formatForHttpRequest(toTranslate);
    }

    private String detectSourceLanguage() throws TranslationFailedException {
        DOMHttpRequest detectRequest = httpRequestCreator.buildDetectLanguageHttpRequest(getTextSampleToDetectLanguage());
        String detectedLanguage = httpResponseHandler.getDetectResponse(detectRequest);
        return detectedLanguage;
    }

    private String doTranslation(String toTranslate) throws TranslationFailedException {
        DOMHttpRequest translateRequest = httpRequestCreator.buildTranslateLanguageHttpRequest(toTranslate, this.sourceLanguage, this.targetLanguage);
        String translatedText = httpResponseHandler.getTranslateResponse(translateRequest);
        return translatedText;
    }

    private String getTextSampleToDetectLanguage(){
        return toTranslate.get(0);
    }

}