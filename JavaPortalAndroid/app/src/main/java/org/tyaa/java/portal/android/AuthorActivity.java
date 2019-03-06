package org.tyaa.java.portal.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.tyaa.java.portal.model.Author;

public class AuthorActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);
        Intent intent = getIntent();
        Author author =
                (Author) intent.getSerializableExtra(MainActivity.SELECTED_AUTHOR);
        Log.d("my - AuthorActivity", author.getAbout());
        TextView aboutTextView = findViewById(R.id.authorTextView);
        aboutTextView.setText(author.getAbout());
    }
}
