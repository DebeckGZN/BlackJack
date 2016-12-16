package info.androidhive.blackjackbeer.data;

import android.net.Uri;
import android.provider.BaseColumns;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.text.format.Time;
/**
 * Created by Marinete-Cedmar on 03/12/2016.
 */
public class BlackJackBeerContract {

    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = "info.androidhive.blackjackbeer.app";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    // For instance, content://com.example.android.sunshine.app/weather/ is a valid path for
    // looking at weather data. content://com.example.android.sunshine.app/givemeroot/ will fail,
    // as the ContentProvider hasn't been given any information on what to do with "givemeroot".
    // At least, let's hope not.  Don't be that dev, reader.  Don't be that dev.
    public static final String PATH_USER = "user";
    public static final String PATH_PRODUCT = "product";
    public static final String PATH_BOUGHT = "bought";
    public static final String PATH_CODE = "code";

    // To make it easy to query for the exact date, we normalize all dates that go into
    // the database to the start of the the Julian day at UTC.
    public static long normalizeDate(long startDate) {
        // normalize the start date to the beginning of the (UTC) day
        Time time = new Time();
        time.set(startDate);
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);
        return time.setJulianDay(julianDay);
    }

    public static final class ProductEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PRODUCT).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCT;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCT;


        public static final String TABLE_NAME = "product";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_CATEGORY = "category";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_DISPONIBILITY = "disponibility";
        public static final String COLUMN_IMAGE = "image";


        public static Uri buildProductUri() {
            return (CONTENT_URI);
        }

        public static Uri buildProductUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static String getProductFromUri(Uri uri) {
            return (uri.getPathSegments().get(1));
        }

    }

    public static final class UserEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_USER).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER;


        public static final String TABLE_NAME = "user";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_RG = "rg";
        public static final String COLUMN_BIRTHDAY = "birthday";
        public static final String COLUMN_FUNDS = "funds";


        public static Uri buildUserUri() {
            return (CONTENT_URI);
        }

        public static Uri buildUserUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static String getUserFromUri(Uri uri) {
            return (uri.getPathSegments().get(1));
        }

    }

    public static final class BoughtEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_BOUGHT).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOUGHT;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOUGHT;


        public static final String TABLE_NAME = "bought";

        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_PRODUCT_ID = "product_id";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_STATUS = "status";
        public static final String QUANTITY = "quantity";

        public static Uri buildBoughtUri() {
            return (CONTENT_URI);
        }

        public static Uri buildBoughtUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildBoughtProduct(Long product_id) {
            return CONTENT_URI.buildUpon().appendPath(String.valueOf(product_id)).build();
        }

        public static Uri buildBoughtUser(Long user_id) {
            return CONTENT_URI.buildUpon().appendPath(String.valueOf(user_id)).build();
        }

        public static Uri buildBoughtStatusWithProductName() {
            return CONTENT_URI;
        }

        public static String getBoughtFromUri(Uri uri) {
            return (uri.getPathSegments().get(1));
        }

    }

    public static final class CodeEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CODE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CODE;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CODE;


        public static final String TABLE_NAME = "code";

        public static final String COLUMN_QR_CODE = "qr_code";
        public static final String COLUMN_BOUGHT_ID = "bought_id";


        public static Uri buildCodeUri() {
            return (CONTENT_URI);
        }

        public static Uri buildCodeUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildCodeBought(Long bought_id) {
            return CONTENT_URI.buildUpon().appendPath(String.valueOf(bought_id)).build();
        }

        public static String getCodeFromUri(Uri uri) {
            return (uri.getPathSegments().get(1));
        }

    }

}
