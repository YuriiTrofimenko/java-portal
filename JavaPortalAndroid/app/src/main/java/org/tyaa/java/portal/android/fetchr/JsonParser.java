package org.tyaa.java.portal.android.fetchr;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;
import org.tyaa.java.portal.model.Author;

import java.util.List;

public class JsonParser {

    private Gson mGson;

    public JsonParser() {
        mGson = (new GsonBuilder())
                .setDateFormat("dd.MM.yyyy")
                .create();
    }

    public List<Author> parseOrders(JSONObject _jsonArray) throws JSONException {

        //_jsonArray.toString()
        ///List<Order> orders = new ArrayList<>();

        return null;
    }
}
