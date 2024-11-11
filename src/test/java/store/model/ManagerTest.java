package store.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ManagerTest {
    @DisplayName("기능 테스트: 주문 목록에서 증정 상품 목록과 정가 상품 목록 생성 크기 확인")
    @Test
    void check_free_promotion_quantity_and_actual_no_promotion_quantity_execution() {
        create_store_for_manager_test();
        Manager manager = create_manager_for_manager_test();

        Map<Products, Integer> freePromotionQuantity = manager.getFreePromotionQuantity();
        Map<Products, Integer> actualNoPromotionQuantity = manager.getActualNoPromotionQuantity();

        boolean isFreePromotionQuantity = is_expected_quantity(freePromotionQuantity, "콜라", 1);
        boolean isActualNoPromotionQuantity = is_expected_quantity(actualNoPromotionQuantity, "에너지바", 5);

        assertTrue(isFreePromotionQuantity && isActualNoPromotionQuantity);
    }

    private void create_store_for_manager_test() {
        Storage storage = new Storage();

        Products.restockProducts(storage);
        Promotions.recallPromotions(storage);
    }

    private Manager create_manager_for_manager_test() {
        Order order = new Order("[콜라-3],[에너지바-5]");
        Manager manager = new Manager();

        manager.handleOrder(order);
        return manager;
    }

    private boolean is_expected_quantity(Map<Products, Integer> promotionQuantity, String name, int quantity) {
        boolean isExpectedQuantity = false;
        for (Map.Entry<Products, Integer> entry : promotionQuantity.entrySet()) {
            Products product = entry.getKey();
            String productName = Products.getName(product);
            if (productName.equals(name)) {
                int productQuantity = entry.getValue();
                isExpectedQuantity = (productQuantity == quantity);
            }
        }
        return isExpectedQuantity;
    }
}