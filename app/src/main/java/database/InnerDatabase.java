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
        setProduct(db, 4770149204152L); //1
        setProduct(db, 5014016150821L); //2
        setProduct(db, 4770118163091L); //3
        setProduct(db, 4770081165566L); //4
        setProduct(db, 4820001357288L); //5
        setProduct(db, 4770299390583L); //6
        setProduct(db, 4770237041089L); //7

        setFoodAdditive(db, "E102"); //1
        setFoodAdditive(db, "E104"); //2
        setFoodAdditive(db, "E110"); //3
        setFoodAdditive(db, "E122"); //4
        setFoodAdditive(db, "E123"); //5
        setFoodAdditive(db, "E124"); //6
        setFoodAdditive(db, "E127"); //7
        setFoodAdditive(db, "E128"); //8
        setFoodAdditive(db, "E129"); //9
        setFoodAdditive(db, "E131"); //10
        setFoodAdditive(db, "E132"); //11
        setFoodAdditive(db, "E142"); //12
        setFoodAdditive(db, "E151"); //13
        setFoodAdditive(db, "E154"); //14
        setFoodAdditive(db, "E180"); //15
        setFoodAdditive(db, "E211"); //16
        setFoodAdditive(db, "E214"); //17
        setFoodAdditive(db, "E215"); //18
        setFoodAdditive(db, "E217"); //19
        setFoodAdditive(db, "E219"); //20
        setFoodAdditive(db, "E226"); //21
        setFoodAdditive(db, "E227"); //22
        setFoodAdditive(db, "E230"); //23
        setFoodAdditive(db, "E231"); //24
        setFoodAdditive(db, "E233"); //25
        setFoodAdditive(db, "E239"); //26
        setFoodAdditive(db, "E249"); //27
        setFoodAdditive(db, "E320"); //28
        setFoodAdditive(db, "E432"); //29
        setFoodAdditive(db, "E434"); //30
        setFoodAdditive(db, "E474"); //31
        setFoodAdditive(db, "E483"); //32
        setFoodAdditive(db, "E493"); //33
        setFoodAdditive(db, "E494"); //34
        setFoodAdditive(db, "E495"); //35
        setFoodAdditive(db, "E513"); //36
        setFoodAdditive(db, "E524"); //37
        setFoodAdditive(db, "E525"); //38
        setFoodAdditive(db, "E527"); //39
        setFoodAdditive(db, "E528"); //40
        setFoodAdditive(db, "E576"); //41
        setFoodAdditive(db, "E621"); //42
        setFoodAdditive(db, "E626"); //43
        setFoodAdditive(db, "E628"); //44
        setFoodAdditive(db, "E629"); //45
        setFoodAdditive(db, "E630"); //46
        setFoodAdditive(db, "E632"); //47
        setFoodAdditive(db, "E633"); //48
        setFoodAdditive(db, "E634"); //49
        setFoodAdditive(db, "E635"); //50
        setFoodAdditive(db, "E927b"); //51
        setFoodAdditive(db, "E951"); //52
        setFoodAdditive(db, "E952"); //53
        setFoodAdditive(db, "E954"); //54

        setRelationship(db, 1, 42);
        setRelationship(db, 1, 16);


        //setInfoAboutAdditive(db, 1, "Mononatrio glutamatas", "Aromato ir skonio stipriklis", "Alergija, astma, migrena, vėžiniai susirgimai");
        //setInfoAboutAdditive(db, 2, "Natrio benzoatas", "Konservantas", "Alergija, astma, hiperaktyvumas, vėžiniai susirgimai");
    }
}