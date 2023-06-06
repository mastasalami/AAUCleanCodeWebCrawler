package org.example.Translator;

import java.net.http.HttpResponse;

public class DOMHttpResponse {
    private HttpResponse<String> httpResponse;

    public DOMHttpResponse(HttpResponse<String> httpResponse){
        this.httpResponse = httpResponse;
    }

    public String body(){
        return httpResponse.body();
    }
}
