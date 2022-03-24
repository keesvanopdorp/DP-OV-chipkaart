package xyz.keesvanopdorp.DP_OV_Chipkaart.dao.reiziger;

import xyz.keesvanopdorp.DP_OV_Chipkaart.dao.adres.AdresDaoPostgres;
import xyz.keesvanopdorp.DP_OV_Chipkaart.dao.ovchipkaart.OVChipKaartDaoPostgres;
import xyz.keesvanopdorp.DP_OV_Chipkaart.domain.OVChipKaart;
import xyz.keesvanopdorp.DP_OV_Chipkaart.domain.Reiziger;

import java.sql.*;
import java.util.ArrayList;

public class ReizigerDaoPostgres implements ReizigerDAO {
    private Connection connection = null;
    private AdresDaoPostgres adresDaoPostgres;
    private OVChipKaartDaoPostgres ovChipKaartDaoPostgres;

    public ReizigerDaoPostgres(Connection connection) {
        this.connection = connection;
    }

    public AdresDaoPostgres getAdresDaoPostgres() {
        return adresDaoPostgres;
    }

    public OVChipKaartDaoPostgres getOvChipKaartDaoPostgres() {
        return ovChipKaartDaoPostgres;
    }

    public void setAdresDaoPostgres(AdresDaoPostgres adresDaoPostgres) {
        this.adresDaoPostgres = adresDaoPostgres;
    }

    public void setOvChipKaartDaoPostgres(OVChipKaartDaoPostgres ovChipKaartDaoPostgres) {
        this.ovChipKaartDaoPostgres = ovChipKaartDaoPostgres;
    }

    @Override
    public boolean save(Reiziger inReiziger) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO reiziger (reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES (?,?,?,?,?);");
            statement.setInt(1, inReiziger.getId());
            statement.setString(2, inReiziger.getVoorletters());
            statement.setString(3, inReiziger.getTussenvoegsel());
            statement.setString(4, inReiziger.getAchternaam());
            statement.setDate(5, inReiziger.getGeboortedatum());
            statement.executeUpdate();
            statement.close();
            if (inReiziger.getOvChipKaarten() != null) {
                for (OVChipKaart ovChipKaart : inReiziger.getOvChipKaarten()) {

                    this.ovChipKaartDaoPostgres.save(ovChipKaart);
                }
            }
            if(inReiziger.getAdres() != null) {
                this.adresDaoPostgres.save(inReiziger.getAdres());
            }
        } catch (SQLException throwables) {
            System.err.println("SQLExecption:" + throwables.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(Reiziger inReiziger) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE reiziger SET voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ? WHERE reiziger_id = ?");
            statement.setString(1, inReiziger.getVoorletters());
            statement.setString(2, inReiziger.getTussenvoegsel());
            statement.setString(3, inReiziger.getAchternaam());
            statement.setDate(4, inReiziger.getGeboortedatum());
            statement.setInt(5, inReiziger.getId());
            statement.executeUpdate();
            statement.close();
            if (inReiziger.getOvChipKaarten() != null) {
                for (OVChipKaart ovChipKaart : inReiziger.getOvChipKaarten()) {

                    this.ovChipKaartDaoPostgres.update(ovChipKaart);
                }
            }
            if(inReiziger.getAdres() != null) {
                this.adresDaoPostgres.update(inReiziger.getAdres());
            }
        } catch (SQLException throwables) {
            System.err.println("SQLExecption:" + throwables.getMessage());
        }
        return true;
    }

    @Override
    public boolean delete(Reiziger inReiziger) {
        try {
            if (inReiziger.getOvChipKaarten() != null) {
                for (OVChipKaart ovChipKaart : inReiziger.getOvChipKaarten()) {

                    this.ovChipKaartDaoPostgres.delete(ovChipKaart);
                }
            }
            if(inReiziger.getAdres() != null) {
                this.adresDaoPostgres.delete(inReiziger.getAdres());
            }
            PreparedStatement statement = this.connection.prepareStatement("DELETE FROM reiziger WHERE reiziger_id = ?;");
            statement.setInt(1, inReiziger.getId());
            statement.executeUpdate();
            statement.close();

        } catch (SQLException throwables) {
            System.err.println("SQLExecption:" + throwables.getMessage());
        }
        return true;
    }

    public Reiziger findById(int id) {
        Reiziger reiziger = new Reiziger();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM reiziger WHERE reiziger_id = ?;");
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                reiziger.fillFromResultSet(set);
                reiziger.setAdres(this.adresDaoPostgres.findByReiziger(reiziger));
                reiziger.setOvChipKaarten(this.ovChipKaartDaoPostgres.findByReiziger(reiziger));
            }
            set.close();
            statement.close();
        } catch (SQLException throwables) {
            System.err.println("SQLExecption:" + throwables.getMessage());
        }
        return reiziger;
    }

    @Override
    public ArrayList<Reiziger> findAll() {
        ArrayList<Reiziger> reizigers = new ArrayList<>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM reiziger");
            while (rs.next()) {
                Reiziger r = new Reiziger();
                r.fillFromResultSet(rs);
                r.setAdres(this.adresDaoPostgres.findByReiziger(r));
                r.setOvChipKaarten(this.ovChipKaartDaoPostgres.findByReiziger(r));
                reizigers.add(r);
            }
            rs.close();
            st.close();
        } catch (SQLException throwables) {
            System.err.println("SQLExecption:" + throwables.getMessage());
        }
        return reizigers;
    }
}
