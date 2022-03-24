import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import xyz.keesvanopdorp.DP_OV_Chipkaart.dao.adres.AdresDaoPostgres;
import xyz.keesvanopdorp.DP_OV_Chipkaart.dao.ovchipkaart.OVChipKaartDaoPostgres;
import xyz.keesvanopdorp.DP_OV_Chipkaart.dao.reiziger.ReizigerDaoPostgres;
import xyz.keesvanopdorp.DP_OV_Chipkaart.domain.Reiziger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReizigerDaoPostgresTest {
    private static Connection conn = null;
    private final static Dotenv dotenv = Dotenv.load();
    private static ReizigerDaoPostgres reizigerDaoPostgres;
    private static AdresDaoPostgres adresDaoPostgres;
    private static OVChipKaartDaoPostgres ovChipKaartDaoPostgres;

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

    /**
     * function to create the database and setup the dao's need in this test
     */
    @BeforeAll
    public static void setup() {
        createConnection();
        reizigerDaoPostgres = new ReizigerDaoPostgres(conn);
        adresDaoPostgres = new AdresDaoPostgres(conn);
        ovChipKaartDaoPostgres = new OVChipKaartDaoPostgres(conn);
        reizigerDaoPostgres.setOvChipKaartDaoPostgres(ovChipKaartDaoPostgres);
        reizigerDaoPostgres.setAdresDaoPostgres(adresDaoPostgres);
    }

    @AfterAll()
    public static void teardown() {
        closeConnection();
    }

    @Test
    public void testFindAll() {
        ArrayList<Reiziger> reizigers = reizigerDaoPostgres.findAll();
        assertEquals(true, reizigers != null);
    }

    @Test
    public void createReiziger() {
        try {
            Reiziger reiziger = new Reiziger();
            reiziger.setTussenvoegsel("van");
            reiziger.setAchternaam("Opdorp");
            reiziger.setVoorletters("J.C.");
            reiziger.setGeboortedatum(Date.valueOf("2001-06-22"));
            reiziger.setId(9999);
            reizigerDaoPostgres.save(reiziger);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testUpdateReiziger() {
        String surname = "Opdor";
        Reiziger oldReiziger = reizigerDaoPostgres.findById(9999);
        oldReiziger.setAchternaam(surname);
        reizigerDaoPostgres.update(oldReiziger);
        Reiziger newReiziger = reizigerDaoPostgres.findById(9999);
        assertEquals(newReiziger.getAchternaam(), surname);
    }
}
