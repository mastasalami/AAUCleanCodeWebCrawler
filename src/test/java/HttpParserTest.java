import org.example.HttpParser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.net.ssl.SSLSession;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public class HttpParserTest {
    HttpParser httpParser;

    @BeforeEach
    public void setup(){
        httpParser = HttpParser.getHttpRequestParser();
    }
    @AfterEach
    public void teardown(){
        httpParser = null;
    }

    @Test
    public void parseHelloFromTranslateHttpResponse(){
        String parsed = httpParser.parseTranslateResponse(getTranslateHttpResponseWithHello());
        Assertions.assertEquals("Hello",parsed);
    }
    @Test
    public void parseHowAreYouFromDetectHttpResponse(){
        String parsed = httpParser.parseDetectResponse(getDetectHttpResponseWithHowAreYou());
        Assertions.assertEquals("HowAreYou",parsed);
    }


    private HttpResponse<String> getTranslateHttpResponseWithHello(){
        String body =     "{" +
                "data: {" +
                "translations: [ " +
                "{ " +
                "translatedText: \"Hello\"" +
                "}" +
                "]" +
                "}" +
                "}";
        return new HttpResponse<String>() {
            @Override
            public int statusCode() {
                return 0;
            }

            @Override
            public HttpRequest request() {
                return null;
            }

            @Override
            public Optional<HttpResponse<String>> previousResponse() {
                return Optional.empty();
            }

            @Override
            public HttpHeaders headers() {
                return null;
            }

            @Override
            public String body() {
                return body;
            }

            @Override
            public Optional<SSLSession> sslSession() {
                return Optional.empty();
            }

            @Override
            public URI uri() {
                return null;
            }

            @Override
            public HttpClient.Version version() {
                return null;
            }
        };

    }

    private HttpResponse<String> getDetectHttpResponseWithHowAreYou(){
        String body =   "{ " +
                "data: { " +
                "detections: [ " +
                "[ " +
                "{ " +
                "confidence: 1, " +
                "language: \"HowAreYou\", " +
                "isReliable: false" +
                "}" +
                "]" +
                "]" +
                "}" +
                "}";
        return new HttpResponse<String>() {
            @Override
            public int statusCode() {
                return 0;
            }

            @Override
            public HttpRequest request() {
                return null;
            }

            @Override
            public Optional<HttpResponse<String>> previousResponse() {
                return Optional.empty();
            }

            @Override
            public HttpHeaders headers() {
                return null;
            }

            @Override
            public String body() {
                return body;
            }

            @Override
            public Optional<SSLSession> sslSession() {
                return Optional.empty();
            }

            @Override
            public URI uri() {
                return null;
            }

            @Override
            public HttpClient.Version version() {
                return null;
            }
        };

    }


}


