package org.example.Translator;

import java.io.IOException;
import java.util.List;

public interface Translator {

    String translate(String targetLanguage, String toTranslate) throws TranslationFailedException;
    String translateMany(String targetLanguage, List<String> toTranslate) throws TranslationFailedException;
}
