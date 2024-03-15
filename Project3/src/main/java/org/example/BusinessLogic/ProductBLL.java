package org.example.BusinessLogic;

import org.example.BusinessLogic.validators.ProductPriceValidator;
import org.example.BusinessLogic.validators.ProductStockValidator;
import org.example.BusinessLogic.validators.Validator;
import org.example.DataAccess.ProductDAO;
import org.example.Model.product;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Aceasta clasa ofera logica de afaceri pentru gestionarea produselor.
 */
public class ProductBLL {

    /**
     * Lista de validatori care trebuie aplicati la inserarea sau actualizarea unui produs.
     */
    private List<Validator<product>> validators;

    /**
     * Obiectul de acces la date pentru produse.
     */
    private static ProductDAO productDAO;

    /**
     * Construieste o noua instanta ProductBLL cu validatori impliciti si o noua instanta ProductDAO.
     */
    public ProductBLL() {
        validators = new ArrayList<Validator<product>>();
        validators.add(new ProductPriceValidator());
        validators.add(new ProductStockValidator());

        productDAO = new ProductDAO();
    }

    /**
     * Preia un produs dupa ID-ul sau.
     * @param id ID-ul produsului de preluat.
     * @return Produsul cu ID-ul dat, sau null daca nu exista un astfel de produs.
     */
    public product findProductById(int id) {
        product product = productDAO.getById(id);
        return product;
    }

    /**
     * Insereaza un nou produs in baza de date.
     * @param product Produsul de inserat.
     * @return ID-ul produsului nou inserat.
     */
    public int insertProduct(product product) {
        for (Validator<product> v : validators) {
            v.validate(product);
        }
        return productDAO.insert(product);
    }

    /**
     * Actualizeaza un produs existent in baza de date.
     * @param product Produsul de actualizat.
     */
    public void updateProduct(product product) {
        for (Validator<product> v : validators) {
            v.validate(product);
        }
        productDAO.update(product);
    }

    /**
     * Sterge un produs din baza de date.
     * @param id ID-ul produsului de sters.
     */
    public void deleteProduct(int id) {
        productDAO.delete(id);
    }

    /**
     * Preia toate produsele din baza de date.
     * @return O lista cu toate produsele din baza de date.
     */
    public static List<product> getAllProducts() {
        return productDAO.getAll();
    }
}
