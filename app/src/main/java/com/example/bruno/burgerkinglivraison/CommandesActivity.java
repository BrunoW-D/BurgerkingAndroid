package com.example.bruno.burgerkinglivraison;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class CommandesActivity extends AppCompatActivity {

    private ListView lvListe;
    private static ArrayList<Order> uneListe = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commandes);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int restaurant_id = extras.getInt("restaurant_id");
        }
    }
}

        //this.lvListe = (ListView) findViewById(R.id.idListe);

        /* Execution de la tache asynchrone */
        /*Thread unT = new Thread(new Runnable(){
            @Override
            public void run() {
                ExecutionLister uneExe = new ExecutionLister();
                uneExe.execute(restaurant_id);
                final ArrayList<HashMap<String, String>> donnees = new ArrayList<>();
                for (Order uneOrder : uneListe){
                    HashMap<String, String> uneMap = new HashMap<>();
                    uneMap.put("reference", uneOrder.getReference());
                    uneMap.put("designation", uneOrder.getDesignation());
                    uneMap.put("qte", ""+uneOrder.getQte());
                    uneMap.put("prix", ""+uneOrder.getPrix());
                    uneMap.put("categorie", uneOrder.getCategorie());
                    donnees.add(uneMap);
                }
                final int to [] = {R.id.idreference, R.id.iddesignation, R.id.idqte, R.id.idprix, R.id.idcategorie};
                final String from [] = {"reference", "designation", "qte", "prix", "categorie"};
                // definition d'un adaptateur pour afficher la liste
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SimpleAdapter unAdapter = new SimpleAdapter(getApplicationContext(), donnees, R.layout.afficheliste, from, to);
                        lvListe.setAdapter(unAdapter); // affiche les donnees selon l'adaptateur
                    }
                });
            }
        });
        unT.start();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.idRetour)
        {
            Intent unIntent = new Intent(this, MenuGestion.class);
            this.startActivity(unIntent);
        }
    }

    //un setter de l'attribut uneListe
    public static void setListe(ArrayList<Order> uneL){
        CommandesActivity.uneListe = uneL;
    }
} // fin de la classe

// classe synchrone pour la lecture des produits
class ExecutionLister extends AsyncTask<int, Void, ArrayList<Order>>
{
    @Override
    protected ArrayList<Order> doInBackground(int... params) {
        int restaurant_id = pParams[0];
        ArrayList<Order> uneListe = new ArrayList<>();
        //String url = "http://localhost/androidStock/lister.php";
        //HttpURLConnection urlConnection;
        URL uneURL;
        String resultat = "";
        try {
        String url = "http://192.168.1.18/androidStock/lister.php?restaurant=" + restaurant_id;
            uneURL = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) uneURL.openConnection();
            try{
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader unBuffer = new BufferedReader(new InputStreamReader(in));
                Log.e("ca marche", "ca marche");
                StringBuilder unSB = new StringBuilder();
                String ligne;
                while((ligne = unBuffer.readLine()) != null){
                    unSB.append(ligne);
                    Log.e("ligne", ligne);
                }
                // on obtient une chaine contenant le resultat du fichier de l'URL
                resultat = unSB.toString();
                Log.e("resultat", resultat);

                // traitement JSON du resultat
                try{
                    JSONArray tabJson = new JSONArray(resultat);
                    for (int i=0; i<tabJson.length(); i++){
                        // GET JSONARRAY() / GEt JSONOBJECT() !
                        JSONObject unObjet = tabJson.getJSONObject(i);
                        String reference = unObjet.getString("reference");
                        String designation = unObjet.getString("designation");
                        String categorie = unObjet.getString("categorie");
                        int qte = unObjet.getInt("qte");
                        float prix = (float) unObjet.getDouble("prix");
                        Order uneOrder = new Order(reference, designation, categorie, qte, prix);
                        uneListe.add(uneOrder);
                        Log.e("reference : ", reference);
                    }
                }
                catch(JSONException e){
                    Log.e("Erreur :", "Erreur de parse de Json");
                }
                //return uneListe;
            } catch(Exception e){
                e.printStackTrace();
                Log.e("Erreur : ", "" + e);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
            Log.e("Erreur : ", "" + e);
        }

        return uneListe;

    }

    protected void onPostExecute(ArrayList<Order> liste){
        // à la fin de la tache : on modifie l'attribut uneListe
        CommandesActivity.setListe(liste);
    }
}

*/
