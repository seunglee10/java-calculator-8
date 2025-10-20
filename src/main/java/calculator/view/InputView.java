package calculator.view;

import camp.nextstep.edu.missionutils.Console;
import java.util.regex.Pattern;

public class InputView {

    // 커스텀 구분자 패턴: "//"로 시작하고 개행 문자가 포함된 경우
    // ^//.*[\r\n]: "//"로 시작하고 개행 문자(\r 또는 \n)가 포함됨
    private static final Pattern COMPLETE_CUSTOM_DELIMITER_PATTERN = Pattern.compile("^//.*[\\r\\n]", Pattern.DOTALL);

    // 문자열 입력 받는 메소드
    public String getInput() {
        System.out.println("덧셈할 문자열을 입력해 주세요.");

        String firstLine;
        try {
            firstLine = Console.readLine();
        } catch (Exception e) {
            // readLine 에러 발생 시 빈 문자열로 처리
            return "";
        }

        // 빈 문자열 처리
        if (firstLine == null || firstLine.isEmpty()) {
            return "";
        }

        // 커스텀 구분자 형식 체크
        // 이미 개행 문자가 포함된 완전한 형식이면 그대로 반환 (단위 테스트 등)
        if (COMPLETE_CUSTOM_DELIMITER_PATTERN.matcher(firstLine).find()) {
            return firstLine;
        }

        // "//"로 시작하지만 개행 문자가 없는 경우 → 두 번째 줄 읽기 (실제 사용자 입력)
        if (firstLine.startsWith("//")) {
            String secondLine = Console.readLine();
            return firstLine + "\n" + secondLine;
        }

        return firstLine;
    }

}
