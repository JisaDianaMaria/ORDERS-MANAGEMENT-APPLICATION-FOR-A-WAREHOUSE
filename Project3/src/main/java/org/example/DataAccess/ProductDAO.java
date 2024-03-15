package org.example.DataAccess;

import org.example.Connection.ConnectionFactory;
import org.example.Model.client;
import org.example.Model.product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Aceasta clasa este responsabila pentru accesul la datele tabelei 'product' din baza de date.
 */
public class ProductDAO {

    /**
     * Numele tabelului de produse din baza de date.
     */
    private static final String TABLE_NAME = "product";

    /**
     * Metoda getAll returneaza o lista cu toate produsele din tabelul "product" din baza de date.
     * @return o lista cu toate produsele din tabelul "product" din baza de date.
     */
    public static List<product> getAll() {
        List<product> products = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql = "SELECT * FROM " + TABLE_NAME;
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                product product = new product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setStock(rs.getInt("stock"));
                products.add(product);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return products;
    }

    /**
     * Metoda getById returneaza prosudul din tabelul "product" cu id-ul dat ca parametru.
     * @param id id-ul produsului cautat
     * @return produsul cu id-ul specificat
     */
    public static product getById(int id) {
        product product = null;
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                product = new product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setStock(rs.getInt("stock"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return product;
    }

    /**
     * Metoda insert insereaza un produs în tabelul "product" din baza de date.
     * @param product produsul de inserat
     * @return id-ul produsului inserat
     */
    public static int insert(product product) {
        int id = -1;
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql = "INSERT INTO " + TABLE_NAME + " (name, price, stock) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getStock());
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
     * Metoda update actualizeaza un produs din tabelul "product" din baza de date.
     * @param product produsul de actualizat
     */
    public static void update(product product) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql = "UPDATE " + TABLE_NAME + " SET name=?, price=?, stock=? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getStock());
            stmt.setInt(4, product.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Metoda delete sterge un produs din tabelul "product" din baza de date.
     * @param id id-ul produsului care trebuie sters
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