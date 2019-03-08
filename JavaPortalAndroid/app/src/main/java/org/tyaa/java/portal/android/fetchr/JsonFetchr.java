package org.tyaa.java.portal.android.fetchr;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.tyaa.java.portal.android.Utils;
import org.tyaa.java.portal.model.Author;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JsonFetchr implements IFetchr {

    private IFetchedDataHandler mFetchedDataHandler;
    private JsonParser mJsonParser;

    public JsonFetchr(IFetchedDataHandler _fetchedDataHandler) {

        mFetchedDataHandler = _fetchedDataHandler;
        mJsonParser = new JsonParser();
    }

    @Override
    public void fetch(Object _args) {

        String urlString = _args.toString();

        RequestQueue queue = Volley.newRequestQueue((Context) mFetchedDataHandler);
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
                            authors = new JsonParser().parseAuthors(response);
                            authors =
                                    authors.stream().map(new Function<Author, Author>() {
                                        @Override
                                        public Author apply(Author author) {
                                            author.setName(Utils.decodeString(author.getName()));
                                            author.setAbout(Utils.decodeString(author.getAbout()));
                                            return author;
                                        }
                                    }).collect(Collectors.<Author>toList());
                        } catch (Exception ex) {
                            Toast.makeText(((Context) mFetchedDataHandler), ex.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        mFetchedDataHandler.onAuthorsFetched(authors != null ? authors : new ArrayList());
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

    @Override
    public void fetchOne(String _url, Integer _id) throws JSONException {

        _url += _id;
        RequestQueue queue = Volley.newRequestQueue((Context) mFetchedDataHandler);
        Log.d("my", _url);
        JsonObjectRequest jsonArrayRequest =
                new JsonObjectRequest(
                        _url
                        , new JSONObject()
                        , new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("my", response.toString());
                        Author author = null;
                        try {
                            author = new JsonParser().parseAuthor(response);
                            author.setName(Utils.decodeString(author.getName()));
                            author.setAbout(Utils.decodeString(author.getAbout()));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mFetchedDataHandler.onAuthorFetched(author);
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

    @Override
    public void createAuthor(String _url, Author _author) throws JSONException {

        RequestQueue queue = Volley.newRequestQueue((Context) mFetchedDataHandler);
        JSONObject jsonObject = new JSONObject();
        _author.setName(Utils.prepareString(_author.getName()));
        _author.setAbout(Utils.prepareString(_author.getAbout()));
        jsonObject.put("author", mJsonParser.authorToJsonString(_author));
        JsonObjectRequest jsonArrayRequest =
                new JsonObjectRequest(
                        _url
                        , jsonObject
                        , new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("my", response.toString());
                        String status = null;
                        try {
                            status = mJsonParser.parseResponse(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mFetchedDataHandler.onActionComleted(status);
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
