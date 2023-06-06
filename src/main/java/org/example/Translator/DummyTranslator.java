package org.example.Translator;

import java.util.List;

//Dummy class that is used to kind of test the methods of GoogleTranslator Class without having to use the API or other Classes
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

        if (sourceLanguage.equals(targetLanguage)) return getTextFromFailedTranslation();

        return doManyTranslation();
    }

    private String doManyTranslation() {
        StringBuilder translatedText = new StringBuilder();

        for (String translate : toTranslate) {
            String translated = doTranslation(translate);
            translatedText.append(translated);
        }

        return translatedText.toString();
    }

    public void setTargetLanguage(String targetLanguage){
        this.targetLanguage = targetLanguage;
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

    public String getTextFromFailedTranslation(){
        StringBuilder textFromToTranslate = new StringBuilder();

        for (String toTranslateElement: toTranslate) {
            textFromToTranslate.append(toTranslateElement);
        }

        return textFromToTranslate.toString();
    }
}
