package org.example.Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * Clasa MainGUI este principala interfata grafica cu utilizatorul a sistemului de management al stocurilor.
 * Extinde JFrame și implementează ActionListener.
 * Clasa afișeaza trei butoane pentru a efectua diferite operatiuni asupra clientilor, produselor si comenzilor.
 * Clasa asculta, de asemenea, clicurile pe buton si lanseaza vizualizarea corespunzatoare pe baza butonului
 * pe care s-a facut clic.
 */
public class MainGUI extends JFrame implements ActionListener {

    JButton clientBtn, productBtn, orderBtn;
    JPanel mainPanel;

    /**
     * Constructor pentru clasa MainGUI.
     * Initializeaza interfața grafica setand titlul, dimensiunea și locația cadrului.
     * Creeaza panoul principal si ii adauga butoanele client, produs și comanda.
     * Seteaza dimensiunea preferata a panoului principal și o adaugă la container.
     */
    public MainGUI() {
        setTitle("Inventory Management System");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        clientBtn = new JButton("Client Operations");
        clientBtn.addActionListener(this);
        clientBtn.setFont(clientBtn.getFont().deriveFont(16f));
        clientBtn.setPreferredSize(new Dimension(300, 50));
        mainPanel.add(clientBtn, gbc);

        gbc.gridy = 1;
        productBtn = new JButton("Product Operations");
        productBtn.addActionListener(this);
        productBtn.setFont(productBtn.getFont().deriveFont(16f));
        productBtn.setPreferredSize(new Dimension(300, 50));
        mainPanel.add(productBtn, gbc);

        gbc.gridy = 2;
        orderBtn = new JButton("Create Product Order");
        orderBtn.addActionListener(this);
        orderBtn.setFont(orderBtn.getFont().deriveFont(16f));
        orderBtn.setPreferredSize(new Dimension(300, 50));
        mainPanel.add(orderBtn, gbc);

        mainPanel.setPreferredSize(new Dimension(500, 400));

        JPanel container = new JPanel(new BorderLayout());
        container.add(mainPanel, BorderLayout.CENTER);

        add(container);
        setVisible(true);
    }



    /**
     * Implementarea ActionListener care asculta clicurile pe butoane.
     * Lanseaza vizualizarea corespunzatoare pe baza butonului pe care s-a facut clic.
     * @param e Evenimentul de acțiune care a avut loc.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == clientBtn) {
            ClientView c = new ClientView(mainPanel);
            c.setTitle("Client Operations");

        } else if (e.getSource() == productBtn) {

           ProductView p = new ProductView(mainPanel);
           p.setTitle("Product Operations");

        } else if (e.getSource() == orderBtn) {

            OrderView p = new OrderView(mainPanel);
            p.setTitle("Create Product Order");
        }
    }


    /**
     * Metoda principala pentru a porni GUI.
     */
    public static void main(String[] args) {
        new MainGUI();
    }
}
