package store.model;

import java.util.Map;
import java.util.Map.Entry;

public class Cashier {
    private int totalMemberDiscount;
    private int totalPromotionDiscount;
    private int totalCost;
    private int finalCost;

    public void checkTotalMemberDiscount(String response, Manager manager) {
        Customer customer = new Customer(response);

        if (customer.isResponseYes()) {
            calculateTotalMemberDiscount(manager);
        }
    }

    private void checkTotalPromotionDiscount(Manager manager) {
        Map<Products, Integer> freePromotionQuantity = manager.getFreePromotionQuantity();

        for (Entry<Products, Integer> entry : freePromotionQuantity.entrySet()) {
            int price = Products.getPrice(entry.getKey());
            int quantity = entry.getValue();

            totalPromotionDiscount += (price * quantity);
        }
    }

    private void calculateTotalMemberDiscount(Manager manager) {
        Map<Products, Integer> actualDefaultQuantity = manager.getActualNoPromotionQuantity();

        for (Entry<Products, Integer> entry : actualDefaultQuantity.entrySet()) {
            int price = Products.getPrice(entry.getKey());
            int quantity = entry.getValue();

            totalMemberDiscount += (price * quantity);
        }
        calculateTotalMemberDiscountLimit();
    }

    private void calculateTotalMemberDiscountLimit() {
        double totalMemberDiscountLimit = totalMemberDiscount * 0.3;

        if (totalMemberDiscountLimit > 8000) {
            totalMemberDiscountLimit = 8000;
        }
        totalMemberDiscount = (int) totalMemberDiscountLimit;
    }

    private int calculateTotalRequest() {
        int totalRequest = 0;

        for (Products product : Products.values()) {
            int request = Products.getRequest(product);

            totalRequest += request;
        }
        return totalRequest;
    }

    private int calculateCost(Products product) {
        int price = Products.getPrice(product);
        int request = Products.getRequest(product);
        int cost = price * request;

        return cost;
    }

    private void calculateTotalCost() {
        for (Products product : Products.values()) {
            int price = Products.getPrice(product);
            int request = Products.getRequest(product);

            totalCost += (price * request);
        }
    }

    private void calculateFinalCost() {
        finalCost = totalCost - totalPromotionDiscount - totalMemberDiscount;
    }

    public int getTotalRequest() {
        return calculateTotalRequest();
    }

    public int getTotalMemberDiscount() {
        return totalMemberDiscount;
    }

    public int getTotalPromotionDiscount(Manager manager) {
        checkTotalPromotionDiscount(manager);
        return totalPromotionDiscount;
    }

    public int getCost(Products product) {
        return calculateCost(product);
    }

    public int getTotalCost() {
        calculateTotalCost();
        return totalCost;
    }

    public int getFinalCost() {
        calculateFinalCost();
        return finalCost;
    }
}