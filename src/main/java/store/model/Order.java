package store.model;

import java.util.LinkedHashMap;
import java.util.Map;
import store.view.Exception;

public class Order {
    private static final String OPENING_CHARACTER = "[";
    private static final String CLOSING_CHARACTER = "]";
    private static final String SEPARATOR_CHARACTER = "-";
    private static final String DELIMITER = ",";

    private static final int INDEX_UNIT = 1;
    private static final int ZERO_VALUE = 0;

    private final Map<String, Integer> order;

    public Order(String orderRequest) {
        order = new LinkedHashMap<>();
        validateString(orderRequest);
        refineString(orderRequest);
    }

    private void validateString(String orderRequest) {
        if (orderRequest == null || orderRequest.isEmpty()) {
            Exception.getInvalidInput();
        }
        if (!orderRequest.contains(OPENING_CHARACTER) && !orderRequest.contains(CLOSING_CHARACTER)) {
            Exception.getInvalidFormat();
        }
        if (!orderRequest.contains(SEPARATOR_CHARACTER)) {
            Exception.getInvalidFormat();
        }
    }

    private void refineString(String orderRequest) {
        String[] orders = orderRequest.split(DELIMITER);
        for (String order : orders) {
            int index = order.indexOf(SEPARATOR_CHARACTER);
            String orderName = order.substring(INDEX_UNIT, index);
            String orderAmount = order.substring(index + INDEX_UNIT, order.length() - INDEX_UNIT);

            validateName(orderName);
            validateAmount(orderName, orderAmount);
            this.order.put(orderName, Integer.parseInt(orderAmount));
        }
    }

    private void validateName(String orderName) {
        if (Products.lookupFirstMatchProduct(orderName) == null) {
            Exception.getInvalidProductName();
        }
    }

    private void validateAmount(String orderName, String orderAmount) {
        try {
            int amount = Integer.parseInt(orderAmount);
            validateAmountValue(orderName, amount);
        } catch (NumberFormatException e) {
            Exception.getInvalidFormat();
        }
    }

    private void validateAmountValue(String orderName, int amount) {
        if (amount <= ZERO_VALUE) {
            Exception.getInvalidFormat();
        }
        if (!isNotGreaterThanTotalQuantity(orderName, amount)) {
            Exception.getInvalidProductAmount();
        }
    }

    private boolean isNotGreaterThanTotalQuantity(String name, int amount) {
        int totalQuantity = ZERO_VALUE;
        for (Products product : Products.values()) {
            String productName = Products.getName(product);
            int productQuantity = Products.getQuantity(product);
            if (productName.equals(name)) {
                totalQuantity += productQuantity;
            }
        }
        boolean isNotGreaterThanTotalQuantity = (totalQuantity >= amount);
        return isNotGreaterThanTotalQuantity;
    }

    public Map<String, Integer> getOrder() {
        return order;
    }
}