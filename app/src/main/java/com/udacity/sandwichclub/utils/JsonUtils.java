package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject json_data = new JSONObject(json);
            String name = json_data.getJSONObject("name").getString("mainName");
            JSONArray nameAlias = json_data.getJSONObject("name").getJSONArray("alsoKnownAs");
            ArrayList<String> alsoKnownAs = new ArrayList<>(nameAlias.length());
            for(int i=0;i < nameAlias.length();i++){
                alsoKnownAs.add(nameAlias.getString(i));
            }
            Boolean msg = ((json_data.has("placeOfOrigin") && !json_data.isNull("placeOfOrigin")));
            String placeOfOrigin = json_data.get("placeOfOrigin").toString();
            String description = json_data.getString("description");
            String image_url = json_data.getString("image");
            JSONArray ingredients = json_data.getJSONArray("ingredients");
            ArrayList<String> ingredients_arr = new ArrayList<>(ingredients.length());
            for(int i=0;i < ingredients.length();i++){
                ingredients_arr.add(ingredients.getString(i));
            }
            Sandwich sandwich = new Sandwich(name, alsoKnownAs, placeOfOrigin, description, image_url, ingredients_arr);
            return sandwich;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
