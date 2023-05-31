import org.example.DummyTranslator;
import org.example.Translator;
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

    @Test
    public void testTranslateEnglishToGerman() throws IOException, InterruptedException {
        String translated = translator.translate("German","Good day!");

        Assertions.assertEquals("Guten Tag!",translated);

    }
    @Test
    public void testTranslateManyEnglishToGerman() throws IOException, InterruptedException {
        List<String> twoGoodDays = returnListWithTwoGoodDays();
        String translated = translator.translateMany("German",twoGoodDays);

        Assertions.assertEquals("Guten Tag!Guten Tag!",translated);

    }

    private List<String> returnListWithTwoGoodDays(){
        List<String> twoGoodDays = new ArrayList<>();
        twoGoodDays.add("Good day!");
        twoGoodDays.add("Good day!");
        return twoGoodDays;
    }
}
