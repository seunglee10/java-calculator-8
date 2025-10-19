package calculator.service;

import calculator.model.DelimiterParser;
import calculator.model.InputValidator;

public class StringCalculator {

    private final DelimiterParser parser;
    private final InputValidator validator;


    public StringCalculator() {
        this.parser = new DelimiterParser();
        this.validator = new InputValidator();
    }

    // 문자열을 입력받아 숫자를 추출하고 합을 반환하는 메소드
    public int add(String text) {
        // TDD 2단계 빈 문자열 처리 (GREEN)
        if (text == null || text.isEmpty()) {
            return 0;
        }

        String[] parsedStrings = parser.parse(text);

        return sum(parsedStrings);

    }
    private int sum(String[] parsedStrings) {
        int totalSum = 0;

        String numberString = parsedStrings[0];

        String delimiterRegx = "[,:]";

        String replacedText = numberString.replaceAll(":", ",");

        // 문자열을 쉼표(,) 기분으로 분리
        String[] numbers = replacedText.split(",");

        // 분리된 문자열 배열을 순회하며 계산

        for (String number : numbers) {
            validator.validateNumber(number);
        }
        return totalSum;
    }
}
