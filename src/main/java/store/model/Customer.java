package store.model;

import store.view.Exception;

public class Customer {
    private final String response;

    public Customer(String response) {
        validateResponse(response);
        this.response = response;
    }

    private void validateResponse(String response) {
        if (!response.contains("Y") && !response.contains("N")) {
            Exception.getInvalidInput();
        }
        if (response.contains("Y") && !response.equals("Y")) {
            Exception.getInvalidInput();
        }
        if (response.contains("N") && !response.equals("N")) {
            Exception.getInvalidInput();
        }
    }

    public boolean isResponseYes() {
        boolean isResponseYes = response.equals("Y");
        return isResponseYes;
    }
}