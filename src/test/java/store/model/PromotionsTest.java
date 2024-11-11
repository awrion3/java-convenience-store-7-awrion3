package store.model;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PromotionsTest {
    @DisplayName("기능 테스트: 리스트로부터 프로모션 생성 확인")
    @Test
    void check_products_creation_execution() {
        Storage storage = new Storage();
        Promotions.recallPromotions(storage);

        boolean isNotValidDate = false;
        for (Promotions promotion : Promotions.values()) {
            isNotValidDate = is_not_valid_start_date(promotion, isNotValidDate);
            isNotValidDate = is_not_valid_end_date(promotion, isNotValidDate);
        }
        assertFalse(isNotValidDate);
    }

    private boolean is_not_valid_start_date(Promotions promotion, boolean isNotValidDate) {
        LocalDateTime promotionStartDateTime = promotion.getStartDateTime();
        String promotionStart = promotionStartDateTime.toString();

        if (promotionStart.equals("0000-00-00")) {
            isNotValidDate = true;
        }
        return isNotValidDate;
    }

    private boolean is_not_valid_end_date(Promotions promotion, boolean isNotValidDate) {
        LocalDateTime promotionEndDateTime = promotion.getEndDateTime();
        String promotionEnd = promotionEndDateTime.toString();

        if (promotionEnd.equals("0000-00-00")) {
            isNotValidDate = true;
        }
        return isNotValidDate;
    }
}
