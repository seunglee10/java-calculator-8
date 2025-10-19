package calculator.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;  // 예외 테스트에 사용

public class InputValidatorTest {

    private InputValidator validator;

    @BeforeEach
    void setUp() {
        // Model 계층의 실제 구현 객체를 생성
        validator = new InputValidator();
    }
}
