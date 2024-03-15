package org.example.Model;

/**
 * Clasa order reprezinta un obiect de tip order.
 */

public class order {

    private int id;
    private client client;
    private product product;
    private int quantity;


    /**
     * Constructorul clasei Order.
     *
     * @param client obiectul de tipul client asociat comenzii
     * @param product obiectul de tipul product asociat comenzii
     * @param quantity cantitatea produsului comandat
     */
    public order(client client, product product, int quantity) {
        super();
        this.client = client;
        this.product = product;
        this.quantity = quantity;
    }

    public order() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public client getClient() {
        return client;
    }

    public void setClient(client client) {
        this.client = client;
    }

    public product getProduct() {
        return product;
    }

    public void setProduct(product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Order: " + "id=" + id ;
    }

}
