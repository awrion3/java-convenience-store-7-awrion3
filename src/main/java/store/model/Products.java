package store.model;

import java.util.Iterator;
import java.util.List;

public enum Products {
    COKE_EVENT("콜라", 0, 0, "프로모션", 0, 0),
    COKE("콜라", 0, 0, null, 0, 0),
    CIDER_EVENT("사이다", 0, 0, "프로모션", 0, 0),
    CIDER("사이다", 0, 0, null, 0, 0),
    ORANGE_JUICE_EVENT("오렌지주스", 0, 0, "프로모션", 0, 0),
    ORANGE_JUICE("오렌지주스", 0, 0, null, 0, 0),
    SPARKLING_WATER_EVENT("탄산수", 0, 0, "프로모션", 0, 0),
    SPARKLING_WATER("탄산수", 0, 0, null, 0, 0),
    WATER("물", 0, 0, null, 0, 0),
    VITAMIN_WATER("비타민워터", 0, 0, null, 0, 0),
    CHIPS_EVENT("감자칩", 0, 0, "프로모션", 0, 0),
    CHIPS("감자칩", 0, 0, null, 0, 0),
    CHOCO_BAR_EVENT("초코바", 0, 0, "프로모션", 0, 0),
    CHOCO_BAR("초코바", 0, 0, null, 0, 0),
    ENERGY_BAR("에너지바", 0, 0, null, 0, 0),
    LUNCHBOX("정식도시락", 0, 0, null, 0, 0),
    NOODLE_EVENT("컵라면", 0, 0, "프로모션", 0, 0),
    NOODLE("컵라면", 0, 0, null, 0, 0);

    private final String name;
    private int price;
    private int quantity;
    private String promotion;

    private int measure;
    private int request;

    Products(String name, int price, int quantity, String promotion, int measure, int request) {
        this.name = name;
        this.promotion = promotion;

        this.measure = measure;
        this.request = request;
    }

    public static void restockProducts(Storage storage) {
        List<List<String>> productResource = storage.getResourceProducts();

        restockProductsFromResource(productResource);
        handleProductsPriceUpdate();
    }

    private static void restockProductsFromResource(List<List<String>> productResources) {
        Iterator<List<String>> productResource = productResources.iterator();
        boolean isNotPending = true;
        List<String> productItems = null;
        for (Products product : Products.values()) {
            if (isNotPending) {
                productItems = productResource.next();
                isNotPending = false;
            }
            isNotPending = updateProduct(product, productItems, isNotPending);
        }
    }

    private static boolean updateProduct(Products product, List<String> productItems, boolean isNotPending) {
        if (hasSameName(product, productItems) && hasSamePromotionStatus(product, productItems)) {
            updateProductItems(product, productItems);
            isNotPending = true;
        }
        return isNotPending;
    }

    private static boolean hasSameName(Products product, List<String> productItems) {
        boolean hasSameName = false;
        String name = productItems.get(0);

        if (name.equals(product.name)) {
            hasSameName = true;
        }
        return hasSameName;
    }

    private static boolean hasSamePromotionStatus(Products product, List<String> productItems) {
        String promotion = productItems.get(3);
        boolean isPromotionProduct = (product.promotion != null);
        boolean isPromotionTag = (!promotion.equals("null"));
        boolean hasSamePromotionStatus = (isPromotionProduct == isPromotionTag);

        return hasSamePromotionStatus;
    }

    private static void updateProductItems(Products product, List<String> productItems) {
        product.price = Integer.parseInt(productItems.get(1));
        product.quantity = Integer.parseInt(productItems.get(2));
        product.promotion = productItems.get(3);

        if (product.promotion.equals("null")) {
            product.promotion = null;
        }
    }

    private static void handleProductsPriceUpdate() {
        for (Products product : Products.values()) {
            if (product.price == 0) {
                Products promotionProduct = lookupPromotionPairProduct(product);
                updatePriceFromPairProduct(promotionProduct, product);

                Products defaultProduct = lookupDefaultPairProduct(product);
                updatePriceFromPairProduct(defaultProduct, product);
            }
        }
    }

