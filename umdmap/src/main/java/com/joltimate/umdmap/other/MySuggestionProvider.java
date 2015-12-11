/*package com.joltimate.umdmap;

import android.content.SearchRecentSuggestionsProvider;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.v4.widget.SimpleCursorAdapter;


public class MySuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "com.joltimate.umdmap.SearchProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public MySuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        if (selectionArgs != null && selectionArgs.length > 0 && selectionArgs[0].length() > 0) {
            // the entered text can be found in selectionArgs[0]
            // return a cursor with appropriate data
        }
        else {
            // user hasn’t entered anything
            // thus return a default cursor
        }
        return null;
    }
    private static final String[] SUGGESTIONS = {
            "Bauru", "Sao Paulo", "Rio de Janeiro",
            "Bahia", "Mato Grosso", "Minas Gerais",
            "Tocantins", "Rio Grande do Sul"
    };
    private SimpleCursorAdapter mAdapter;
    // You must implements your logic to get data using OrmLite
    private void populateAdapter(String query) {
        final MatrixCursor c = new MatrixCursor(new String[]{ BaseColumns._ID, "cityName" });
        for (int i=0; i<SUGGESTIONS.length; i++) {
            if (SUGGESTIONS[i].toLowerCase().startsWith(query.toLowerCase()))
                c.addRow(new Object[] {i, SUGGESTIONS[i]});
        }
        mAdapter.changeCursor(c);
    }
}
*/