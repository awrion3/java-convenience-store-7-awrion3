package store.view;

import java.util.Map;
import store.model.Cashier;
import store.model.Manager;
import store.model.Products;

public class Output {
    public static void printStatus() {
        System.out.println("안녕하세요. W편의점입니다.\n"
                + "현재 보유하고 있는 상품입니다.\n");
        for (Products product : Products.values()) {
            System.out.println(product);
        }
    }

    public static void printReceipt(Manager manager, Cashier cashier) {
        System.out.println();
        System.out.println("==============W 편의점================");
        printReceiptProductPart();

        System.out.println("=============증\t정===============");
        printReceiptPlusOnePart(manager);

        System.out.println("====================================");
        printReceiptResultPart(cashier, manager);
    }

    private static void printReceiptProductPart() {
        System.out.println("상품명\t\t수량\t금액");
        for (Products product : Products.values()) {
            if (Products.getRequest(product) == 0) {
                continue;
            }
            String productFormat = formatProductString(product);
            System.out.println(productFormat);
        }
    }

    private static String formatProductString(Products product) {
        Cashier cashier = new Cashier();
        StringBuilder string = new StringBuilder();
        String orderName = Products.getName(product);
        int orderRequest = Products.getRequest(product);
        int cost = cashier.getCost(product);

        string.append(String.format("%s\t\t", orderName));
        string.append(String.format("%d \t", orderRequest));
        string.append(String.format("%,d", cost));
        return string.toString();
    }

    private static void printReceiptPlusOnePart(Manager manager) {
        Map<Products, Integer> freeProductsQuantity = manager.getFreePromotionQuantity();
        for (Map.Entry<Products, Integer> entry : freeProductsQuantity.entrySet()) {
            Products freeProduct = entry.getKey();
            String freeProductName = Products.getName(freeProduct);
            int freeProductQuantity = entry.getValue();

            if (freeProductQuantity != 0) {
                System.out.println(freeProductName + "\t\t" + freeProductQuantity);
            }
        }
    }

    private static void printReceiptResultPart(Cashier cashier, Manager manager) {
        printReceiptResultTotalCost(cashier);
        printReceiptResultDiscount(cashier, manager);
        printReceiptResultFinalCost(cashier);
    }

    private static void printReceiptResultTotalCost(Cashier cashier) {
        StringBuilder totalCostFormat = new StringBuilder();
        totalCostFormat.append("총구매액\t\t");

        int totalQuantity = cashier.getTotalRequest();
        totalCostFormat.append(String.format("%d\t", totalQuantity));

        int totalCost = cashier.getTotalCost();
        totalCostFormat.append(String.format("%,d", totalCost));

        System.out.println(totalCostFormat);
    }

    private static void printReceiptResultDiscount(Cashier cashier, Manager manager) {
        int totalPromotionDiscount = cashier.getTotalPromotionDiscount(manager);
        String totalPromotionDiscountFormat = String.format("행사할인\t\t\t-%,d", totalPromotionDiscount);

        int totalMemberDiscount = cashier.getTotalMemberDiscount();
        String totalMemberDiscountFormat = String.format("멤버십할인\t\t\t-%,d", totalMemberDiscount);

        System.out.println(totalPromotionDiscountFormat);
        System.out.println(totalMemberDiscountFormat);
    }

    private static void printReceiptResultFinalCost(Cashier cashier) {
        int finalCost = cashier.getFinalCost();
        String finalCostFormat = String.format("내실돈\t\t\t %,d", finalCost);

        System.out.println(finalCostFormat);
    }
}