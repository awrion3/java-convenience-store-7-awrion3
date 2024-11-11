package store.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProductsTest {
    @DisplayName("기능 테스트: 리스트로부터 상품 생성 확인")
    @Test
    void check_products_creation_execution() {
        Storage storage = new Storage();
        Products.restockProducts(storage);

        int totalPrice = 0;
        for (Products product : Products.values()) {
            int price = Products.getPrice(product);
            totalPrice += price;
        }
        assertTrue(totalPrice != 0);
    }
}