package org.tyaa.java.portal.android;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.tyaa.java.portal.android.adapter.AuthorsAdapter;
import org.tyaa.java.portal.android.fetchr.IFetchedDataHandler;
import org.tyaa.java.portal.android.fetchr.JsonFetchr;
import org.tyaa.java.portal.model.Author;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends ListActivity implements IFetchedDataHandler {

    private List<Author> mAuthors;
    private AuthorsAdapter mAuthorsAdapter;
    public static final String SELECTED_AUTHOR =
            "org.tyaa.java.portal.android.SELECTED_AUTHOR";
    public static final Integer NEW_AUTHOR_REQUEST = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuthors = new ArrayList<>();
        mAuthorsAdapter =
                new AuthorsAdapter(this, R.layout.authors_list_item, mAuthors);
        setListAdapter(mAuthorsAdapter);
        getAllAuthors();

        ButterKnife.bind(this);
    }

    @OnClick(R.id.newAuthorFab)
    public void newAuthor(View view) {
        Intent intent = new Intent(this, NewAuthorActivity.class);
        startActivityForResult(intent, NEW_AUTHOR_REQUEST);
    }

    private void getAllAuthors(){

        //Mock data
        /*mAuthors.add(new Author(1, "auth1", "about a1", new Date()));
        mAuthors.add(new Author(2, "auth2", "about a2", new Date()));
        mAuthors.add(new Author(3, "auth3", "about a3", new Date()));*/
        //10.20.60.10
        //10.0.3.2
        new JsonFetchr(this)
                //.fetch("http://10.0.3.2:8080/JavaPortalEJB-war/api/author");
                .fetch("http://10.0.2.2:8080/JavaPortalEJB-war/api/author");
    }

    @Override
    public void onAuthorsFetched(List _authorList) {

        mAuthors.clear();
        mAuthors.addAll(_authorList);
        mAuthorsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAuthorFetched(Author _author) {

        Intent intent = new Intent(this, AuthorActivity.class);
        intent.putExtra(SELECTED_AUTHOR, _author);
        startActivity(intent);
    }

    @Override
    public void onActionComleted(String status) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_AUTHOR_REQUEST && resultCode == RESULT_OK) {
            getAllAuthors();
        }
    }
}
