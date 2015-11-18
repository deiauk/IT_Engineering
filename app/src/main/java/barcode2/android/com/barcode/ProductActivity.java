package barcode2.android.com.barcode;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
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

        String q = "SELECT " + Constants.FOOD_ADDITIVES_TABLE + "._id, " + Constants.FOOD_ADDITIVES + " FROM " +
                Constants.FOOD_ADDITIVES_TABLE + " INNER JOIN " + Constants.PRODUCTS_INFO + " ON " +
                Constants.PRODUCTS_INFO + "._id = " + Constants.FOOD_ADDITIVES_TABLE+"."+Constants.PRODUCT_ID + " WHERE " + Constants.BARCODE + " = ?";

        cursor = db.rawQuery(q, new String[]{barcode});

        DatabaseUtils.dumpCursor(cursor);

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