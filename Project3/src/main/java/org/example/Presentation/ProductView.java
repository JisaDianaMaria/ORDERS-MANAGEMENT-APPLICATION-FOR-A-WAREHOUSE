package org.example.Presentation;

import org.example.BusinessLogic.ProductBLL;
import org.example.DataAccess.TableGenerator;
import org.example.Model.product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 *
 * Clasa ClientView reprezinta interfata grafica cu utilizatorul pentru sistemul de management al produselor.
 * Acesta extinde clasa JFrame si contine mai multe JPanels și JButtons care permit utilizatorului sa
 * adauge, sa stearga, sa actualizeze si sa vizualizeze produse.
 * Clasa foloseste clasa ProductBLL pentru a interactiona cu baza de date si clasa TableGenerator pentru a genera tabele.
 */
public class ProductView extends JFrame  {

    JPanel productPanel,mainPanel;
    /**
     * Constructor pentru clasa ProductView.
     * Initializeaza panoul principal si seteaza butoanele si ascultatorii pentru sistemul de management al produselor.
     * @param mainPanel Panoul principal al aplicatiei.
     */
    public ProductView(JPanel mainPanel) {
        this.mainPanel = mainPanel;
        productPanel = new JPanel(new BorderLayout());
        productPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2));
        JButton addBtn = new JButton("Add Product");
        JButton deleteBtn = new JButton("Delete Product");
        JButton updateBtn = new JButton("Update Product");
        JButton viewBtn = new JButton("View Products");

        buttonPanel.add(addBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(viewBtn);

        addBtn.setFont(addBtn.getFont().deriveFont(12f));
        addBtn.setPreferredSize(new Dimension(200, 50));
        deleteBtn.setFont(deleteBtn.getFont().deriveFont(12f));
        deleteBtn.setPreferredSize(new Dimension(100, 20));
        updateBtn.setFont(updateBtn.getFont().deriveFont(12f));
        updateBtn.setPreferredSize(new Dimension(100, 20));
        viewBtn.setFont(viewBtn.getFont().deriveFont(12f));
        viewBtn.setPreferredSize(new Dimension(100, 20));

        JPanel closePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton closeBtn = new JButton("Close");
        closeBtn.setFont(closeBtn.getFont().deriveFont(12f));
        closeBtn.setPreferredSize(new Dimension(200, 50));
        closePanel.add(closeBtn);

        productPanel.add(buttonPanel, BorderLayout.NORTH);
        productPanel.add(closePanel, BorderLayout.SOUTH);

        closeBtn.addActionListener(new ActionListener() {
            /**
             * Gestioneaza clicul butonului Close prin eliminarea cadrului ProductView.
             * @param e Evenimentul care a declansat ascultatorul.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        addBtn.addActionListener(new ActionListener() {
            /**
             * Se ocupa de clicul butonului Add product afisand mai multe campuri care trebuie completate si
             * adaugand produsul corespunzator la baza de date.
             * @param e Evenimentul care a declansat ascultatorul.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel addProductPanel = new JPanel(new GridLayout(4, 2));
                addProductPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

                JLabel nameLabel = new JLabel("Name:");
                JTextField nameField = new JTextField();

                JLabel priceLabel = new JLabel("Price:");
                JTextField priceField = new JTextField();

                JLabel stockLabel = new JLabel("Stock:");
                JTextField stockField = new JTextField();

                JButton addBtn = new JButton("Add");
                JButton backBtn = new JButton("Back");

                addProductPanel.add(nameLabel);
                addProductPanel.add(nameField);
                addProductPanel.add(priceLabel);
                addProductPanel.add(priceField);
                addProductPanel.add(stockLabel);
                addProductPanel.add(stockField);
                addProductPanel.add(addBtn);
                addProductPanel.add(backBtn);

                remove(getContentPane());
                setContentPane(addProductPanel);
                pack();
                setVisible(true);

                addBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String name = nameField.getText();
                        Double price = Double.parseDouble(priceField.getText());
                        int stock = Integer.parseInt(stockField.getText());

                        ProductBLL productBLL = new ProductBLL();
                        product newProduct = new product(name, price, stock);
                        productBLL.insertProduct(newProduct);

                        JOptionPane.showMessageDialog(null, "The product was added successfully!");

                        remove(getContentPane());
                        setContentPane(productPanel);
                        pack();
                        setVisible(true);
                    }
                });
                backBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        remove(getContentPane());
                        setContentPane(productPanel);
                        pack();
                        setVisible(true);
                    }
                });
            }
        });

        deleteBtn.addActionListener(new ActionListener() {
            /**
             * Se ocupa de clicul butonului Delete product afisand campul pentru id care trebuie completat si
             * stergand produsul corespunzator din baza de date.
             * @param e Evenimentul care a declansat ascultatorul.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel deleteProductPanel = new JPanel(new GridLayout(2, 2));
                deleteProductPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

                JLabel idLabel = new JLabel("ID Product:");
                JTextField idField = new JTextField();

                JButton deleteBtn = new JButton("Delete");
                JButton backBtn = new JButton("Back");

                deleteProductPanel.add(idLabel);
                deleteProductPanel.add(idField);
                deleteProductPanel.add(deleteBtn);
                deleteProductPanel.add(backBtn);

                remove(getContentPane());
                setContentPane(deleteProductPanel);
                pack();
                setVisible(true);

                deleteBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int id = Integer.parseInt(idField.getText());
                        ProductBLL productBLL = new ProductBLL();
                        product productToDelete = productBLL.findProductById(id);
                        if (productToDelete != null) {
                            String message = "Are you sure you want to delete the following product?\n\n" + productToDelete.toString();
                            int confirm = JOptionPane.showConfirmDialog(null, message, "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                productBLL.deleteProduct(id);
                                JOptionPane.showMessageDialog(null, "The product was deleted successfully!");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "The product with ID " + id + " does not exist!");
                        }
                    }
                });

                backBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        remove(getContentPane());
                        setContentPane(productPanel);
                        pack();
                        setVisible(true);

                    }
                });
            }
        });

        updateBtn.addActionListener(new ActionListener() {
            /**
             * Se ocupa de clicul butonului Update product afisand mai multe campuri care trebuie completate si
             * modificand produsul corespunzator in baza de date.
             * @param e Evenimentul care a declanșat ascultătorul.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel updateProductPanel = new JPanel(new GridLayout(5, 2));
                updateProductPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

                JLabel idLabel = new JLabel("ID:");
                JTextField idField = new JTextField();

                JLabel nameLabel = new JLabel("Name:");
                JTextField nameField = new JTextField();

                JLabel priceLabel = new JLabel("Price:");
                JTextField priceField = new JTextField();

                JLabel stockLabel = new JLabel("Stock:");
                JTextField stockField = new JTextField();

                JButton updateBtn = new JButton("Update");
                JButton backBtn = new JButton("Back");

                updateProductPanel.add(idLabel);
                updateProductPanel.add(idField);
                updateProductPanel.add(nameLabel);
                updateProductPanel.add(nameField);
                updateProductPanel.add(priceLabel);
                updateProductPanel.add(priceField);
                updateProductPanel.add(stockLabel);
                updateProductPanel.add(stockField);
                updateProductPanel.add(updateBtn);
                updateProductPanel.add(backBtn);

                remove(getContentPane());
                setContentPane(updateProductPanel);
                pack();
                setVisible(true);

                updateBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int id = Integer.parseInt(idField.getText());
                        ProductBLL productBLL = new ProductBLL();
                        product productToUpdate = productBLL.findProductById(id);
                        if(productToUpdate != null) {
                            String name = nameField.getText();
                            double price = Double.parseDouble(priceField.getText());
                            int stock = Integer.parseInt(stockField.getText());

                            product updatedProduct = new product(id, name, price, stock);
                            productBLL.updateProduct(updatedProduct);

                            JOptionPane.showMessageDialog(null, "The product was updated successfully!");

                            remove(getContentPane());
                            setContentPane(productPanel);
                            pack();
                            setVisible(true);
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "This product doesn't exist!");
                        }
                    }
                });
                backBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        remove(getContentPane());
                        setContentPane(productPanel);
                        pack();
                        setVisible(true);
                    }
                });
            }
        });


        viewBtn.addActionListener(new ActionListener() {
            /**
             * Se ocupa de clicul butonului View products afisand un JTable cu toate produsele stocate in baza de date
             * @param e Evenimentul care a declanșat ascultătorul.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                List<product> productsList = ProductBLL.getAllProducts();
                TableGenerator.generateClientTable(productsList);

                remove(getContentPane());
                setContentPane(productPanel);
                pack();
                setVisible(true);
            }
        });

        remove(getContentPane());
        setContentPane(productPanel);
        pack();
        setVisible(true);
    }

}
