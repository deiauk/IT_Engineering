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
    private static final int DB_VERSION = 2;

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

        db.execSQL("CREATE TABLE " + Constants.ADDITIVE_INFO + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Constants.ADDITIVE_ID + " INTEGER, " + Constants.ADDITIVE_FULL_NAME + " TEXT, " + Constants.FUNCTION +
                " TEXT, " + Constants.DISEASES + " TEXT);");


        fillDatabase(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.PRODUCTS_INFO);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.FOOD_ADDITIVES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.PRODUCTS_INFO_AND_FOOD_ADDITIVES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.ADDITIVE_INFO);
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

    /*
      db.execSQL("CREATE TABLE " + Constants.ADDITIVE_INFO + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Constants.ADDITIVE_ID + " INTEGER, " + Constants.ADDITIVE_FULL_NAME + " TEXT, " + Constants.FUNCTION +
                " TEXT, " + Constants.DISEASES + " TEXT);");
     */
    public void setInfoAboutAdditive(SQLiteDatabase db, int id, String fullName, String function, String diseases){
        ContentValues values = new ContentValues();
        values.put(Constants.ADDITIVE_ID, id);
        values.put(Constants.ADDITIVE_FULL_NAME, fullName);
        values.put(Constants.FUNCTION, function);
        values.put(Constants.DISEASES, diseases);
        db.insert(Constants.ADDITIVE_INFO, null, values);
    }

    public void setRelationship(SQLiteDatabase db, int productID, int foodAdditiveID){
        ContentValues values = new ContentValues();
        values.put(Constants.PRODUCT_ID, productID);
        values.put(Constants.FOOD_ADDITIVES_ID, foodAdditiveID);
        db.insert(Constants.PRODUCTS_INFO_AND_FOOD_ADDITIVES_TABLE, null, values);
    }

    public void fillDatabase(SQLiteDatabase db){
        setProduct(db, 4779028061008L); //1
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
        setFoodAdditive(db, "E250"); //28
        setFoodAdditive(db, "E320"); //29
        setFoodAdditive(db, "E432"); //30
        setFoodAdditive(db, "E434"); //31
        setFoodAdditive(db, "E474"); //32
        setFoodAdditive(db, "E483"); //33
        setFoodAdditive(db, "E493"); //34
        setFoodAdditive(db, "E494"); //35
        setFoodAdditive(db, "E495"); //36
        setFoodAdditive(db, "E513"); //37
        setFoodAdditive(db, "E524"); //38
        setFoodAdditive(db, "E525"); //39
        setFoodAdditive(db, "E527"); //40
        setFoodAdditive(db, "E528"); //41
        setFoodAdditive(db, "E576"); //42
        setFoodAdditive(db, "E621"); //43
        setFoodAdditive(db, "E626"); //44
        setFoodAdditive(db, "E628"); //45
        setFoodAdditive(db, "E629"); //46
        setFoodAdditive(db, "E630"); //47
        setFoodAdditive(db, "E632"); //48
        setFoodAdditive(db, "E633"); //49
        setFoodAdditive(db, "E634"); //50
        setFoodAdditive(db, "E635"); //51
        setFoodAdditive(db, "E927b"); //52
        setFoodAdditive(db, "E951"); //53
        setFoodAdditive(db, "E952"); //54
        setFoodAdditive(db, "E954"); //55
        setFoodAdditive(db, "E155"); //56

        setRelationship(db, 1, 42);
        setRelationship(db, 1, 16);
        setRelationship(db, 1, 55);
        setRelationship(db, 1, 27);


        setInfoAboutAdditive(db, 1, "Tartrazinas", "Dažiklis", "Alergija, astma, hiperaktyvumas");//E102
        setInfoAboutAdditive(db, 2, "Chinolino geltonasis", "Dažiklis", "Alergija, astma, hiperaktyvumas");//E104
        setInfoAboutAdditive(db, 3, "Saulėlydžio geltonasis FCF", "Dažiklis", "Alergija, astma, hiperaktyvumas");//E110
        setInfoAboutAdditive(db, 4, "Azorubinas, kamosinas", "Dažiklis", "Alergija, astma, hiperaktyvumas");//E122
        setInfoAboutAdditive(db, 5, "Amarantas", "Dažiklis", "Alergija, astma, vėžiniai susirgimai");//E123
        setInfoAboutAdditive(db, 6, "Ponso 4R, Kochinelas raudonasis A", "Dažiklis", "Alergija, astma, vėžiniai susirgimai");//E124
        setInfoAboutAdditive(db, 7, "Eritrozinas", "Dažiklis", "Alergija, astma, hiperaktyvumas");//E127
        setInfoAboutAdditive(db, 8, "Raudonasis 2G", "Dažiklis", "Alergija, astma");//E128
        setInfoAboutAdditive(db, 9, "Alura raudonasis AC", "Dažiklis", "Astma, alergija, vėžiniai susirgimai");//E129
        setInfoAboutAdditive(db, 10, "Patent mėlynasis V", "Dažiklis", "Astma, alergija");//E131
        setInfoAboutAdditive(db, 11, "Indigotinas, indigokaminas", "Dažiklis", "Alergija, astma");//E132
        setInfoAboutAdditive(db, 12, "Žaliasis S", "Dažiklis", "Alergija, astma, vėžiniai susirgimai");//E142
        setInfoAboutAdditive(db, 13, "Briliantinis juodasis BN, juodasis PN", "Dažiklis", "Alergija, astma");//E151
        setInfoAboutAdditive(db, 14, "Rudasis FK", "Dažiklis", "Alergija, astma");//E154
        setInfoAboutAdditive(db, 15, "Indigotinas, indigokaminas", "Dažiklis", "Alergija, astma");//E180
        setInfoAboutAdditive(db, 16, "Natrio benzoatas", "Konservantas", "Alergija, astma, hiperaktyvumas, vėžiniai susirgimai");//E211
        setInfoAboutAdditive(db, 17, "Etilo p–hidroksibenzoatas", "Konservantas", "Alergija, astma");//E214
        setInfoAboutAdditive(db, 18, "Natrio etilo p–hidroksibenzoatas", "Konservantas", "Alergija, astma");//E215
        setInfoAboutAdditive(db, 19, "Natrio propilo p–hidroksibenzonatas", "Konservantas", "Alergija, astma");//E217
        setInfoAboutAdditive(db, 20, "Natrio metilo p–hidroksibenzonatas", "Konservantas", "Alergija, astma");//E219
        setInfoAboutAdditive(db, 21, "Kalcio sulfitas", "Konservantas, antioksidatorius", "Alergija, astma");//E226
        setInfoAboutAdditive(db, 22, "Kalcio rūgštusis sulfitas", "Konservantas, antioksidatorius", "Alergija, astma");//E227
        setInfoAboutAdditive(db, 23, "Bifenilas", "Konservantas", "Alergija, astma, migrena");//E230
        setInfoAboutAdditive(db, 24, "Ortofenilfenolis", "Konservantas", "Alergija, astma");//E231
        setInfoAboutAdditive(db, 25, "Tiabendazolas", "Konservantas", "Alergija, astma");//E233
        setInfoAboutAdditive(db, 26, "Heksametilentetraminas", "Konservantas", "Alergija, astma");//E239
        setInfoAboutAdditive(db, 27, "Kalio nitritas", "Konservantas", "Alergija, astma, migrena, vėžiniai susirgimai");//E249
        setInfoAboutAdditive(db, 28, "Natrio nitritas", "Konservantas", "Alergija, astma, migrena, vėžiniai susirgimai");//E250
        setInfoAboutAdditive(db, 29, "Butilintas hidrokisanizolas (BHA)", "Antioksidatorius", "Alergija, astma, mutageniškumas");//E320
        setInfoAboutAdditive(db, 30, "Polioksietileno sorbitano monolauratas   (polisorbatas 20)", "Emulsiklis", "Alergija, astma");//E432
        setInfoAboutAdditive(db, 31, "Polioksietileno sorbitano monopalmitatas (polisorbatas 40)", "Emulsiklis", "Alergija, astma");//E434
        setInfoAboutAdditive(db, 32, "Sacharozės gliceridai", "Emulsiklis", "Alergija, astma");//E474
        setInfoAboutAdditive(db, 33, "Stearilo tartratas", "Miltų apdorojimo medžiaga", "Alergija, astma");//E483
        setInfoAboutAdditive(db, 34, "Sorbitano monolauratas", "Emulsiklis", "Alergija, astma");//E493
        setInfoAboutAdditive(db, 35, "Sorbitano monoleatas", "Emulsiklis", "Alergija, astma");//E494
        setInfoAboutAdditive(db, 36, "Sorbitano monopalmitatas", "Emulsiklis", "Alergija, astma");//E495
        setInfoAboutAdditive(db, 37, "Sulfato rūgštis", "Rūgštingumą reguliuojanti medžiaga", "Alergija, astma");//E513
        setInfoAboutAdditive(db, 38, "Natrio hidroksidas", "Rūgštingumą reguliuojanti medžiaga", "Alergija, astma");//E524
        setInfoAboutAdditive(db, 39, "Kalio hidroksidas", "Rūgštingumą reguliuojanti medžiaga", "Alergija, astma");//E525
        setInfoAboutAdditive(db, 40, "Amonio hidroksidas", "Rūgštingumą reguliuojanti medžiaga", "Alergija, astma");//E527
        setInfoAboutAdditive(db, 41, "Magnio hidroksidas", "Rūgštingumą reguliuojanti medžiaga", "Alergija, astma");//E528
        setInfoAboutAdditive(db, 42, "Natrio gliukonatas", "Antioksidatorius", "Alergija, astma");//E576
        setInfoAboutAdditive(db, 43, "Mononatrio glutamatas", "Aromato ir skonio stipriklis", "Alergija, astma, migrena, vėžiniai susirgimai");//E621
        setInfoAboutAdditive(db, 44, "Guanilo rūgštis", "Aromato ir skonio stipriklis", "Alergija, astma");//E626
        setInfoAboutAdditive(db, 45, "Dikalio guanilatas", "Aromato ir skonio stipriklis", "Alergija, astma");//E628
        setInfoAboutAdditive(db, 46, "Kalcio guanilatas", "Aromato ir skonio stipriklis", "Alergija, astma");//E629
        setInfoAboutAdditive(db, 47, "Inozino rūgštis", "Aromato ir skonio stipriklis", "Alergija, astma");//E630
        setInfoAboutAdditive(db, 48, "Dikalio inozinatas", "Aromato ir skonio stipriklis", "Alergija, astma");//E632
        setInfoAboutAdditive(db, 49, "Kalcio inozinatas", "Aromato ir skonio stipriklis", "Alergija, astma");//E633
        setInfoAboutAdditive(db, 50, "Kalcio 5‘-ribonukleotidai", "Aromato ir skonio stipriklis", "Alergija, astma");//E634
        setInfoAboutAdditive(db, 51, "Dinatrio 5‘-ribonukleotidai", "Aromato ir skonio stipriklis", "Alergija, astma");//E635
        setInfoAboutAdditive(db, 52, "Karbamidas", "Miltų apdorojimo medžiaga", "Alergija, astma");//E927b
        setInfoAboutAdditive(db, 53, "Aspartamas", "Saldiklis", "Alergija, astma, migrena");//E951
        setInfoAboutAdditive(db, 54, "Ciklamo rūgštis, Kalcio ciklamatas,  Natrio ciklamatas", "Saldiklis", "Alergija, astma");//E952
        setInfoAboutAdditive(db, 55, "Sacharinas, Kalcio sacharinas, Kalio sacharinas, Natrio sacharinas", "Saldiklis", "Alergija, astma");//E954
        setInfoAboutAdditive(db, 56, "Rudasis HT", "Dažiklis", "Alergija, astma, hiperaktyvumas");//E155
    }
}