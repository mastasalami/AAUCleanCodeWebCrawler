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
        translator = new Translator();
    }

    @AfterEach
    public void teardown() {
        translator = null;
    }
}
