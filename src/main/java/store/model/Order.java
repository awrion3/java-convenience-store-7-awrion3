package store.model;

import java.util.LinkedHashMap;
import java.util.Map;
import store.view.Exception;

public class Order {
    private static final int INDEX_UNIT = 1;
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
        if (!orderRequest.contains("[") && !orderRequest.contains("]")) {
            Exception.getInvalidFormat();
        }
        if (!orderRequest.contains("-")) {
            Exception.getInvalidFormat();
        }
    }

    private void refineString(String orderRequest) {
        String[] orders = orderRequest.split(",");
        for (String order : orders) {
            int index = order.indexOf("-");
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
        if (amount <= 0) {
            Exception.getInvalidFormat();
        }
        if (!isNotGreaterThanTotalQuantity(orderName, amount)) {
            Exception.getInvalidProductAmount();
        }
    }

    private boolean isNotGreaterThanTotalQuantity(String name, int amount) {
        int totalQuantity = 0;
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