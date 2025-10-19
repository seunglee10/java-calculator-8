package calculator.service;

import calculator.service.StringCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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

    @Test
    @DisplayName("숫자 하나를 입력하면 해당 숫자를 반환해야 한다.")
    void single_number_should_return_itself() {
        // given
        String input = "5";

        // when
        int result = calculator.add(input); // 현재 add()는 "5"에 대해 0을 반환. 테스트 실패 유도

        // then
        assertThat(result).isEqualTo(5);
    }

    @ParameterizedTest
    @CsvSource(value = {"1,2=3", "1:2:3=6", "1,2:3=6", "10,20=30"}, delimiter = '=')
    @DisplayName("쉽표나 콜론을 구분자로 사용해 합을 구해야 한다.")
    void default_delimiters_should_return_sum(String input, int expected) {
        // given (입력과 기대값은 CsvSource에서 제공)

        //when
        int result = calculator.add(input); // 현재 add()는 0을 반환하므로 테스트 실패 유도

        //then
        assertThat(result).isEqualTo(expected);
    }

}
