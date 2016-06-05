package com.example.bruno.burgerkinglivraison;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Toast;

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

public class ConnectionActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText txtUsername, txtPassword;
    private Button btnAnnuler, btnSeConnecter;
    private static ArrayList<User> uneListe = new ArrayList<>();

    private static User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        this.txtUsername = (EditText) findViewById(R.id.idUsername);
        this.txtPassword = (EditText) findViewById(R.id.idPassword);
        this.btnAnnuler = (Button) findViewById(R.id.idAnnuler);
        this.btnSeConnecter = (Button) findViewById(R.id.idSeConnecter);

        // rendre écoutable les boutons
        this.btnSeConnecter.setOnClickListener((View.OnClickListener) this);
        this.btnAnnuler.setOnClickListener((View.OnClickListener) this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.idAnnuler:
                this.txtUsername.setText("");
                this.txtPassword.setText("");
                break;
            case R.id.idSeConnecter:
                final String username = this.txtUsername.getText().toString();
                final String password = this.txtPassword.getText().toString();


                try {
                /* Execution de la tache asynchrone */
                    Thread unT = new Thread(new Runnable() {
                        @Override
                        public void run() {

                            ExecutionConnection uneExe = new ExecutionConnection();
                            uneExe.execute();

                            for (User unUser : uneListe) {
                                //remplir les données
                                BddUtilisateur.remplirDonnees(unUser);
                            }
                            user = BddUtilisateur.verifUser(username, password);
                            //unUser = BddUtilisateur.verifUser(unUser.getUsername(), unUser.getPassword());

                        }
                    });
                    unT.start();
                } catch(Exception e){
                    e.printStackTrace();
                    Log.e("Erreur : ", "" + e);
                }

                if (user == null) {
                    Toast.makeText(getApplicationContext(), "Identifiant ou mdp erronés ", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Bienvenue " + user.getPrenom(), Toast.LENGTH_LONG).show();

                    //Appel d'une nouvelle vue !
                    Intent unIntent = new Intent(this, CommandesActivity.class);
                    unIntent.putExtra("restaurant_id", user.getRestaurant_id());
                    this.startActivity(unIntent);
                }

                break;
        }
    }

    //un setter de l'attribut uneListe
    public static void setListe(ArrayList<User> uneL){
        ConnectionActivity.uneListe = uneL;
    }
}

// classe synchrone pour la lecture des produits
class ExecutionConnection extends AsyncTask<Void, Void, ArrayList<User>> {
    @Override
    protected ArrayList<User> doInBackground(Void... params) {
        ArrayList<User> uneListe = new ArrayList<>();
        //String url = "http://localhost/androidStock/lister.php";
        //HttpURLConnection urlConnection;
        URL uneURL;
        String resultat = "";

        try {
            uneURL = new URL("http://192.168.1.18/androidBurgerKing/connection.php");
            HttpURLConnection urlConnection = (HttpURLConnection) uneURL.openConnection();
            Log.e("ca marche", "ca marche");

            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader unBuffer = new BufferedReader(new InputStreamReader(in));

                StringBuilder unSB = new StringBuilder();
                String ligne;
                while ((ligne = unBuffer.readLine()) != null) {
                    unSB.append(ligne);
                    //Log.e("ligne", ligne);
                }
                // on obtient une chaine contenant le resultat du fichier de l'URL
                resultat = unSB.toString();
                //Log.e("resultat", resultat);

                // traitement JSON du resultat
                try {
                    JSONArray tabJson = new JSONArray(resultat);
                    for (int i = 0; i < tabJson.length(); i++) {
                        JSONObject unObjet = tabJson.getJSONObject(i);
                        int id = unObjet.getInt("id");
                        String username = unObjet.getString("username");
                        String email = unObjet.getString("email");
                        String password = unObjet.getString("password");
                        String role = unObjet.getString("role");
                        String nom = unObjet.getString("nom");
                        String prenom = unObjet.getString("prenom");
                        String telephone = unObjet.getString("telephone");
                        String ville = unObjet.getString("ville");
                        String cp = unObjet.getString("cp");
                        String adresse = unObjet.getString("adresse");
                        String dateInscription = unObjet.getString("dateInscription");
                        int restaurant_id = unObjet.getInt("restaurant_id");
                        User unUser = new User(id, username, email, password, role, nom, prenom, telephone, ville, cp, adresse, dateInscription, restaurant_id);
                        uneListe.add(unUser);
                    }
                } catch (JSONException e) {
                    Log.e("Erreur :", "Erreur de parse de Json");
                }
                //return uneListe;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Erreur : ", "" + e);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e("MalformedURLException :", ""+e);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("IOException :", ""+e);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Erreur : ", "" + e);
        }

        return uneListe;
    }

    protected void onPostExecute(ArrayList<User> liste){
        // à la fin de la tache : on modifie l'attribut uneListe
        ConnectionActivity.setListe(liste);
    }
}
