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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import database.InnerDatabase;

public class ProductActivity extends Activity {

    private SQLiteDatabase db;
    private ApiConnector apiConnector;
    private SimpleCursorAdapter adapter;
    private Cursor cursor;
    private TextView barcodeText;
    private ListView listView;
    private String barcode;
    private InnerDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        listView = (ListView) findViewById(R.id.listView);

        apiConnector = new ApiConnector();

        database = new InnerDatabase(this);

        Intent intent = getIntent();
        barcode = intent.getStringExtra("barcode");
        barcodeText = (TextView) findViewById(R.id.barcode);
        barcodeText.setText(barcode);

        SQLiteOpenHelper helper = new InnerDatabase(this);
        db = helper.getReadableDatabase();

        BackTask backTask = new BackTask(getApplication());
        backTask.execute(barcode);
    }

    public class BackTask extends AsyncTask<String, Void, Cursor>{

        private Context context;

        public BackTask(Context c){
            context = c;
        }

        @Override
        protected Cursor doInBackground(String... strings) {
            String q = "SELECT a._id, a." + Constants.FOOD_ADDITIVES + ", b." + Constants.ADDITIVE_FULL_NAME  +
                    " FROM " + Constants.FOOD_ADDITIVES_TABLE + " AS a INNER JOIN " +  Constants.ADDITIVE_INFO +
                    " AS b ON a._id = b." + Constants.ADDITIVE_ID + " INNER JOIN " + Constants.PRODUCTS_INFO_AND_FOOD_ADDITIVES_TABLE +
                    " AS c ON a._id = c." + Constants.FOOD_ADDITIVES_ID + " INNER JOIN " + Constants.PRODUCTS_INFO +
                    " AS d ON c." + Constants.PRODUCT_ID + " = d._id WHERE d." + Constants.BARCODE + " = ?";
            cursor = db.rawQuery(q, new String[]{strings[0]});
            return cursor;
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            if(cursor.getCount() > 0) {
                database.updateBarcodeDate(barcode, database.getWritableDatabase());
                adapter = new SimpleCursorAdapter(context, R.layout.additive_info_full, cursor,
                        new String[]{Constants.FOOD_ADDITIVES, Constants.ADDITIVE_FULL_NAME},
                        new int[]{R.id.additive, R.id.additive_full_name}, 0);

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
            }else{
                connection(context);
            }
        }
    }

    public void connection(Context context){
        if(isConnectedToInternet()){
            try {
                JSONArray array = new NetworkBackgroundTask(context).
                        execute(Constants.SERVER_ADRESS + Constants.BARCODE_ADR + barcode).get();

                List<Map<String, String>> employeeList = parse(array);
                SimpleAdapter itemsAdapter = new SimpleAdapter(context,
                        employeeList, R.layout.additive_info_full,
                        new String[] {"additive", "full_name"}, new int[]{R.id.additive, R.id.additive_full_name});
                Toast.makeText(context, "Informacija iš interneto", Toast.LENGTH_LONG).show();
                listView.setAdapter(itemsAdapter);
                if(employeeList.size() > 0){
                    database.updateBarcodeDate(barcode, database.getWritableDatabase());
                }
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String foodAdditive = ((TextView) view.findViewById(R.id.additive)).getText().toString();
                        Intent intent = new Intent(getApplicationContext(), FoodAdditiveInfoActivity.class);
                        intent.putExtra("foodAdditive", foodAdditive);
                        startActivity(intent);
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(context, "Vidinėje duomenų bazėje nieko nerasta." +
                            " Prisijunkite prie interneto, kad paieška būtų atliekama ir išorinėje duomenų bazėje",
                    Toast.LENGTH_LONG).show();
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
        }
    }

    public List<Map<String, String>> parse(JSONArray jsonArray){
        List<Map<String, String>> employeeList = new ArrayList<>();
        if(jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = jsonArray.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String additive = jsonObject.optString("food_additives").toString();
                String fullName = jsonObject.optString("Additive_Full_Name").toString();
                employeeList.add(createAdditive(additive, fullName));
            }
        }
        return employeeList;
    }

    private HashMap<String, String> createAdditive(String additive, String fullName) {
        HashMap<String, String> employeeNameNo = new HashMap<>();
        employeeNameNo.put("additive", additive);
        employeeNameNo.put("full_name", fullName);
        return employeeNameNo;
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