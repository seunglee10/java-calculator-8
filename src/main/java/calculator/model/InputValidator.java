package calculator.model;

// 모든 유효성 검사 로직을 담는 클래스
public class InputValidator {

    // 개별 숫자 문자열의 유효성 검사하는 메소드
    public int validateNumber(String numberString) {
        // Todo
        // 1. numberString이 숫자로 변환 가능한지 확인
        // 2. 음수 형식의 토큰인지 확인
        // 3. 값 반환

        try {
            return Integer.parseInt(numberString);
        } catch (NumberFormatException e) {
            throw e;
        }
    }

    // 최종 합계나 개별 숫자가 int 양수 범위를 초과하는지 검사
    public void validateRange(long number) {

    }
}
