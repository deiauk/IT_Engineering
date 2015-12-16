package barcode2.android.com.barcode;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import database.InnerDatabase;

public class DatabaseActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private SimpleCursorAdapter adapter;
    private Cursor cursor;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        listView = (ListView) findViewById(R.id.listView);

        Context context = getApplicationContext();

        SQLiteOpenHelper helper = new InnerDatabase(context);
        db = helper.getReadableDatabase();

        new SearchInBackground(context).execute();
    }

    public class SearchInBackground extends AsyncTask<Void, Void, Cursor>{

        private Context context;

        public SearchInBackground(Context c){
            context = c;
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            String q = "SELECT _id, " + Constants.BARCODE + ", " + Constants.DATE_COLUMN + " FROM " +
                    Constants.BARCODE_TABLE + " ORDER BY " + Constants.DATE_COLUMN + " DESC";
            cursor = db.rawQuery(q, null);
            return cursor;
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);

            adapter = new SimpleCursorAdapter(context, R.layout.barcode_single, cursor,
                    new String[]{Constants.BARCODE, Constants.DATE_COLUMN},
                    new int[]{R.id.barcode, R.id.date}, 0);
            adapter.changeCursor(cursor);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String barcode = ((TextView) view.findViewById(R.id.barcode)).getText().toString();
                    Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
                    intent.putExtra("categoryToDetect", "0");
                    intent.putExtra("barcode", barcode);
                    intent.putExtra("infoFromInnerDatabase", true);
                    startActivity(intent);
                }
            });
        }
    }
}
