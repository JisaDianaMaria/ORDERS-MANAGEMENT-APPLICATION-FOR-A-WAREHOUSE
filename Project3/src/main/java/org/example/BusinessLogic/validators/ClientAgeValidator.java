package org.example.BusinessLogic.validators;

import org.example.Model.client;

public class ClientAgeValidator implements Validator<client> {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 80;

    public void validate(client t) {

        if (t.getAge() < MIN_AGE || t.getAge() > MAX_AGE) {
            throw new IllegalArgumentException("The client Age limit is not respected!");
        }
    }
}

