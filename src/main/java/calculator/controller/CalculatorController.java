package calculator.controller;

import calculator.service.StringCalculator;
import calculator.view.InputView;
import calculator.view.OutputView;

public class CalculatorController {

    private final InputView inputView;
    private final OutputView outputView;
    private final StringCalculator calculator;

    public CalculatorController() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.calculator = new StringCalculator();
    }

    public void run() {
        try {
            // 1. InputView를 통해 입력
            String input = inputView.getInput();

            // 2. 빈 문자열 체크 (모든 로직 시작 전 맨 처음)
            if (input == null || input.isEmpty()) {
                outputView.printResult(0);
                return;
            }

            // 3. StringCalculator(Service)를 호출
            long result = calculator.add(input);

            // 4. OutputView로 결과 출력
            outputView.printResult(result);

        } catch (IllegalArgumentException e) {
            // 예외 발생 시 오류 메시지 출력 후 프로그램 종료
            outputView.printError(e);
            throw e;
        }
    }
}
