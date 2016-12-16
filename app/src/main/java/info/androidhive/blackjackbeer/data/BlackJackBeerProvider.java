package info.androidhive.blackjackbeer.data;

import android.content.ContentProvider;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import info.androidhive.blackjackbeer.data.BlackJackBeerContract;

/**
 * Created by Marinete-Cedmar on 05/12/2016.
 */
public class BlackJackBeerProvider extends ContentProvider {
    // The URI Matcher used by this content provider.
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private BlackJackBeerDbHelper mOpenHelper;

    public static final int PRODUCT = 100;
    public static final int PRODUCT_WITH_BOUGHT = 101;
    public static final int PRODUCT_WITH_ID = 102;

    public static final int USER = 200;
    public static final int USER_WITH_BOUGHT = 201;

    public static final int BOUGHT = 300;
    public static final int BOUGHT_WITH_PRODUCT = 301;
    public static final int BOUGHT_WITH_USER = 302;
    public static final int BOUGHT_WITH_ID = 302;

    public static final int CODE = 400;
    public static final int CODE_WITH_BOUGHT = 401;

    private static final SQLiteQueryBuilder sProductByBoughtQueryBuilder;
    private static final SQLiteQueryBuilder sProductByIdQueryBuilder;

    private static final SQLiteQueryBuilder sUserByBoughtQueryBuilder;

    private static final SQLiteQueryBuilder sBoughtByProductQueryBuilder;

    private static final SQLiteQueryBuilder sBoughtByUserQueryBuilder;

    static{
        sBoughtByProductQueryBuilder = new SQLiteQueryBuilder();

        //This is an inner join which looks like
        //weather INNER JOIN location ON weather.location_id = location._id
        sBoughtByProductQueryBuilder.setTables(
                BlackJackBeerContract.BoughtEntry.TABLE_NAME + " INNER JOIN " +
                        BlackJackBeerContract.ProductEntry.TABLE_NAME +
                        " ON " + BlackJackBeerContract.BoughtEntry.TABLE_NAME +
                        "." + BlackJackBeerContract.BoughtEntry.COLUMN_PRODUCT_ID +
                        " = " + BlackJackBeerContract.ProductEntry.TABLE_NAME +
                        "." + BlackJackBeerContract.ProductEntry._ID);
    }

    static{
        sBoughtByUserQueryBuilder = new SQLiteQueryBuilder();

        //This is an inner join which looks like
        //weather INNER JOIN location ON weather.location_id = location._id
        sBoughtByUserQueryBuilder.setTables(
                BlackJackBeerContract.BoughtEntry.TABLE_NAME + " INNER JOIN " +
                        BlackJackBeerContract.UserEntry.TABLE_NAME +
                        " ON " + BlackJackBeerContract.BoughtEntry.TABLE_NAME +
                        "." + BlackJackBeerContract.BoughtEntry.COLUMN_PRODUCT_ID +
                        " = " + BlackJackBeerContract.UserEntry.TABLE_NAME +
                        "." + BlackJackBeerContract.UserEntry._ID);
    }

    static{
        sProductByBoughtQueryBuilder = new SQLiteQueryBuilder();

        //This is an inner join which looks like
        //weather INNER JOIN location ON weather.location_id = location._id
        sProductByBoughtQueryBuilder.setTables(
                BlackJackBeerContract.ProductEntry.TABLE_NAME + " INNER JOIN " +
                        BlackJackBeerContract.BoughtEntry.TABLE_NAME +
                        " ON " + BlackJackBeerContract.ProductEntry.TABLE_NAME +
                        "." + BlackJackBeerContract.ProductEntry._ID +
                        " = " + BlackJackBeerContract.BoughtEntry.TABLE_NAME +
                        "." + BlackJackBeerContract.BoughtEntry.COLUMN_PRODUCT_ID);
    }

