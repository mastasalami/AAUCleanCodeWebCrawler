package org.example.Translator;

import org.json.JSONObject;

public class DOMJSONObject {
    private JSONObject jsonObject;

    DOMJSONObject(JSONObject jsonObject){
        this.jsonObject = jsonObject;
    }

    public String getString(String jsonObjectKey){
        String extracted = this.jsonObject.getString(jsonObjectKey);

        return extracted;
    }


}
