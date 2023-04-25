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

    String parseHttpResponse(HttpResponse<String> response, String jsonObjectKey) {
        JSONArray responseJson = extractJsonArrayFromHttpResponse(response);
        StringBuilder parsedResponse = new StringBuilder();
        //Maybe extract method
        for (int i = 0; i < responseJson.length(); i++) {
            JSONObject responseObject = responseJson.getJSONObject(i);
            String parsed = responseObject.getString(jsonObjectKey);
            parsedResponse.append(parsed);
        }
        String parsedString = parsedResponse.toString();
        return parsedString;
    }

    JSONArray extractJsonArrayFromHttpResponse(HttpResponse<String> response) {
        String responseBody = response.body();
        //TODO write comment
        int jsonArrayStartIndex = responseBody.lastIndexOf('[');
        String extracted = responseBody.substring(jsonArrayStartIndex);
        return new JSONArray(extracted);
    }
}