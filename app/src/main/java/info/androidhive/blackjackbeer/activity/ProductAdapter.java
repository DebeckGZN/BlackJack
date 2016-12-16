package info.androidhive.blackjackbeer.activity;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import info.androidhive.blackjackbeer.R;
import info.androidhive.blackjackbeer.activity.BeersFragment;


/**
 * Created by Marinete-Cedmar on 07/12/2016.
 */
public class ProductAdapter extends android.widget.CursorAdapter {

    public ProductAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        //TODO: ver como fica a amarracao o itemXML a listView  e Cursor. Explicar em aula.
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_beers_list_item, parent, false);

        return view;
    }

    /*
        This is where we fill-in the views with the contents of the cursor.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // our view is pretty simple here --- just a text view
        // we'll keep the UI functional with a simple (and slow!) binding.

        TextView textViewName = (TextView) view.findViewById(R.id.textViewName);
        TextView textViewCategory = (TextView) view.findViewById(R.id.textViewCategory);
        TextView textViewPreco = (TextView) view.findViewById(R.id.textViewPrice);

        String title = cursor.getString(BeersFragment.COLUMN_NAME);
        textViewName.setText(title);
        title = cursor.getString(BeersFragment.COLUMN_CATEGORY);
        textViewCategory.setText(title);
        title = "R$ "+cursor.getString(BeersFragment.COLUMN_PRICE)+",00";
        textViewPreco.setText(title);

        //TextView tv = (TextView)view;
        //String title = cursor.getString((MainActivityFragment.COLUMN_NAME));
        //tv.setText(title);



/*        TextView textViewTopic = (TextView) view.findViewById(R.id.textViewTopic);
        TextView textSummary = (TextView) view.findViewById(R.id.textViewSummary);
        String topic = cursor.getString(AssessmentActivityFragment.COLUMN_TOPIC);
        textViewTopic.setText(topic); //  cursor.getDouble(ForecastFragment.COL_WEATHER_MAX_TEMP),
        String summary = cursor.getString(AssessmentActivityFragment.COLUMN_SUMMARY);
        textSummary.setText(topic);*/


    }
}
