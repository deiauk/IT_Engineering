package barcode2.android.com.barcode;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
    private TextView category;
    private TextView about;
    private TextView usage;
    private TextView usageTAG;
    private TextView aboutAdditive;
    private Context context;
    private SpecialQuery query;
    private String foodAdditive;
    //private ScrollView scroller;

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
        category = (TextView) findViewById(R.id.category);
        about = (TextView) findViewById(R.id.textView3);
        usage = (TextView) findViewById(R.id.usage);
        usageTAG = (TextView) findViewById(R.id.usageTAG);
        aboutAdditive = (TextView) findViewById(R.id.aboutAdditive);

        //usageTAG.setMovementMethod(new ScrollingMovementMethod());

        //scroller = new ScrollView(this);

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
            String q = "SELECT a." + Constants.ADDITIVE_FULL_NAME + ", b." + Constants.TYPE + ", c." + Constants.IS_DANGEROUS +
                    ", a." + Constants.ABOUT_ADDITIVE + ", a." + Constants.USAGE + " FROM " + Constants.FULL_INFO +
                    " AS a INNER JOIN " + Constants.ADDITIVE_TYPE + " AS b ON a." + Constants.CATEGORY_ID +
                    " = b._id INNER JOIN " + Constants.DANGEROUS_TABLE + " AS d INNER JOIN " +
                    Constants.DANGEROUS_TABLE + " AS c ON a." + Constants.IS_DANGEROUS_ID + " = c._id INNER JOIN " +
                    Constants.FOOD_ADDITIVES_TABLE + " AS m ON a." + Constants.ADDITIVE_ID + " = m._id WHERE m." +
                    Constants.FOOD_ADDITIVES + " = ? ";
            return db.rawQuery(q, new String[]{strings[0]});
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            if(cursor.getCount() > 0){
                if(cursor.moveToFirst()) {

                    String fullName = cursor.getString(0);
                    String type = cursor.getString(1);
                    String isDangerous = cursor.getString(2);
                    String aboutAdd = cursor.getString(3);
                    String usage1 = cursor.getString(4);

                    additiveText.setText(fullName);
                    category.setText(type);
                    about.setText(isDangerous);
                    usage.setText(usage1);
                    aboutAdditive.setText(aboutAdd);

                    if(usage1.length() == 0){
                        usageTAG.setText("");
                    }
                }
            }else{
                connection(context);
            }
        }
    }

    public void connection(Context context){
        if(isConnectedToInternet()){
            try {
                JSONArray array = new NetworkBackgroundTask().
                        execute(Constants.SERVER_ADRESS + Constants.ADDITIVE + foodAdditive).get();

                HashMap map = parse(array);
                String fullName = (String) map.get("Additive_Full_Name");
                String type = (String) map.get("type");
                String isDangerous = (String) map.get("isDangerous");
                String usage1 = (String) map.get("usage");
                String aboutAdd = (String) map.get("aboutAdditive");
                additiveText.setText(fullName);
                category.setText(type);
                about.setText(isDangerous);
                usage.setText(usage1);
                aboutAdditive.setText(aboutAdd);

                if(usage1.length() == 0){
                    usageTAG.setText("");
                }

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
                String type = jsonObject.optString("type").toString();
                String isDangerous = jsonObject.optString("isDangerous").toString();
                String usage = jsonObject.optString("usage").toString();
                String aboutAdditive_Info = jsonObject.optString("aboutAdditive").toString();

                res.put("Additive_Full_Name", name);
                res.put("type", type);
                res.put("isDangerous", isDangerous);
                res.put("usage", usage);
                res.put("aboutAdditive", aboutAdditive_Info);
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