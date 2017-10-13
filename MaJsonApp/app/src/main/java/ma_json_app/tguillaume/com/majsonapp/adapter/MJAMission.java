package ma_json_app.tguillaume.com.majsonapp.adapter;

/**
 * @Project : AND_MaJsonApp
 *
 * MJAMission.java
 *
 * Created by TARTARA Guillaume on 11/10/2017
 * Copyright © 2017 tguillaume. All rights reserved.
 */

/**
 * cette classe defini le modele d'une mission
 */
public class MJAMission {

    private int id;
    private String nom;
    private int date;
    private String ville;
    private String pays;

    public MJAMission(int sId, String sNom, int sDate, String sVille, String sPays) {
        this.id = sId;
        this.nom = sNom;
        this.date = sDate;
        this.ville = sVille;
        this.pays = sPays;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }
}
