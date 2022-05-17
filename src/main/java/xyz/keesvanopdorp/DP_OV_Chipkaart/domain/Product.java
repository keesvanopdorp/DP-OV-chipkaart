package xyz.keesvanopdorp.DP_OV_Chipkaart.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Product {
    private int productNummer;
    private String naam;
    private String beschrijving;
    private double prijs;

    public int getProductNummer() {
        return productNummer;
    }

    public void setProductNummer(int productNummer) {
        this.productNummer = productNummer;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    public void fillFromResultSet(ResultSet set) throws SQLException {
        this.beschrijving = set.getString("beschrijving");
        this.productNummer = set.getInt("product_nummer");
        this.naam = set.getString("naam");
        this.prijs = set.getDouble("prijs");
    }
}
