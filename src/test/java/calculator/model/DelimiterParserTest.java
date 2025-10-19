package calculator.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy; // 예외 테스트에 사용

public class DelimiterParserTest {

    private DelimiterParser parser;

    // 각 테스트 메소드가 실행되기 전 호출되어 테스트에 필요한 객체를 준비.
    @BeforeEach
    void setUp() {
        // Model 계층의 실제 구현 객체를 생성
        parser = new DelimiterParser();
    }

    @ParameterizedTest
    @CsvSource(value = {"//;\n1;2;3=1;2;3", "//a\n4a5a6=4a5a6", "// \n1 2 3=1 2 3"}, delimiter = '=')
    @DisplayName("커스텀 구분자가 있을 경우, 숫자 문자열만 추출해야 한다.")
    void custom_delimiter_should_extract_numbers_string(String input, String expected) {
        // given
        // 입력값과 기대값은 CsvSource에서 제공

        // when
        // 현재 parse() 메서드는 입력 전체를 반환하므로, 구분자가 포함된 문자열을 반환하여 테스트 실패(RED) 유도
        String[] result = parser.parse(input);

        // then
        assertThat(result).containsExactly(expected.split(" ")); // 배열을 문자열로 비교하기 위해 분리합니다.
    }

    @Test
    @DisplayName("커스텀 구분자가 없을 경우, 입력 전체를 반환해야 한다.")
    void no_custom_delimiter_should_return_all_input() {
        // given
        String input = "1,2:3";

        // when
        String[] result = parser.parse(input);

        // then
        // 현재 parse()는 입력 전체를 반환하므로, 이 테스트는 당장은 성공하지만, 이후 로직 변경 시 다시 실패(RED)할 수 있습니다.
        assertThat(result).containsExactly(input);
    }

}
