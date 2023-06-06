package org.example.Translator;

//Dummy Class to test some functionality of HttpResponseHandler without having to use the API calls
public class DummyHttpResponseHandler extends HttpResponseHandler{

    public DummyHttpResponseHandler() {
        super();
    }
    @Override
    protected String parseHttpResponse(DOMHttpResponse response, JSON_KEY jsonKey) throws TranslationFailedException {
        return super.parseHttpResponse(response, jsonKey);
    }

    public String parseDetectHttpResponse(DOMHttpResponse detectResponse) throws TranslationFailedException {
        return parseHttpResponse(detectResponse, JSON_KEY.JSONOBJECT_DETECTED_KEY);
    }

    public String parseTranslateHttpResponse(DOMHttpResponse translateResponse) throws TranslationFailedException {
        return parseHttpResponse(translateResponse, JSON_KEY.JSONOBJECT_TRANSLATED_KEY);
    }

}
