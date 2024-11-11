package store.model;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StorageTest {
    @DisplayName("기능 테스트: 리소스 파일로부터 리스트 생성 여부 확인")
    @Test
    void check_resource_list_creation() {
        Storage storage = new Storage();

        List<List<String>> resourceProducts = storage.getResourceProducts();
        List<List<String>> resourcePromotions = storage.getResourcePromotions();

        assertFalse(resourceProducts.isEmpty() && resourcePromotions.isEmpty());
    }
}