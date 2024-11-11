package store.view;

public class Exception {
    private static final String INVALID_INPUT = "[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.";
    private static final String INVALID_FORMAT = "[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.";
    private static final String INVALID_PRODUCT_NAME = "[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.";
    private static final String INVALID_PRODUCT_AMOUNT = "[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.";

    public static void getInvalidInput() {
        throw new IllegalArgumentException(INVALID_INPUT);
    }

    public static void getInvalidFormat() {
        throw new IllegalArgumentException(INVALID_FORMAT);
    }

    public static void getInvalidProductName() {
        throw new IllegalArgumentException(INVALID_PRODUCT_NAME);
    }

    public static void getInvalidProductAmount() {
        throw new IllegalArgumentException(INVALID_PRODUCT_AMOUNT);
    }
}