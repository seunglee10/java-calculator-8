package calculator.model;

// DelimiterParser의 파싱 결과를 담는 Value Object
public class DelimiterParserResult {

    // 숫자 문자열 : "1,2:3" 또는 "1;2;3"
    private final String numbersString;

    private final String[] customDelimiters;

    public DelimiterParserResult(String numbersString, String[] customDelimiters) {
        this.numbersString = numbersString;
        this.customDelimiters = customDelimiters;
    }

    public String getNumbersString() {
        return numbersString;
    }

    public  String[] getCustomDelimiters() {
        return customDelimiters;
    }
}
