package org.example.Translator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class LanguageTransformer {
    private static LanguageTransformer languageTransformer;
    private static final List<String> googleLanguageCodes = Arrays.asList("af", "ak", "am", "ar", "as", "ay", "az", "be", "bg", "bho", "bm", "bn", "bs", "ca",
                "ceb", "ckb", "co", "cs", "cy", "da", "de", "doi", "dv", "ee", "el", "en", "eo", "es", "et", "eu", "fa",
                "fi", "fr", "fy", "ga", "gd", "gl", "gn", "gom", "gu", "ha", "haw", "he", "hi", "hmn", "hr", "ht", "hu", "hy",
                "id", "ig", "ilo", "is", "it", "ja", "jv", "ka", "kk", "km", "kn", "ko", "kri", "ku", "ky", "la", "lb", "lg", "ln",
                "lo", "lt", "lus", "lv", "mai", "mg", "mi", "mk", "ml", "mn", "mni-Mtei", "mr", "ms", "mt", "my", "ne", "nl", "no", "nso", "ny",
                "om", "or", "pa", "pl", "ps", "pt", "qu", "ro", "ru", "rw", "sa", "sd", "si", "sk", "sl", "sm", "sn", "so", "sq", "sr", "st", "su",
                "sv", "sw", "ta", "te", "tg", "th", "ti", "tk", "tl", "tr", "ts", "tt", "ug", "uk", "ur", "uz", "vi", "xh", "yi", "yo", "zh-CN",
                "zh-TW", "zu");
    private static final List<String> googleLanguages = Arrays.asList("afrikaans","akan","amharic","arabic","assamese","aymara","azerbaijani","belarusian",
            "bulgarian","bhojpuri","bambara","bengali","bosnian","catalan","cebuano","kurdish(sorani)","corsican", "czech", "welsh",
            "danish", "german", "dogri", "dhivehi", "ewe", "greek", "english", "esperanto", "spanish", "estonian", "basque", "persian",
            "finnish", "french", "frisian", "irish", "scots gaelic", "galician", "guarani", "konkani", "gujarati", "hausa", "hawaiian",
            "hebrew", "hindi", "hmong", "croation", "haitian creole", "hungarian", "armenian", "indonesian", "igbo", "ilocano",
            "icelandic", "italian", "japanese", "javanese", "georgian", "kazakh", "khmer", "kannada", "korean", "krio", "kurdish", "kyrgyz",
            "latin", "luxembourgish", "luganda", "lingala", "lao", "lithuanian", "mizo", "latvian", "maithili", "malagasy", "maori", "macedonian",
            "malayalam", "mongolian", "manipuri", "marathi", "malay", "maltese", "burmese", "nepali", "dutch", "norwegian", "sepedi", "chichewa",
            "oromo", "ordia", "punjabi", "polish", "pashto", "portuguese", "quechua", "romanian", "russian", "kinyarwanda", "sanskrit", "sindhi",
            "sinhala", "slovak", "slovenian", "somoan", "shona", "somali", "albanian", "serbian", "sesotho", "sundanese", "swedish", "swahili",
            "tamil", "telugu", "tajik", "thai", "tigrinya", "turkmen", "tagalog", "turkish", "tsonga", "tatar", "uyghur", "ukrainian", "urdu", "uzbek",
            "vietnamese", "xhosa", "yiddish", "yoruba", "chinese (simplified)", "chinese (traditional)", "zulu");


    private static HashMap<String,String> googleLanguagesAndCodes;
    private LanguageTransformer(){
        initializeHashmap();
    }

    public static LanguageTransformer getLanguageTransformer(){
        return LanguageTransformerHolder.languageTransformer;
    }

    private static class LanguageTransformerHolder{
        private static final LanguageTransformer languageTransformer = new LanguageTransformer();
    }

    private static void initializeHashmap(){
        googleLanguagesAndCodes = new HashMap<>();
        for (int i = 0; i < googleLanguages.size(); i++) {
            String language = googleLanguages.get(i);
            String languageCode = googleLanguageCodes.get(i);
            googleLanguagesAndCodes.put(language,languageCode);
        }
    }

    private boolean isGoogleLanguage(String language){
        return googleLanguagesAndCodes.containsKey(language);
    }

    private boolean isGoogleLanguageCode(String language){
        return googleLanguagesAndCodes.containsValue(language);
    }

    public String getLanguageCode(String language){
        String lowerCase = language.toLowerCase();
        return languageToLanguageCode(lowerCase);
    }

    private String languageToLanguageCode(String language){
        if(isGoogleLanguageCode(language)) return language;
        if(!isGoogleLanguage(language)) throw new IllegalArgumentException("Language is not supported");

        String languageCode = googleLanguagesAndCodes.get(language);
        return languageCode;
    }

}