    private static void updatePriceFromPairProduct(Products pairProduct, Products product) {
        if (pairProduct != null) {
            product.price = pairProduct.price;
        }
    }

    public static Products lookupDefaultPairProduct(Products product) {
        Products pairProduct = null;

        for (Products products : Products.values()) {
            if (products.name.equals(product.name) && products.promotion == null) {
                pairProduct = products;
            }
        }
        return pairProduct;
    }

    public static Products lookupPromotionPairProduct(Products product) {
        Products pairProduct = null;

        for (Products products : Products.values()) {
            if (products.name.equals(product.name) && products.promotion != null) {
                pairProduct = products;
            }
        }
        return pairProduct;
    }

    public static Products lookupFirstMatchProduct(String name) {
        Products productID = null;

        for (Products product : Products.values()) {
            if (product.name.equals(name)) {
                productID = product;
                break;
            }
        }
        return productID;
    }

    static void clearMeasure() {
        for (Products product : Products.values()) {
            product.measure = 0;
        }
    }

    static void updateMeasureFromQuantity() {
        for (Products product : Products.values()) {
            product.measure = product.quantity;
        }
    }

    static void removeMeasure(String orderName, int orderRequest) {
        for (Products product : Products.values()) {
            if (orderRequest == 0) {
                return;
            }
            if (product.name.equals(orderName)) {
                if (product.measure != 0) {
                    orderRequest = deductMeasure(product, orderRequest);
                }
            }
        }
    }

    private static int deductMeasure(Products product, int orderRequest) {
        while (product.measure > 0 && orderRequest > 0) {
            product.measure--;
            orderRequest--;
        }
        return orderRequest;
    }

    static void removeQuantity(String orderName, int orderAmount) {
        for (Products product : Products.values()) {
            if (orderAmount == 0) {
                return;
            }
            if (product.name.equals(orderName)) {
                if (product.quantity != 0) {
                    orderAmount = deductQuantity(product, orderAmount);
                }
            }
        }
    }

    private static int deductQuantity(Products product, int orderAmount) {
        while (product.quantity > 0 && orderAmount > 0) {
            product.quantity--;
            orderAmount--;
        }
        return orderAmount;
    }

    static void clearRequest() {
        for (Products product : Products.values()) {
            product.request = 0;
        }
    }

    static void updateRequestFromAmount(Products product, int orderAmount) {
        product.request = orderAmount;
    }

    static void addPlusOneToRequest(Products product) {
        product.request += 1;
    }

    static void removeNoPromotionFromRequest(Products product, int noPromotionAppliedRequest) {
        product.request -= noPromotionAppliedRequest;
    }

    public static String getName(Products product) {
        return product.name;
    }

    public static int getPrice(Products product) {
        return product.price;
    }

    public static int getQuantity(Products product) {
        return product.quantity;
    }

    public static String getPromotion(Products product) {
        return product.promotion;
    }

    public static int getMeasure(Products product) {
        return product.measure;
    }

    public static int getRequest(Products product) {
        return product.request;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();

        string.append(String.format("- %s ", name));
        string.append(String.format("%,d원 ", price));
        string.append(checkQuantityFormat(quantity));
        string.append(checkPromotionFormat(promotion));

        return string.toString();
    }

    private String checkQuantityFormat(int quantity) {
        String quantityFormat = null;

        if (quantity != 0) {
            quantityFormat = String.format("%d개", quantity);
        }
        if (quantity == 0) {
            quantityFormat = "재고 없음";
        }
        return quantityFormat;
    }

    private String checkPromotionFormat(String promotion) {
        String promotionFormat = null;

        if (promotion != null) {
            promotionFormat = String.format(" %s", promotion);
        }
        if (promotion == null) {
            promotionFormat = "";
        }
        return promotionFormat;
    }
}