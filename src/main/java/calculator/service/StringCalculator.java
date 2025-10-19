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
    public int add(String text) {
        // TDD 2단계 빈 문자열 처리 (GREEN)
        if (text == null || text.isEmpty()) {
            return 0;
        }
        DelimiterParserResult parsedResult = parser.parse(text);
        return sum(parsedResult);

    }
    private int sum(DelimiterParserResult result) {
        String numbersString = result.getNumbersString();

        String delimiterRegex = "[,:]";

        if (result.getCustomDelimiters().length > 0) {
            String customDelimiter = Pattern.quote(result.getCustomDelimiters()[0]);
            delimiterRegex += "|" + customDelimiter;
        }

        // 문자열을 쉼표(,) 기분으로 분리
        String[] numbers = numbersString.split(delimiterRegex);

        int totalSum = 0;
        // 분리된 문자열 배열을 순회하며 계산

        for (String number : numbers) {
            validator.validateNumber(number);
            totalSum += Integer.parseInt(number);
        }
        return totalSum;
    }
}
