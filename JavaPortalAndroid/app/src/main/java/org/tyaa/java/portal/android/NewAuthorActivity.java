package org.tyaa.java.portal.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.tyaa.java.portal.android.fetchr.IFetchedDataHandler;
import org.tyaa.java.portal.android.fetchr.JsonFetchr;
import org.tyaa.java.portal.model.Author;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewAuthorActivity extends Activity implements IFetchedDataHandler {

    @BindView(R.id.newAuthorNameEditText)
    EditText mNewAuthorNameEditText;

    @BindView(R.id.newAuthorAboutEditText)
    EditText mNewAuthorAboutEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_author);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.addAuthorButton)
    public void newAuthor(View view) {
        Author newAuthor = new Author();
        newAuthor.setName(mNewAuthorNameEditText.getText().toString());
        newAuthor.setAbout(mNewAuthorAboutEditText.getText().toString());
        try {
            new JsonFetchr(this)
                    .createAuthor(
                            //"http://10.0.2.2:8080/JavaPortalEJB-war/api/author/create"
                            //"http://10.0.3.2:8080/JavaPortalEJB-war/api/author/create"
                            getResources().getString(R.string.avd_base_url)
                            + "author/create"
                            , newAuthor
                    );
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAuthorsFetched(List authorList) {

    }

    @Override
    public void onAuthorFetched(Author author) {

    }

    @Override
    public void onActionCompleted(String status) {
        if (status.equals("created")){
            setResult(RESULT_OK);
            finish();
        }
    }
}
