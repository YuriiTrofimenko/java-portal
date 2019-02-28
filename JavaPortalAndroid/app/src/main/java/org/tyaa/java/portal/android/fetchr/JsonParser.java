package org.tyaa.java.portal.android.fetchr;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.tyaa.java.portal.model.Author;
import org.tyaa.java.portal.model.JsonHttpResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonParser {

    private Gson mGson;

    public JsonParser() {
        mGson = (new GsonBuilder())
                .setDateFormat("dd.MM.yyyy")
                .create();
    }

    public List<Author> parseOrders(JSONObject _jsonObject)
            throws JSONException {

        //
        Type authorListType =
                new TypeToken<ArrayList<Author>>(){}.getType();
        return (List<Author>)mGson.fromJson(
                _jsonObject.getJSONArray("data").toString()
                , authorListType
        );
    }
}
