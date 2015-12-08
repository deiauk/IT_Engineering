package barcode2.android.com.barcode;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import database.InnerDatabase;

public class FoodAdditiveInfoActivity extends Activity {

    private TextView mainText;
    private TextView additiveText;
    private TextView function;
    private TextView diseases;
    private Context context;
    private SpecialQuery query;
    private String foodAdditive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_aditive_info);

        context = getApplicationContext();

        query = new SpecialQuery();

        Intent intent = getIntent();
        foodAdditive = intent.getStringExtra("foodAdditive");

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

        @Override
        protected Cursor doInBackground(String... strings) {
            String q = "SELECT " + Constants.ADDITIVE_FULL_NAME + ", " + Constants.FUNCTION + ", " + Constants.DISEASES + " FROM " +
                    Constants.ADDITIVE_INFO + " INNER JOIN " + Constants.FOOD_ADDITIVES_TABLE + " ON " + Constants.ADDITIVE_INFO +
                    "." + Constants.ADDITIVE_ID + " = " + Constants.FOOD_ADDITIVES_TABLE + "._id WHERE " +
                    Constants.FOOD_ADDITIVES_TABLE + "." + Constants.FOOD_ADDITIVES  + " = ?";
            return db.rawQuery(q, new String[]{strings[0]});
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            //DatabaseUtils.dumpCursor(cursor);
            if(cursor.getCount() > 0){
                if(cursor.moveToFirst()) {
                    String fulName = cursor.getString(0);
                    String function1 = cursor.getString(1);
                    String disease1 = cursor.getString(2);
                    additiveText.setText(fulName);
                    function.setText(function1);
                    diseases.setText(disease1);
                }
            }else{
                connection(context);
            }
        }
    }

    public void connection(Context context){
        if(isConnectedToInternet()){
            try {
                JSONArray array = new NetworkBackgroundTask(context).
                        execute(Constants.SERVER_ADRESS + Constants.ADDITIVE + foodAdditive).get();

                HashMap map = parse(array);
                String fullName = (String) map.get("Additive_Full_Name");
                String function_string = (String) map.get("Function");
                String disease = (String) map.get("Diseases");
                additiveText.setText(fullName);
                function.setText(function_string);
                diseases.setText(disease);

                Toast.makeText(context, "Informacija iš interneto", Toast.LENGTH_LONG).show();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public HashMap parse(JSONArray jsonArray){
        HashMap res = new HashMap();
        if(jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = jsonArray.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String name = jsonObject.optString("Additive_Full_Name").toString();
                String function = jsonObject.optString("Function").toString();
                String diseases = jsonObject.optString("Diseases").toString();

                res.put("Additive_Full_Name", name);
                res.put("Function", function);
                res.put("Diseases", diseases);
            }
        }
        return res;
    }

    public boolean isConnectedToInternet(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}