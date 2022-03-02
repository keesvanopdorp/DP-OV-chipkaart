package xyz.keesvanopdorp.DP_OV_Chipkaart;
import xyz.keesvanopdorp.DP_OV_Chipkaart.dao.ReizigerDaoPostgres;

import java.sql.*;
import java.util.Properties;

public class Main {
    private static Connection conn = null;

    private static void createConnection() {
        if(conn == null) {
            String url = "jdbc:postgresql://localhost/ovchip";
            // creates a property object
            Properties props = new Properties();
            // sets the user property on the props
            props.setProperty("user", "postgres");
            // sets the user property on the props
            props.setProperty("password", "Welkom@14291722");
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
            if(conn != null) {
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        createConnection();
        System.out.println("Alle reizigers: ");
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * from reiziger");
            while (rs != null && rs.next()) {
                String name;
                String insertion = rs.getString("tussenvoegsel");
                String initial = rs.getString("voorletters");
                String lastName = rs.getString("achternaam");
                if (insertion != null) {
                    name = String.format("%s %s %s",initial ,insertion, lastName);
                } else {
                    name = String.format("%s %s", initial, lastName);
                }
                System.out.printf("#%s %s (%s)%n", rs.getInt("reiziger_id"), name, rs.getString("geboortedatum"));
            }
            System.out.println(new ReizigerDaoPostgres(conn).findAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
    }
}