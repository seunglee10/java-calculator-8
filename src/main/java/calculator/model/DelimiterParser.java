package calculator.model;

import java.util.regex.Pattern;

public class DelimiterParser {

    // 입력 문자열에서 커스텀 구분자를 파싱, 구분자를 포함하지 않는 숫자 문자열을 반환
    public String[] parse(String text) {
        // Todo
        // 1. 커스텀 구분자 패턴이 있는지 검사 (예: "//;\n")
        // 2. 커스텀 구분자가 있다면 구분자를 추출하고, 규칙 위반 시 예외 발생
        // 3. 추출된 구분자와 기본 구분자([, :])를 조합한 정규 표현식 반환

        // 임시로 빈 메서드 반환
        return new String[]{text};
    }
}
