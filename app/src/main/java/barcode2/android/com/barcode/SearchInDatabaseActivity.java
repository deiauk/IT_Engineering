package barcode2.android.com.barcode;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import database.InnerDatabase;

public class SearchInDatabaseActivity extends Activity {

    private SearchView searchView;
    private SQLiteDatabase db;
    private SQLiteOpenHelper helper;
    private SimpleCursorAdapter adapter;
    private Cursor cursor;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_in_database);

        listView = (ListView) findViewById(R.id.listView);

        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setQueryHint("pvz:. e621");

        SQLiteOpenHelper helper = new InnerDatabase(this);
        db = helper.getReadableDatabase();

        cursor = db.query(Constants.FOOD_ADDITIVES_TABLE, new String[]{"_id", Constants.FOOD_ADDITIVES}, null, null, null, null, null);

        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor,
                new String[]{Constants.FOOD_ADDITIVES}, new int[]{android.R.id.text1}, 0);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(SearchInDatabaseActivity.this, s, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                cursor = db.query(Constants.FOOD_ADDITIVES_TABLE,
                        new String[]{"_id", Constants.FOOD_ADDITIVES},
                        Constants.FOOD_ADDITIVES + " LIKE ?",
                        new String[]{"%" + text + "%"}, null, null, null);
                adapter.changeCursor(cursor);
                listView.setAdapter(adapter);
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(SearchInDatabaseActivity.this, "clicked on " + i + " long " + l + adapterView.getAdapter().getItem(i), Toast.LENGTH_SHORT).show();
            }
        });

        listView.setAdapter(adapter);
    }
}
