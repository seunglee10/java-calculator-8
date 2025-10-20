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
        long result = calculator.add(input);

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
        long result = calculator.add(input); // 현재 add()는 "5"에 대해 0을 반환. 테스트 실패 유도

        // then
        assertThat(result).isEqualTo(5);
    }

    @ParameterizedTest
    @CsvSource(value = {"1,2=3", "1:2:3=6", "1,2:3=6", "10,20=30"}, delimiter = '=')
    @DisplayName("쉽표나 콜론을 구분자로 사용해 합을 구해야 한다.")
    void default_delimiters_should_return_sum(String input, long expected) {
        // given (입력과 기대값은 CsvSource에서 제공)

        //when
        long result = calculator.add(input); // 현재 add()는 0을 반환하므로 테스트 실패 유도

        //then
        assertThat(result).isEqualTo(expected);
    }
    @Test
    @DisplayName("커스텀 구분자 세미콜론(;)으로 분리하여 합을 구해야 한다.")
    void custom_delimiter_semicolon_should_return_sum() {
        // given
        String input = "//;\n1;2;3";
        long expected = 6;

        // when
        long result = calculator.add(input);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("커스텀 구분자 샵(#)으로 분리하여 합을 구해야 한다.")
    void custom_delimiter_hash_should_return_sum() {
        // given
        String input = "//#\n4#5#6";
        long expected = 15;

        // when
        long result = calculator.add(input);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("커스텀 구분자와 기본 구분자를 함께 사용할 수 있어야 한다.")
    void custom_and_default_delimiters_should_work_together() {
        // given
        String input = "//-\n5-6,7";
        long expected = 18;

        // when
        long result = calculator.add(input);

        //then
        assertThat(result).isEqualTo(expected);
    }

    // 예외 처리 테스트
    @Test
    @DisplayName("영문자를 커스텀 구분자로 사용할 수 있어야 한다.")
    void alphabetic_custom_delimiter_should_work() {
        // given
        String input = "//a\n1a2a3";
        long expected = 6;

        // when
        long result = calculator.add(input);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("2글자 이상 커스텀 구분자를 사용할 수 있어야 한다.")
    void multi_char_custom_delimiter_should_work() {
        // given
        String input = "//;;\n1;;2;;3";
        long expected = 6;

        // when
        long result = calculator.add(input);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("맨 뒤에 구분자가 있으면 예외가 발생해야 한다.")
    void trailing_delimiter_should_throw_exception() {
        // given
        String input = "1,2,";

        // when & then
        assertThat(org.junit.jupiter.api.Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> calculator.add(input)
        )).hasMessageContaining("구분자");
    }

    @Test
    @DisplayName("0 입력 시 예외가 발생해야 한다.")
    void zero_input_should_throw_exception() {
        // given
        String input = "0";

        // when & then
        assertThat(org.junit.jupiter.api.Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> calculator.add(input)
        )).hasMessageContaining("0 초과하는 양수");
    }

    @Test
    @DisplayName("숫자가 아닌 문자가 포함되면 예외가 발생해야 한다.")
    void non_numeric_character_should_throw_exception() {
        // given
        String input = "1,a,3";

        // when & then
        assertThat(org.junit.jupiter.api.Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> calculator.add(input)
        )).hasMessageContaining("숫자");
    }

    @Test
    @DisplayName("long 범위 최대값은 정상 처리되어야 한다.")
    void long_max_value_should_work() {
        // given
        String input = "9223372036854775807"; // Long.MAX_VALUE

        // when
        long result = calculator.add(input);

        // then
        assertThat(result).isEqualTo(9223372036854775807L);
    }

    @Test
    @DisplayName("앞에 0이 있는 숫자는 정상적으로 처리되어야 한다.")
    void leading_zeros_should_be_handled() {
        // given
        String input = "0001,0002,0003";
        long expected = 6;

        // when
        long result = calculator.add(input);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("000 같은 모든 0으로 구성된 숫자는 예외가 발생해야 한다.")
    void all_zeros_should_throw_exception() {
        // given
        String input = "000,1,2";

        // when & then
        assertThat(org.junit.jupiter.api.Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> calculator.add(input)
        )).hasMessageContaining("0 초과하는 양수");
    }

    @Test
    @DisplayName("합계가 long 최대값을 초과하면 예외가 발생해야 한다.")
    void sum_exceeding_long_max_should_throw_exception() {
        // given
        String input = "9223372036854775807,1"; // Long.MAX_VALUE + 1

        // when & then
        assertThat(org.junit.jupiter.api.Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> calculator.add(input)
        )).hasMessageContaining("long");
    }

    @Test
    @DisplayName("숫자만으로 구성된 커스텀 구분자는 예외가 발생해야 한다.")
    void numeric_only_custom_delimiter_should_throw_exception() {
        // given
        String input = "//123\n1,2,3";

        // when & then
        assertThat(org.junit.jupiter.api.Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> calculator.add(input)
        )).hasMessageContaining("숫자만으로 구성될 수 없습니다");
    }

    @Test
    @DisplayName("숫자와 문자가 결합된 커스텀 구분자는 정상 처리되어야 한다.")
    void alphanumeric_custom_delimiter_should_work() {
        // given
        String input = "//a1b\n1a1b2a1b3";
        long expected = 6;

        // when
        long result = calculator.add(input);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("커스텀 구분자 형식에서 개행이 2개 있으면 예외가 발생해야 한다.")
    void double_newline_after_delimiter_should_throw_exception() {
        // given
        String input = "//?\n\n4?5?6";

        // when & then
        assertThat(org.junit.jupiter.api.Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> calculator.add(input)
        )).hasMessageContaining("구분자");
    }

}
