package barcode2.android.com.barcode;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import database.InnerDatabase;

public class SearchInDatabaseActivity extends Activity {

    private SearchView searchView;
    private SQLiteDatabase db;
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

        BackTask2 backTask = new BackTask2(getApplicationContext());
        backTask.execute();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String finalText) {
                //keisti!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                Intent intent = new Intent(getApplicationContext(), FoodAdditiveInfoActivity.class);
                intent.putExtra("foodAdditive", finalText);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                String q = "SELECT a._id, a." + Constants.FOOD_ADDITIVES + ", b." +
                        Constants.ADDITIVE_FULL_NAME  + " FROM " + Constants.FOOD_ADDITIVES_TABLE +
                        " AS a INNER JOIN " +  Constants.ADDITIVE_INFO + " AS b ON a._id = b." +
                        Constants.ADDITIVE_ID + " WHERE " + Constants.FOOD_ADDITIVES + " LIKE ? OR " +
                        Constants.ADDITIVE_FULL_NAME + " LIKE ?";
                cursor = db.rawQuery(q, new String[]{"%" + text + "%", "%" + text + "%"});

                adapter.changeCursor(cursor);
                listView.setAdapter(adapter);
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String foodAdditive = ((TextView) view.findViewById(R.id.additive)).getText().toString();
                Intent intent = new Intent(getApplicationContext(), FoodAdditiveInfoActivity.class);
                intent.putExtra("foodAdditive", foodAdditive);
                startActivity(intent);
            }
        });
        listView.setAdapter(adapter);
    }

    public class BackTask2 extends AsyncTask<Void, Void, Cursor>{

        private Context context;

        public BackTask2(Context c){
            context = c;
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            String q = "SELECT a._id, a." + Constants.FOOD_ADDITIVES + ", b." + Constants.ADDITIVE_FULL_NAME  +
                    " FROM " + Constants.FOOD_ADDITIVES_TABLE +  " AS a INNER JOIN " +  Constants.ADDITIVE_INFO +
                    " AS b ON a._id = b." + Constants.ADDITIVE_ID;
            cursor = db.rawQuery(q, null);
            return cursor;
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);

            adapter = new SimpleCursorAdapter(context, R.layout.additive_info_full, cursor,
                    new String[]{Constants.FOOD_ADDITIVES, Constants.ADDITIVE_FULL_NAME},
                    new int[]{R.id.additive, R.id.additive_full_name}, 0);
            adapter.changeCursor(cursor);
            listView.setAdapter(adapter);
        }
    }
}
