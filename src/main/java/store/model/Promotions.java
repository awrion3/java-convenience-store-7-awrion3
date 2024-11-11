package store.model;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

public enum Promotions {
    N_PLUS_ONE("프로모션", 0, 0, "0000-00-00", "0000-00-00"),
    MD_RECOMMEND("프로모션", 0, 0, "0000-00-00", "0000-00-00"),
    FLASH_SALE("프로모션", 0, 0, "0000-00-00", "0000-00-00");

    private String name;
    private int buy;
    private int get;
    private LocalDate start_date;
    private LocalDate end_date;

    Promotions(String name, int buy, int get, String start_date, String end_date) {
    }

    public static void recallPromotions(Storage storage) {
        List<List<String>> promotionResources = storage.getResourcePromotions();

        recallPromotionsFromResource(promotionResources);
    }

    private static void recallPromotionsFromResource(List<List<String>> promotionResources) {
        Iterator<List<String>> promotionResource = promotionResources.iterator();

        for (Promotions promotion : Promotions.values()) {
            List<String> promotionItems = promotionResource.next();

            updatePromotion(promotionItems, promotion);
        }
    }

    private static void updatePromotion(List<String> promotionItems, Promotions promotion) {
        String buy = promotionItems.get(1);
        String get = promotionItems.get(2);
        String start_date = promotionItems.get(3);
        String end_date = promotionItems.get(4);

        promotion.name = promotionItems.get(0);
        promotion.buy = Integer.parseInt(buy);
        promotion.get = Integer.parseInt(get);
        promotion.start_date = LocalDate.parse(start_date);
        promotion.end_date = LocalDate.parse(end_date);
    }

    public static boolean isPromotionDate(Products product) {
        boolean isPromotionDate = true;
        Promotions promotion = lookupPromotion(product);
        LocalDateTime start = promotion.getStartDateTime();
        LocalDateTime end = promotion.getEndDateTime();
        LocalDateTime today = DateTimes.now();

        if (today.isBefore(start) || today.isAfter(end)) {
            isPromotionDate = false;
        }
        return isPromotionDate;
    }

    public static Promotions lookupPromotion(Products product) {
        Promotions promotionID = null;
        String promotionName = Products.getPromotion(product);

        for (Promotions promotions : Promotions.values()) {
            if (promotions.name.equals(promotionName)) {
                promotionID = promotions;
            }
        }
        return promotionID;
    }

    public static int getBuy(String promotionName) {
        int buy = 0;

        for (Promotions promotion : Promotions.values()) {
            if (promotion.name.equals(promotionName)) {
                buy = promotion.buy;
            }
        }
        return buy;
    }

    public static int getGet(String promotionName) {
        int get = 0;

        for (Promotions promotion : Promotions.values()) {
            if (promotion.name.equals(promotionName)) {
                get = promotion.get;
            }
        }
        return get;
    }

    public LocalDateTime getStartDateTime() {
        return start_date.atStartOfDay();
    }

    public LocalDateTime getEndDateTime() {
        return end_date.atTime(23, 59, 59);
    }
}