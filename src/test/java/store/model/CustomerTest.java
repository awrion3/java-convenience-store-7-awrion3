package store.model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class CustomerTest {
    @DisplayName("예외 테스트: 고객이 입력한 응답이 Y 또는 N가 아닌 경우")
    @ParameterizedTest
    @ValueSource(strings = {"\n", " ", "Yes", "No", "예", "아니오", "1", "0"})
    void check_customer_response_exception(String response) {
        assertThatThrownBy(() -> new Customer(response))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
    }
}