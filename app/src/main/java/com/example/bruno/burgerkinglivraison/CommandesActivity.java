package com.example.bruno.burgerkinglivraison;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

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
    private static int restaurant_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commandes);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            restaurant_id = extras.getInt("restaurant_id");
        }

        this.lvListe = (ListView) findViewById(R.id.idListe);

        /* Execution de la tache asynchrone */
        Thread unT = new Thread(new Runnable(){
            @Override
            public void run() {
                ExecutionLister uneExe = new ExecutionLister();
                uneExe.execute(restaurant_id);
                final ArrayList<HashMap<String, String>> donnees = new ArrayList<>();
                for (Order uneOrder : uneListe){
                    HashMap<String, String> uneMap = new HashMap<>();
                    uneMap.put("id", ""+uneOrder.getId());
                    uneMap.put("adresse", uneOrder.getAdresse());
                    uneMap.put("cp", uneOrder.getCp());
                    uneMap.put("ville", uneOrder.getVille());
                    donnees.add(uneMap);
                }
                final int to [] = {R.id.idId, R.id.idAdresse, R.id.idCp, R.id.idVille};
                final String from [] = {"id", "adresse", "cp", "ville"};
                // definition d'un adaptateur pour afficher la liste
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //SimpleAdapter unAdapter = new SimpleAdapter(getApplicationContext(), donnees, R.layout.afficheliste, from, to);
                        //lvListe.setAdapter(unAdapter); // affiche les donnees selon l'adaptateur
                        //Log.e("ok", "ok");

                        MyCustomAdapter unAdapter = new MyCustomAdapter(getBaseContext(), donnees, R.layout.afficheliste, from, to);
                        lvListe.setAdapter(unAdapter); // affiche les donnees selon l'adaptateur
                        Log.e("ok", "ok");
                    }
                });
            }
        });
        unT.start();
        try {
            unT.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //un setter de l'attribut uneListe
    public static void setListe(ArrayList<Order> uneL){
        CommandesActivity.uneListe = uneL;
    }
}


// classe synchrone pour la lecture des commandes
class ExecutionLister extends AsyncTask<Integer, Void, ArrayList<Order>>
{
    @Override
    protected ArrayList<Order> doInBackground(Integer... params) {
        int restaurant_id = params[0];
        ArrayList<Order> uneListe = new ArrayList<>();
        URL uneURL;
        String resultat;
        try {
        String url = "http://192.168.1.18/androidBurgerking/lister.php?restaurant=" + restaurant_id;
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
                        JSONObject unObjet = tabJson.getJSONObject(i);
                        int id = unObjet.getInt("id");
                        String ville = unObjet.getString("ville");
                        String cp = unObjet.getString("cp");
                        String adresse = unObjet.getString("adresse");
                        float prix = (float) unObjet.getDouble("prix");

                        JSONArray produits = unObjet.getJSONArray("produits");
                        ArrayList<Product> lesProduits = new ArrayList<>();
                        for (int j = 0; j < produits.length(); j++) {
                            JSONObject unProduit = produits.getJSONObject(j);
                            String nomProduit = unProduit.getString("produit");
                            int qteProduit = unProduit.getInt("qte");
                            Product unProduct = new Product(nomProduit, qteProduit);
                            lesProduits.add(unProduct);
                        }

                        JSONArray menus = unObjet.getJSONArray("menus");
                        ArrayList<Menu> lesMenus = new ArrayList<>();
                        for (int j = 0; j < menus.length(); j++) {
                            JSONObject unMenu = menus.getJSONObject(j);
                            String nomMenu = unMenu.getString("menu");
                            JSONArray produitsDuMenu = unMenu.getJSONArray("produits");
                            String accompagnement = produitsDuMenu.getString(0);
                            String boisson = produitsDuMenu.getString(1);
                            String type = unMenu.getString("type");
                            int qte = unMenu.getInt("qte");
                            Menu menu = new Menu(nomMenu, accompagnement, boisson, type, qte);
                            lesMenus.add(menu);
                        }
                        Order uneOrder = new Order(id, ville, cp, adresse, prix, lesProduits, lesMenus);
                        uneListe.add(uneOrder);
                        Log.e("id : ", ""+id);
                    }
                }
                catch(JSONException e){
                    Log.e("Erreur :", "Erreur de parse de Json");
                }
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


