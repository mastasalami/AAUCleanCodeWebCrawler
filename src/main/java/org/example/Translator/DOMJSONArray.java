package org.example.Translator;

import org.json.JSONArray;

public class DOMJSONArray {
    private JSONArray jsonArray;

    DOMJSONArray(String turnIntoJsonArray){
        this.jsonArray = new JSONArray(turnIntoJsonArray);
    }

   public int length(){
        return jsonArray.length();
   }

   public DOMJSONObject getDOMJSONObject(int index){
       DOMJSONObject domjsonObject = new DOMJSONObject(jsonArray.getJSONObject(index));

       return domjsonObject;
   }

}
