import org.example.Translator.DOMHttpRequest;
import org.example.Translator.HttpRequestCreator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class HttpRequestCreatorTest {
    HttpRequestCreator httpRequestCreator;
    @BeforeEach
    public void setup(){
        httpRequestCreator = new HttpRequestCreator();
    }

    @AfterEach
    public void teardown(){
        httpRequestCreator = null;
    }

    @Test
    public void createOneTranslateRequest(){
        DOMHttpRequest translateRequestCreator;
        translateRequestCreator = httpRequestCreator.buildTranslateLanguageHttpRequest("Test","en","de");

        HttpRequest translateRequestNormal = normalHttpRequestCreationTranslate();

        String normalHeaders = transformHeaders(translateRequestNormal.headers());
        String createHeaders = transformHeaders(translateRequestCreator.headers());


        Assertions.assertEquals(translateRequestNormal.method(),translateRequestCreator.method());
        Assertions.assertEquals(translateRequestNormal.uri(),translateRequestCreator.uri());
        Assertions.assertEquals(normalHeaders, createHeaders);

    }

    @Test
    public void createOneDetectRequest(){
        DOMHttpRequest detectRequestCreator;
        detectRequestCreator = httpRequestCreator.buildDetectLanguageHttpRequest("Guten Tag!");

        HttpRequest detectRequestNormal = normalHttpRequestCreationDetect();

        String normalHeaders = transformHeaders(detectRequestNormal.headers());
        String createHeaders = transformHeaders(detectRequestCreator.headers());


        Assertions.assertEquals(detectRequestNormal.method(),detectRequestCreator.method());
        Assertions.assertEquals(detectRequestNormal.uri(),detectRequestCreator.uri());
        Assertions.assertEquals(normalHeaders, createHeaders);

    }

    @Test
    public void transformListWithTenEntriesAndNineThousandChars(){
        List<String> listWithTenEntriesAndTenThousandChars = returnListWithTenEntriesAndNineHundredCharsPerEntry();
       List<String> transformedList = httpRequestCreator.formatForHttpRequest(listWithTenEntriesAndTenThousandChars);
       Assertions.assertEquals(3,transformedList.size());
       Assertions.assertEquals(3600,transformedList.get(0).length());
    }


    private HttpRequest normalHttpRequestCreationTranslate(){
        HttpRequest translateRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://google-translator9.p.rapidapi.com/v2"))
                .header("content-type", "application/json")
                .header("X-RapidAPI-Key", "aadb5d6ff7msh13b2cb8721a86c9p18710ajsn2027bc2ca5c9")
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

    private HttpRequest normalHttpRequestCreationDetect(){
        HttpRequest detectRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://google-translator9.p.rapidapi.com/v2/detect"))
                .header("content-type", "application/json")
                .header("X-RapidAPI-Key", "aadb5d6ff7msh13b2cb8721a86c9p18710ajsn2027bc2ca5c9")
                .header("X-RapidAPI-Host", "google-translator9.p.rapidapi.com")
                .method("POST", HttpRequest.BodyPublishers.ofString("{\r\"" +
                        "q\": \"Guten Tag!\"\r" +
                        "}"))
            .build();
        return detectRequest;
    }

    private String transformHeaders(HttpHeaders headers){
        String headersToString = headers.toString();
        String transformedHeaders = headersToString.substring(34);
        return transformedHeaders;
    }

    private List<String> returnListWithTenEntriesAndNineHundredCharsPerEntry(){
        String ninehundredChars = returnNinehundredChars();

        List<String> listWithTenEntriesAndNineHundredCharsPerEntry = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            listWithTenEntriesAndNineHundredCharsPerEntry.add(ninehundredChars);
        }
        return listWithTenEntriesAndNineHundredCharsPerEntry;
    }

    private String returnNinehundredChars(){
        String ninehundredChars =  "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb" +
                        "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb" +
                        "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb" +
                        "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb" +
                        "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb" +
                        "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb" +
                        "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb" +
                        "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb" +
                        "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb";

        return ninehundredChars;
    }


}
