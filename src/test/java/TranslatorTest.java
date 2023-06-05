import org.example.Translator.DummyTranslator;
import org.example.Translator.Translator;
import org.example.Translator.TranslationFailedException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TranslatorTest {
    Translator translator;

    @BeforeEach
    public void setup() {
        translator = new DummyTranslator();
    }

    @AfterEach
    public void teardown() {
        translator = null;
    }

    private List<String> returnListWithTwoGoodDays(){
        List<String> twoGoodDays = new ArrayList<>();
        twoGoodDays.add("Good day!");
        twoGoodDays.add("Good day!");
        return twoGoodDays;
    }
}
