import org.example.DummyTranslator;
import org.example.Translator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

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
    public void testEnglishToGerman() throws IOException, InterruptedException {
        String translated = translator.translate("German","Good day!");

        Assertions.assertEquals("Guten Tag!",translated);

    }
}
