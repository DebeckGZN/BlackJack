package info.androidhive.blackjackbeer.activity;

import android.app.Dialog;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.content.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import info.androidhive.blackjackbeer.R;
import info.androidhive.blackjackbeer.data.BlackJackBeerContract;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settings = new Intent(this, SettingsActivity.class);
            startActivity(settings);
            return true;
        }
        if (id == R.id.action_refresh){
            Toast.makeText(this, "Dados Atualizados!", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

        private static final int DETAIL_LOADER = 0;

        private static final String[] PRODUCT_COLUMNS = {
                BlackJackBeerContract.ProductEntry.TABLE_NAME + "." + BlackJackBeerContract.ProductEntry._ID,
                BlackJackBeerContract.ProductEntry.COLUMN_NAME,//PERMANECE
                BlackJackBeerContract.ProductEntry.COLUMN_PRICE,
                BlackJackBeerContract.ProductEntry.COLUMN_CATEGORY,
                BlackJackBeerContract.ProductEntry.COLUMN_DESCRIPTION,
                BlackJackBeerContract.ProductEntry.COLUMN_DISPONIBILITY,
                BlackJackBeerContract.ProductEntry.COLUMN_IMAGE
        };

        static final int COL_PRODUCT_ID = 0;
        static final int COLUMN_NAME = 1;
        static final int COLUMN_PRICE = 2;
        static final int COLUMN_CATEGORY = 3;
        static final int COLUMN_DESCRIPTION = 4;
        static final int COLUMN_DISPONIBILITY = 5;
        static final int COLUMN_IMAGE = 6;

        public PlaceholderFragment() {
        }

        static long id;
        static final int status = 0;
        static String dialogName;
        static String dialogPrice;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            Button comprar = (Button) rootView.findViewById(R.id.buttonComprar);
            comprar.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    new ConfirmBoxDialog().show(getFragmentManager(),"");

                }

                });

        return rootView;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            getLoaderManager().initLoader(DETAIL_LOADER, null, this);
            super.onActivityCreated(savedInstanceState);
        }


        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {

            Intent intent = getActivity().getIntent();
            if (intent == null)
                return null;
            else return new CursorLoader(
                    getActivity(),
                    intent.getData(),
                    PRODUCT_COLUMNS,
                    null,
                    null,
                    null
            );

        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

            if (!data.moveToFirst()) return;
            TextView textViewDetail = (TextView) getView().findViewById(R.id.textViewDetailName);
            TextView textViewDescription = (TextView) getView().findViewById(R.id.textViewDescription);
            TextView textViewPreco = (TextView) getView().findViewById(R.id.textViewDetailPrice);
            ImageView imageView = (ImageView) getView().findViewById(R.id.imageViewProduct);

            id=Long.parseLong(data.getString(COL_PRODUCT_ID));

            String title = data.getString(COLUMN_NAME);
            dialogName = title;
            textViewDetail.setText(title);
            title = data.getString(COLUMN_DESCRIPTION);
            textViewDescription.setText(title);
            title = "R$ " + data.getString(COLUMN_PRICE) + ",00";
            dialogPrice = title;
            textViewPreco.setText(title);

            ImageContract.setImage(imageView,Integer.valueOf(data.getString(COLUMN_IMAGE)));

        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }

        public static class ConfirmBoxDialog extends DialogFragment
                implements DialogInterface.OnClickListener {



            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {

                View view = getActivity().getLayoutInflater()
                        .inflate(R.layout.buy_dialog_activity, null);

                TextView textName = (TextView) view.findViewById(R.id.dialog_name);
                TextView textPrice = (TextView) view.findViewById(R.id.dialog_price);
                textName.setText(dialogName);
                textPrice.setText(dialogPrice);

                return new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.title_dialog)
                        .setView(view)
                        .setPositiveButton(R.string.ok_dialog, this)
                        .setNegativeButton(R.string.cancel_dialog, null)
                        .create();
            }

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        insertBuys(id,0);

                        Toast.makeText(getActivity(),
                                "Pedido Realizado", Toast.LENGTH_SHORT).show();

                        getActivity().onBackPressed();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        getActivity().onBackPressed();
                        break;
                }

            }

            private long insertBuys(long productId, int status) {

                ContentValues productValues = new ContentValues();

                productValues.put(BlackJackBeerContract.BoughtEntry.COLUMN_USER_ID,1);
                productValues.put(BlackJackBeerContract.BoughtEntry.COLUMN_PRODUCT_ID,productId);
                productValues.put(BlackJackBeerContract.BoughtEntry.COLUMN_DATE,"01/01/2027");
                productValues.put(BlackJackBeerContract.BoughtEntry.COLUMN_TIME,1000);
                productValues.put(BlackJackBeerContract.BoughtEntry.COLUMN_STATUS,status);
                productValues.put(BlackJackBeerContract.BoughtEntry.QUANTITY,1);

                // Finally, insert location data into the database.

                Uri insertedUri = getContext().getContentResolver().insert(
                        BlackJackBeerContract.BoughtEntry.CONTENT_URI,
                        productValues
                );
                // The resulting URI contains the ID for the row.  Extract the locationId from the Uri.
                productId = ContentUris.parseId(insertedUri);

                return productId;
            }
        }



    }
}