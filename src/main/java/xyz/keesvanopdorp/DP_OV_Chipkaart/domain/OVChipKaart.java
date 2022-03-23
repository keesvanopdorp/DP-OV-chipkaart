package xyz.keesvanopdorp.DP_OV_Chipkaart.domain;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OVChipKaart {
    private int kaartNummer;
    private Date geldigTot;
    private BigDecimal saldo;
    private int klasse;
    private Reiziger reiziger;

    public int getKaartNummer() {
        return kaartNummer;
    }

    public Date getGeldigTot() {
        return geldigTot;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public int getKlasse() {
        return klasse;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setKaartNummer(int kaartNummer) {
        this.kaartNummer = kaartNummer;
    }

    public void setGeldigTot(Date geldigTot) {
        this.geldigTot = geldigTot;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    public void fillFromResultSet(ResultSet set) {
        try {
            this.geldigTot = set.getDate("geldig_tot");
            this.kaartNummer = set.getInt("kaart_nummer");
            this.klasse = set.getInt("klasse");
            this.saldo = set.getBigDecimal("saldo");
        } catch (SQLException throwables) {
            System.err.println("SQLExecption:" + throwables.getMessage());
        }
    }
}
