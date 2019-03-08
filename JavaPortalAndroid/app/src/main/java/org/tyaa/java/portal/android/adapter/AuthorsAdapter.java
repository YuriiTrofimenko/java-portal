package org.tyaa.java.portal.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.tyaa.java.portal.android.MainActivity;
import org.tyaa.java.portal.android.R;
import org.tyaa.java.portal.android.fetchr.IFetchedDataHandler;
import org.tyaa.java.portal.android.fetchr.JsonFetchr;
import org.tyaa.java.portal.model.Author;

import java.text.SimpleDateFormat;
import java.util.List;

public class AuthorsAdapter extends ArrayAdapter<Author> {

    private List<Author> mAuthors;
    private Context mContext;
    private int mResource;

    public AuthorsAdapter(@NonNull Context _context, int _resource, @NonNull List<Author> _authors) {
        super(_context, _resource, _authors);
        mAuthors = _authors;
        mContext = _context;
        mResource = _resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view;
        LayoutInflater inflater = ((MainActivity) mContext).getLayoutInflater();
        view = inflater.inflate(mResource, parent, false);

        TextView authorNameTextView = view.findViewById(R.id.authorNameTextView);
        TextView authorStartedAtTextView = view.findViewById(R.id.authorStartedAtTextView);

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        authorNameTextView.setText(mAuthors.get(position).getName());
        authorStartedAtTextView.setText(
                (mAuthors.get(position).getStartedAt() != null)
                ? "(since "
                    + format.format(mAuthors.get(position).getStartedAt())
                    + ")"
                : ""
        );

        final int finalPosition = position;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("my", mAuthors.get(finalPosition).getId().toString());
                try {
                    new JsonFetchr((IFetchedDataHandler)mContext)
                            .fetchOne("http://10.0.3.2:8080/JavaPortalEJB-war/api/author/get/", mAuthors.get(finalPosition).getId());
                            //.fetchOne("http://10.0.2.2:8080/JavaPortalEJB-war/api/author/get/", mAuthors.get(finalPosition).getId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }
}
