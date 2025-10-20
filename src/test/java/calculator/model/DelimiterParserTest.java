package calculator.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

public class DelimiterParserTest {

    private DelimiterParser parser;

    // 각 테스트 메소드가 실행되기 전 호출되어 테스트에 필요한 객체를 준비.
    @BeforeEach
    void setUp() {
        // Model 계층의 실제 구현 객체를 생성
        parser = new DelimiterParser();
    }

    @Test
    @DisplayName("커스텀 구분자(;)가 있을 경우, 계산할 문자열만 추출해야 함")
    void custom_delimiter_semicolon_should_extract_numbers_string() {
        // given
        String input = "//;\n1;2;3";
        String expectedNumbers = "1;2;3";
        String expectedDelimiter = ";";

        // when
        DelimiterParserResult result = parser.parse(input);

        // then
        assertThat(result.getNumbersString()).isEqualTo(expectedNumbers);
        assertThat(result.getCustomDelimiters()).containsExactly(expectedDelimiter);
    }

    @Test
    @DisplayName("커스텀 구분자(?)가 있을 경우, 계산할 문자열만 추출해야 함")
    void custom_delimiter_question_should_extract_numbers_string() {
        // given
        String input = "//?\n4?5?6";
        String expectedNumbers = "4?5?6";
        String expectedDelimiter = "?";

        // when
        DelimiterParserResult result = parser.parse(input);

        // then
        assertThat(result.getNumbersString()).isEqualTo(expectedNumbers);
        assertThat(result.getCustomDelimiters()).containsExactly(expectedDelimiter);
    }



    @Test
    @DisplayName("커스텀 구분자가 없을 경우, 입력 전체를 반환해야 함")
    void no_custom_delimiter_should_return_all_input() {
        // given
        String input = "1,2:3";

        // when
        DelimiterParserResult result = parser.parse(input);

        // then
        // 현재 parse()는 입력 전체를 반환하므로, 이 테스트는 당장은 성공하지만, 이후 로직 변경 시 다시 실패(RED)할 수 있습니다.
        assertThat(result.getNumbersString()).isEqualTo(input);
        assertThat(result.getCustomDelimiters()).isEmpty();
    }
    @ParameterizedTest
    @ValueSource(strings = {"//1\\n1;2", "//123\\n1;2", "//999\\n1;2"})
    @DisplayName("구분자가 숫자로만 구성된 경우에도 파싱은 성공해야 함 (검증은 InputValidator에서 수행)")
    void delimiter_numeric_only_should_be_parsed(String input) {
        // given
        // 입력값은 ValueSource에서 제공

        // when
        DelimiterParserResult result = parser.parse(input);

        // then
        // 파싱은 성공하고, 구분자와 숫자 문자열이 분리되어야 함
        assertThat(result.getCustomDelimiters()).hasSize(1);
        assertThat(result.getNumbersString()).isEqualTo("1;2");
        // 숫자로만 구성된 구분자 검증은 InputValidator에서 수행됨
    }

    @Test
    @DisplayName("커스텀 구분자가 없을 경우, 입력 문자열과 빈 구분자 배열을 반환해야 함")
    void no_custom_delimiter_should_return_default_delimiters() {
        // given
        String input = "1,2:3";

        //when
        DelimiterParserResult result = parser.parse(input);

        // then
        assertThat(result.getNumbersString()).isEqualTo("1,2:3");
        assertThat(result.getCustomDelimiters()).isEmpty();
    }
    @Test
    @DisplayName("커스텀 구분자가 있을 경우, 숫자 문자열과 해당 구분자를 반환해야 함")
    void custom_delimiter_should_return_delimiter_and_numbers_string() {
        // given
        String input = "//;\n1,2;3";

        //when
        DelimiterParserResult result = parser.parse(input);

        //then
        assertThat(result.getNumbersString()).isEqualTo("1,2;3");
        assertThat(result.getCustomDelimiters()).containsExactly(";");
    }

    @Test
    @DisplayName("백슬래시(\\) 구분자를 사용할 수 있어야 한다.")
    void backslash_delimiter_should_work() {
        // given
        String input = "//\\\n1\\2\\3";

        //when
        DelimiterParserResult result = parser.parse(input);

        //then
        assertThat(result.getNumbersString()).isEqualTo("1\\2\\3");
        assertThat(result.getCustomDelimiters()).containsExactly("\\");
    }

    @Test
    @DisplayName("정규식 메타 문자($) 구분자를 사용할 수 있어야 한다.")
    void regex_metacharacter_delimiter_should_work() {
        // given
        String input = "//$\n1$2$3";

        //when
        DelimiterParserResult result = parser.parse(input);

        //then
        assertThat(result.getNumbersString()).isEqualTo("1$2$3");
        assertThat(result.getCustomDelimiters()).containsExactly("$");
    }

    @Test
    @DisplayName("점(.) 구분자를 사용할 수 있어야 한다.")
    void dot_delimiter_should_work() {
        // given
        String input = "//.\n1.2.3";

        //when
        DelimiterParserResult result = parser.parse(input);

        //then
        assertThat(result.getNumbersString()).isEqualTo("1.2.3");
        assertThat(result.getCustomDelimiters()).containsExactly(".");
    }

    @Test
    @DisplayName("다글자 구분자(abc)를 사용할 수 있어야 한다.")
    void multi_char_delimiter_should_work() {
        // given
        String input = "//abc\n1abc2abc3";

        //when
        DelimiterParserResult result = parser.parse(input);

        //then
        assertThat(result.getNumbersString()).isEqualTo("1abc2abc3");
        assertThat(result.getCustomDelimiters()).containsExactly("abc");
    }

    @Test
    @DisplayName("숫자와 문자 결합 구분자(a1b)를 사용할 수 있어야 한다.")
    void alphanumeric_delimiter_should_work() {
        // given
        String input = "//a1b\n1a1b2a1b3";

        //when
        DelimiterParserResult result = parser.parse(input);

        //then
        assertThat(result.getNumbersString()).isEqualTo("1a1b2a1b3");
        assertThat(result.getCustomDelimiters()).containsExactly("a1b");
    }

    @Test
    @DisplayName("숫자 문자열에 개행이 여러 개 있어도 첫 번째 개행까지만 구분자로 인식해야 한다.")
    void multiple_newlines_in_numbers_should_work() {
        // given
        String input = "//;\n1;2\n3";

        //when
        DelimiterParserResult result = parser.parse(input);

        //then
        assertThat(result.getNumbersString()).isEqualTo("1;2\n3");
        assertThat(result.getCustomDelimiters()).containsExactly(";");
    }

}
