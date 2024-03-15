package org.example.BusinessLogic;
import org.example.BusinessLogic.validators.ClientAgeValidator;
import org.example.BusinessLogic.validators.Validator;
import org.example.DataAccess.ClientDAO;
import org.example.Model.client;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Aceasta clasa ofera logica de afaceri pentru gestionarea clientilor.
 */
public class ClientBLL {

    /**
     * Lista de validatori care trebuie aplicati la inserarea sau actualizarea unui client.
     */
    private List<Validator<client>> validators;

    /**
     * Obiectul de acces la date pentru clienti.
     */
    private static ClientDAO clientDAO;

    /**
     * Construieste o noua instanta ClientBLL cu validatori impliciti si o noua instanta ClientDAO.
     */
    public ClientBLL() {
        validators = new ArrayList<Validator<client>>();
        validators.add(new ClientAgeValidator());

        clientDAO = new ClientDAO();
    }

    /**
     * Preia un client dupa ID-ul sau.
     * @param id ID-ul clientului de preluat.
     * @return Clientul cu ID-ul dat, sau null daca nu exista un astfel de client.
     */
    public client findClientById(int id) {
        client client = clientDAO.getById(id);
        return client;
    }

    /**
     * Insereaza un nou client in baza de date.
     * @param client Clientul de inserat.
     * @return ID-ul clientului nou inserat.
     */
    public int insertClient(client client) {
        for (Validator<client> v : validators) {
            v.validate(client);
        }
        return clientDAO.insert(client);
    }

    /**
     * Actualizeaza un client existent in baza de date.
     * @param client Clientul de actualizat.
     */
    public void updateClient(client client) {
        for (Validator<client> v : validators) {
            v.validate(client);
        }
        clientDAO.update(client);
    }


    /**
     * Sterge un client din baza de date.
     * @param id ID-ul clientului de sters.
     */
    public void deleteClient(int id) {
        clientDAO.delete(id);
    }

    /**
     * Preia toti clientii din baza de date.
     * @return O lista cu toti clientii din baza de date.
     */
    public static List<client> getAllClients() {
        return clientDAO.getAll();
    }

}
