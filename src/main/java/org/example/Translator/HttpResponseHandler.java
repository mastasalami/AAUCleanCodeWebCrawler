package org.example.Translator;

import java.io.IOException;

public class HttpResponseHandler {
    private static HttpResponseHandler parser;
    final static String JSONOBJECT_DETECTED_KEY = "language";
    final static String JSONOBJECT_TRANSLATED_KEY = "translatedText";

    private HttpResponseHandler() {
    }

    public static HttpResponseHandler getHttpRequestParser() {
        if (parser == null) {
            parser = new HttpResponseHandler();
        }

        return parser;
    }


    public String getDetectResponse(DOMHttpRequest request) throws TranslationFailedException{
        DOMHttpResponse response = sendHttpRequest(request);
        return parseHttpResponse(response, JSONOBJECT_DETECTED_KEY);
    }
    public String getTranslateResponse(DOMHttpRequest request) throws TranslationFailedException{
        DOMHttpResponse response = sendHttpRequest(request);
        return parseHttpResponse(response, JSONOBJECT_TRANSLATED_KEY);
    }

  private String parseHttpResponse(DOMHttpResponse response, String jsonObjectKey) throws TranslationFailedException{
        DOMJSONArray responseJson = extractDOMJsonArrayFromHttpResponse(response);
        StringBuilder parsedResponse = new StringBuilder();

        for (int i = 0; i < responseJson.length(); i++) {
            DOMJSONObject responseObject = responseJson.getDOMJSONObject(i);
            String parsed = responseObject.getString(jsonObjectKey);
            parsedResponse.append(parsed);
        }

      return parsedResponse.toString();
    }

    private DOMJSONArray extractDOMJsonArrayFromHttpResponse(DOMHttpResponse response) throws TranslationFailedException {
        String responseBody = response.body();
        try {
            //In the HttpResponse of the Google Translate API wraps the JSON Array into something else. It also wraps the JSON array inside
            // another JSON array for detect Requests. That's why I cut off part of the responseBody with lastIndexof('[')
            int jsonArrayStartIndex = responseBody.lastIndexOf('[');
            String extracted = responseBody.substring(jsonArrayStartIndex);
            return new DOMJSONArray(extracted);
        } catch (RuntimeException e){
            throw new TranslationFailedException("The translation api could not process the request because: " + responseBody);
        }
    }
    private DOMHttpResponse sendHttpRequest(DOMHttpRequest request) throws TranslationFailedException {
        DOMHttpResponse response = null;
        try {
            response = request.send();
            return response;
        } catch (IOException | InterruptedException e) {
            throw new TranslationFailedException("An error occured while trying to send a Httprequest to the translation API");
        }
    }
}