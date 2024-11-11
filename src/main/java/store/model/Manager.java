package store.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import store.view.Input;

public class Manager {
    private static final int ZERO_VALUE = 0;

    private final Map<Products, Integer> freePromotionQuantity;
    private final Map<Products, Integer> actualNoPromotionQuantity;

    public Manager() {
        Products.clearMeasure();
        Products.clearRequest();
        freePromotionQuantity = new LinkedHashMap<>();
        actualNoPromotionQuantity = new LinkedHashMap<>();
    }

    public void handleOrder(Order order) {
        Map<String, Integer> orders = order.getOrder();

        for (Entry<String, Integer> entry : orders.entrySet()) {
            String orderName = entry.getKey();
            int orderAmount = entry.getValue();

            Products product = Products.lookupFirstMatchProduct(orderName);
            Products.updateRequestFromAmount(product, orderAmount);
            handleRequest(product, orderName, orderAmount);
        }
    }

    private void handleRequest(Products product, String orderName, int orderAmount) {
        if (!isPromotion(product)) {
            handleDefaultRequest(product, orderName, orderAmount);
            return;
        }
        if (isPromotion(product) && !Promotions.isPromotionDate(product)) {
            handleDefaultRequest(product, orderName, orderAmount);
            return;
        }
        handlePromotionRequest(product, orderName, orderAmount);
    }

    private void handleDefaultRequest(Products product, String orderName, int orderAmount) {
        Products.removeQuantity(orderName, orderAmount);

        freePromotionQuantity.put(product, ZERO_VALUE);
        actualNoPromotionQuantity.put(product, orderAmount);
    }

    private void handlePromotionRequest(Products product, String orderName, int orderAmount) {
        checkPlusOnePromotion(product, orderName, orderAmount);
        checkNoPromotion(product, orderName);
    }

    private void checkPlusOnePromotion(Products product, String orderName, int orderAmount) {
        String promotionName = Products.getPromotion(product);
        int buy = Promotions.getBuy(promotionName);
        int get = Promotions.getGet(promotionName);
        if ((orderAmount % (buy + get) == buy) && !isOverPromotionQuantity(product, get, orderAmount)) {
            String response = Input.askPlusOnePromotion(orderName);
            Customer customer = new Customer(response);
            if (customer.isResponseYes()) {
                Products.addPlusOneToRequest(product);
            }
        }
    }

    private boolean isOverPromotionQuantity(Products product, int get, int orderAmount) {
        boolean isOverPromotionQuantity = false;
        int promotionQuantity = Products.getQuantity(product);

        if (orderAmount + get > promotionQuantity) {
            isOverPromotionQuantity = true;
        }
        return isOverPromotionQuantity;
    }

    private void checkNoPromotion(Products product, String orderName) {
        Products.updateMeasureFromQuantity();
        int orderRequest = Products.getRequest(product);
        Products.removeMeasure(orderName, orderRequest);

        checkNoPromotionFromRequest(product, orderRequest);
        int actualOrderRequest = Products.getRequest(product);

        int beforeRequestPromotionQuantity = Products.getQuantity(product);
        Products.removeQuantity(orderName, actualOrderRequest);
        checkFreePromotionQuantity(beforeRequestPromotionQuantity, product);
    }

    private void checkNoPromotionFromRequest(Products product, int orderRequest) {
        int noPromotionAppliedRequest = calculateNoPromotionAppliedRequest(product, orderRequest);
        if (isOutOfStockInMeasure(product)) {
            noPromotionAppliedRequest = calculateActualNoPromotionAppliedRequest(product, noPromotionAppliedRequest);
        }
        actualNoPromotionQuantity.put(product, noPromotionAppliedRequest);

        if (noPromotionAppliedRequest != ZERO_VALUE) {
            confirmNoPromotionAppliedRequest(product, noPromotionAppliedRequest);
        }
    }

    private int calculateNoPromotionAppliedRequest(Products product, int orderRequest) {
        String promotionName = Products.getPromotion(product);
        int buy = Promotions.getBuy(promotionName);
        int get = Promotions.getGet(promotionName);
        int noPromotionRequest = orderRequest % (buy + get);

        return noPromotionRequest;
    }

    private int calculateActualNoPromotionAppliedRequest(Products product, int noPromotionRequest) {
        Products defaultProduct = Products.lookupDefaultPairProduct(product);

        int defaultRequest = Products.getQuantity(defaultProduct) - Products.getMeasure(defaultProduct);
        int actualNoPromotionRequest = noPromotionRequest + defaultRequest;

        return actualNoPromotionRequest;
    }

    private void confirmNoPromotionAppliedRequest(Products product, int noPromotionAppliedRequest) {
        String orderName = Products.getName(product);
        String response = Input.askNoPromotionAppliedRequest(orderName, noPromotionAppliedRequest);
        Customer customer = new Customer(response);

        if (!customer.isResponseYes()) {
            Products.removeNoPromotionFromRequest(product, noPromotionAppliedRequest);
            actualNoPromotionQuantity.put(product, ZERO_VALUE);
        }
    }

    private void checkFreePromotionQuantity(int beforeRequestPromotionQuantity, Products product) {
        String promotionName = Products.getPromotion(product);
        int buy = Promotions.getBuy(promotionName);
        int get = Promotions.getGet(promotionName);
        int afterRequestPromotionQuantity = Products.getQuantity(product);
        int freePromotionQuantity = (beforeRequestPromotionQuantity - afterRequestPromotionQuantity) / (buy + get);

        this.freePromotionQuantity.put(product, freePromotionQuantity);
    }

    private boolean isPromotion(Products product) {
        boolean isPromotion = false;
        String promotionName = Products.getPromotion(product);

        if (promotionName != null) {
            isPromotion = true;
        }
        return isPromotion;
    }

    private boolean isOutOfStockInMeasure(Products product) {
        boolean isOutOfStockInMeasure = false;
        int measureValue = Products.getMeasure(product);

        if (measureValue == ZERO_VALUE) {
            isOutOfStockInMeasure = true;
        }
        return isOutOfStockInMeasure;
    }

    public Map<Products, Integer> getFreePromotionQuantity() {
        return freePromotionQuantity;
    }

    public Map<Products, Integer> getActualNoPromotionQuantity() {
        return actualNoPromotionQuantity;
    }
}