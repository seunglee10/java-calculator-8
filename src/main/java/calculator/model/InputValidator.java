package calculator.model;

import java.util.List;

// 모든 유효성 검사 로직을 담는 클래스
public class InputValidator {

    private final DelimiterParser delimiterParser;

    public InputValidator() {
        this.delimiterParser = new DelimiterParser();
    }

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

    // 커스텀 구분자 형식 검증 - DelimiterParser를 사용하여 파싱 시도
    private void validateCustomDelimiterStructure(String text) {
        DelimiterParserResult result = delimiterParser.parse(text);

        // 파싱 실패 시 (커스텀 구분자 형식이 아님)
        if (result.getCustomDelimiters().length == 0) {
            throw new IllegalArgumentException("커스텀 구분자 형식이 올바르지 않습니다. 형식: //[구분자]\\n[숫자]");
        }

        String delimiter = result.getCustomDelimiters()[0];
        String numbersString = result.getNumbersString();

        // 커스텀 구분자 검증 (숫자만으로 구성된 경우 불가, 숫자와 문자 결합은 허용)
        if (delimiter.matches("^[0-9]+$")) {
            throw new IllegalArgumentException("구분자는 숫자만으로 구성될 수 없습니다. 입력된 구분자: " + delimiter);
        }

        // 개행 문자 포함 검증
        if (delimiter.contains("\n") || delimiter.contains("\r")) {
            throw new IllegalArgumentException("구분자에 개행 문자를 포함할 수 없습니다. 입력된 구분자: " + delimiter);
        }

        // 숫자 문자열에 개행 문자가 맨 앞이나 맨 뒤에 있는지 검증
        if (numbersString.startsWith("\n") || numbersString.startsWith("\r") ||
            numbersString.endsWith("\n") || numbersString.endsWith("\r")) {
            throw new IllegalArgumentException("문자열의 맨 앞이나 맨 뒤에 구분자가 올 수 없습니다.");
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