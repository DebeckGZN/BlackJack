package info.androidhive.blackjackbeer.activity;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import info.androidhive.blackjackbeer.R;


/**
 * Created by Marinete-Cedmar on 07/12/2016.
*/
public class BoughtAdapter extends android.widget.CursorAdapter {

    public BoughtAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        //TODO: ver como fica a amarracao o itemXML a listView  e Cursor. Explicar em aula.
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_bought_item, parent, false);

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
        TextView textViewCategory = (TextView) view.findViewById(R.id.textViewStatus);

        String title = cursor.getString(BoughtFragment.PRODUCT_NAME);
        textViewName.setText(title);
        if(cursor.getInt(BoughtFragment.BOUGHT_STATUS) == 0) {
            title = "Pendente";
        }else{
            title = "Retirado";
        }
            textViewCategory.setText(title);

        Button button = (Button) view.findViewById(R.id.delete_button);
        if(Long.parseLong(cursor.getString(BoughtFragment.BOUGHT_STATUS)) == 1) {
            button.setClickable(false);
            button.setVisibility(view.INVISIBLE);
        }

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
