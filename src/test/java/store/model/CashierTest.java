package store.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CashierTest {
    @DisplayName("기능 테스트: 고객 응답에 따른 멤버십 할인 금액 계산 확인")
    @ParameterizedTest
    @CsvSource(value = {"[콜라-3],[에너지바-5]&Y:3000", "[콜라-3],[에너지바-5]&N:0", "[콜라-6]&Y:0",
            "[오렌지주스-2]&Y:0"}, delimiter = ':')
    void check_member_discount_conditional_execution(String reply, String memberDiscount) {
        String[] customerReply = reply.split("&");

        create_store_for_cashier_test();
        Manager manager = create_manager_for_cashier_test(customerReply[0]);
        
        Cashier cashier = new Cashier();
        String response = customerReply[1];
        cashier.checkTotalMemberDiscount(response, manager);

        assertTrue(is_equal_total_member_discount(cashier, memberDiscount));
    }

    private void create_store_for_cashier_test() {
        Storage storage = new Storage();

        Products.restockProducts(storage);
        Promotions.recallPromotions(storage);
    }

    private Manager create_manager_for_cashier_test(String orders) {
        Order order = new Order(orders);
        Manager manager = new Manager();

        manager.handleOrder(order);
        return manager;
    }

    private boolean is_equal_total_member_discount(Cashier cashier, String memberDiscount) {
        int totalMemberDiscount = cashier.getTotalMemberDiscount();
        int expectedTotalMemberDiscount = Integer.parseInt(memberDiscount);
        boolean isEqualTotalMemberDiscount = (totalMemberDiscount == expectedTotalMemberDiscount);

        return isEqualTotalMemberDiscount;
    }
}