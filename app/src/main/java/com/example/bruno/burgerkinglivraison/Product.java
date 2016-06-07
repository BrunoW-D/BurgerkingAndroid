package com.example.bruno.burgerkinglivraison;

/**
 * Created by Bruno on 06/06/2016.
 */
public class Product {

    private String nom;
    private int qte;

    /*
    public Product(){
        this.nom = "";
        this.qte = 0;
    }
    */

    public Product(String nom, int qte){
        this.nom = nom;
        this.qte = qte;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
