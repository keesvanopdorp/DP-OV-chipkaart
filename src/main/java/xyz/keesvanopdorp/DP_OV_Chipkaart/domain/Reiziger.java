package xyz.keesvanopdorp.DP_OV_Chipkaart.domain;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Reiziger {
    private int id = 0;
    private String voorletters = "";
    private String tussenvoegsel = "";
    private String achternaam = "";
    private Date geboortedatum = new Date(0);
    private Adres adres;
    private ArrayList<OVChipKaart> ovChipKaarten;

    public String getVoorletters() {
        return this.voorletters;
    }

    public void setVoorletters(String voorletters) {
        if (voorletters != null) {
            if (voorletters.length() > 10) {
                voorletters = voorletters.substring(0, 10);
            }
            this.voorletters = voorletters;
        }
    }

    public ArrayList<OVChipKaart> getOvChipKaarten() {
        return ovChipKaarten;
    }

    public void setOvChipKaarten(ArrayList<OVChipKaart> ovChipKaarten) {
        this.ovChipKaarten = ovChipKaarten;
    }

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public String getTussenvoegsel() {
        return this.tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        if (tussenvoegsel != null) {
            if (tussenvoegsel.length() > 10) {
                tussenvoegsel = tussenvoegsel.substring(0, 10);
            }
            this.tussenvoegsel = tussenvoegsel;
        }
    }

    public void setAchternaam(String achternaam) {
        if (achternaam != null) {
            if (achternaam.length() > 10) {
                achternaam = achternaam.substring(0, 10);
            }
            this.achternaam = achternaam;
        }
    }

    @Override
    public String toString() {
        String name;
        String insertion = this.getTussenvoegsel();
        String initial = this.getVoorletters();
        String lastName = this.getAchternaam();
        if (insertion != null) {
            name = String.format("%s %s %s", initial, insertion, lastName);
        } else {
            name = String.format("%s %s", initial, lastName);
        }
        return String.format("#%s %s (%s)%n", this.getId(), name, this.getGeboortedatum());
    }

    public void fillFromResultSet(ResultSet set) throws SQLException {
        this.id = set.getInt("reiziger_id");
        this.tussenvoegsel = set.getString("tussenvoegsel");
        this.voorletters = set.getString("voorletters");
        this.achternaam = set.getString("achternaam");
        this.geboortedatum = set.getDate("geboortedatum");
    }
}
