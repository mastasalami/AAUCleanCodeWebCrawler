package org.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class LanguageTransformer {
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
            "hebrew", "hindi", "hmong", "croation", "haitian creole", "hungarian", "armenian", "indonesian", "igbo", "ilocano", "icelandic",
            "ilocano", "icelandic", "italian", "japanese", "georgian", "kazakh", "khmer", "kannada", "korean", "krio", "kurdish", "kyrgyz",
            "latin", "luxembourgish", "luganda", "lingala", "lao", "lithuanian", "mizo", "latvian", "maithili", "malagasy", "maori", "macedonian",
            "malayalam", "mongolian", "manipuri", "marathi", "malay", "maltese", "burmese", "nepali", "dutch", "norwegian", "sepedi", "chichewa",
            "oromo", "ordia", "punjabi", "polish", "pashto", "portuguese", "quechua", "romanian", "russian", "kinyarwanda", "sanskrit", "sindhi",
            "sinhala", "slovak", "slovenian", "somoan", "shona", "somali", "albanian", "serbian", "sesotho", "sundanese", "swedish", "swahili",
            "tamil", "telugu", "tajik", "thai", "tigrinya", "turkmen", "tagalog", "turkish", "tsonga", "tatar", "uyghur", "urdu", "uzbek",
            "vietnamese", "xhosa", "yiddish", "yoruba", "chinese (simplified)", "chinese (traditional)", "zulu");


    private static HashMap<String,String> googleLanguagesAndCodes;
    
}
