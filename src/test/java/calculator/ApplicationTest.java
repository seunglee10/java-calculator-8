package calculator;

import camp.nextstep.edu.missionutils.test.NsTest;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

// NsTest를 상속받아 콘솔 입출력 모의 테스트 환경 설정
class ApplicationTest extends NsTest {

//    API 적용 시점에 구현
      /*
      @Test
      @DisplayName("정상 입력 시 결과를 출력해야 한다.")
      void run_test_success() {
          // given: input("1,2:3")

          // when: runMain();

          // then: Nstest 의 assertOutput() 사용으로 검증
      }
      */

//    @Test
//    void 커스텀_구분자_사용() {
//        assertSimpleTest(() -> {
//            run("//;\\n1");
//            assertThat(output()).contains("결과 : 1");
//        });
//    }
//
//    @Test
//    void 예외_테스트() {
//        assertSimpleTest(() ->
//            assertThatThrownBy(() -> runException("-1,2,3"))
//                .isInstanceOf(IllegalArgumentException.class)
//        );
//    }
//
    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}


