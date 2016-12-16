package info.androidhive.blackjackbeer.activity;

import android.app.Dialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import info.androidhive.blackjackbeer.R;
import info.androidhive.blackjackbeer.data.BlackJackBeerContract;

public class ConfirmDialog extends DialogFragment
        implements DialogInterface.OnClickListener {



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater()
                .inflate(R.layout.buy_dialog_activity, null);

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
                //insertBuys(Long.parseLong(EXTRA_ID),1);

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
