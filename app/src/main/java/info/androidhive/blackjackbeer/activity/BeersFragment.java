package info.androidhive.blackjackbeer.activity;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import info.androidhive.blackjackbeer.R;
import info.androidhive.blackjackbeer.models.Produto;
import info.androidhive.blackjackbeer.data.*;

/**
 * A placeholder fragment containing a simple view.
 */
public class BeersFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int PRODUCT_LOADER = 0;

    private static final String[] PRODUCT_COLUMNS = {
            BlackJackBeerContract.ProductEntry.TABLE_NAME + "." + BlackJackBeerContract.ProductEntry._ID,
            BlackJackBeerContract.ProductEntry.COLUMN_NAME,
            BlackJackBeerContract.ProductEntry.COLUMN_PRICE,
            BlackJackBeerContract.ProductEntry.COLUMN_CATEGORY,
            BlackJackBeerContract.ProductEntry.COLUMN_DISPONIBILITY,
            BlackJackBeerContract.ProductEntry.COLUMN_IMAGE
    };

    static final int COL_PRODUCT_ID = 0;
    static final int COLUMN_NAME = 1;
    static final int COLUMN_PRICE = 2;
    static final int COLUMN_CATEGORY = 3;
    static final int COLUMN_DISPONIBILITY = 4;
    static final int COLUMN_IMAGE = 5;

    static ArrayList<Produto> produtos = null;

    private ProductAdapter mProductAdapter;

    public BeersFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(PRODUCT_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_refresh){
            updateProducts();
            Toast.makeText(getActivity(), "Dados Atualizados!", Toast.LENGTH_LONG).show();
            return true;

        }else{
            if(item.getItemId() == R.id.action_settings){

                Intent intent = new Intent(getContext(),SettingsActivity.class);

                startActivity(intent);

            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateProducts(){
        FetchData fetch = new FetchData();
        fetch.execute();

    }



    @Override
    public void onStart() {
        super.onStart();
        updateProducts();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_beers,container,false);

        produtos = new ArrayList<Produto>();
        produtos.add(new Produto("Heinekein", 9f, "Cerveja Premium", true));
        produtos.get(0).descricao = "-Cerveja importada Holandesa\n-355ml";
        produtos.add(new Produto("Budweiser", 9f, "Cerveja Premium", true));
        produtos.get(1).descricao = "-Cerveja importada AlemÃ£\n-343ml";
        produtos.add(new Produto("Produto 3", 0f, "Cerveja Premium", true));
        produtos.add(new Produto("Produto 4", 0f, "Cerveja Premium", true));
        produtos.add(new Produto("Produto 5", 0f, "Cerveja Premium", true));
        produtos.add(new Produto("Produto 6", 0f, "Cerveja Premium", true));
        produtos.add(new Produto("Produto 7", 0f, "Cerveja Premium", true));
        produtos.add(new Produto("Produto 8", 0f, "Cerveja Premium", true));
        produtos.add(new Produto("Produto 9", 0f, "Cerveja", true));

        //AssessmentDataParser parser = new AssessmentDataParser();


        //ArrayList<Produto>produtos2 = parser.getProducts("{\"produto\":[{\"nome\":\"Heineken\",\"preco\":9,\"categoria\":\"Cerveja Premium\",\"descricao\":\"Cerveja Holandesa 355ml\",\"disponibilidade\":true},{\"nome\":\"Budweiser\",\"preco\":9,\"categoria\":\"Cerveja Premium\",\"descricao\":\"Cerveja AlemÃ£ 343ml\",\"disponibilidade\":true},{\"nome\":\"Brahma\",\"preco\":9,\"categoria\":\"Cerveja\",\"descricao\":\"Cerveja Nacional 250ml\",\"disponibilidade\":true},{\"nome\":\"Stella Artois\",\"preco\":9,\"categoria\":\"Cerveja Premium\",\"descricao\":\"Cerveja Belga 275ml\",\"disponibilidade\":true},{\"nome\":\"Eisenbahn\",\"preco\":9,\"categoria\":\"Cerveja Premium\",\"descricao\":\"Cerveja AlemÃ£ 355ml\",\"disponibilidade\":true},{\"nome\":\"Skol\",\"preco\":9,\"categoria\":\"Cerveja\",\"descricao\":\"Cerveja Nacional 250ml\",\"disponibilidade\":true},{\"nome\":\"Skol Beats(Secrets)\",\"preco\":9,\"categoria\":\"Cerveja\",\"descricao\":\"Cerveja Nacional 313ml\",\"disponibilidade\":true},{\"nome\":\"Skol Beats(Senses)\",\"preco\":9,\"categoria\":\"Cerveja\",\"descricao\":\"Cerveja Nacional 313ml\",\"disponibilidade\":true},{\"nome\":\"Skol Beats(Spirits)\",\"preco\":9,\"categoria\":\"Cerveja\",\"descricao\":\"Cerveja Nacional 313ml\",\"disponibilidade\":true}]}");

        //if(!produtos2.isEmpty()) produtos = produtos2;

        mProductAdapter = new ProductAdapter(getActivity(),null,0);


        //adapter =
        //      new MyAdapter(
        //            getActivity(),
        //          R.layout.presentation_listview_item,
        //      produtos
        //);

        ListView listaDeViews = (ListView) rootView.findViewById(R.id.listview_products);

        listaDeViews.setAdapter(mProductAdapter);


        listaDeViews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(),mProductAdapter.getItem(position).getNome(),Toast.LENGTH_LONG).show();
                //String forecast = mProductAdapter.getItem(position).getNome();
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                if (cursor != null) {
                    Intent chamarDetalhe = new Intent(getActivity(), InfoActivity.class)
                            .setData(BlackJackBeerContract.ProductEntry.buildProductUri(cursor.getLong(COL_PRODUCT_ID)));
                    startActivity(chamarDetalhe);
                }
            }
        });



        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle args) {
        Uri productUri = BlackJackBeerContract.ProductEntry.buildProductUri();

        CursorLoader cursorLoader = new CursorLoader(getActivity(),
                productUri,
                PRODUCT_COLUMNS,
                null,
                null,
                null

        );

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mProductAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mProductAdapter.swapCursor(null);
    }


    public class FetchData extends AsyncTask<Void,Void, ArrayList<Produto>> {

        private long insertProduct(String name, float price, String category, String description, boolean disponibility, String image) {
            long productId =0;

            int dis = 0;
            if (disponibility)
                dis = 1;

            Cursor productCursor = getContext().getContentResolver().query(
                    BlackJackBeerContract.ProductEntry.CONTENT_URI,
                    new String[]{BlackJackBeerContract.ProductEntry._ID},
                    BlackJackBeerContract.ProductEntry.COLUMN_NAME + " = ?",
                    new String[]{name},
                    null);

            if (productCursor.moveToFirst()){
                int productIdIndex = productCursor.getColumnIndex(BlackJackBeerContract.ProductEntry._ID);
                productId = productCursor.getLong(productIdIndex);
            }
            else{
                ContentValues productValues = new ContentValues();

                productValues.put(BlackJackBeerContract.ProductEntry.COLUMN_NAME,name);
                productValues.put(BlackJackBeerContract.ProductEntry.COLUMN_PRICE,price);
                productValues.put(BlackJackBeerContract.ProductEntry.COLUMN_CATEGORY,category);
                productValues.put(BlackJackBeerContract.ProductEntry.COLUMN_DESCRIPTION,description);
                productValues.put(BlackJackBeerContract.ProductEntry.COLUMN_DISPONIBILITY,dis);
                productValues.put(BlackJackBeerContract.ProductEntry.COLUMN_IMAGE,image);

                // Finally, insert location data into the database.
                Uri insertedUri = getContext().getContentResolver().insert(
                        BlackJackBeerContract.ProductEntry.CONTENT_URI,
                        productValues
                );

                // The resulting URI contains the ID for the row.  Extract the locationId from the Uri.
                productId = ContentUris.parseId(insertedUri);
            }

            productCursor.close();

            return productId;
        }


        protected ArrayList<Produto> doInBackground(Void... params) {

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;



            try {




                //URL url = new URL("https://raw.githubusercontent.com/llcostalonga/rate-ur-mate/Lesson-2/fakeAssessment.jason");

                URL url = new URL("https://raw.githubusercontent.com/Caiovr/Json/master/jsonProducts.json");

                //URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7&appid=74dee42ab7b3d4b4a8a4e62d691a1d3b");


                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                forecastJsonStr = buffer.toString();

                Log.v("Assessment", "json" + forecastJsonStr);





            } catch (IOException e) {
                Log.e("Assessment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("Assessment", "Error closing stream", e);
                    }
                }
            }



            // FakeAssessmentDataParser parser = new FakeAssessmentDataParser();
            ProductDataParser parser = new ProductDataParser();
            try {

                return parser.getProducts(forecastJsonStr);

            } catch (Exception e) {
                Log.e("Assessment", "Error closing stream", e);
            }


            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Produto> result) {
            super.onPostExecute(result);

            if(result !=null){
                //adapter.clear();
                //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                //prefs.getString(R.string.)
                //int categoria = prefs.getString(R.string.);
                for (Produto produto :  result){
                    // if(produto.getCategoria().equals()) {
                    //adapter.add(produto);
                    insertProduct(produto.getNome(),produto.getPreco(),produto.getCategoria(),produto.descricao,produto.isDisponibilidade()," ");
                    //  }
                }
            }
        }
    }

}


