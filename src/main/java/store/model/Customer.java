package store.model;

import store.view.Exception;

public class Customer {
    private static final String POSITIVE_RESPONSE = "Y";
    private static final String NEGATIVE_RESPONSE = "N";

    private final String response;

    public Customer(String response) {
        validateResponse(response);
        this.response = response;
    }

    private void validateResponse(String response) {
        if (!response.contains(POSITIVE_RESPONSE) && !response.contains(NEGATIVE_RESPONSE)) {
            Exception.getInvalidInput();
        }
        if (response.contains(POSITIVE_RESPONSE) && !response.equals(POSITIVE_RESPONSE)) {
            Exception.getInvalidInput();
        }
        if (response.contains(NEGATIVE_RESPONSE) && !response.equals(NEGATIVE_RESPONSE)) {
            Exception.getInvalidInput();
        }
    }

    public boolean isResponseYes() {
        boolean isResponseYes = response.equals(POSITIVE_RESPONSE);
        return isResponseYes;
    }
}