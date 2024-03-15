package org.example.DataAccess;

import org.example.Connection.ConnectionFactory;
import org.example.Model.client;
import org.example.Model.order;
import org.example.Model.product;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Aceasta clasa este responsabila pentru accesul la datele tabelei 'order1' din baza de date.
 */
public class OrderDAO {

    private static final String TABLE_NAME = "order1";

    /**
     * Metoda getAll returneaza o lista cu toate comenzile din tabelul "order1" din baza de date.
     * @return o lista cu toate comenzile din tabelul "order1" din baza de date.
     */
    public static List<order> getAll() {
        List<order> orders = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql = "SELECT * FROM " + TABLE_NAME;
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                order order = new order();
                order.setId(rs.getInt("id"));
                client client = new client();
                client.setId(rs.getInt("client_id"));
                order.setClient(client);
                product product = new product();
                product.setId(rs.getInt("product_id"));
                order.setProduct(product);
                order.setQuantity(rs.getInt("quantity"));
                orders.add(order);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return orders;
    }

    /**
     * Metoda insert insereaza o comanda in tabelul "order1" din baza de date.
     * @param order comanda de inserat
     */
    public static void insert(order order) {
        int id = -1;
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql = "INSERT INTO " + TABLE_NAME + " (client_id, product_id, quantity) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, order.getClient().getId());
            stmt.setInt(2, order.getProduct().getId());
            stmt.setInt(3, order.getQuantity());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        product product = ProductDAO.getById(order.getProduct().getId());
        int newStock = product.getStock() - order.getQuantity();
        if (newStock < 0) {
            JOptionPane.showMessageDialog(null, "There is not enough stock");
        } else {
            product.setStock(newStock);
            ProductDAO.update(product);
        }
    }
}
