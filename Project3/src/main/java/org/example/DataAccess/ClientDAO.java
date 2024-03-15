package org.example.DataAccess;

import org.example.Connection.ConnectionFactory;
import org.example.Model.client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Aceasta clasa este responsabila pentru accesul la datele tabelei 'client' din baza de date.
 */
public class ClientDAO {

    /**
     * Numele tabelului de clienti din baza de date.
     */
    private static final String TABLE_NAME = "client";

    /**
     * Metoda getAll returneaza o lista cu toti clientii din tabelul "client" din baza de date.
     * @return o lista cu toti clientii din tabelul "client" din baza de date.
     */
    public static List<client> getAll() {
        List<client> clients = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql = "SELECT * FROM " + TABLE_NAME;
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                client client = new client();
                client.setId(rs.getInt("id"));
                client.setName(rs.getString("name"));
                client.setAddress(rs.getString("address"));
                client.setAge(rs.getInt("age"));
                clients.add(client);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return clients;
    }

    /**
     * Metoda getById returneaza clientul din tabelul "client" cu id-ul dat ca parametru.
     * @param id id-ul clientului cautat
     * @return clientul cu id-ul specificat
     */
    public static client getById(int id) {
        client client = null;
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                client = new client();
                client.setId(rs.getInt("id"));
                client.setName(rs.getString("name"));
                client.setAddress(rs.getString("address"));
                client.setAge(rs.getInt("age"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return client;
    }

    /**
     * Metoda insert insereaza un client in tabelul "client" din baza de date.
     * @param client clientul de inserat
     * @return id-ul clientului inserat
     */
    public static int insert(client client) {
        int id = -1;
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql = "INSERT INTO " + TABLE_NAME + " (name, address, age) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, client.getName());
            stmt.setString(2, client.getAddress());
            stmt.setInt(3, client.getAge());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return id;
    }

    /**
     * Metoda update actualizeaza un client din tabelul "client" din baza de date.
     * @param client clientul de actualizat
     */
    public static void update(client client) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql = "UPDATE " + TABLE_NAME + " SET name=?, address=?, age=? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, client.getName());
            stmt.setString(2, client.getAddress());
            stmt.setInt(3, client.getAge());
            stmt.setInt(4, client.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Metoda delete sterge un client din tabelul "client" din baza de date.
     * @param id id-ul clientului care trebuie sters
     */

    public static void delete(int id) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql = "DELETE FROM " + TABLE_NAME + " WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch(SQLException ex){
                ex.printStackTrace();
            }
        }
    }