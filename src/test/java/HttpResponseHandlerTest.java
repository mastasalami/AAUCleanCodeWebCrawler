import org.example.Translator.DOMHttpResponse;
import org.example.Translator.HttpResponseHandler;
import org.example.Translator.TranslationFailedException;
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

public class HttpResponseHandlerTest {
    HttpResponseHandler httpResponseHandler;

    @BeforeEach
    public void setup(){
        httpResponseHandler = HttpResponseHandler.getHttpRequestParser();
    }
    @AfterEach
    public void teardown(){
        httpResponseHandler = null;
    }

   /* @Test
    public void parseHelloFromTranslateHttpResponse() throws TranslationFailedException {
        String parsed = httpResponseHandler.getTranslateResponse(getTranslateHttpResponseWithHello());
        Assertions.assertEquals("Hello",parsed);
    }
    @Test
    public void parseHowAreYouFromDetectHttpResponse() throws TranslationFailedException {
        String parsed = httpResponseHandler.parseDetectResponse(getDetectHttpResponseWithHowAreYou());
        Assertions.assertEquals("HowAreYou",parsed);
    }


    private DOMHttpResponse getTranslateHttpResponseWithHello(){
        String body =     "{" +
                "data: {" +
                "translations: [ " +
                "{ " +
                "translatedText: \"Hello\"" +
                "}" +
                "]" +
                "}" +
                "}";
        return new DOMHttpResponse(new HttpResponse<String>() {
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
        });

    }

    private DOMHttpResponse getDetectHttpResponseWithHowAreYou(){
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
        return new DOMHttpResponse(new HttpResponse<String>() {
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
        });

    }*/


}


