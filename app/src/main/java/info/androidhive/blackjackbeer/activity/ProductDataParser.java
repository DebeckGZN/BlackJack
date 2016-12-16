package info.androidhive.blackjackbeer.activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import info.androidhive.blackjackbeer.models.Produto;


/**
 * Created by LeandroHD on 30/09/16.
 */
public class ProductDataParser {

    public ArrayList<Produto> getProducts (String jsonString){

        ArrayList<Produto> products = new ArrayList();

        try {


            JSONObject jsonAssessment = new JSONObject(jsonString);

            JSONArray jsonProducts = jsonAssessment.getJSONArray("produto");

            for(int i = 0; i < jsonProducts.length(); i++) {

                JSONObject productObject = jsonProducts.getJSONObject(i);


                String nome = (String)productObject.get("nome");

                int preco = (int) productObject.get("preco");

                String categoria = (String)productObject.get("categoria");

                boolean disponibilidade = (boolean)productObject.get("disponibilidade");

                String descricao = (String)productObject.get("descricao");

                Produto insere = new Produto(nome,(float)preco,categoria,disponibilidade);
                if (descricao != null)
                    insere.descricao = descricao;

                products.add(insere);

            }

        } catch (JSONException e) {
            products = null;
        }


        return products;

    }
}
