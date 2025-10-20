package calculator.model;

import java.util.Objects;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

// 모든 유효성 검사 로직을 담는 클래스
public class InputValidator {

    // 커스텀 구분자 패턴: 1글자 이상, 숫자와 문자 결합 허용
    // 개행 형식: 실제 개행 문자만 지원 (\n, \r\n, \r)
    private static final Pattern CUSTOM_DELIMITER_PATTERN = Pattern.compile("^//(.+)(?:\\n|\\r\\n|\\r)(.*)$", Pattern.DOTALL);

    // 모든 개행 형식 지원 (리터럴 포함) - 필요시 활성화
    // private static final Pattern CUSTOM_DELIMITER_PATTERN = Pattern.compile("^//([^0-9a-zA-Z\\s])(?:\\\\n|\\\\r\\\\n|\\n|\\r\\n|\\r)(.*)$", Pattern.DOTALL);

    // 입력 문자열 전체 구조 검증
    public void validateInputStructure(String text) {
        if (text == null || text.isEmpty()) {
            return;
        }

        // 커스텀 구분자 형식인지 확인
        if (text.startsWith("//")) {
            validateCustomDelimiterStructure(text);
        } else {
            validateBasicStructure(text);
        }
    }

    // 커스텀 구분자 형식 검증
    private void validateCustomDelimiterStructure(String text) {
        Matcher matcher = CUSTOM_DELIMITER_PATTERN.matcher(text);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("커스텀 구분자 형식이 올바르지 않습니다. 형식: //[구분자]\\n[숫자]");
        }

        // 정규표현식 그룹으로 구분자와 숫자 문자열 추출
        matcher.reset();
        matcher.find();
        String delimiter = matcher.group(1);
        String numbersString = matcher.group(2);

        // 커스텀 구분자 검증 (숫자만으로 구성된 경우 불가, 숫자와 문자 결합은 허용)
        if (delimiter.matches("^[0-9]+$")) {
            throw new IllegalArgumentException("구분자는 숫자만으로 구성될 수 없습니다. 입력된 구분자: " + delimiter);
        }

        // 개행 문자 포함 검증
        if (delimiter.contains("\n") || delimiter.contains("\r")) {
            throw new IllegalArgumentException("구분자에 개행 문자를 포함할 수 없습니다. 입력된 구분자: " + delimiter);
        }

        // 맨 앞/뒤는 반드시 숫자여야 함 (어떤 특수문자든 불가)
        if (!numbersString.matches("^\\d.*\\d$") && !numbersString.matches("^\\d$")) {
            throw new IllegalArgumentException("문자열의 맨 앞이나 맨 뒤에 구분자가 올 수 없습니다.");
        }

    }

    // 기본 구분자 형식 검증
    private void validateBasicStructure(String text) {
        validateNumbersStructure(text, "[,:]");
    }

    // 숫자 문자열 구조 검증 (구분자 위치, 연속된 구분자 등)
    private void validateNumbersStructure(String text, String delimiterRegex) {
        // 맨 앞이나 맨 뒤에 구분자가 있는지 검증
        if (text.matches("^" + delimiterRegex + ".*") || text.matches(".*" + delimiterRegex + "$")) {
            throw new IllegalArgumentException("문자열의 맨 앞이나 맨 뒤에 구분자가 올 수 없습니다.");
        }

    }

    // 숫자로 분리된 문자열 목록을 받아 유효성 검사
    public void validateNumbers(List<String> numbers) {
        for (String number : numbers) {
            validateNumber(number);
        }
    }

    public void validateNumber(String number) {
        if (number == null || number.trim().isEmpty()) {
            throw new IllegalArgumentException("빈 숫자는 허용되지 않습니다.");
        }

        // 앞의 0 제거 (예: "0001" -> "1", "000" -> "")
        String trimmedNumber = number.replaceFirst("^0+", "");

        // 모두 0이었던 경우 0 초과하는 양수가 아니므로 에러 처리
        if (trimmedNumber.isEmpty()) {
            throw new IllegalArgumentException("0 초과하는 양수만 허용됩니다. 입력된 값: " + number);
        }

        try {
            long num = Long.parseLong(number);

            // long 범위 검증
            if (num > Long.MAX_VALUE) {
                throw new IllegalArgumentException("숫자가 long 타입의 최대값을 초과합니다. 입력된 값: " + number);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("유효하지 않은 숫자 형식입니다. 입력된 값: " + number);
        }
    }
}
