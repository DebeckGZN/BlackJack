package info.androidhive.blackjackbeer.activity;

import android.app.Dialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import info.androidhive.blackjackbeer.R;
import info.androidhive.blackjackbeer.data.BlackJackBeerContract;
import info.androidhive.blackjackbeer.models.Produto;


public class BoughtFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int BOUGHT_LOADER = 0;

    private static final String[] BOUGHT_COLUMNS = {
            BlackJackBeerContract.BoughtEntry.TABLE_NAME + "." + BlackJackBeerContract.BoughtEntry._ID,
            BlackJackBeerContract.BoughtEntry.COLUMN_STATUS,
            BlackJackBeerContract.ProductEntry.COLUMN_NAME
    };

    static final int BOUGHT_ID = 0;
    static final int BOUGHT_STATUS = 1;
    static final int PRODUCT_NAME = 2;

    static long deleteId;
    static String deleteName;

    static ArrayList<Produto> produtos = null;

    private BoughtAdapter mBoughtAdapter;

    public BoughtFragment() {
    }

    void atualizarCursor(){
        getLoaderManager().restartLoader(BOUGHT_ID,null,this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(BOUGHT_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onStart() {
        atualizarCursor();
        super.onStart();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_bought,container,false);

        mBoughtAdapter = new BoughtAdapter(getActivity(),null,0);

        ListView listaDeViews = (ListView) rootView.findViewById(R.id.listview_bought);

        listaDeViews.setAdapter(mBoughtAdapter);

        listaDeViews.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                new CodeDialog().show(getFragmentManager(),"");

                final Cursor cursor = (Cursor) parent.getItemAtPosition(position);

                deleteId = cursor.getLong(BOUGHT_ID);
                deleteName = cursor.getString(PRODUCT_NAME);

                Button cancelar = (Button) view.findViewById(R.id.delete_button);

                cancelar.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {

                        //Toast.makeText(getActivity(),
                          //      "Clicou", Toast.LENGTH_SHORT).show();

                        new DeleteDialog().show(getFragmentManager(),"");

                    }

                });

                /*if (cursor != null) {
                    Intent chamarDetalhe = new Intent(getActivity(), InfoActivity.class)
                            .setData(BlackJackBeerContract.BoughtEntry.buildProductUri(cursor.getLong(COL_PRODUCT_ID)));
                    startActivity(chamarDetalhe);
                }
                */
            }

        });

        return rootView;
    }



    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle args) {
        Uri boughtUri = BlackJackBeerContract.BoughtEntry.buildBoughtProduct((long)1);

        CursorLoader cursorLoader = new CursorLoader(getActivity(),
                boughtUri,
                BOUGHT_COLUMNS,
                null,
                null,
                null

        );

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mBoughtAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mBoughtAdapter.swapCursor(null);
    }

    public static class CodeDialog extends DialogFragment
            implements DialogInterface.OnClickListener {



        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            View view = getActivity().getLayoutInflater()
                    .inflate(R.layout.code_dialog_activity, null);

            return new AlertDialog.Builder(getActivity())
                    .setCancelable(false)
                    .setView(view)
                    .setPositiveButton(R.string.ok_dialog, this)
                    .create();
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
        }

    }

    public static class DeleteDialog extends DialogFragment
            implements DialogInterface.OnClickListener {



        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            View view = getActivity().getLayoutInflater()
                    .inflate(R.layout.delete_dialog_activity, null);

            TextView name = (TextView) view.findViewById(R.id.dialog_name);
            name.setText(deleteName);

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
                    deleteListItem(deleteId);


                    Toast.makeText(getActivity(),
                            "Pedido Cancelado", Toast.LENGTH_SHORT).show();

                    //getActivity().onBackPressed();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }

        public void deleteListItem(long id){

            String[] mSelectionArgs = {String.valueOf(id)};

            getContext().getContentResolver().delete(
                    BlackJackBeerContract.BoughtEntry.CONTENT_URI, BlackJackBeerContract.BoughtEntry.TABLE_NAME +
                    "." + BlackJackBeerContract.ProductEntry._ID + " = " + String.valueOf(id),
                    null);
        }

    }



}

