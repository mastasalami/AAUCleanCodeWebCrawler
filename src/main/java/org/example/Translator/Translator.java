package org.example.Translator;

import java.io.IOException;
import java.util.List;

public interface Translator {
    String translate(String targetLanguage, List<String> toTranslate) throws TranslationFailedException;
}
