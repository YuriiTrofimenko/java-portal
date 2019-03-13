package org.tyaa.java.portal.android.fetchr;

import org.json.JSONException;
import org.tyaa.java.portal.model.Author;

public interface IFetchr {
    void fetch(Object args);
    void fetchOne(String url, Integer id) throws JSONException;
    void createAuthor(String url, Author author) throws JSONException;
    void deleteAuthor(String url, Integer id);
}
