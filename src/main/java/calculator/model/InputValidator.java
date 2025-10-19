package calculator.model;

import java.util.Objects;
import java.util.Arrays;
import java.util.List;

// 모든 유효성 검사 로직을 담는 클래스
public class InputValidator {

    // 숫자로 분리된 문자열 목록을 받아 유효성 검사
    public void validateNumbers(List<String> numbers) {
        // Todo 리스트의 개수가 100개를 초과하는지 검사하여 예외처리
        for (String number : numbers) {
            validateNumber(number);
        }
    }

    public void validateNumber(String number) {
        if (number == null || number.trim().isEmpty()) {
            return;
        }

        try {
            int num = Integer.parseInt(number);
//
//            if (num < 0) {
//                throw new RuntimeException("음수는 입력할 수 없습니다. 입력된 값: " + number);
//            }
        } catch (NumberFormatException e) {
            throw new RuntimeException("유효하지 않은 숫자 형식입니다. 입력된 값: " + number, e);
        }

    }

}
