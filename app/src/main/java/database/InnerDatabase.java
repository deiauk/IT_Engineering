package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import barcode2.android.com.barcode.Constants;
import barcode2.android.com.barcode.DatabaseActivity;
import barcode2.android.com.barcode.ProductInformation;

/**
 * Created by Deividas on 2015-10-07.
 */
public class InnerDatabase extends SQLiteOpenHelper {

    private static final String DB_NAME = "PRODUCTS_DATABASE";
    private static final int DB_VERSION = 1;

    private static ContentValues values = new ContentValues();

    public InnerDatabase(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Constants.PRODUCTS_INFO + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Constants.BARCODE + " INTEGER, " + Constants.FOOD_ADDITIVES_ID + " INTEGER, " +
                Constants.BASIC_INFO_ID + " INTEGER, " + Constants.PICTURE_ID + " INTEGER);");

        db.execSQL("CREATE TABLE " + Constants.FOOD_ADDITIVES_TABLE + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Constants.FOOD_ADDITIVES + " TEXT);");

        db.execSQL("CREATE TABLE " + Constants.FOOD_ADDITIVES_MAIN_TABLE + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Constants.FOOD_ADDITIVES_ID + " INTEGER, " + Constants.BASIC_INFO_ID + " INTEGER);");

        db.execSQL("CREATE TABLE " + Constants.BASIC_INFO_TABLE + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Constants.BASIC_INFO + " TEXT);");

        init(db, "E621");
        init(db, "E523");
        init(db, "E571");
        init(db, "E651");
        init(db, "E125");
        init(db, "E765");
        init(db, "E535");
        init(db, "E843");
        init(db, "E678");
        init(db, "E455");
        init(db, "E621");
        init(db, "E523");
        init(db, "E571");
        init(db, "E651");
        init(db, "E125");
        init(db, "E765");
        init(db, "E535");
        init(db, "E843");
        init(db, "E678");
        init(db, "E455");
        init(db, "E621");
        init(db, "E523");
        init(db, "E571");
        init(db, "E651");
        init(db, "E125");
        init(db, "E765");
        init(db, "E535");
        init(db, "E843");
        init(db, "E678");
        init(db, "E455");
        init(db, "E621");
        init(db, "E523");
        init(db, "E571");
        init(db, "E651");
        init(db, "E125");
        init(db, "E765");
        init(db, "E535");
        init(db, "E843");
        init(db, "E678");
        init(db, "E455");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.PRODUCTS_INFO);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.FOOD_ADDITIVES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.FOOD_ADDITIVES_MAIN_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.BASIC_INFO_TABLE);

        onCreate(db);
    }

    public void addInfo(ProductInformation info){
        SQLiteDatabase db = this.getWritableDatabase();

        values.put(Constants.BARCODE, info.getBarcode());
        values.put(Constants.FOOD_ADDITIVES_ID, info.getFoodAdditivesID());
        values.put(Constants.BASIC_INFO_ID, info.getBasicInfoID());
        values.put(Constants.PICTURE_ID, info.getPictureID());

        db.insert(Constants.PRODUCTS_INFO, null, values);
        db.close();
    }

    public void init(SQLiteDatabase db, String value){
        values.put(Constants.FOOD_ADDITIVES, value);
        db.insert(Constants.FOOD_ADDITIVES_TABLE, null, values);
    }
}
