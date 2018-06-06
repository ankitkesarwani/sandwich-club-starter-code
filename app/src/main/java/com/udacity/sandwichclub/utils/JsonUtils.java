package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        try {
            JSONObject sandwichObject = new JSONObject(json);

            JSONObject sandwichName = sandwichObject.getJSONObject("name");

            String mainName = sandwichName.getString("mainName");
            JSONArray alsoKnownAs = sandwichName.getJSONArray("alsoKnownAs");

            List<String> alsoKnownAsListItem = new ArrayList<>();
            for (int i=0; i<alsoKnownAs.length(); i++) {

                alsoKnownAsListItem.add(alsoKnownAs.getString(i));

            }

            String placeOfOrigin = sandwichObject.getString("placeOfOrigin");
            String description = sandwichObject.getString("description");

            String image = sandwichObject.getString("image");

            JSONArray ingredients = sandwichObject.getJSONArray("ingredients");

            List<String> ingredientsListItem = new ArrayList<>();
            for (int i=0; i<ingredients.length(); i++) {

                ingredientsListItem.add(ingredients.getString(i));

            }

            return new Sandwich(mainName, alsoKnownAsListItem, placeOfOrigin, description, image, ingredientsListItem);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
