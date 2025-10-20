package calculator;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * ApplicationTest - 통합 테스트 (콘솔 입출력 테스트)
 *
 * NsTest를 상속받아 콘솔 입출력을 모의하는 통합 테스트입니다.
 *
 * 커스텀 구분자 처리:
 * - InputView가 "//"로 시작하는 입력을 감지하면 자동으로 두 번째 줄을 읽습니다.
 * - NsTest에서는 run("//;", "1;2;3") 형식으로 2줄 입력을 시뮬레이션합니다.
 * - 실제 실행 시에는 사용자가 구분자 선언과 숫자를 각각 입력합니다.
 */
class ApplicationTest extends NsTest {

    // camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest 사용
    // static import되어 있어 바로 사용 가능

    // 기본 기능 테스트 (한 줄 입력으로 가능한 경우)

    @Test
    @DisplayName("단일 숫자 입력 시 해당 숫자를 출력해야 한다.")
    void single_number_should_return_itself() {
        assertSimpleTest(() -> {
            run("5");
            assertThat(output()).contains("결과 : 5");
        });
    }

    @Test
    @DisplayName("쉼표 구분자로 숫자를 입력하면 합계를 출력해야 한다.")
    void comma_delimiter_should_return_sum() {
        assertSimpleTest(() -> {
            run("1,2,3");
            assertThat(output()).contains("결과 : 6");
        });
    }

    @Test
    @DisplayName("콜론 구분자로 숫자를 입력하면 합계를 출력해야 한다.")
    void colon_delimiter_should_return_sum() {
        assertSimpleTest(() -> {
            run("1:2:3");
            assertThat(output()).contains("결과 : 6");
        });
    }

    @Test
    @DisplayName("쉼표와 콜론을 혼합하여 사용할 수 있어야 한다.")
    void mixed_delimiters_should_return_sum() {
        assertSimpleTest(() -> {
            run("1,2:3");
            assertThat(output()).contains("결과 : 6");
        });
    }

    // 예외 처리 테스트

    @Test
    @DisplayName("맨 뒤에 구분자가 있으면 예외가 발생해야 한다.")
    void trailing_delimiter_should_throw_exception() {
        assertSimpleTest(() ->
            assertThatThrownBy(() -> runException("1,2,"))
                .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    @DisplayName("숫자가 아닌 문자가 포함되면 예외가 발생해야 한다.")
    void non_numeric_should_throw_exception() {
        assertSimpleTest(() ->
            assertThatThrownBy(() -> runException("1,a,3"))
                .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    @DisplayName("0 입력 시 예외가 발생해야 한다.")
    void zero_input_should_throw_exception() {
        assertSimpleTest(() ->
            assertThatThrownBy(() -> runException("0"))
                .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    @DisplayName("000 입력 시 예외가 발생해야 한다.")
    void all_zeros_should_throw_exception() {
        assertSimpleTest(() ->
            assertThatThrownBy(() -> runException("000"))
                .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    @DisplayName("맨 앞에 구분자가 있으면 예외가 발생해야 한다.")
    void leading_delimiter_should_throw_exception() {
        assertSimpleTest(() ->
            assertThatThrownBy(() -> runException(",1,2"))
                .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    @DisplayName("앞에 0이 있는 숫자는 정상 처리되어야 한다.")
    void leading_zeros_should_work() {
        assertSimpleTest(() -> {
            run("0001,0002,0003");
            assertThat(output()).contains("결과 : 6");
        });
    }

    @Test
    @DisplayName("큰 숫자의 합계를 정상 처리해야 한다.")
    void large_numbers_should_work() {
        assertSimpleTest(() -> {
            run("1000,2000,3000");
            assertThat(output()).contains("결과 : 6000");
        });
    }

    @Test
    @DisplayName("많은 숫자를 입력해도 정상 처리되어야 한다.")
    void many_numbers_should_work() {
        assertSimpleTest(() -> {
            run("1,1,1,1,1,1,1,1,1,1"); // 10개
            assertThat(output()).contains("결과 : 10");
        });
    }

    @Test
    @DisplayName("빈 문자열 입력 시 0을 출력해야 한다.")
    void empty_string_should_return_zero() {
        assertSimpleTest(() -> {
            run("");
            assertThat(output()).contains("결과 : 0");
        });
    }

    @Test
    @DisplayName("long 최대값에 가까운 숫자도 정상 처리되어야 한다.")
    void near_long_max_should_work() {
        assertSimpleTest(() -> {
            run("9223372036854775806");
            assertThat(output()).contains("결과 : 9223372036854775806");
        });
    }

    // 커스텀 구분자 테스트
    @Test
    @DisplayName("커스텀 구분자(;)로 숫자를 구분할 수 있어야 한다.")
    void custom_delimiter_semicolon_should_work() {
        assertSimpleTest(() -> {
            run("//;", "1;2;3");
            assertThat(output()).contains("결과 : 6");
        });
    }

    @Test
    @DisplayName("커스텀 구분자(-)로 숫자를 구분할 수 있어야 한다.")
    void custom_delimiter_dash_should_work() {
        assertSimpleTest(() -> {
            run("//-", "2-3-4");
            assertThat(output()).contains("결과 : 9");
        });
    }

    @Test
    @DisplayName("커스텀 구분자와 기본 구분자를 함께 사용할 수 있어야 한다.")
    void custom_and_default_delimiters_should_work() {
        assertSimpleTest(() -> {
            run("//-", "5-6,7");
            assertThat(output()).contains("결과 : 18");
        });
    }

    @Test
    @DisplayName("다글자 커스텀 구분자를 사용할 수 있어야 한다.")
    void multi_char_custom_delimiter_should_work() {
        assertSimpleTest(() -> {
            run("//abc", "1abc2abc3");
            assertThat(output()).contains("결과 : 6");
        });
    }

    @Test
    @DisplayName("숫자와 문자 결합 커스텀 구분자(a1b)를 사용할 수 있어야 한다.")
    void alphanumeric_custom_delimiter_should_work() {
        assertSimpleTest(() -> {
            run("//a1b", "1a1b2a1b3");
            assertThat(output()).contains("결과 : 6");
        });
    }

    @Test
    @DisplayName("숫자와 문자 결합 커스텀 구분자(abc3)를 사용할 수 있어야 한다.")
    void alphanumeric_with_number_at_end_should_work() {
        assertSimpleTest(() -> {
            run("//abc3", "1abc32abc33");
            assertThat(output()).contains("결과 : 6");
        });
    }

    @Test
    @DisplayName("숫자와 문자 결합 커스텀 구분자(1a)를 사용할 수 있어야 한다.")
    void number_first_alphanumeric_delimiter_should_work() {
        assertSimpleTest(() -> {
            run("//1a", "11a21a3");
            assertThat(output()).contains("결과 : 6");
        });
    }

    @Test
    @DisplayName("숫자와 문자 결합 커스텀 구분자(a2b3)를 사용할 수 있어야 한다.")
    void complex_alphanumeric_delimiter_should_work() {
        assertSimpleTest(() -> {
            run("//a2b3", "1a2b32a2b33");
            assertThat(output()).contains("결과 : 6");
        });
    }

    @Test
    @DisplayName("숫자만으로 구성된 커스텀 구분자는 예외가 발생해야 한다.")
    void numeric_only_custom_delimiter_should_throw_exception() {
        assertSimpleTest(() ->
            assertThatThrownBy(() -> runException("//123", "1,2,3"))
                .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}

