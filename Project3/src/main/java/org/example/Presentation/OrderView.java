package org.example.Presentation;

import org.example.BusinessLogic.ClientBLL;
import org.example.BusinessLogic.OrderBLL;
import org.example.BusinessLogic.ProductBLL;
import org.example.DataAccess.TableGenerator;
import org.example.Model.client;
import org.example.Model.order;
import org.example.Model.product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;



/**
 *
 * Clasa OrderView reprezinta interfata grafica cu utilizatorul pentru sistemul de management al comenzilor.
 * Acesta extinde clasa JFrame si contine mai multe JPanels și JButtons care permit utilizatorului sa
 * adauge si sa vizualizeze comenzile plasate.
 * Clasa foloseste clasa OrderBLL pentru a interactiona cu baza de date si clasa TableGenerator pentru a genera tabele.
 */
public class OrderView extends JFrame {

    JPanel orderPanel,mainPanel;

    /**
     * Constructor pentru clasa OrderView.
     * Initializeaza panoul principal si seteaza butoanele si ascultatorii pentru sistemul de management al comenzilor.
     * @param mainPanel Panoul principal al aplicatiei.
     */
    public OrderView(JPanel mainPanel)
    {
        this.mainPanel = mainPanel;
        orderPanel = new JPanel(new GridLayout(5, 2));
        orderPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

        JLabel clientLabel = new JLabel("Client:");
        JComboBox<client> clientComboBox = new JComboBox<client>();
        ClientBLL clientBLL = new ClientBLL();
        java.util.List<client> clients = clientBLL.getAllClients();
        for (client c : clients) {
            clientComboBox.addItem(c);
        }

        JLabel productLabel = new JLabel("Product:");
        JComboBox<product> productComboBox = new JComboBox<product>();
        ProductBLL productBLL = new ProductBLL();
        List<product> products = productBLL.getAllProducts();
        for (product p : products) {
            productComboBox.addItem(p);
        }

        JLabel quantityLabel = new JLabel("Quantity:");
        JTextField quantityField = new JTextField();

        JButton orderBtn = new JButton("Order");
        JButton viewBtn = new JButton("View Orders");
        JButton closeBtn = new JButton("Close");

        orderPanel.add(clientLabel);
        orderPanel.add(clientComboBox);
        orderPanel.add(productLabel);
        orderPanel.add(productComboBox);
        orderPanel.add(quantityLabel);
        orderPanel.add(quantityField);
        orderPanel.add(orderBtn);
        orderPanel.add(viewBtn);
        orderPanel.add(closeBtn);

        remove(getContentPane());
        setContentPane(orderPanel);
        pack();
        setVisible(true);

        orderBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int clientId = clientComboBox.getItemAt(clientComboBox.getSelectedIndex()).getId();
                int productId =productComboBox.getItemAt(productComboBox.getSelectedIndex()).getId();
                int quantity = Integer.parseInt(quantityField.getText());

                ClientBLL clientBLL = new ClientBLL();
                client client = clientBLL.findClientById(clientId);

                ProductBLL productBLL = new ProductBLL();
                product product = productBLL.findProductById(productId);

                OrderBLL orderBLL = new OrderBLL();
                order newOrder = new order(client, product, quantity);
                orderBLL.insertOrder(newOrder);

                if(quantity<=product.getStock())
                    JOptionPane.showMessageDialog(null, "The order was created successfully!");

            }
        });

        viewBtn.addActionListener(new ActionListener() {
            /**
             * Se ocupa de clicul butonului View Orders afisand un JTable cu toate comenzile stocate in baza de date
             * @param e Evenimentul care a declanșat ascultătorul.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                List<order> orderList = OrderBLL.getAllOrders();
                TableGenerator.generateClientTable(orderList);

                remove(getContentPane());
                setContentPane(orderPanel);
                pack();
                setVisible(true);
            }
        });

        closeBtn.addActionListener(new ActionListener() {
            /**
             * Gestioneaza clicul butonului Close prin eliminarea cadrului OrderView.
             * @param e Evenimentul care a declansat ascultatorul.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
               dispose();
            }
        });

        remove(getContentPane());
        setContentPane(orderPanel);
        pack();
        setVisible(true);

    }
}


