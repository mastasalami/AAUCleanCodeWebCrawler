package org.example.Translator;

import java.io.IOException;

public class HttpResponseHandler {
    private static HttpResponseHandler parser;
    final static int sleepTime = 500;

    private HttpResponseHandler() {
    }

   public enum JSON_KEY{
        JSONOBJECT_DETECTED_KEY("language"),
       JSONOBJECT_TRANSLATED_KEY("translatedText");

      final String getJSONKey;

       JSON_KEY(String jsonKey) {
           this.getJSONKey = jsonKey;
       }
   }




    public static HttpResponseHandler getHttpRequestParser() {
        return HttpResponseHandlerHolder.httpResponseHandler;
    }

    private static class HttpResponseHandlerHolder{
        private static final HttpResponseHandler httpResponseHandler = new HttpResponseHandler();
    }


    public String getDetectResponse(DOMHttpRequest request) throws TranslationFailedException{
        DOMHttpResponse response = sendHttpRequest(request);
        return parseHttpResponse(response, JSON_KEY.JSONOBJECT_DETECTED_KEY);
    }
    public String getTranslateResponse(DOMHttpRequest request) throws TranslationFailedException{
        DOMHttpResponse response = sendHttpRequest(request);
        return parseHttpResponse(response, JSON_KEY.JSONOBJECT_TRANSLATED_KEY);
    }

  private String parseHttpResponse(DOMHttpResponse response, JSON_KEY jsonKey) throws TranslationFailedException{
        DOMJSONArray responseJson = extractDOMJsonArrayFromHttpResponse(response, jsonKey);
        StringBuilder parsedResponse = new StringBuilder();

        for (int i = 0; i < responseJson.length(); i++) {
            DOMJSONObject responseObject = responseJson.getDOMJSONObject(i);
            String parsed = responseObject.getString(jsonKey.getJSONKey);
            parsedResponse.append(parsed);
        }

      return parsedResponse.toString();
    }

    private DOMJSONArray extractDOMJsonArrayFromHttpResponse(DOMHttpResponse response, JSON_KEY jsonKey) throws TranslationFailedException {
        String responseBody = response.body();
        try {
            String extracted = cutOffPartOfResponse(responseBody, jsonKey);
            return new DOMJSONArray(extracted);
        } catch (RuntimeException e){
            throw new TranslationFailedException("The translation api could not process the request because: " + responseBody);
        }
    }

    private String cutOffPartOfResponse(String toCutOffFrom, JSON_KEY jsonKey){
        //The HttpResponse of the Google Translate API wraps the JSON Array into something else. It also wraps the JSON array inside
        // another JSON array for detect Requests. That's why I cut off part of the responseBody.
        int jsonArrayStartIndex = toCutOffFrom.indexOf('[');

        if(jsonKey.equals(JSON_KEY.JSONOBJECT_DETECTED_KEY))    //Should the response be from a detect language request I need to cut off another JSON Array.
            jsonArrayStartIndex = toCutOffFrom.indexOf('[', jsonArrayStartIndex +1);

        String extracted = toCutOffFrom.substring(jsonArrayStartIndex);
        return extracted;
    }

    //As the number of requests you can send per second is limited, the method is sychronized and I also added a sleep in the method.
   synchronized private DOMHttpResponse sendHttpRequest(DOMHttpRequest request) throws TranslationFailedException {
        try {
           DOMHttpResponse response = request.send();
           Thread.sleep(sleepTime);
            return response;
        } catch (IOException | InterruptedException e) {
            throw new TranslationFailedException("An error occured while trying to send a Httprequest to the translation API");
        }
    }
}