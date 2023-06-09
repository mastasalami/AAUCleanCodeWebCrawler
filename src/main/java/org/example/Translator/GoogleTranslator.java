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
        httpRequestCreator = new HttpRequestCreator();
        languageTransformer = LanguageTransformer.getLanguageTransformer();
        httpResponseHandler = HttpResponseHandler.getHttpRequestParser();
    }
    @Override
    public String translate(String targetLanguage, List<String> toTranslate) throws TranslationFailedException{
        setUpTranslation(targetLanguage, toTranslate);

        if(isSourceLanguageEqualToTargetLanguage()) return getTextFromFailedTranslation();

        return doManyTranslation();
    }

    private boolean isSourceLanguageEqualToTargetLanguage(){
        return sourceLanguage.equals(targetLanguage);
    }

    private String doManyTranslation() throws TranslationFailedException {
        StringBuilder translatedText = new StringBuilder();
        int index = 0;

        for (String translate : toTranslate) {
            String translated =  doTranslation(translate);
            translatedText.append(translated);
            toTranslate.set(index,translated);
            index++;
        }

        return translatedText.toString();
    }

    private void setUpTranslation(String targetLanguage, List<String> toTranslate) throws TranslationFailedException {
        setUpToTranslate(toTranslate);
        setSourceLanguageToDetectedLanguage(getTextSample(toTranslate));
        setTargetLanguage(targetLanguage);
    }

    private String getTextSample(List<String> toTranslate) throws TranslationFailedException{
            for (String elementFromToTranslate : toTranslate) {
                if (!elementFromToTranslate.isEmpty()) return elementFromToTranslate;
            }

            throw new TranslationFailedException("Text that should be translated cannot be empty!");
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

    private String detectSourceLanguage(String textToDetectLanguageFrom) throws TranslationFailedException {
        DOMHttpRequest detectRequest = httpRequestCreator.buildDetectLanguageHttpRequest(textToDetectLanguageFrom);
        String detectedLanguage = httpResponseHandler.getDetectResponse(detectRequest);
        return detectedLanguage;
    }

    private void setSourceLanguageToDetectedLanguage(String textToDetectLanguageFrom) throws TranslationFailedException {
        String detectedLanguage = detectSourceLanguage(textToDetectLanguageFrom);
        setSourceLanguage(detectedLanguage);
    }

    private String doTranslation(String toTranslate) throws TranslationFailedException {
        try {
            DOMHttpRequest translateRequest = httpRequestCreator.buildTranslateLanguageHttpRequest(toTranslate, this.sourceLanguage, this.targetLanguage);
            String translatedText = httpResponseHandler.getTranslateResponse(translateRequest);
            return translatedText;
        } catch (TranslationFailedException e){
            if(checkIfHeadingHaveDifferentLanguage(toTranslate))
                throw new TranslationFailedException("Translation failed probably due to the fact that the headers" +
                        "of the crawled website are in multiple languages (the API cannot handle this)");
            else throw new TranslationFailedException(e.getMessage());
        }
    }

    private boolean checkIfHeadingHaveDifferentLanguage(String toTranslate) throws TranslationFailedException {
        String detectedLanguage = detectSourceLanguage(toTranslate);

        return !detectedLanguage.equals(sourceLanguage);
    }

    public String getTextFromFailedTranslation(){
        StringBuilder textFromToTranslate = new StringBuilder();

        for (String toTranslateElement: toTranslate) {
            textFromToTranslate.append(toTranslateElement);
        }

        return textFromToTranslate.toString();
    }

}