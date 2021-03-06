package xyz.keesvanopdorp.DP_OV_Chipkaart.dao.ovchipkaart;

import xyz.keesvanopdorp.DP_OV_Chipkaart.dao.product.ProductDaoPostgres;
import xyz.keesvanopdorp.DP_OV_Chipkaart.dao.reiziger.ReizigerDaoPostgres;
import xyz.keesvanopdorp.DP_OV_Chipkaart.domain.OVChipKaart;
import xyz.keesvanopdorp.DP_OV_Chipkaart.domain.Product;
import xyz.keesvanopdorp.DP_OV_Chipkaart.domain.Reiziger;
import xyz.keesvanopdorp.DP_OV_Chipkaart.util.SQLUtil;

import java.sql.*;
import java.util.ArrayList;

public class OVChipKaartDaoPostgres implements OVChipKaartDAO {
    private Connection connection;
    private ReizigerDaoPostgres reizigerDaoPostgres;
    private ProductDaoPostgres productDaoPostgres;

    public OVChipKaartDaoPostgres(Connection connection) {
        this.connection = connection;
    }

    public ReizigerDaoPostgres getReizigerDaoPostgres() {
        return reizigerDaoPostgres;
    }

    public void setReizigerDaoPostgres(ReizigerDaoPostgres reizigerDaoPostgres) {
        this.reizigerDaoPostgres = reizigerDaoPostgres;
    }

    public ProductDaoPostgres getProductDaoPostgres() {
        return productDaoPostgres;
    }

    public void setProductDaoPostgres(ProductDaoPostgres productDaoPostgres) {
        this.productDaoPostgres = productDaoPostgres;
    }

    @Override
    public boolean save(OVChipKaart inOVChipkaart) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO ov_chipkaart (kaart_nummer,geldig_tot, klasse, saldo, reiziger_id) VALUES (?,?,?,?,?)");
            statement.setInt(1, inOVChipkaart.getKaartNummer());
            statement.setDate(2, inOVChipkaart.getGeldigTot());
            statement.setInt(3, inOVChipkaart.getKlasse());
            statement.setBigDecimal(4, inOVChipkaart.getSaldo());
            statement.setInt(5, inOVChipkaart.getReiziger().getId());
            statement.executeQuery();
        } catch (SQLException throwables) {
            SQLUtil.printSqlException(throwables);
        }
        return true;
    }

    @Override
    public boolean update(OVChipKaart inOVChipkaart) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE ov_chipkaart SET geldig_tot = ?, klasse = ?, saldo = ?, reiziger_id =? WHERE kaart_nummer =?;");
            statement.setDate(1, inOVChipkaart.getGeldigTot());
            statement.setInt(2, inOVChipkaart.getKlasse());
            statement.setBigDecimal(3, inOVChipkaart.getSaldo());
            statement.setInt(4, inOVChipkaart.getReiziger().getId());
            statement.setInt(5, inOVChipkaart.getKaartNummer());
            statement.executeQuery();
        } catch (SQLException throwables) {
            SQLUtil.printSqlException(throwables);
        }
        return true;
    }

    public ArrayList<OVChipKaart> findByProduct(Product inProduct) {
        ArrayList<OVChipKaart> ovChipKaarten = new ArrayList<>();
        try {
            PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM ov_chipkaart WHERE kaart_nummer IN ((SELECT kaart_nummer FROM ov_chipkaart_product WHERE product_nummer = ?));");
            statement.setInt(1, inProduct.getProductNummer());
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                OVChipKaart ovChipKaart = new OVChipKaart();
                ovChipKaart.fillFromResultSet(set);
                ovChipKaarten.add(ovChipKaart);
            }
            set.close();
            statement.close();
        } catch (SQLException throwables) {
            SQLUtil.printSqlException(throwables);
        }
        return ovChipKaarten;
    }

    @Override
    public boolean delete(OVChipKaart inOVChipkaart) {
        try {
            PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM ov_chipkaart_product WHERE kaart_nummer = ?");
            deleteStatement.setInt(1, inOVChipkaart.getKaartNummer());
            deleteStatement.execute();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM ov_chipkaart WHERE kaart_nummer = ?");
            statement.setInt(1, inOVChipkaart.getKaartNummer());
            statement.executeQuery();
        } catch (SQLException throwables) {
            SQLUtil.printSqlException(throwables);
        }
        return true;
    }

    @Override
    public ArrayList<OVChipKaart> findByReiziger(Reiziger inReiziger) {
        ArrayList<OVChipKaart> ovChipKaarten = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM ov_chipkaart WHERE reiziger_id = ?");
            statement.setInt(1, inReiziger.getId());
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                OVChipKaart ovChipKaart = new OVChipKaart();
                ovChipKaart.fillFromResultSet(set);
                if (ovChipKaart.getReiziger() == null) {
                    ovChipKaart.setReiziger(inReiziger);
                }
                if (ovChipKaart.getProducten() != null) {
                    ovChipKaart.setProducten(this.productDaoPostgres.findByOvchipkaart(ovChipKaart));
                }
            }
            set.close();
            statement.close();
        } catch (SQLException throwables) {
            SQLUtil.printSqlException(throwables);
        }
        return ovChipKaarten;
    }

    @Override
    public ArrayList<OVChipKaart> findAll() {
        ArrayList<OVChipKaart> ovChipKaarten = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery("SELECT * FROM ov_chipkaart;");
            while (set.next()) {
                OVChipKaart ovChipKaart = new OVChipKaart();
                ovChipKaart.fillFromResultSet(set);
                if(ovChipKaart.getReiziger() == null) {
                    ovChipKaart.setReiziger(this.reizigerDaoPostgres.findById(set.getInt("reiziger_id")));
                }
                ovChipKaarten.add(ovChipKaart);
            }
            set.close();
            statement.close();
        } catch (SQLException throwables) {
            SQLUtil.printSqlException(throwables);
        }
        return ovChipKaarten;
    }
}
