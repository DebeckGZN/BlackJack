package info.androidhive.blackjackbeer.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Marinete-Cedmar on 03/12/2016.
 */
public class BlackJackBeerDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 7;

    public static final String DATABASE_NAME = "blackJackB.db";

    public BlackJackBeerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_PRODUCT_TABLE = "CREATE TABLE " + BlackJackBeerContract.ProductEntry.TABLE_NAME + " (" +
                BlackJackBeerContract.ProductEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + //TODO AUTOINCREMENT?
                BlackJackBeerContract.ProductEntry.COLUMN_NAME + " TEXT UNIQUE NOT NULL, " + //TODO VERIFICAR SYNTAXE DESSA LINHA
                BlackJackBeerContract.ProductEntry.COLUMN_PRICE + " REAL NOT NULL, " +
                BlackJackBeerContract.ProductEntry.COLUMN_CATEGORY + " TEXT NOT NULL, " +
                BlackJackBeerContract.ProductEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                BlackJackBeerContract.ProductEntry.COLUMN_DISPONIBILITY + " INTEGER NOT NULL, " + //TODO IDEAL UM BOOLEAN
                BlackJackBeerContract.ProductEntry.COLUMN_IMAGE + " TEXT NOT NULL " + //TODO TEM QUE SER IMAGEM
                " );";

        final String SQL_CREATE_USER_TABLE = "CREATE TABLE " + BlackJackBeerContract.UserEntry.TABLE_NAME + " (" +
                BlackJackBeerContract.UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + //TODO AUTOINCREMENT?
                BlackJackBeerContract.UserEntry.COLUMN_NAME + " TEXT UNIQUE NOT NULL, " + //TODO VERIFICAR SYNTAXE DESSA LINHA
                BlackJackBeerContract.UserEntry.COLUMN_EMAIL + " TEXT NOT NULL, " +
                BlackJackBeerContract.UserEntry.COLUMN_PHONE + " TEXT UNIQUE NOT NULL, " + //TODO STRING?
                BlackJackBeerContract.UserEntry.COLUMN_PASSWORD + " TEXT NOT NULL, " + //TODO STRING?
                BlackJackBeerContract.UserEntry.COLUMN_RG + " TEXT UNIQUE NOT NULL, " + //TODO STRING?
                BlackJackBeerContract.UserEntry.COLUMN_BIRTHDAY + " TEXT NOT NULL, " +
                BlackJackBeerContract.UserEntry.COLUMN_FUNDS + " REAL NOT NULL " +
                " );";

        final String SQL_CREATE_BOUGHT_TABLE = "CREATE TABLE " + BlackJackBeerContract.BoughtEntry.TABLE_NAME + " (" +
                BlackJackBeerContract.BoughtEntry._ID + " INTEGER PRIMARY KEY," + //TODO AUTOINCREMENT?

                // CHAVE ESTRANGEIRA DE USER
                BlackJackBeerContract.BoughtEntry.COLUMN_USER_ID + " INTEGER NOT NULL, " +
                // CHAVE ESTRANGEIRA DE PRODUCT
                BlackJackBeerContract.BoughtEntry.COLUMN_PRODUCT_ID + " INTEGER NOT NULL, " +

                BlackJackBeerContract.BoughtEntry.COLUMN_DATE + " TEXT NOT NULL, " + //TODO DATA É REALMENTE STRING?
                BlackJackBeerContract.BoughtEntry.COLUMN_TIME + " INTEGER NOT NULL, " +
                BlackJackBeerContract.BoughtEntry.COLUMN_STATUS + " INTEGER NOT NULL, " +//TODO OU STRING AINDA NAO DECIDI
                BlackJackBeerContract.BoughtEntry.QUANTITY + " INTEGER NOT NULL, " +

                " FOREIGN KEY (" +  BlackJackBeerContract.BoughtEntry.COLUMN_USER_ID + ") REFERENCES " +
                BlackJackBeerContract.UserEntry.COLUMN_NAME + " (" + BlackJackBeerContract.UserEntry._ID + "), " +

                " FOREIGN KEY (" +  BlackJackBeerContract.BoughtEntry.COLUMN_PRODUCT_ID + ") REFERENCES " +
                BlackJackBeerContract.ProductEntry.COLUMN_NAME + " (" + BlackJackBeerContract.ProductEntry._ID +
                ")); " ;

        final String SQL_CREATE_CODE_TABLE = "CREATE TABLE " + BlackJackBeerContract.CodeEntry.TABLE_NAME + " (" +
                BlackJackBeerContract.CodeEntry._ID + " INTEGER PRIMARY KEY," +
                BlackJackBeerContract.CodeEntry.COLUMN_QR_CODE + " INTEGER UNIQUE NOT NULL, " + //TODO VERIFICAR SYNTAXE DESSA LINHA
                // CHAVE ESTRANGEIRA DE BOUGH
                BlackJackBeerContract.CodeEntry.COLUMN_BOUGHT_ID + " INTEGER NOT NULL, " +

                " FOREIGN KEY (" +  BlackJackBeerContract.CodeEntry.COLUMN_BOUGHT_ID + ") REFERENCES " +
                BlackJackBeerContract.BoughtEntry.TABLE_NAME + " (" + BlackJackBeerContract.BoughtEntry._ID +
                " ));";

        sqLiteDatabase.execSQL(SQL_CREATE_PRODUCT_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_BOUGHT_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_CODE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BlackJackBeerContract.CodeEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BlackJackBeerContract.BoughtEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BlackJackBeerContract.ProductEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BlackJackBeerContract.UserEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
}
