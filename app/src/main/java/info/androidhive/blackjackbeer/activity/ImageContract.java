package info.androidhive.blackjackbeer.activity;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import info.androidhive.blackjackbeer.R;

/**
 * Created by Marinete-Cedmar on 20/12/2016.
 */

public class ImageContract {
    public static final int HEINEKEN = 0;
    public static final int BUDWAISER = 1;
    public static final int STELLA = 2;
    public static final int EISENHBAHN = 3;

    public static final int BRAHMA = 4;
    public static final int SKOL = 5;

    public static final int SPIRITS = 6;
    public static final int SENSES = 7;
    public static final int SECRETS = 8;

    public static void setImage (ImageView imageView, int productImage){
        switch (productImage){
            case HEINEKEN:
                imageView.setImageResource(R.drawable.heineken_image);
                break;
            case BUDWAISER:
                imageView.setImageResource(R.drawable.budweiser_image);
                break;
            case STELLA:
                imageView.setImageResource(R.drawable.stella_image);
                break;
            case EISENHBAHN:
                imageView.setImageResource(R.drawable.eisenbahn_image);
                break;
            case BRAHMA:
                imageView.setImageResource(R.drawable.brahma_image);
                break;
            case SKOL:
                imageView.setImageResource(R.drawable.skol_image);
                break;
            case SPIRITS:
                imageView.setImageResource(R.drawable.skol_verde_image);
                break;
            case SENSES:
                imageView.setImageResource(R.drawable.skol_azul_image);
                break;
            case SECRETS:
                imageView.setImageResource(R.drawable.skol_vermelho_image);
                break;
            default:
                imageView.setImageResource(R.drawable.interrogacao_image);
        }
    }

}
