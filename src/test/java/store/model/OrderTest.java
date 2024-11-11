package store.model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class OrderTest {
    @DisplayName("예외 테스트: 잘못된 형식의 주문 문자열 입력")
    @ParameterizedTest
    @ValueSource(strings = {"\n", "콜라-1", "[콜라-1]&[오렌지주스-2]", "[새우깡-1]", "[콜라-one]", "[콜라-0]", "[콜라-100]"})
    void check_order_string_exception(String orders) {
        assertThatThrownBy(() -> new Order(orders))
                .isInstanceOf(IllegalArgumentException.class);
    }
}