package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import barcode2.android.com.barcode.Constants;

/**
 * Created by Deividas on 2015-10-07.
 */
public class InnerDatabase extends SQLiteOpenHelper {

    private static final String DB_NAME = "PRODUCTS_DATABASE";
    public static final int DB_VERSION = 1;

    public InnerDatabase(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Constants.PRODUCTS_INFO +
                " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + Constants.BARCODE + " TEXT);");

        db.execSQL("CREATE TABLE " +
                Constants.FOOD_ADDITIVES_TABLE + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Constants.FOOD_ADDITIVES + " TEXT);");

        db.execSQL("CREATE TABLE " + Constants.PRODUCTS_INFO_AND_FOOD_ADDITIVES_TABLE +
                " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + Constants.PRODUCT_ID + " INTEGER, " +
                Constants.FOOD_ADDITIVES_ID + " INTEGER);");

        db.execSQL("CREATE TABLE " + Constants.ADDITIVE_TYPE + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Constants.TYPE + " TEXT);");

        db.execSQL("CREATE TABLE " + Constants.DANGEROUS_TABLE + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Constants.IS_DANGEROUS + " TEXT);");

        db.execSQL("CREATE TABLE " + Constants.FULL_INFO + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Constants.ADDITIVE_ID + " INTEGER, " + Constants.ADDITIVE_FULL_NAME + " TEXT, " +
                Constants.CATEGORY_ID + " INTEGER, " + Constants.IS_DANGEROUS_ID + " INTEGER, " +
                Constants.ABOUT_ADDITIVE + " TEXT, " + Constants.USAGE + " TEXT);");

        db.execSQL("CREATE TABLE " + Constants.BARCODE_TABLE + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Constants.BARCODE + " TEXT, " + Constants.DATE_COLUMN + " DATETIME);");

        fillDatabase(db);
    }

    public void setAllInfo(SQLiteDatabase db, int additive_ID, String full_name, int category_ID,
                           int dangerous_ID,  String about, String usage){
        ContentValues values = new ContentValues();
        values.put(Constants.ADDITIVE_ID, additive_ID);
        values.put(Constants.ADDITIVE_FULL_NAME, full_name);
        values.put(Constants.CATEGORY_ID, category_ID);
        values.put(Constants.IS_DANGEROUS_ID, dangerous_ID);
        values.put(Constants.ABOUT_ADDITIVE, about);
        values.put(Constants.USAGE, usage);
        db.insert(Constants.FULL_INFO, null, values);

    }

    public void setDangerousTable(SQLiteDatabase db, String danger){
        if(getDangerousID(db, danger) == -1) {
            ContentValues values = new ContentValues();
            values.put(Constants.IS_DANGEROUS, danger);
            db.insert(Constants.DANGEROUS_TABLE, null, values);
        }
    }

    public void setAdditiveType(SQLiteDatabase db, String additive){
        if(getAdditiveTypeID(db, additive) == -1) {
            ContentValues values = new ContentValues();
            values.put(Constants.TYPE, additive);
            db.insert(Constants.ADDITIVE_TYPE, null, values);
        }
    }

    public int getDangerousID(SQLiteDatabase db, String danger){
        int id = -1;
        String query = "SELECT _id FROM " + Constants.DANGEROUS_TABLE + " WHERE " + Constants.IS_DANGEROUS + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{danger});
        if(cursor.moveToFirst()){
            id = cursor.getInt(0);
        }
        cursor.close();
        return id;
    }

    public int getAdditiveTypeID(SQLiteDatabase db, String additive){
        int id = -1;
        String query = "SELECT _id FROM " + Constants.ADDITIVE_TYPE + " WHERE " + Constants.TYPE + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{additive});
        if(cursor.moveToFirst()){
            id = cursor.getInt(0);
        }
        cursor.close();
        return id;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.PRODUCTS_INFO);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.FOOD_ADDITIVES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.PRODUCTS_INFO_AND_FOOD_ADDITIVES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.ADDITIVE_TYPE);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.DANGEROUS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.FULL_INFO);

        onCreate(db);
    }

    public void setProduct(SQLiteDatabase db, String barcode){
        ContentValues values = new ContentValues();
        values.put(Constants.BARCODE, barcode);
        db.insert(Constants.PRODUCTS_INFO, null, values);
    }

    public int execute(String barcode, SQLiteDatabase database){
        try {
            return new DatabaseSearcher(database).execute(barcode).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void setFoodAdditive(SQLiteDatabase db, String text){
        ContentValues values = new ContentValues();
        values.put(Constants.FOOD_ADDITIVES, text);
        db.insert(Constants.FOOD_ADDITIVES_TABLE, null, values);
    }

    public int getFoodAdditiveID(SQLiteDatabase db, String text){
        int id = -1;
        String query = "SELECT _id FROM " + Constants.FOOD_ADDITIVES_TABLE + " WHERE " +
                Constants.FOOD_ADDITIVES + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{text});
        if(cursor.moveToFirst()){
            id = cursor.getInt(0);
        }
        cursor.close();
        return id;
    }

    public void setRelationship(SQLiteDatabase db, int productID, int foodAdditiveID){
        ContentValues values = new ContentValues();
        values.put(Constants.PRODUCT_ID, productID);
        values.put(Constants.FOOD_ADDITIVES_ID, foodAdditiveID);
        db.insert(Constants.PRODUCTS_INFO_AND_FOOD_ADDITIVES_TABLE, null, values);
    }

    public void addToAlreadyScannedTable(String barcode, SQLiteDatabase db, int howManyItems){
        if(howManyItems == 0) {
            ContentValues values = new ContentValues();
            values.put(Constants.BARCODE, barcode);
            values.put(Constants.DATE_COLUMN, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
                    format(new Date()));
            db.insert(Constants.BARCODE_TABLE, null, values);
        }else{
            ContentValues values = new ContentValues();
            values.put(Constants.DATE_COLUMN, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
                    format(new Date()));
            db.update(Constants.BARCODE_TABLE, values, Constants.BARCODE + " =?", new String[]{barcode});
        }
    }

    public void fillDatabase(SQLiteDatabase db){
        setProduct(db, "1234567890128"); //1
        setProduct(db, "5014016150821"); //2
        setProduct(db, "4770118163091"); //3
        setProduct(db, "4770081165566"); //4
        setProduct(db, "4820001357288"); //5
        setProduct(db, "4770299390583"); //6
        setProduct(db, "4770237041089"); //7

        setRelationship(db, 1, 42);
        setRelationship(db, 1, 16);

        for(int i=1; i<50; i++){
            setRelationship(db, 2, i);
        }
    }

    public class DatabaseSearcher extends AsyncTask<String, Void, Integer>{

        private SQLiteDatabase db;

        public DatabaseSearcher(SQLiteDatabase d){
            db = d;
        }

        @Override
        protected Integer doInBackground(String... strings) {
            String query = "SELECT _id FROM " + Constants.BARCODE_TABLE + " WHERE  "+
                    Constants.BARCODE + " =?";
            Cursor cursor = db.rawQuery(query, new String[]{strings[0]});
            return cursor.getCount();
        }
    }
}