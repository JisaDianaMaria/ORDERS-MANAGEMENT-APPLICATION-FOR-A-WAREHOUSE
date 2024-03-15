package org.example.BusinessLogic;

import org.example.BusinessLogic.validators.ClientAgeValidator;
import org.example.BusinessLogic.validators.OrderQuantityValidator;
import org.example.BusinessLogic.validators.Validator;
import org.example.DataAccess.OrderDAO;
import org.example.Model.order;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Aceasta clasa ofera logica de afaceri pentru gestionarea comenzilor.
 */
public class OrderBLL {

    /**
     * Lista de validatori care trebuie aplicati la inserarea sau actualizarea unei comenzi.
     */
    private List<Validator<order>> validators;
    /**
     * Obiectul de acces la date pentru comenzi.
     */
    private OrderDAO orderDAO;

    /**
     * Construieste o noua instanta OrderBLL cu validatori impliciti si o noua instanta OrderDAO.
     */
    public OrderBLL() {
        validators = new ArrayList<Validator<order>>();
        validators.add(new OrderQuantityValidator());

        orderDAO = new OrderDAO();
    }

    public void insertOrder(order order) {
        for (Validator<order> v : validators) {
            v.validate(order);
        }
        orderDAO.insert(order);
    }

    public static List<order> getAllOrders() {
        return OrderDAO.getAll();
    }
}
