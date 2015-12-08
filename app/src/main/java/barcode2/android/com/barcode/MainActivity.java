package barcode2.android.com.barcode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends Activity {

    private IntentIntegrator integrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            if(result != null && result.length() > 0){
                Intent intent2 = new Intent(this, ProductActivity.class);
                intent2.putExtra("barcode", result);
                startActivity(intent2);
            }
        }
    }

    public void goToDatabase(View v){
        Intent intent = new Intent(this, DatabaseActivity.class);
        startActivity(intent);
    }
}