    static{
        sUserByBoughtQueryBuilder = new SQLiteQueryBuilder();

        //This is an inner join which looks like
        //weather INNER JOIN location ON weather.location_id = location._id
        sUserByBoughtQueryBuilder.setTables(
                BlackJackBeerContract.UserEntry.TABLE_NAME + " INNER JOIN " +
                        BlackJackBeerContract.BoughtEntry.TABLE_NAME +
                        " ON " + BlackJackBeerContract.UserEntry.TABLE_NAME +
                        "." + BlackJackBeerContract.UserEntry._ID +
                        " = " + BlackJackBeerContract.BoughtEntry.TABLE_NAME +
                        "." + BlackJackBeerContract.BoughtEntry.COLUMN_USER_ID);
    }

    static{
        sProductByIdQueryBuilder = new SQLiteQueryBuilder();

        //This is an inner join which looks like
        //weather INNER JOIN location ON weather.location_id = location._id
        sProductByIdQueryBuilder.setTables(
                BlackJackBeerContract.ProductEntry.TABLE_NAME
        );
    }

    //location.location_setting = ?
    private static final String sAssessementTopicSelection =
            BlackJackBeerContract.ProductEntry.TABLE_NAME+
                    "." + BlackJackBeerContract.ProductEntry.COLUMN_NAME +  " = ? ";


    private static final String sProductIdSelection =
            BlackJackBeerContract.ProductEntry.TABLE_NAME+
                    "." + BlackJackBeerContract.ProductEntry._ID +  " = ? ";



    //location.location_setting = ? AND date >= ?
    /*private static final String sLocationSettingWithStartDateSelection =
            RateUrMateContract.AssessementEntry.TABLE_NAME+
                    "." + RateUrMateContract.AssessementEntry..COLUMN_LOCATION_SETTING + " = ? AND " +
                    WeatherContract.WeatherEntry.COLUMN_DATE + " >= ? ";
                    */

    //location.location_setting = ? AND date = ?
  /*  private static final String sLocationSettingAndDaySelection =
            WeatherContract.LocationEntry.TABLE_NAME +
                    "." + WeatherContract.LocationEntry.COLUMN_LOCATION_SETTING + " = ? AND " +
                    WeatherContract.WeatherEntry.COLUMN_DATE + " = ? ";*/
    //TODO MEIO BGADO ISSO AKI VC PEGA O PRODUTO PELA COMPRA E NAO A COMPRA PELO PRODUTO ?!
    private Cursor getBoughtByProduct(Uri uri, String[] projection, String sortOrder) {
        String product_id = BlackJackBeerContract.ProductEntry.getProductFromUri(uri);

        String selection = sProductIdSelection;
        String[] selectionArgs = new String[]{product_id};

        return sBoughtByProductQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                sProductIdSelection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }
    private Cursor getProductById(Uri uri, String[] projection, String sortOrder) {
        String product_id = BlackJackBeerContract.ProductEntry.getProductFromUri(uri);

        String selection = sProductIdSelection;
        String[] selectionArgs = new String[]{product_id};

        return sProductByIdQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                sProductIdSelection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }


    private Cursor getProductByBought(Uri uri, String[] projection, String sortOrder) {
        String product_id = BlackJackBeerContract.ProductEntry.getProductFromUri(uri);

        String selection = sProductIdSelection;
        String[] selectionArgs = new String[]{product_id};

        return sProductByBoughtQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                sProductIdSelection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }


    public static UriMatcher buildUriMatcher() {
        // I know what you're thinking.  Why create a UriMatcher when you can use regular
        // expressions instead?  Because you're not crazy, that's why.

        // All paths added to the UriMatcher have a corresponding code to return when a match is
        // found.  The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = BlackJackBeerContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, BlackJackBeerContract.PATH_PRODUCT, PRODUCT);
        matcher.addURI(authority, BlackJackBeerContract.PATH_PRODUCT + "/#", PRODUCT_WITH_BOUGHT);
        matcher.addURI(authority, BlackJackBeerContract.PATH_PRODUCT + "/#", PRODUCT_WITH_ID);
        // matcher.addURI(authority, WeatherContract.PATH_WEATHER + "/*", WEATHER_WITH_LOCATION);
        //matcher.addURI(authority, WeatherContract.PATH_WEATHER + "/*/#", WEATHER_WITH_LOCATION_AND_DATE);

        matcher.addURI(authority, BlackJackBeerContract.PATH_BOUGHT, BOUGHT);
        matcher.addURI(authority, BlackJackBeerContract.PATH_BOUGHT + "/#", BOUGHT_WITH_PRODUCT);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new BlackJackBeerDbHelper(getContext());
        return true;
    }

