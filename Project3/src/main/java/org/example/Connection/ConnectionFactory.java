package org.example.Connection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * Clasa ConnectionFactory este utilizata pentru a stabili o conexiune cu o baza de date MySQL.
 * Aceasta contine metode pentru a crea o conexiune, a obtine o conexiune si a inchide diverse obiecte de baza de date.
 *
 */
public class ConnectionFactory {

    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/warehouse";
    private static final String USER = "root";
    private static final String PASS = "Dianamaria28!";

    private static ConnectionFactory singleInstance = new ConnectionFactory();

    /**
     * Constructorul privat previne instantierea clasei ConnectionFactory din exterior.
     * Incarca clasa driver-ului de baza de date.
     */
    private ConnectionFactory() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creeaza o conexiune cu baza de date folosind URL-ul, numele de utilizator și parola specificate.
     *
     * @return un obiect Connection care reprezinta conexiunea cu baza de date
     * @throws SQLException daca apare o eroare de acces la baza de date sau URL-ul este null
     */
    private Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DBURL, USER, PASS);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "An error occured while trying to connect to the database");
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Returneaza un obiect Connection care reprezinta conexiunea cu baza de date.
     *
     * @return un obiect Connection care reprezinta conexiunea cu baza de date
     */
    public static Connection getConnection() {
        return singleInstance.createConnection();
    }

    /**
     * Inchide obiectul Connection specificat.
     *
     * @param connection obiectul Connection de inchis
     */
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the connection");
            }
        }
    }

    /**
     * Inchide obiectul Statement specificat.
     *
     * @param statement obiectul Statement de inchis
     */
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the statement");
            }
        }
    }


    /**
     * Inchide obiectul ResultSet specificat.
     *
     * @param resultSet obiectul ResultSet de inchis
     */
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the ResultSet");
            }
        }
    }
}
