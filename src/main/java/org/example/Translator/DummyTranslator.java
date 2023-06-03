package org.example.Translator;

import org.example.Translator;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

//Only used for testing
public class DummyTranslator implements Translator {
    private String sourceLanguage;
    private String targetLanguage;
    private String toTranslate;
    @Override
    public String translate(String targetLanguage, String toTranslate) throws IOException, InterruptedException {
        setUpTranslation(targetLanguage, toTranslate);
        String translatedText = doTranslation();
        return translatedText;
    }
    @Override
    public String translateMany(String targetLanguage, List<String> toTranslate) throws IOException, InterruptedException {
        StringBuilder translatedText = new StringBuilder();

        for (String translate: toTranslate) {
            String translated = translate(targetLanguage, translate);
            translatedText.append(translated);
        }
        return translatedText.toString();
    }

    private void setUpTranslation(String targetLanguage, String toTranslate) throws IOException, InterruptedException {
        setTargetLanguage(targetLanguage);
        String detectedLanguage = detectSourceLanguage(toTranslate);
        setSourceLanguage(detectedLanguage);
        setToTranslate(toTranslate);
    }

    private void setTargetLanguage(String targetLanguage){
        this.targetLanguage = "de";
    }
    private void setSourceLanguage(String sourceLanguage){
        this.sourceLanguage = sourceLanguage;
    }

    private void setToTranslate(String toTranslate){
        this.toTranslate = toTranslate;
    }


    private String detectSourceLanguage(String toTranslate) throws IOException, InterruptedException {
        String detectedLanguage = "en";
        return detectedLanguage;
    }

    private String doTranslation() throws IOException, InterruptedException {
        String translatedText = "Guten Tag!";
        return translatedText;
    }
}
