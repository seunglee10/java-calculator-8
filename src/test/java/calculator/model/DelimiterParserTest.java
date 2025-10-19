package calculator.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

}
