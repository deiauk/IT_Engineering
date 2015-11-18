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

    public InnerDatabase(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Constants.PRODUCTS_INFO +
                " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + Constants.BARCODE + " BIGINT);");

        db.execSQL("CREATE TABLE " +
                Constants.FOOD_ADDITIVES_TABLE + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Constants.FOOD_ADDITIVES + " TEXT, " + Constants.PRODUCT_ID + " INTEGER);");

        db.execSQL("CREATE TABLE " + Constants.ADDITIVE_INFO + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            Constants.FOOD_ADDITIVES + " TEXT, " + Constants.ADDITIVE_FULL_NAME + " TEXT, " + Constants.FUNCTION + " TEXT, " + Constants.DISEASES + " TEXT);");

        fillDatabase(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.PRODUCTS_INFO);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.FOOD_ADDITIVES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.FOOD_ADDITIVES_MAIN_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.BASIC_INFO_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.PRODUCT_INFO_AND_FOOD_ADDITIVES);

        onCreate(db);
    }

    public void setProduct(SQLiteDatabase db, long barcode){
        ContentValues values = new ContentValues();
        values.put(Constants.BARCODE, barcode);
        db.insert(Constants.PRODUCTS_INFO, null, values);
    }

    public void setFoodAdditive(SQLiteDatabase db, String text, int id){
        ContentValues values = new ContentValues();
        values.put(Constants.FOOD_ADDITIVES, text);
        values.put(Constants.PRODUCT_ID, id);
        db.insert(Constants.FOOD_ADDITIVES_TABLE, null, values);
    }

    public void setInfoAboutAdditive(SQLiteDatabase db, String text, String text2, String text3, String text4){
        ContentValues values = new ContentValues();
        values.put(Constants.FOOD_ADDITIVES, text);
        values.put(Constants.ADDITIVE_FULL_NAME, text2);
        values.put(Constants.FUNCTION, text3);
        values.put(Constants.DISEASES, text4);
        db.insert(Constants.ADDITIVE_INFO, null, values);
    }

    public void fillDatabase(SQLiteDatabase db){
        setProduct(db, 4770149204152L);
        setProduct(db, 5014016150821L);
        setProduct(db, 4770118163091L);
        setProduct(db, 4770081165566L);
        setProduct(db, 4820001357288L);
        setProduct(db, 4770299390583L);
        setProduct(db, 4770237041089L);

        setFoodAdditive(db, "E211", 2);
        setFoodAdditive(db, "E621", 1);
        setFoodAdditive(db, "E301", 1);
        setFoodAdditive(db, "E450", 2);
        setFoodAdditive(db, "E451", 2);
        setFoodAdditive(db, "E120", 1);
        setFoodAdditive(db, "E250", 1);
        setFoodAdditive(db, "E316", 1);
        setFoodAdditive(db, "E250", 1);
        setFoodAdditive(db, "E202", 2);


        setInfoAboutAdditive(db, "E621", "Mononatrio glutamatas", "Aromato ir skonio stipriklis", "Alergija, astma, migrena, vėžiniai susirgimai");
        setInfoAboutAdditive(db, "E211", "Natrio benzoatas", "Konservantas", "Alergija, astma, hiperaktyvumas, vėžiniai susirgimai");
    }
}