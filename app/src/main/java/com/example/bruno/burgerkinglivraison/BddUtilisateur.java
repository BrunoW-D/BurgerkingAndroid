package com.example.bruno.burgerkinglivraison;

import java.util.HashMap;

public class BddUtilisateur {
    // on utilise un conteneur Java pour les users
    private static HashMap<String, User> lesUsers = new HashMap<String, User>();

    public static void remplirDonnees(User user) {
        lesUsers.put(user.getUsername(), user);
    }

    public static User verifUser(String username, String password) {
        User unUser;
        if (lesUsers.containsKey(username)) {
            unUser = lesUsers.get(username);
            if (unUser.getPassword().equals(password)) {
                return unUser;
            } else {
                return null;
            }
        }
        return null;
    }
}
