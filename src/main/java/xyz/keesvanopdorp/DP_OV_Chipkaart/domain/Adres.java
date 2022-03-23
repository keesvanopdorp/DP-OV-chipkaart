package xyz.keesvanopdorp.DP_OV_Chipkaart.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Adres {
    public int id = 0;
    public String postcode = "";
    public String huisnummer = "";
    public String straat = "";
    public String woonplaats = "";
    public Reiziger reiziger;

    public int getId() {
        return id;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public String getStraat() {
        return straat;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPostcode(String poscode) {
        if (poscode != null) {
            if (poscode.length() > 10) {
                poscode = poscode.substring(0, 10);
            }
        }
        this.postcode = poscode;
    }

    public void setHuisnummer(String huisnummer) {
        if (huisnummer != null) {
            if (huisnummer.length() > 10) {
                huisnummer = huisnummer.substring(0, 10);
            }
        }
        this.huisnummer = huisnummer;
    }

    public void setStraat(String straat) {
        if (straat != null) {
            if (straat.length() > 10) {
                straat = straat.substring(0, 10);
            }
        }
        this.straat = straat;
    }

    public void setWoonplaats(String woonplaats) {
        if (woonplaats != null) {
            if (woonplaats.length() > 10) {
                woonplaats = woonplaats.substring(0, 10);
            }
        }
        this.woonplaats = woonplaats;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    public void fillFromResultSet(ResultSet set) throws SQLException {
        this.id = set.getInt("adres_id");
        this.postcode = set.getString("postcode");
        this.huisnummer = set.getString("huisnummer");
        this.straat = set.getString("straat");
        this.woonplaats = set.getString("woonplaats");
    }
}
