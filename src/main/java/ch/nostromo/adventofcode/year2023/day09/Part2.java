package ch.nostromo.adventofcode.year2023.day09;

import ch.nostromo.adventofcode.BasePuzzle;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Part2 extends BasePuzzle {

    private static final String EXPECTED_TEST_RESULT = "2";


    public String solvePuzzle(List<String> input) {

        int result = 0;
        for (String line : input) {
            History history = new History(line);

            while (!history.addNewHistoryLine()) {

            }

            result += history.getHistoryLines().get(0).get(0);
        }


        return String.valueOf(result);
    }

    @Data
    public class History {

        List<List<Integer>> historyLines = new ArrayList<>();

        public History(String line) {
            List<Integer> historyLine = new ArrayList<>();
            for (String number : line.split("\\s+")) {
                historyLine.add(Integer.valueOf(number));
            }

            historyLines.add(historyLine);
        }

        public boolean addNewHistoryLine() {
            List<Integer> parentLine = historyLines.get(historyLines.size() - 1);
            List<Integer> childLine = new ArrayList<>();

            boolean result = true;

            for (int i = 0; i < parentLine.size() - 1; i++) {
                int diff = parentLine.get(i + 1) - parentLine.get(i);

                childLine.add(diff);

                if (diff != 0) {
                    result = false;
                }
            }

            historyLines.add(childLine);

            if (result) {

                for (int i = historyLines.size() - 1; i > 0; i--) {
                    List<Integer> lineToAdd = historyLines.get(i - 1);
                    List<Integer> diffLine = historyLines.get(i);

                    lineToAdd.add(0, lineToAdd.get(0) - diffLine.get(0));
                }
            }

            return result;
        }
    }

    public Part2() {
        super(EXPECTED_TEST_RESULT);
    }

    public static void main(String... args) {
        new Part2().run();
    }

}
