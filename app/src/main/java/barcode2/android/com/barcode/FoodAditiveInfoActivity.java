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
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

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

        mainText.setText(foodAdditive);

        query.execute(foodAdditive);
    }

    public class SpecialQuery extends AsyncTask<String, Void, Cursor>{

        private SQLiteDatabase db;

        public SpecialQuery(){
            SQLiteOpenHelper helper = new InnerDatabase(context);
            db = helper.getReadableDatabase();
        }
        /*
              db.execSQL("CREATE TABLE " + Constants.ADDITIVE_INFO + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        Constants.ADDITIVE_ID + " INTEGER, " + Constants.ADDITIVE_FULL_NAME + " TEXT, " + Constants.FUNCTION +
                        " TEXT, " + Constants.DISEASES + " TEXT);");
             */
        /*
         String q = "SELECT aa._id, aa." + Constants.FOOD_ADDITIVES + " FROM " +
                Constants.FOOD_ADDITIVES_TABLE + " as aa INNER JOIN " + Constants.PRODUCTS_INFO_AND_FOOD_ADDITIVES_TABLE +
                " AS bb ON aa._id = bb." + Constants.FOOD_ADDITIVES_ID + " INNER JOIN " + Constants.PRODUCTS_INFO +
                " AS cc ON bb." + Constants.PRODUCT_ID + " = cc._id WHERE cc." + Constants.BARCODE + " = ?";
         */
        @Override
        protected Cursor doInBackground(String... strings) {
            String q = "SELECT " + Constants.ADDITIVE_FULL_NAME + ", " + Constants.FUNCTION + ", " + Constants.DISEASES + " FROM " +
                    Constants.ADDITIVE_INFO + " INNER JOIN " + Constants.FOOD_ADDITIVES_TABLE + " ON " + Constants.ADDITIVE_INFO +
                    "." + Constants.ADDITIVE_ID + " = " + Constants.FOOD_ADDITIVES_TABLE + "._id WHERE " +
                    Constants.FOOD_ADDITIVES_TABLE + "." + Constants.FOOD_ADDITIVES  + " = ?";

            Log.d("TAG", q);
            return db.rawQuery(q, new String[]{strings[0]});
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            DatabaseUtils.dumpCursor(cursor);
            if(cursor.getCount() > 0){
                if(cursor.moveToFirst()) {
                    String fulName = cursor.getString(0);
                    String function1 = cursor.getString(1);
                    String disease1 = cursor.getString(2);

                    additiveText.setText(fulName); // pav
                    function.setText(function1); // fun
                    diseases.setText(disease1); // afdafa
                }
            }
        }
    }
}
