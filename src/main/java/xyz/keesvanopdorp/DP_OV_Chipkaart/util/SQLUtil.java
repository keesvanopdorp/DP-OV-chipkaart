package xyz.keesvanopdorp.DP_OV_Chipkaart.util;

import java.sql.SQLException;

public class SQLUtil {

    public static void printSqlException(SQLException throwables) {
        System.err.println("SQLException:" + throwables.getMessage());
    }
}
