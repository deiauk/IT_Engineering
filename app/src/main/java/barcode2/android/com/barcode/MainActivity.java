package barcode2.android.com.barcode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import database.InnerDatabase;

public class MainActivity extends Activity {

    private IntentIntegrator integrator;
    private InnerDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new InnerDatabase(this);

        integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setPrompt("Atitraukite prekės kodą maždaug 30 cm nuo telefono");
        integrator.setBeepEnabled(true);
    }

    public void scanCode(View v) {
        integrator.initiateScan();
    }

    public void searchInDatabase(View v){
        Intent intent = new Intent(this, SearchInDatabaseActivity.class);
        startActivity(intent);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            String result = scanResult.getContents();
            if(result.length() > 0){
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
            }
        }
    }
}
