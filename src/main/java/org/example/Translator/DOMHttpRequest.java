package org.example.Translator;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DOMHttpRequest {
    private HttpRequest httpRequest;

    public DOMHttpRequest(HttpRequest httpRequest){
        this.httpRequest = httpRequest;
    }


    public DOMHttpResponse send() throws IOException, InterruptedException {
        HttpResponse<String> httpResponse = HttpClient.newHttpClient().send(this.httpRequest, HttpResponse.BodyHandlers.ofString());

        return new DOMHttpResponse(httpResponse);
    }

    public HttpHeaders headers(){
        return httpRequest.headers();
    }

    public URI uri(){
        return httpRequest.uri();
    }

    public String method(){
        return httpRequest.method();
    }


}
