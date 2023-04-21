import org.example.HttpRequestCreator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.http.HttpRequest;
import java.net.URI;
import java.util.List;

public class HttpRequestCreatorTest {
    HttpRequestCreator httpRequestCreator;
    @BeforeEach
    public void setup(){
        httpRequestCreator = HttpRequestCreator.gethttpRequestCreator();
    }

    @AfterEach
    public void teardown(){
        httpRequestCreator = null;
    }

    @Test
    public void createOneTranslateRequest(){
        HttpRequest translateRequestCreator;
        translateRequestCreator = httpRequestCreator.buildTranslateLanguageHttpRequest("Test","en","de");

        HttpRequest translateRequestNormal = normalHttpRequestCreationTranslate();

        String normalHeaders = translateRequestNormal.headers().toString().substring(34);
        String createHeaders = translateRequestCreator.headers().toString().substring(34);


        Assertions.assertEquals(translateRequestNormal.method(),translateRequestCreator.method());
        Assertions.assertEquals(translateRequestNormal.uri(),translateRequestCreator.uri());
        Assertions.assertEquals(normalHeaders, createHeaders);

    }

    private HttpRequest normalHttpRequestCreationTranslate(){
        HttpRequest translateRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://google-translator9.p.rapidapi.com/v2"))
                .header("content-type", "application/json")
                .header("X-RapidAPI-Key", "1f5de7c1b6mshc346a13cd58af05p1a5df0jsne027c8bfa2cc")
                .header("X-RapidAPI-Host", "google-translator9.p.rapidapi.com")
                .method("POST", HttpRequest.BodyPublishers.ofString("{\r" +
                        "\"q\": \"Test\",\r" +
                        "\"source\": \"en\",\r" +
                        "\"target\": \"de\",\r" +
                        "\"format\": \"text\"\r" +
                        "}"))
                .build();

        return translateRequest;
    }


}
