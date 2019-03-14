package org.tyaa.java.portal.android;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

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
    private AdapterView.AdapterContextMenuInfo mSelectedItemInfo;

    public static final String SELECTED_AUTHOR =
            "org.tyaa.java.portal.android.SELECTED_AUTHOR";
    public static final Integer NEW_AUTHOR_REQUEST = 0;

    private final int MENU_DELETE_AUTHOR = 1;
    private final int MENU_CANCEL = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.registerForContextMenu(findViewById(android.R.id.list));
        Log.d("my_padding", String.valueOf(findViewById(android.R.id.list).getTag()));

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
        new JsonFetchr(this).fetch();
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
    public void onActionCompleted(String status) {
        if (status.equals("deleted")){
            getAllAuthors();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_AUTHOR_REQUEST && resultCode == RESULT_OK) {
            getAllAuthors();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        switch (v.getId()) {
            case android.R.id.list: {
                menu.add(0, MENU_DELETE_AUTHOR, 0, "DELETE");
                menu.add(0, MENU_CANCEL, 1, "CANCEL");
                mSelectedItemInfo =
                    (AdapterView.AdapterContextMenuInfo) menuInfo;
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_DELETE_AUTHOR :{

                Integer authorId =
                        mAuthors.get(mSelectedItemInfo.position).getId();
                new JsonFetchr(this)
                        .deleteAuthor(authorId);
                //Log.d("my_pos", String.valueOf(mSelectedItemInfo.position));
                break;
            }
            case MENU_CANCEL :{
                //System.out.println("");
                break;
            }
        }
        return super.onContextItemSelected(item);
    }
}
