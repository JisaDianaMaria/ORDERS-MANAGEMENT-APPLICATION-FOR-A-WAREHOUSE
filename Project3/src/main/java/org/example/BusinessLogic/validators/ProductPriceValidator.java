package org.example.BusinessLogic.validators;

import org.example.Model.product;

public class ProductPriceValidator implements Validator<product> {
    private static final int MIN_PRICE = 1;
    private static final int MAX_PRICE = 100000;

    public void validate(product p) {

        if (p.getPrice() < MIN_PRICE || p.getPrice() > MAX_PRICE) {
            throw new IllegalArgumentException("The product Price limit is not respected!");
        }
    }
}

