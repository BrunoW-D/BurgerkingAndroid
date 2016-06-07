package com.example.bruno.burgerkinglivraison;

/**
 * Created by Bruno on 06/06/2016.
 */
public class Menu {

    private String nom, accompagnement, boisson, type;
    private int qte;

    /*
    public Menu(){
        this.nom = this.accompagnement = this.boisson = this.type = "";
        this.qte = 0;
    }
    */

    public Menu(String nom, String accompagnement, String boisson, String type, int qte){
        this.nom = nom;
        this.accompagnement = accompagnement;
        this.boisson = boisson;
        this.type = type;
        this.qte = qte;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public String getAccompagnement() {
        return accompagnement;
    }

    public void setAccompagnement(String accompagnement) {
        this.accompagnement = accompagnement;
    }

    public String getBoisson() {
        return boisson;
    }

    public void setBoisson(String boisson) {
        this.boisson = boisson;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
