package org.example.DataAccess;

import java.awt.Dimension;
import java.lang.reflect.Field;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * Clasa TableGenerator genereaza o tabela grafica din datele dintr-o lista data.
 */
public class TableGenerator {

    /**
     * Metoda generateClientTable genereaza o tabela grafica din datele dintr-o lista data.
     * Dacă lista este goala, se afiseaza un mesaj de avertizare.
     * Numele coloanelor sunt preluate din numele campurilor obiectelor din lista data,
     * iar datele din obiecte sunt afisate in randurile tabelului.
     *
     * @param dataList lista de obiecte din care se genereaza tabelul
     */
    public static void generateClientTable(List<?> dataList) {
        if (dataList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No data to display.");
            return;
        }

        Class<?> clazz = dataList.get(0).getClass();
        Field[] fields = clazz.getDeclaredFields();
        String[] columnNames = new String[fields.length];
        Object[][] rowData = new Object[dataList.size()][fields.length];
        for (int i = 0; i < fields.length; i++) {
            columnNames[i] = fields[i].getName();
        }
        for (int i = 0; i < dataList.size(); i++) {
            Object dataObj = dataList.get(i);
            for (int j = 0; j < fields.length; j++) {
                fields[j].setAccessible(true);
                try {
                    rowData[i][j] = fields[j].get(dataObj);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        JTable table = new JTable(rowData, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        JOptionPane.showMessageDialog(null, scrollPane, "JTable", JOptionPane.PLAIN_MESSAGE);
    }
}