    /*
        Students: Here's where you'll code the getType function that uses the UriMatcher.  You can
        test this by uncommenting testGetType in TestProvider.
     */
    @Override
    public String getType(Uri uri) {

        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {
            // Student: Uncomment and fill out these two cases
            case PRODUCT:
                return BlackJackBeerContract.ProductEntry.CONTENT_TYPE;
            case BOUGHT:
                return BlackJackBeerContract.BoughtEntry.CONTENT_TYPE;
            case USER:
                return BlackJackBeerContract.UserEntry.CONTENT_TYPE;
            case CODE:
                return BlackJackBeerContract.CodeEntry.CONTENT_TYPE;
            case BOUGHT_WITH_PRODUCT:
                return BlackJackBeerContract.BoughtEntry.CONTENT_TYPE;
        }
        throw new UnsupportedOperationException("Unknown uri: " + uri);

    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Cursor retCursor;
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case PRODUCT_WITH_ID:
            {
                retCursor = getProductById(uri, projection, sortOrder);
                break;
            }

            case BOUGHT_WITH_PRODUCT:
            {
                //retCursor = getBoughtByProduct(uri, projection, sortOrder);
                retCursor = sBoughtByProductQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }

            case PRODUCT: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        BlackJackBeerContract.ProductEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            case BOUGHT: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        BlackJackBeerContract.BoughtEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            case USER: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        BlackJackBeerContract.UserEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            case CODE: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        BlackJackBeerContract.CodeEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    /*
        Student: Add the ability to insert Locations to the implementation of this function.
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case PRODUCT: {
                //normalizeDate(values);
                long _id = db.insert(BlackJackBeerContract.ProductEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = (BlackJackBeerContract.ProductEntry.buildProductUri(_id));
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case BOUGHT: {
                long _id = db.insert(BlackJackBeerContract.BoughtEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = BlackJackBeerContract.BoughtEntry.buildBoughtUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case USER: {
                long _id = db.insert(BlackJackBeerContract.UserEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = BlackJackBeerContract.UserEntry.buildUserUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case CODE: {
                long _id = db.insert(BlackJackBeerContract.CodeEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = BlackJackBeerContract.CodeEntry.buildCodeUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        db.close();
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if ( null == selection ) selection = "1";
        switch (match) {
            case BOUGHT:
                rowsDeleted = db.delete(
                        BlackJackBeerContract.BoughtEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case PRODUCT:
                rowsDeleted = db.delete(
                        BlackJackBeerContract.ProductEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case USER:
                rowsDeleted = db.delete(
                        BlackJackBeerContract.UserEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case CODE:
                rowsDeleted = db.delete(
                        BlackJackBeerContract.CodeEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

 /*   private void normalizeDate(ContentValues values) {
        // normalize the date value
        if (values.containsKey(WeatherContract.WeatherEntry.COLUMN_DATE)) {
            long dateValue = values.getAsLong(WeatherContract.WeatherEntry.COLUMN_DATE);
            values.put(WeatherContract.WeatherEntry.COLUMN_DATE, WeatherContract.normalizeDate(dateValue));
        }
    }*/

    @Override
    public int update(
            Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case BOUGHT:
                //  normalizeDate(values);
                rowsUpdated = db.update(BlackJackBeerContract.BoughtEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case PRODUCT:
                rowsUpdated = db.update(BlackJackBeerContract.ProductEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case USER:
                rowsUpdated = db.update(BlackJackBeerContract.UserEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case CODE:
                rowsUpdated = db.update(BlackJackBeerContract.CodeEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

}
