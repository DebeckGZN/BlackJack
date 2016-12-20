package info.androidhive.blackjackbeer.activity;

import android.widget.ImageView;

import info.androidhive.blackjackbeer.R;

/**
 * Created by Marinete-Cedmar on 20/12/2016.
 */

public class ImageContract {
    public static final int HEINEKEN = 0;
    public static final int BUDWAISER = 1;

    public static final int BRAHMA = 2;
    public static final int SKOL = 3;

    public static final int SPIRIT = 4;
    public static final int SENSE = 5;
    public static final int SECRET = 6;

    public static void setImage (ImageView imageView, int productImage){
        switch (productImage){
            case HEINEKEN:
                imageView.setImageResource(R.drawable.heineken_image);
                break;
            case BUDWAISER:
                imageView.setImageResource(R.drawable.budweiser_image);
                break;
            default:
                imageView.setImageResource(R.drawable.interrogacao_image);
        }
    }

}
