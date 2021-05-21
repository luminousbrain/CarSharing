package ru.gontarenko.carsharing.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public final class H2Database {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.h2.Driver";
    static  String DB_URL = "jdbc:h2:D:\\Java\\repos\\JBAcademy\\Git\\CarSharing\\src\\main\\java\\ru\\gontarenko\\carsharing\\db\\";

    //  Database credentials
    static final String USER = "";
    static final String PASS = "";

    private H2Database() {}

    static {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void setDbFileName(String fileName) {
        DB_URL += Objects.requireNonNullElse(fileName, "default");
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }
}
