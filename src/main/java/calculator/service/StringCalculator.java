package calculator.service;

public class StringCalculator {

    // 문자열을 입력받아 숫자를 추출하고 합을 반환하는 메소드
    public int add(String text) {
        // TDD 2단계 빈 문자열 처리 (GREEN)
        if (text == null || text.isEmpty()) {
            return 0;
        }

        // TDD 단계 : 기본 구분자 처리 (RED -> GREEN)
        // 문자열을 쉼표(,)와 콜론(:)을 모두 구분자로 사용하기 위해 하나의 구분자(쉼표)로 통일
        String replacedText = text.replaceAll(":",",");

        // 문자열을 쉼표(,) 기분으로 분리
        String[] numbers = replacedText.split(",");

        // 분리된 문자열 배열을 순회하며 계산
        int sum = 0;
        for (String number : numbers) {
            // 음수나 숫자 외 문자에 대한 유효성 검사 로직 추가 예정
            sum += Integer.parseInt(number);
        }
        return sum;

    }
}
