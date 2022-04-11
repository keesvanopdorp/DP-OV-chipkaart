package xyz.keesvanopdorp.DP_OV_Chipkaart.dao.product;

import xyz.keesvanopdorp.DP_OV_Chipkaart.domain.OVChipKaart;
import xyz.keesvanopdorp.DP_OV_Chipkaart.domain.Product;
import xyz.keesvanopdorp.DP_OV_Chipkaart.util.SQLUtil;

import java.sql.*;
import java.util.ArrayList;

public class ProductDaoPostgres implements ProductDAO {
    private Connection connection;

    public ProductDaoPostgres(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean save(Product inProduct) {
        try {
            PreparedStatement statement = this.connection.prepareStatement("INSERT INTO product(product_nummer, beschrijving, naam, prijs) VALUES (?,?,?,?);");
            statement.setInt(1, inProduct.getProductNummer());
            statement.setString(2, inProduct.getBeschrijving());
            statement.setString(3, inProduct.getNaam());
            statement.setDouble(4, inProduct.getPrijs());
            statement.executeQuery();
            statement.close();
        } catch (SQLException throwables) {
            SQLUtil.printSqlException(throwables);
        }
        return true;
    }

    @Override
    public boolean update(Product inProduct) {
        try {
            PreparedStatement statement = this.connection.prepareStatement("UPDATE product SET naam = ?, beschrijving = ?, prijs = ? WHERE product_nummer = ?;");
            statement.setString(1, inProduct.getNaam());
            statement.setString(2, inProduct.getBeschrijving());
            statement.setDouble(3, inProduct.getPrijs());
            statement.setInt(4, inProduct.getProductNummer());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException throwables) {
            SQLUtil.printSqlException(throwables);
        }
        return true;
    }

    @Override
    public ArrayList<Product> findAll() {
        ArrayList<Product> products = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            ResultSet set = statement.executeQuery("SELECT * FROM ov_chipkaart_product;");
            while (set.next()) {
                Product product = new Product();
                product.fillFromResultSet(set);
                products.add(product);
            }
            statement.close();
            set.close();
        } catch (SQLException throwables) {
            SQLUtil.printSqlException(throwables);
        }
        return products;
    }

    @Override
    public ArrayList<Product> findByOvchipkaart(OVChipKaart inOvChipKaart) {
        ArrayList<Product> products = new ArrayList<>();
        try {
            PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM product WHERE product_nummer IN ((SELECT product_nummer FROM ov_chipkaart_product WHERE kaart_nummer = ?));");
            statement.setInt(1, inOvChipKaart.getKaartNummer());
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                Product product = new Product();
                product.fillFromResultSet(set);
                products.add(product);
            }
            set.close();
            statement.close();
        } catch (SQLException throwables) {
            SQLUtil.printSqlException(throwables);
        }
        return products;
    }

    @Override
    public Product findById(int id) {
        Product product = new Product();
        try {
            PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM product WHERE product_nummer = ?;");
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                product.fillFromResultSet(set);
            }
            set.close();
            statement.close();
        } catch (SQLException throwables) {
            SQLUtil.printSqlException(throwables);
        }
        return product;
    }
}
