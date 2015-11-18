package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import barcode2.android.com.barcode.Constants;

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
                Constants.FOOD_ADDITIVES + " TEXT);");

        db.execSQL("CREATE TABLE " + Constants.PRODUCTS_INFO_AND_FOOD_ADDITIVES_TABLE +
                " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + Constants.PRODUCT_ID + " INTEGER, " +
                Constants.FOOD_ADDITIVES_ID + " INTEGER);");


        fillDatabase(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.PRODUCTS_INFO);

        onCreate(db);
    }

    public void setProduct(SQLiteDatabase db, long barcode){
        ContentValues values = new ContentValues();
        values.put(Constants.BARCODE, barcode);
        db.insert(Constants.PRODUCTS_INFO, null, values);
    }

    public void setFoodAdditive(SQLiteDatabase db, String text){
        ContentValues values = new ContentValues();
        values.put(Constants.FOOD_ADDITIVES, text);
        db.insert(Constants.FOOD_ADDITIVES_TABLE, null, values);
    }

    public void setInfoAboutAdditive(SQLiteDatabase db, String text, String text2, String text3, String text4){
        ContentValues values = new ContentValues();

    }

    public void setRelationship(SQLiteDatabase db, int productID, int foodAdditiveID){
        ContentValues values = new ContentValues();
        values.put(Constants.PRODUCT_ID, productID);
        values.put(Constants.FOOD_ADDITIVES_ID, foodAdditiveID);
        db.insert(Constants.PRODUCTS_INFO_AND_FOOD_ADDITIVES_TABLE, null, values);
    }

    public void fillDatabase(SQLiteDatabase db){
        setProduct(db, 4770149204152L); // 1
        setProduct(db, 5014016150821L); // 2
        setProduct(db, 4770118163091L);
        setProduct(db, 4770081165566L);
        setProduct(db, 4820001357288L);
        setProduct(db, 4770299390583L);
        setProduct(db, 4770237041089L);

        setFoodAdditive(db, "E621"); // 1
        setFoodAdditive(db, "E211"); // 2
        setFoodAdditive(db, "E301");
        setFoodAdditive(db, "E450");
        setFoodAdditive(db, "E451");
        setFoodAdditive(db, "E120");
        setFoodAdditive(db, "E250");
        setFoodAdditive(db, "E316");
        setFoodAdditive(db, "E250");
        setFoodAdditive(db, "E202");

        setRelationship(db, 1, 1);
        setRelationship(db, 1, 2);


        //setInfoAboutAdditive(db, 1, "Mononatrio glutamatas", "Aromato ir skonio stipriklis", "Alergija, astma, migrena, vėžiniai susirgimai");
        //setInfoAboutAdditive(db, 2, "Natrio benzoatas", "Konservantas", "Alergija, astma, hiperaktyvumas, vėžiniai susirgimai");
    }
}