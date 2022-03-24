package xyz.keesvanopdorp.DP_OV_Chipkaart.dao.adres;

import xyz.keesvanopdorp.DP_OV_Chipkaart.dao.reiziger.ReizigerDaoPostgres;
import xyz.keesvanopdorp.DP_OV_Chipkaart.domain.Adres;
import xyz.keesvanopdorp.DP_OV_Chipkaart.domain.Reiziger;

import java.sql.*;
import java.util.ArrayList;

public class AdresDaoPostgres implements AdresDAO {
    private Connection connection;

    private ReizigerDaoPostgres reizigerDaoPostgres;

    public AdresDaoPostgres(Connection connection) {
        this.connection = connection;
    }

    public ReizigerDaoPostgres getReizigerDaoPostgres() {
        return reizigerDaoPostgres;
    }

    public void setReizigerDaoPostgres(ReizigerDaoPostgres reizigerDaoPostgres) {
        this.reizigerDaoPostgres = reizigerDaoPostgres;
    }

    @Override
    public boolean save(Adres inAdres) {
        try {
            if (inAdres.reiziger != null) {
                reizigerDaoPostgres.save(inAdres.reiziger);
            }
            PreparedStatement statement = connection.prepareStatement("INSERT INTO ADRES (ADRES_ID, POSTCODE, HUISNUMMER, STRAAT, WOONPLAATS, REIZIGER_ID) VALUES (?,?,?,?,?,?);");
            statement.setInt(1, inAdres.getId());
            statement.setString(2, inAdres.getPostcode());
            statement.setString(3, inAdres.getHuisnummer());
            statement.setString(4, inAdres.getStraat());
            statement.setString(5, inAdres.getWoonplaats());
            if (inAdres.reiziger != null) {
                statement.setInt(6, inAdres.reiziger.getId());
            }
            statement.executeUpdate();
            statement.close();
        } catch (SQLException throwables) {
            System.err.println("SQLExecption:" + throwables.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(Adres inAdres) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE adres SET woonplaats = ?, straat = ?, huisnummer = ?, postcode = ?, reiziger_id = ? WHERE adres_id =?;");
            statement.setString(1, inAdres.getWoonplaats());
            statement.setString(2, inAdres.getStraat());
            statement.setString(3, inAdres.getHuisnummer());
            statement.setString(3, inAdres.getPostcode());
            statement.setInt(4, inAdres.getReiziger().getId());
            statement.setInt(5, inAdres.getId());
        } catch (SQLException throwables) {
            System.err.println("SQLExecption:" + throwables.getMessage());
        }
        return true;
    }

    @Override
    public boolean delete(Adres inAdres) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM ADRES WHERE ADRES_ID = ?");
            statement.setInt(1, inAdres.getId());
            statement.executeQuery();
            statement.close();
        } catch (SQLException throwables) {
            System.err.println("SQLExecption:" + throwables.getMessage());
        }
        return true;
    }

    @Override
    public ArrayList<Adres> findAll() {
        ArrayList<Adres> adressen = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            ResultSet set = statement.executeQuery("SELECT * FROM ADRES;");
            while (set.next()) {
                Adres adres = new Adres();
                adres.fillFromResultSet(set);
                adres.setReiziger(this.reizigerDaoPostgres.findById(set.getInt("reiziger_id")));
                adressen.add(adres);
            }
            set.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adressen;
    }

    public Adres findByReiziger(Reiziger inReiziger) {
        Adres adres = new Adres();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM ADRES WHERE REIZIGER_ID = ?;");
            statement.setInt(1, inReiziger.getId());
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                adres.fillFromResultSet(set);
                adres.setReiziger(inReiziger);
                System.out.println(adres.reiziger);
            }
            set.close();
            statement.close();
        } catch (SQLException throwables) {
            System.err.println("SQLExecption:" + throwables.getMessage());
        }
        return adres;
    }
}
