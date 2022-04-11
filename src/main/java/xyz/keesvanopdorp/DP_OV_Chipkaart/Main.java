package xyz.keesvanopdorp.DP_OV_Chipkaart;

import io.github.cdimascio.dotenv.Dotenv;
import xyz.keesvanopdorp.DP_OV_Chipkaart.dao.adres.AdresDaoPostgres;
import xyz.keesvanopdorp.DP_OV_Chipkaart.dao.ovchipkaart.OVChipKaartDaoPostgres;
import xyz.keesvanopdorp.DP_OV_Chipkaart.dao.product.ProductDaoPostgres;
import xyz.keesvanopdorp.DP_OV_Chipkaart.dao.reiziger.ReizigerDAO;
import xyz.keesvanopdorp.DP_OV_Chipkaart.dao.reiziger.ReizigerDaoPostgres;
import xyz.keesvanopdorp.DP_OV_Chipkaart.domain.Reiziger;

import java.sql.*;
import java.util.List;
import java.util.Properties;

public class Main {
    private static Connection conn = null;
    private final static Dotenv dotenv = Dotenv.load();

    private static void createConnection() {
        if (conn == null) {
            String url = dotenv.get("DB_URL");
            // creates a property object
            Properties props = new Properties();
            // sets the user property on the props
            props.setProperty("user", dotenv.get("DB_USER"));
            // sets the user property on the props
            props.setProperty("password", dotenv.get("DB_PASSWORD"));
            try {
                // use the props and base url to create a db connection
                conn = DriverManager.getConnection(url, props);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // create database connection
        createConnection();

        //<editor-fold desc="definition Dao for classes">
        ReizigerDaoPostgres reizigerDaoPostgres = new ReizigerDaoPostgres(conn);
        AdresDaoPostgres adresDaoPostgres = new AdresDaoPostgres(conn);
        OVChipKaartDaoPostgres ovChipKaartDaoPostgres = new OVChipKaartDaoPostgres(conn);
        ProductDaoPostgres productDaoPostgres = new ProductDaoPostgres(conn);
        ovChipKaartDaoPostgres.setReizigerDaoPostgres(reizigerDaoPostgres);
        reizigerDaoPostgres.setAdresDaoPostgres(adresDaoPostgres);
        reizigerDaoPostgres.setOvChipKaartDaoPostgres(ovChipKaartDaoPostgres);
        adresDaoPostgres.setReizigerDaoPostgres(reizigerDaoPostgres);
        ovChipKaartDaoPostgres.setProductDaoPostgres(productDaoPostgres);
        //</editor-fold>

        try {
            //<editor-fold desc="opdracht 1 & 2">
            System.out.println("Alle reizigers: ");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM REIZIGER");
            while (rs != null && rs.next()) {
                String name;
                String insertion = rs.getString("tussenvoegsel");
                String initial = rs.getString("voorletters");
                String lastName = rs.getString("achternaam");
                if (insertion != null) {
                    name = String.format("%s %s %s", initial, insertion, lastName);
                } else {
                    name = String.format("%s %s", initial, lastName);
                }
                System.out.printf("#%s %s (%s)%n", rs.getInt("reiziger_id"), name, rs.getString("geboortedatum"));
            }
//            testReizigerDAO(reizigerDaoPostgres);
            //</editor-fold>

            System.out.println(reizigerDaoPostgres.findAll());
            System.out.println(adresDaoPostgres.findAll());
            System.out.println(reizigerDaoPostgres.findById(0));
            System.out.println(ovChipKaartDaoPostgres.findAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //close the connection to the database
        closeConnection();
    }

    /**
     * P2. Reiziger DAO: persistentie van een klasse
     * <p>
     * Deze methode test de CRUD-functionaliteit van de Reiziger DAO
     */
    private static void testReizigerDAO(ReizigerDAO rdao) {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();

        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger();
        sietske.setAchternaam("Broers");
        sietske.setGeboortedatum(Date.valueOf(gbdatum));
        sietske.setVoorletters("S");
        sietske.setTussenvoegsel("");
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
    }
}