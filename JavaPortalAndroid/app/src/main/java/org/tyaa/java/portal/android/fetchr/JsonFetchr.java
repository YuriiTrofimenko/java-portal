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
import org.tyaa.java.portal.android.R;
import org.tyaa.java.portal.android.Utils;
import org.tyaa.java.portal.model.Author;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JsonFetchr implements IFetchr {

    private IFetchedDataHandler mFetchedDataHandler;
    private JsonParser mJsonParser;
    private String mBaseUrl;

    public JsonFetchr(IFetchedDataHandler _fetchedDataHandler) {

        mFetchedDataHandler = _fetchedDataHandler;
        mJsonParser = new JsonParser();
        mBaseUrl =
                ((Context)mFetchedDataHandler).getResources().getString(R.string.avd_base_url);
    }

    @Override
    public void fetch() {

        String urlString = mBaseUrl + "author";

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
                        Toast.makeText(((Context) mFetchedDataHandler), "Authors list fetch error", Toast.LENGTH_LONG).show();
                    }
                }
            );
        queue.add(jsonArrayRequest);
    }

    @Override
    public void fetchOne(Integer _id) throws JSONException {

        String urlString = mBaseUrl + "author/get/" + _id;
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
    public void createAuthor(Author _author) throws JSONException {

        String urlString = mBaseUrl + "author/create";
        RequestQueue queue = Volley.newRequestQueue((Context) mFetchedDataHandler);
        JSONObject jsonObject = new JSONObject();
        _author.setName(Utils.prepareString(_author.getName()));
        _author.setAbout(Utils.prepareString(_author.getAbout()));
        jsonObject.put("author", mJsonParser.authorToJsonString(_author));
        JsonObjectRequest jsonArrayRequest =
                new JsonObjectRequest(
                        urlString
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
                        mFetchedDataHandler.onActionCompleted(status);
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
    public void deleteAuthor(Integer _id) {

        String urlString = mBaseUrl + "author/delete/" + _id;
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
                        String status = null;
                        try {
                            status = mJsonParser.parseResponse(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mFetchedDataHandler.onActionCompleted(status);
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
