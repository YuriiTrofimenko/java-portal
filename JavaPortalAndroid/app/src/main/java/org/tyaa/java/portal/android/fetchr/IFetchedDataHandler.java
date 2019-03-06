package org.tyaa.java.portal.android.fetchr;

import org.tyaa.java.portal.model.Author;

import java.util.List;

public interface IFetchedDataHandler {
    void onAuthorsFetched(List authorList);
    void onAuthorFetched(Author author);
    void onActionComleted(String status);
}
