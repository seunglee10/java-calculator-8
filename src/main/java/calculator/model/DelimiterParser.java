package calculator.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DelimiterParser {

    // 괄호 안의 '.'은 임의의 문자 하나를 커스텀 구분자로 캡쳐, 두 번째 괄호 안의 '.*'은 숫자 문자열 부분을 캡쳐
    // private static final Pattern CUSTOM_DELIMITER_PATTERN = Pattern.compile("//(.)\\\\n(.*)");
    private static final Pattern CUSTOM_DELIMITER_PATTERN = Pattern.compile("^//([^0-9a-zA-Z])\\\\n(.*)$");
    // 입력 문자열에서 커스텀 구분자를 파싱, 계산에 사용될 숫자 문자열만 반환
    public String[] parse(String text) {
        if (text == null  || text.isEmpty()) {
            return new String[]{text};
        }

        Matcher m = CUSTOM_DELIMITER_PATTERN.matcher(text);

        if (m.find()) {
            // 커스텀 구분자가 존재하는 경우 :
            // m.group(1)은 커스텀 구분자 (예: ';'), m.group(2)는 숫자 문자열 (예: '1;2;3')
            // 테스트 통과를 위해 숫자 문자열 부분만 배열에 담아 반환
            return new String[]{m.group(2)};
        }
        // 커스텀 구분자가 없는 경우 입력 전체를 배열에 담아 반환
        return new String[]{text};
    }
}
