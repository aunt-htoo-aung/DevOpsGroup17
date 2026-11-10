package com.devops.group17;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection {

    /**
     * Establishes and returns a connection to the MySQL database.
     * Uses default location ("db:3306" or system property/env "DB_LOCATION")
     * and delay of 10000ms.
     *
     * @return a java.sql.Connection to the world database, or null if all retries fail
     */
    public static java.sql.Connection getConnection() {
        String location = System.getProperty("db.location",
                System.getenv().getOrDefault("DB_LOCATION", "db:3306"));
        return getConnection(location, 10000);
    }

    /**
     * Establishes and returns a connection to the MySQL database at specified location.
     *
     * @param location the host:port of the database (e.g. "localhost:33060" or "db:3306")
     * @param delay    delay in milliseconds between retry attempts
     * @return a java.sql.Connection or null if all retries fail
     */
    public static java.sql.Connection getConnection(String location, int delay) {
        return getConnection(location, delay, 10);
    }

    /**
     * Establishes and returns a connection to the MySQL database at specified location with retries.
     *
     * @param location the host:port of the database
     * @param delay    delay in milliseconds between retry attempts
     * @param retries  number of retry attempts
     * @return a java.sql.Connection or null if all retries fail
     */
    public static java.sql.Connection getConnection(String location, int delay, int retries) {
        try {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            return null;
        }

        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database at " + location + " (attempt " + (i + 1) + ")...");
            try {
                java.sql.Connection con = DriverManager.getConnection(
                        "jdbc:mysql://" + location + "/world?allowPublicKeyRetrieval=true&useSSL=false",
                        "root", "abc123!@#");
                System.out.println("Successfully connected to " + location);
                return con;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " + i);
                System.out.println(sqle.getMessage());
            }

            if (delay > 0 && i < retries - 1) {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException ie) {
                    System.out.println("Thread interrupted? Should not happen.");
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
        return null;
    }
}
