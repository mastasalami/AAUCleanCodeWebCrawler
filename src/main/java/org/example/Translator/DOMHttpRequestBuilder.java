package org.example.Translator;

import java.net.URI;
import java.net.http.HttpRequest;

public class DOMHttpRequestBuilder {
    private HttpRequest.Builder httpRequestBuilder;

    public DOMHttpRequestBuilder(){
        this.httpRequestBuilder = HttpRequest.newBuilder();
    }

    public void setUri(String uri){
        httpRequestBuilder.uri(URI.create(uri));
    }

    public void addHeader(String headerName, String headerValue){
        httpRequestBuilder.header(headerName,headerValue);
    }

    public void setMethod(String requestMethod, String requestBodyPublisher){
        httpRequestBuilder.method(requestMethod,HttpRequest.BodyPublishers.ofString(requestBodyPublisher));
    }

    public DOMHttpRequest build(){
        return new DOMHttpRequest(httpRequestBuilder.build());
    }




}
