package barcode2.android.com.barcode;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import database.InnerDatabase;

public class FoodAditiveInfoActivity extends Activity {

    private TextView mainText;
    private TextView additiveText;
    private TextView function;
    private TextView diseases;

    private Context context;

    private SpecialQuery query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_aditive_info);

        context = getApplicationContext();

        query = new SpecialQuery();

        Intent intent = getIntent();
        String foodAdditive = intent.getStringExtra("foodAdditive");

        mainText = (TextView) findViewById(R.id.mainText);
        additiveText = (TextView) findViewById(R.id.textView2);
        function = (TextView) findViewById(R.id.function);
        diseases = (TextView) findViewById(R.id.diseases);

        query.execute(foodAdditive);
    }

    public class SpecialQuery extends AsyncTask<String, Void, Cursor>{

        private SQLiteDatabase db;

        public SpecialQuery(){
            SQLiteOpenHelper helper = new InnerDatabase(context);
            db = helper.getReadableDatabase();
        }


        @Override
        protected Cursor doInBackground(String... strings) {
//            return db.query(Constants.ADDITIVE_INFO,
//                    new String[]{Constants.FOOD_ADDITIVES, Constants.ADDITIVE_FULL_NAME, Constants.FUNCTION, Constants.DISEASES},
//                    Constants.FOOD_ADDITIVES + " = ?", new String[]{strings[0]}, null, null, null);

            return null;
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
//            if(cursor.getCount() > 0){
//                if(cursor.moveToFirst()) {
//                    DatabaseUtils.dumpCursor(cursor);
//                    String main = cursor.getString(0);
//                    String full = cursor.getString(1);
//                    String function1 = cursor.getString(2);
//                    String diseases1 = cursor.getString(3);
//
//                    mainText.setText(main);
//                    additiveText.setText(full);
//                    function.setText(function1);
//                    diseases.setText(diseases1);
//                }
//            }
        }
    }
}
