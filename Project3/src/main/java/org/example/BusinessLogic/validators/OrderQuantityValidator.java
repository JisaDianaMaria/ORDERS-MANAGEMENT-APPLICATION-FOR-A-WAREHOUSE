package org.example.BusinessLogic.validators;

import org.example.Model.order;

public class OrderQuantityValidator implements Validator<order> {
    private static final int MIN_QUANTITY = 0;

    public void validate(order o) {

        if (o.getQuantity() <= MIN_QUANTITY ) {
            throw new IllegalArgumentException("The order Quantity limit is not respected!");
        }
    }
}

