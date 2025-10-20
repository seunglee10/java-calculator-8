package calculator.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;  // 예외 테스트에 사용

public class InputValidatorTest {

    private InputValidator validator;

    @BeforeEach
    void setUp() {
        // Model 계층의 실제 구현 객체를 생성
        validator = new InputValidator();
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "!", "100.5", "abc123"})
    @DisplayName("숫자 외 문자 또는 잘못된 형식 입력 시 예외를 발생시켜야 한다.")
    void non_numeric_should_throw_exception(String input) {
        // given : 입력값 ValueSource에서 제공

        // when & then
        assertThatThrownBy(() -> validator.validateNumber(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("유효하지 않은 숫자");
    }

    @Test
    @DisplayName("0 입력 시 예외가 발생해야 한다.")
    void zero_should_throw_exception() {
        // given
        String input = "0";

        // when & then
        assertThatThrownBy(() -> validator.validateNumber(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("0 초과하는 양수");
    }

    @Test
    @DisplayName("000 같은 모든 0 입력 시 예외가 발생해야 한다.")
    void all_zeros_should_throw_exception() {
        // given
        String input = "000";

        // when & then
        assertThatThrownBy(() -> validator.validateNumber(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("0 초과하는 양수");
    }

    @Test
    @DisplayName("빈 문자열 입력 시 예외가 발생해야 한다.")
    void empty_string_should_throw_exception() {
        // given
        String input = "";

        // when & then
        assertThatThrownBy(() -> validator.validateNumber(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("빈 숫자");
    }

    @Test
    @DisplayName("long 최대값은 정상 처리되어야 한다.")
    void long_max_value_should_be_valid() {
        // given
        String input = "9223372036854775807"; // Long.MAX_VALUE

        // when & then - 예외가 발생하지 않아야 함
        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> validator.validateNumber(input));
    }

    @Test
    @DisplayName("앞의 0이 있는 숫자는 정상 처리되어야 한다.")
    void leading_zeros_should_be_valid() {
        // given
        String input = "0001";

        // when & then - 예외가 발생하지 않아야 함
        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> validator.validateNumber(input));
    }

    @Test
    @DisplayName("커스텀 구분자 형식 검증 - 숫자만으로 구성된 구분자는 예외가 발생해야 한다.")
    void numeric_only_delimiter_should_throw_exception() {
        // given
        String input = "//123\n1,2,3";

        // when & then
        assertThatThrownBy(() -> validator.validateInputStructure(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("숫자만으로 구성될 수 없습니다");
    }

    @Test
    @DisplayName("커스텀 구분자 형식 검증 - 개행 문자 포함 시 예외가 발생해야 한다.")
    void delimiter_with_newline_should_throw_exception() {
        // given
        String input = "//\n\n1,2,3";

        // when & then
        assertThatThrownBy(() -> validator.validateInputStructure(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("개행 문자");
    }

    @Test
    @DisplayName("커스텀 구분자 형식 검증 - 숫자와 문자 결합은 허용되어야 한다.")
    void alphanumeric_delimiter_should_be_valid() {
        // given
        String input = "//a1b\n1a1b2a1b3";

        // when & then - 예외가 발생하지 않아야 함
        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> validator.validateInputStructure(input));
    }

    @Test
    @DisplayName("맨 앞에 구분자가 있으면 예외가 발생해야 한다.")
    void leading_delimiter_should_throw_exception() {
        // given
        String input = ",1,2";

        // when & then
        assertThatThrownBy(() -> validator.validateInputStructure(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("맨 앞이나 맨 뒤");
    }

    @Test
    @DisplayName("맨 뒤에 구분자가 있으면 예외가 발생해야 한다.")
    void trailing_delimiter_should_throw_exception() {
        // given
        String input = "1,2,";

        // when & then
        assertThatThrownBy(() -> validator.validateInputStructure(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("맨 앞이나 맨 뒤");
    }
}