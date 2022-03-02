package xyz.keesvanopdorp.DP_OV_Chipkaart.domain;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Reiziger {
    private int id =0;
    private String voorletters = "";
    private String tussenvoegsel = "";
    private String achternaam = "";
    private Date geboortedatum = new Date(0);

    public String getVoorletters() {
        return this.voorletters;
    }

    public void setVoorletters(String voorletters) {
        if(voorletters != null) {
            if(voorletters.length() > 10) {
                voorletters = voorletters.substring(0, 10);
            }
            this.voorletters = voorletters;
        }
    }

    public int getId() {return this.id;}

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
        if(tussenvoegsel != null) {
            if(tussenvoegsel.length() > 10) {
                tussenvoegsel = tussenvoegsel.substring(0, 10);
            }
            this.tussenvoegsel = tussenvoegsel;
        }
    }

    public void setAchternaam(String achternaam) {
        if(achternaam != null) {
            if(achternaam.length() > 10) {
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
            name = String.format("%s %s %s",initial ,insertion, lastName);
        } else {
            name = String.format("%s %s", initial, lastName);
        }
        return String.format("#%s %s (%s)%n", this.getId(), name, this.getGeboortedatum());
    }

    //    public  boolean save(Connection connection) {
//        try {
//            PreparedStatement statement = connection.prepareStatement("INSERT INTO reiziger (reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES (?,?,?,?);");
//            statement.setInt(1,this.getId());
//            statement.setString(2,this.getVoorletters());
//            statement.setString(3, this.getTussenvoegsel());
//            statement.setString(4, this.getAchternaam());
//            statement.setDate(5, this.getGeboortedatum());
//            statement.executeQuery();
//        }catch (SQLException throwables) {
//            System.err.println("SQLExecption:" + throwables.getMessage());
//        }
//        return true;
//    }
}
