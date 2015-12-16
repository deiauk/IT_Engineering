package barcode2.android.com.barcode;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import database.InnerDatabase;

public class MainActivity extends Activity {

    private IntentIntegrator integrator;
    private InnerDatabase database;
    private BroadcastReceiver broadcastReceiver;
    public static String categoryToDetect = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new InnerDatabase(this);

        integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setPrompt("Atitraukite prekės kodą maždaug 30 cm nuo telefono");
        integrator.setBeepEnabled(true);

        boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isfirstrun", true);

        Intent intent = getIntent();
        categoryToDetect = intent.getStringExtra("categoryToDetect");

        if(categoryToDetect != null && categoryToDetect != "0"){
            scanCode();
        }

        if(isFirstRun) {
            installListener();
        }
    }

    public void scanCode(View v) {
        categoryToDetect = "0";
        integrator.initiateScan();
    }

    public void scanCode() {
        integrator.initiateScan();
    }

    public void searchInDatabase(View v){
        Intent intent = new Intent(this, SearchInDatabaseActivity.class);
        startActivity(intent);
    }


    public void selectFoodAdditivesToIgnore(View v){
        Intent intent = new Intent(this, SelectAdditivesActivity.class);
        startActivity(intent);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            String result = scanResult.getContents();
            if(result != null && result.length() > 0){
                Intent intent2 = new Intent(this, ProductActivity.class);
                intent2.putExtra("barcode", result);
                intent2.putExtra("categoryToDetect", categoryToDetect);
                startActivity(intent2);
            }
        }
    }

    public void goToDatabase(View v){
        Intent intent = new Intent(this, DatabaseActivity.class);
        startActivity(intent);
    }

    public class ServerTask extends AsyncTask<String, Void, JSONArray>{

        private String comparator = "";

        @Override
        protected JSONArray doInBackground(String... strings) {
            comparator = strings[1];
            return ApiConnector.getAllInfo(strings[0]);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            super.onPostExecute(jsonArray);

            if(comparator.equals("additives")){
                parse(jsonArray);
            }else{
                parse2(jsonArray);
            }
        }

        public void parse(JSONArray jsonArray){
            if(jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = jsonArray.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String additive = jsonObject.optString("food_additives").toString();
                    database.setFoodAdditive(database.getWritableDatabase(), additive);
                }
            }
        }

        public void parse2(JSONArray jsonArray){
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
                    String type = jsonObject.optString("type").toString();
                    String isDangerous = jsonObject.optString("isDangerous").toString();
                    String aboutAdditive = jsonObject.optString("aboutAdditive").toString();
                    String usage = jsonObject.optString("usage").toString();

                    database.setAdditiveType(database.getWritableDatabase(), type);
                    database.setDangerousTable(database.getWritableDatabase(), isDangerous);

                    int additiveTypeID = database.getAdditiveTypeID(database.getReadableDatabase(), type);
                    int dangerID = database.getDangerousID(database.getReadableDatabase(), isDangerous);
                    int additiveID = database.getFoodAdditiveID(database.getReadableDatabase(), additive);

                    database.setAllInfo(database.getWritableDatabase(), additiveID, fullName, additiveTypeID, dangerID, aboutAdditive, usage);
                }
            }
        }
    }

    private void installListener() {
        if (broadcastReceiver == null) {
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Bundle extras = intent.getExtras();
                    NetworkInfo info = extras.getParcelable("networkInfo");
                    NetworkInfo.State state = info.getState();
                    if (state == NetworkInfo.State.CONNECTED) {
                        new ServerTask().execute(Constants.SERVER_ADRESS + Constants.FILL_DATABASE, "additives");
                        new ServerTask().execute(Constants.SERVER_ADRESS + Constants.FILL_DATABASE2, "fill");
                        Toast.makeText(getApplicationContext(), "Atsiunčiama reikalinga papildoma informacija iš serverio",
                                Toast.LENGTH_LONG).show();
                        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isfirstrun", false).commit();
                    } else {
                        Toast.makeText(getApplicationContext(), "Prisijunkite prie interneto, nes reikia atsiųsti papildomus duomenis",
                                Toast.LENGTH_LONG).show();
                    }
                }
            };
            final IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(broadcastReceiver, intentFilter);
        }
    }
}