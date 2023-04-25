package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.http.HttpResponse;

public class HttpParser {
    private static HttpParser parser;
    final static String JSONOBJECT_DETECTED_KEY = "language";
    final static String JSONOBJECT_TRANSLATED_KEY = "translatedText";

    private HttpParser() {
    }

    public static HttpParser getHttpRequestParser() {
        if (parser == null) {
            parser = new HttpParser();
        }

        return parser;
    }

    public String parseDetectResponse(HttpResponse<String> response){
        return parseHttpResponse(response, JSONOBJECT_DETECTED_KEY);
    }
    public String parseTranslateResponse(HttpResponse<String> response){
        return parseHttpResponse(response, JSONOBJECT_TRANSLATED_KEY);
    }

  private String parseHttpResponse(HttpResponse<String> response, String jsonObjectKey) {
        JSONArray responseJson = extractJsonArrayFromHttpResponse(response);
        StringBuilder parsedResponse = new StringBuilder();
        for (int i = 0; i < responseJson.length(); i++) {
            JSONObject responseObject = responseJson.getJSONObject(i);
            String parsed = responseObject.getString(jsonObjectKey);
            parsedResponse.append(parsed);
        }
        String parsedString = parsedResponse.toString();
        return parsedString;
    }

    private JSONArray extractJsonArrayFromHttpResponse(HttpResponse<String> response) {
        String responseBody = response.body();
        //In the HttpResponse of the Google Translate API wraps the JSON Array into something else. It also wraps the JSON array inside
        // another JSON array for detect Requests. That's why I cut off part of the responseBody with lastIndexof('[')
        int jsonArrayStartIndex = responseBody.lastIndexOf('[');
        String extracted = responseBody.substring(jsonArrayStartIndex);
        return new JSONArray(extracted);
    }
}