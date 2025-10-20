package calculator.service;

import calculator.model.DelimiterParser;
import calculator.model.DelimiterParserResult;
import calculator.model.InputValidator;

import java.util.regex.Pattern;

public class StringCalculator {

    private final DelimiterParser parser;
    private final InputValidator validator;

    public StringCalculator() {
        this.parser = new DelimiterParser();
        this.validator = new InputValidator();
    }

    // 문자열을 입력받아 숫자를 추출하고 합을 반환하는 메소드
    public long add(String text) {
        // 빈 문자열 처리 (Controller에서도 체크하지만 Service 레이어의 안정성을 위해 중복 체크)
        if (text == null || text.isEmpty()) {
            return 0;
        }

        // 입력 구조 검증
        validator.validateInputStructure(text);

        DelimiterParserResult parsedResult = parser.parse(text);
        return sum(parsedResult);

    }
    private long sum(DelimiterParserResult result) {
        String numbersString = result.getNumbersString();

        // 커스텀 구분자가 있으면 먼저 커스텀 구분자를 통일된 기본 구분자로 치환
        if (result.getCustomDelimiters().length > 0) {
            String customDelimiter = result.getCustomDelimiters()[0];
            numbersString = numbersString.replace(customDelimiter, ",");
        }

        // 기본 구분자 정규식으로 분리 (쉼표, 콜론)
        String delimiterRegex = "[,:]";
        String[] numbers = numbersString.split(delimiterRegex);

        long totalSum = 0;
        // 분리된 문자열 배열을 순회하며 계산

        for (String number : numbers) {
            validator.validateNumber(number);
            long num = Long.parseLong(number);

            // long 범위 overflow 체크
            if (totalSum > Long.MAX_VALUE - num) {
                throw new IllegalArgumentException("합계가 long 타입의 최대값을 초과합니다.");
            }

            totalSum += num;
        }
        return totalSum;
    }
}
