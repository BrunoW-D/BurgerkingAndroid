package com.example.bruno.burgerkinglivraison;

import java.util.ArrayList;

/**
 * Created by Bruno on 02/06/2016.
 */
public class Order {

    private int id;
    private String ville, cp, adresse;
    private float prix;
    private ArrayList<Product> products;
    private ArrayList<Menu> menus;

    /*
    public Order()
    {
        this.id = 0;
        this.ville = this.cp = this.adresse = "";
        this.prix = 0;
        this.products = new ArrayList<Product>();
        this.menus = new ArrayList<Menu>();
    }
    */

    public Order(int id, String ville, String cp, String adresse, float prix, ArrayList<Product> products, ArrayList<Menu> menus){
        this.id = id;
        this.ville = ville;
        this.cp = cp;
        this.adresse = adresse;
        this.prix = prix;
        this.products = products;
        this.menus = menus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public ArrayList<Menu> getMenus() {
        return menus;
    }

    public void setMenus(ArrayList<Menu> menus) {
        this.menus = menus;
    }
}
