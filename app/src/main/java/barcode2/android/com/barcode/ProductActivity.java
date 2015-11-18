package barcode2.android.com.barcode;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import database.InnerDatabase;

public class ProductActivity extends Activity {

    private SQLiteDatabase db;
    private SQLiteOpenHelper helper;
    private SimpleCursorAdapter adapter;
    private Cursor cursor;
    private TextView barcodeText;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        listView = (ListView) findViewById(R.id.listView);

        Intent intent = getIntent();
        String barcode = intent.getStringExtra("barcode");
        barcodeText = (TextView) findViewById(R.id.barcode);
        barcodeText.setText(barcode);

        SQLiteOpenHelper helper = new InnerDatabase(this);
        db = helper.getReadableDatabase();

        String q = "SELECT aa._id, aa." + Constants.FOOD_ADDITIVES + " FROM " +
                Constants.FOOD_ADDITIVES_TABLE + " as aa INNER JOIN " + Constants.PRODUCTS_INFO_AND_FOOD_ADDITIVES_TABLE +
                " AS bb ON aa._id = bb." + Constants.FOOD_ADDITIVES_ID + " INNER JOIN " + Constants.PRODUCTS_INFO +
                " AS cc ON bb." + Constants.PRODUCT_ID + " = cc._id WHERE cc." + Constants.BARCODE + " = ?";

        cursor = db.rawQuery(q, new String[]{barcode});

        adapter = new SimpleCursorAdapter(this, R.layout.product_item_layout, cursor,
                  new String[]{Constants.FOOD_ADDITIVES}, new int[]{R.id.text}, 0);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String foodAdditive = ((TextView) view.findViewById(R.id.text)).getText().toString();
                Intent intent = new Intent(getApplicationContext(), FoodAditiveInfoActivity.class);
                Toast.makeText(getApplicationContext(), foodAdditive, Toast.LENGTH_SHORT).show();
                intent.putExtra("foodAdditive", foodAdditive);
                startActivity(intent);
            }
        });

        listView.setAdapter(adapter);
    }
}