package org.example.Translator;

import java.util.List;

//Only used for testing
public class DummyTranslator implements Translator {
    private String sourceLanguage;
    private String targetLanguage;
    private List<String> toTranslate;

    private void setUpTranslation(String targetLanguage, List<String> toTranslate) {
        setTargetLanguage(targetLanguage);
        String detectedLanguage = detectSourceLanguage();
        setSourceLanguage(detectedLanguage);
        setToTranslate(toTranslate);
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

    private void setTargetLanguage(String targetLanguage){
        this.targetLanguage = "de";
    }
    private void setSourceLanguage(String sourceLanguage){
        this.sourceLanguage = sourceLanguage;
    }

    private void setToTranslate(List<String> toTranslate){
        this.toTranslate = toTranslate;
    }


    private String detectSourceLanguage() {
        String detectedLanguage = "en";
        return detectedLanguage;
    }

    private String doTranslation(String toTranslate)  {
        String translatedText = "Guten Tag!";
        return translatedText;
    }
}
