import org.example.LanguageTransformer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

public class LanguageTransformerTest {
    LanguageTransformer languageTransformer;
    @BeforeEach
    public void setup(){
        languageTransformer = LanguageTransformer.getLanguageTransformer();
    }

    @AfterEach
    public void teardown(){
        languageTransformer = null;
    }

    @ParameterizedTest
    @MethodSource("languagesAndCodes")
    public void getLanguageCodesForLanguagesNormalInput(String language, String expectedLanguageCode){
        String gottenLanguageCode = languageTransformer.getLanguageCode(language);
        Assertions.assertEquals(expectedLanguageCode,gottenLanguageCode);
    }

    @ParameterizedTest
    @MethodSource("languagesAndCodes")
    public void getLanguageCodesForLanguagesUppercaseInput(String language, String expectedLanguageCode){
        String gottenLanguageCode = languageTransformer.getLanguageCode(language.toUpperCase());
        Assertions.assertEquals(expectedLanguageCode,gottenLanguageCode);
    }

    @ParameterizedTest
    @MethodSource("justCodes")
    public void getLanguageCodesForLanguageCodeInput(String expectedLanguageCode){
        String gottenLanguageCode = languageTransformer.getLanguageCode(expectedLanguageCode);
        Assertions.assertEquals(expectedLanguageCode,gottenLanguageCode);
    }


    private static Stream<Arguments> languagesAndCodes(){
        return Stream.of(
                Arguments.of("german","de"),
                Arguments.of("dutch","nl"),
                Arguments.of("afrikaans","af"),
                Arguments.of("zulu","zu"),
                Arguments.of("english","en")
        );
    }

    private static Stream<Arguments> justCodes(){
        return Stream.of(
                Arguments.of("de"),
                Arguments.of("nl"),
                Arguments.of("af"),
                Arguments.of("zu"),
                Arguments.of("en")
        );
    }
}
