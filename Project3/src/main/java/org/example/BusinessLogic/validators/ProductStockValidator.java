package org.example.BusinessLogic.validators;

import org.example.Model.product;

public class ProductStockValidator implements Validator<product> {
    private static final int MIN_STOCK = 0;
    private static final int MAX_STOCK = 5000;

    public void validate(product p) {

        if (p.getStock() <= MIN_STOCK || p.getStock() > MAX_STOCK) {
            throw new IllegalArgumentException("The product Stock limit is not respected!");
        }
    }
}