package org.tyaa.java.portal.android.fetchr;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tyaa.java.portal.model.Author;

import java.util.List;

public class JsonFetchr implements IFetchr{

    private IFetchedDataHandler mFetchedDataHandler;

    public JsonFetchr(IFetchedDataHandler _fetchedDataHandler){

        mFetchedDataHandler = _fetchedDataHandler;
    }

    @Override
    public void fetch(Object _args) {

        String urlString = _args.toString();

        RequestQueue queue = Volley.newRequestQueue((Context)mFetchedDataHandler);
        Log.d("my", urlString);
        JsonObjectRequest jsonArrayRequest =
                new JsonObjectRequest(
                        urlString
                        , new JSONObject()
                        , new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("my", response.toString());
                        List<Author> authors = null;
                        try {
                            authors = new JsonParser().parseOrders(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mFetchedDataHandler.onOrdersFetched(authors);
                    }
                }
                        , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("my", error.toString());
                    }
                }
                );
        queue.add(jsonArrayRequest);
    }
}
