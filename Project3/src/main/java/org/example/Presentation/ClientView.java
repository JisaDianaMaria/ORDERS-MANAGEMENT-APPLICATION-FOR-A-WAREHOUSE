package org.example.Presentation;

import org.example.BusinessLogic.ClientBLL;
import org.example.DataAccess.TableGenerator;
import org.example.Model.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


/**
 *
 * Clasa ClientView reprezinta interfata grafica cu utilizatorul pentru sistemul de management al clienților.
 * Acesta extinde clasa JFrame si contine mai multe JPanels și JButtons care permit utilizatorului sa
 * adauge, sa stearga, sa actualizeze si sa vizualizeze clientii.
 * Clasa foloseste clasa ClientBLL pentru a interactiona cu baza de date si clasa TableGenerator pentru a genera tabele.
 */
public class ClientView extends JFrame {

    JPanel clientPanel,mainPanel;

    /**
     * Constructor pentru clasa ClientView.
     * Inițializeaza panoul principal si seteaza butoanele si ascultatorii pentru sistemul de management al clientilor.
     * @param mainPanel Panoul principal al aplicatiei.
     */
    public ClientView(JPanel mainPanel)
    {
        this.mainPanel = mainPanel;
        clientPanel = new JPanel(new BorderLayout());
        clientPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2));
        JButton addBtn = new JButton("Add Cient");
        JButton deleteBtn = new JButton("Delete Client");
        JButton updateBtn = new JButton("Update Client");
        JButton viewBtn = new JButton("View Clients");
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

        clientPanel.add(buttonPanel, BorderLayout.NORTH);
        clientPanel.add(closePanel, BorderLayout.SOUTH);


        closeBtn.addActionListener(new ActionListener() {
            /**
             * Gestioneaza clicul butonului Close prin eliminarea cadrului ClientView.
             * @param e Evenimentul care a declansat ascultatorul.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        addBtn.addActionListener(new ActionListener() {
            /**
             * Se ocupa de clicul butonului Add client afisand mai multe campuri care trebuie completate si
             * adaugand clientul corespunzator la baza de date.
             * @param e Evenimentul care a declansat ascultatorul.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel addClientPanel = new JPanel(new GridLayout(4, 2));
                addClientPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

                JLabel nameLabel = new JLabel("Name:");
                JTextField nameField = new JTextField();

                JLabel addressLabel = new JLabel("Address:");
                JTextField addressField = new JTextField();

                JLabel ageLabel = new JLabel("Age:");
                JTextField ageField = new JTextField();

                JButton addBtn = new JButton("Add");
                JButton backBtn = new JButton("Back");

                addClientPanel.add(nameLabel); addClientPanel.add(nameField);
                addClientPanel.add(addressLabel); addClientPanel.add(addressField);
                addClientPanel.add(ageLabel); addClientPanel.add(ageField);
                addClientPanel.add(addBtn); addClientPanel.add(backBtn);

                remove(getContentPane());
                setContentPane(addClientPanel);
                pack();
                setVisible(true);

                addBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String name = nameField.getText();
                        String address = addressField.getText();
                        int age = Integer.parseInt(ageField.getText());

                        ClientBLL clientBLL = new ClientBLL();
                        client newClient = new client(name, address, age);
                        clientBLL.insertClient(newClient);

                        JOptionPane.showMessageDialog(null, "The client was added successfully!");

                        remove(getContentPane());
                        setContentPane(clientPanel);
                        pack();
                        setVisible(true);
                    }
                });
                backBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        remove(getContentPane());
                        setContentPane(clientPanel);
                        pack();
                        setVisible(true);
                    }
                });
            }
        });

        deleteBtn.addActionListener(new ActionListener() {
            /**
             * Se ocupa de clicul butonului Delete client afisand campul pentru id care trebuie completat si
             * stergand clientul corespunzator din baza de date.
             * @param e Evenimentul care a declansat ascultatorul.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel deleteClientPanel = new JPanel(new GridLayout(2, 2));
                deleteClientPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

                JLabel idLabel = new JLabel("ID Client:");
                JTextField idField = new JTextField();

                JButton deleteBtn = new JButton("Delete");
                JButton backBtn = new JButton("Back");

                deleteClientPanel.add(idLabel);  deleteClientPanel.add(idField);
                deleteClientPanel.add(deleteBtn);  deleteClientPanel.add(backBtn);

                remove(getContentPane());
                setContentPane(deleteClientPanel);
                pack();
                setVisible(true);

                deleteBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int id = Integer.parseInt(idField.getText());
                        ClientBLL clientBLL = new ClientBLL();
                        client clientToDelete = clientBLL.findClientById(id);
                        if (clientToDelete != null) {
                            String message = "Are you sure you want to delete the following client?\n\n" + clientToDelete.toString();
                            int confirm = JOptionPane.showConfirmDialog(null, message, "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                clientBLL.deleteClient(id);
                                JOptionPane.showMessageDialog(null, "The client was deleted successfully!");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "The client with ID " + id + " does not exist!");
                        }
                    }
                });
                backBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        remove(getContentPane());
                        setContentPane(clientPanel);
                        pack();
                        setVisible(true);
                    }
                });
            }
        });

        updateBtn.addActionListener(new ActionListener() {
            /**
             * Se ocupa de clicul butonului Update client afisand mai multe campuri care trebuie completate si
             * modificand clientul corespunzator in baza de date.
             * @param e Evenimentul care a declanșat ascultătorul.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel updateClientPanel = new JPanel(new GridLayout(5, 2));
                updateClientPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

                JLabel idLabel = new JLabel("ID:");
                JTextField idField = new JTextField();

                JLabel nameLabel = new JLabel("Name:");
                JTextField nameField = new JTextField();

                JLabel addressLabel = new JLabel("Address:");
                JTextField addressField = new JTextField();

                JLabel ageLabel = new JLabel("Age:");
                JTextField ageField = new JTextField();

                JButton updateBtn = new JButton("Update");
                JButton backBtn = new JButton("Back");

                updateClientPanel.add(idLabel);
                updateClientPanel.add(idField);
                updateClientPanel.add(nameLabel);
                updateClientPanel.add(nameField);
                updateClientPanel.add(addressLabel);
                updateClientPanel.add(addressField);
                updateClientPanel.add(ageLabel);
                updateClientPanel.add(ageField);
                updateClientPanel.add(updateBtn);
                updateClientPanel.add(backBtn);

                remove(getContentPane());
                setContentPane(updateClientPanel);
                pack();
                setVisible(true);

                updateBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int id = Integer.parseInt(idField.getText());
                        ClientBLL clientBLL = new ClientBLL();
                        client clientToUpdate = clientBLL.findClientById(id);
                        if(clientToUpdate != null) {
                            String name = nameField.getText();
                            String address = addressField.getText();
                            int age = Integer.parseInt(ageField.getText());

                            client updatedClient = new client(id, name, address, age);
                            clientBLL.updateClient(updatedClient);

                            JOptionPane.showMessageDialog(null, "The client was updated successfully!");

                            remove(getContentPane());
                            setContentPane(clientPanel);
                            pack();
                            setVisible(true);
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "This client doesn't exist!");
                        }
                    }
                });
                backBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        remove(getContentPane());
                        setContentPane(clientPanel);
                        pack();
                        setVisible(true);
                    }
                });
            }
        });


        viewBtn.addActionListener(new ActionListener() {
            /**
             * Se ocupa de clicul butonului View clients afisand un JTable cu toti clientii stocati in baza de date
             * @param e Evenimentul care a declanșat ascultătorul.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                List<client> clientList = ClientBLL.getAllClients();
                TableGenerator.generateClientTable(clientList);

                remove(getContentPane());
                setContentPane(clientPanel);
                pack();
                setVisible(true);
            }
        });
        remove(getContentPane());
        setContentPane(clientPanel);
        pack();
        setVisible(true);

    }
}

