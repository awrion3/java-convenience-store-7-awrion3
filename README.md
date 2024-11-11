## java-convenience-store-precourse

---

### 개요

* 우아한 테크코스 4주차 프리코스 미션: 편의점
* 편의점의 재고 상황 및 할인 혜택을 고려해 사용자에게 최종 구매 금액을 안내하는 결제 시스템입니다.
* 사용자가 구매 상품과 수량 및 동의 여부를 잘못된 형식으로 입력한 경우 안내 문구를 출력하고 다시 입력받습니다.

### 목차

[1. 요구 사항](#요구-사항)

[2. 기능 검사](#기능-검사)

[3. 기능 목록](#기능-목록)

[4. 구현 구조](#구현-구조)

---

### 요구 사항

#### 1주차

- 깃 커밋 컨벤션을 준수하도록 합니다.
- 일급 컬렉션, 디미터 법칙 등의 자바 코드 컨벤션을 준수하도록 합니다.
- 변수명 축약 및 자료형 이름을 사용하지 않도록 합니다.
- 컬렉션을 사용하도록 합니다.

#### 2주차

- 기능 단위 테스트를 작성하도록 합니다.
- 인덴트는 2까지 허용합니다.
- 3항 연산자를 사용하지 않도록 합니다.
- 메서드가 한 가지 일만 하도록 합니다.
- 기능 목록에 예외 상황도 포함하여 작성하도록 합니다.

#### 3주차

- 마크다운을 사용하여 readme 가독성을 높이도록 합니다.
- 값을 하드코딩하지 않고 static final 상수를 사용하도록 합니다.
- 필드의 수를 줄이도록 합니다.
- else 예약어를 사용하지 않도록 합니다.
- enum 열거형을 사용하도록 합니다.

#### 4주차

- 비즈니스 및 UI 입출력 로직을 별도의 클래스로 분리하도록 합니다.
- 테스트 코드를 위해 구현 코드를 변경하는 것을 지양하도록 합니다.
- 객체가 스스로 데이터를 처리하도록 구성합니다.
- 필드를 private 접근 제어자로 설정해 외부 접근을 제한하도록 합니다.
- 메서드는 공백을 포함해 10라인까지 허용합니다.
- DateTimes, Console API 라이브러리를 사용하도록 합니다.

---

### 기능 검사

#### I. 기본 테스트

1. 기능 테스트
    - 파일에 있는 상품 목록을 읽어서 출력하기
    - 여러 정가 상품 구매하여 최종 결제 금액 계산하기
    - 행사 기간이 아닌 상품 구매하여 최종 결제 금액 계산하기

2. 예외 테스트
    - 재고 수량을 초과하여 구매하면 예외 안내문구 출력하기

#### II. 신규 테스트

1. 창고
    - [x] 기능 테스트: 리소스 파일로부터 상품 및 행사 리스트 생성 여부 확인
        - 둘 중 하나라도 리스트 크기가 0인 경우 제대로 업데이트되지 않았다고 파악

2. 상품
    - [x] 기능 테스트: 리스트로부터 상품 enum 업데이트 되는지 확인
        - 전체 상품 금액 합산이 0인 경우 제대로 업데이트되지 않았다고 파악

3. 행사
    - [x] 기능 테스트: 리스트로부터 행사 enum 업데이트 되는지 확인
        - 전체 행사 시작 및 종료일 중 하나라도 0 초기화 값 그대로인 경우 제대로 업데이트되지 않았다고 파악

4. 주문
    - [x] 예외 테스트: 아래 항목에서 IllegalArgumentException 예외 발생하는지 확인
        - invalid input: 빈 문자열, Y/N 입력이 아닌 경우
        - invalid format: 구매 상품 및 수량 입력 형식이 잘못된 경우, 수량이 숫자가 아닌 경우, 수량이 0 이하 값인 경우
        - invalid product name: 구매 상품 이름이 존재 하지 않는 경우
        - invalid product amount: 구매 수량 개수가 재고 상태를 초과하는 경우

5. 매니저
    - [x] 기능 테스트: 주문 목록에서 증정 상품 및 정가 상품 목록 생성 크기 확인
        - 주문에 대해 행사 상품에서의 증정 상품 개수와 정가 적용인 상품 개수를 계산하는지 확인

6. 캐셔
    - [x] 기능 테스트: 고객 응답에 따른 멤버십 할인 계산 적용 여부 확인
        - 행사 미적용 상품 및 멤버십 할인 적용에 대한 응답이 Y인 경우에만 적용되는지 확인

7. 고객
    - [ ] 예외 테스트: 고객 응답이 Y/N 형식이 아닌 경우
        - IllegalArgumentException 예외 발생하는지 확인
        - '[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.' 예외 안내문구인지 확인

---

### 기능 목록

#### I. View

1. 입력
    - [x] 입력 안내문구 양식에 맞게 출력하기
    - [x] 구매할 상품과 수량 order 입력받기
    - [x] 프로모션 적용 상품 1개 추가 plus one 여부 입력받기
    - [x] 프로모션 혜택 없이 정가 no promotion 계산 여부 입력받기
    - [x] 멤버십 할인 member discount 적용 여부 입력받기
    - [x] 추가 구매 여부 입력받기

2. 출력
    - [ ] 환영 인사 및 재고 상태 양식에 맞게 출력하기
        - 재고 없음, null 값 형식에 맞추기
    - [ ] 영수증 양식에 맞게 출력하기
        - 구매 상품 내역, 증정 상품 내역, 금액 정보 내역 형식에 맞추어 출력하기

3. 예외
    - [ ] 예외 상황에 맞는 ERROR 안내문구 출력하기

#### II. Model

1. 창고
    - [x] product 목록과 promotion 목록을 resources 파일에서 읽어들여 list 생성하기
    - [x] IllegalStateException 예외 발생하기: 파일 없는 경우, 파일 내용 없는 경우
        - 파일 내용은 형식을 지키는 경우에만 수정한다고 가정하기

2. 상품
    - [x] 리소스 list 참조해 products 값 업데이트하기
        - 동일한 이름 값을 지닌 행사와 정가 쌍을 고려하기
            - 해당 쌍에 있어 값 업데이트 확인하기
    - [x] 상품별 값 관련 탐색 lookup 제공하기
    - [x] 재고 수량 변경 시뮬레이션을 위한 측정 measure 제공하기
    - [x] 실제 주문량 request 저장 및 갱신하기
    - [x] 실제 주문량을 반영해 재고 수량 quantity 변경하기
    - [x] 개별 product 값 출력 형식 제공하기

3. 행사
    - [x] 리소스 list 참조해 promotions 값 업데이트하기
    - [x] now 일자가 행사 기간에 포함되는지 확인 기능 제공하기

4. 주문
    - [x] 주문 상품별 이름과 수량으로 구성된 map 생성하기
        - 사용자가 입력한 주문 문자열 검증하기

5. 매니저
    - [x] 증정 상품 및 실제 정가 상품 개수 계산하기
    - [x] 실제 주문량을 반영해 재고 수량을 관리하기
    - [x] 사용자로부터 행사 상품 추가 및 정가 계산 여부 확인하기
        - 주문 amount를 실제 주문량 request로 업데이트하기
        - 정가 상품인 경우와 기한 내 행사 상품인 경우로 나누어 처리하기
            - [x] 정가 상품인 경우:
                - quantity 차감 수행하기
                - 증정 상품 개수 0, 정가 상품 개수 amount 그대로 적용하기
            - [x] 기한 내 행사 상품인 경우:
                - [x] 행사 상품 추가 +1 가능한지 확인하기
                    - 2+1에 2개 주문시 1개 추가 가능하다고 계산하기
                    - customer 응답 형식 검증 후 Y인 경우: request +1로 업데이트하기
                - [x] 정가 상품 적용 개수에 대해 정가 계산 진행할 것인지 확인하기
                    - 시뮬레이션을 위해 quantity 대신 measure 차감 수행하기
                    - 행사 상품 개수에서 buy + 1 묶음이 아닌 상품은 정가 상품으로 취급하기
                        - 시뮬레이션에서 행사 상품이 재고 없음에 도달한 경우:
                          해당 정가 적용 상품 개수를 합산해 업데이트하기
                    - 정가 상품 개수가 0이 아닌 경우:
                        - customer 응답 형식 검증 후 N인 경우:
                          정가 상품 개수 0 처리 및 request에서 정가 상품 개수 차감해 업데이트하기
                - measure 시뮬레이션에 따른 customer의 응답을 반영해 실제 quantity 차감 수행하기
                - 증정 상품 개수에 대해 계산하기
                    - request 전후에 따른 행사 적용 상품 quantity 변동량을 buy + 1으로 나누기

6. 캐셔
    - [x] 실제 주문량 request에 따른 개별 상품 금액 cost 계산하기
    - [x] 총구매액 total cost 계산하기
        - 총주문량 total request 합산하기
    - [x] 행사 할인 금액 total promotion discount 계산하기
    - [x] 멤버십 할인 금액 total member discount 계산하기
        - 멤버십 할인 여부 확인하기
        - 행사 미적용 상품 금액의 30% 할인하기
            - 소수점 발생 시 버림 적용하기
        - 최대 한도 8000원으로 제한하기
    - [x] 내실돈 final cost 계산하기

7. 고객
    - [ ] 사용자 입력한 응답이 Y/N 형식에 맞는지 검증하기
    - [ ] 사용자 응답이 Y인지 확인 기능 제공하기

#### III. Controller

1. 개점
    - [ ] 잘못된 입력에 대해 반복 입력하도록 예외 처리하기

---

### 구현 구조

---