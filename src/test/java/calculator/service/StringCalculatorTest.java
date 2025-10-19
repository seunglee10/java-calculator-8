package calculator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

// 예외 테스트 시 static org.assertj.core.api.Assertions.assertThat; 를 사용

public class StringCalculatorTest {

    private StringCalculator calculator;

    // 각 테스트 메소드가 실행되기 전 호출되어 테스트에 필요한 객체를 준비.
    @BeforeEach
    void setUp() {
        // Service 계층의 실제 구현 객체를 생성
        calculator = new StringCalculator();

    }

    // 빈 문자열 처리 기능 테스트

    @Test
    @DisplayName("빈 문자열을 입력하면 0을 반환해야 한다.")
    void empty_should_return_zero() {
        // given
        String input = "";

        // when
        int result = calculator.add(input);

        // then
        // 입력값이 빈 문자열("")일 경우 0을 반환한다.
        assertThat(result).isEqualTo(0);
    }


}
