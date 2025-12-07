package ch.nostromo.adventofcode.year2025.day06;

import ch.nostromo.adventofcode.BasePuzzle;
import lombok.Data;

import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 --- Day 6: Trash Compactor ---
 After helping the Elves in the kitchen, you were taking a break and helping them re-enact a movie scene when you over-enthusiastically jumped into the garbage chute!

 A brief fall later, you find yourself in a garbage smasher. Unfortunately, the door's been magnetically sealed.

 As you try to find a way out, you are approached by a family of cephalopods! They're pretty sure they can get the door open, but it will take some time. While you wait, they're curious if you can help the youngest cephalopod with her math homework.

 Cephalopod math doesn't look that different from normal math. The math worksheet (your puzzle input) consists of a list of problems; each problem has a group of numbers that need to be either added (+) or multiplied (*) together.

 However, the problems are arranged a little strangely; they seem to be presented next to each other in a very long horizontal list. For example:

 123 328  51 64
 45 64  387 23
 6 98  215 314
 *   +   *   +
 Each problem's numbers are arranged vertically; at the bottom of the problem is the symbol for the operation that needs to be performed. Problems are separated by a full column of only spaces. The left/right alignment of numbers within each problem can be ignored.

 So, this worksheet contains four problems:

 123 * 45 * 6 = 33210
 328 + 64 + 98 = 490
 51 * 387 * 215 = 4243455
 64 + 23 + 314 = 401
 To check their work, cephalopod students are given the grand total of adding together all of the answers to the individual problems. In this worksheet, the grand total is 33210 + 490 + 4243455 + 401 = 4277556.

 Of course, the actual worksheet is much wider. You'll need to make sure to unroll it completely so that you can read the problems clearly.

 Solve the problems on the math worksheet. What is the grand total found by adding together all of the answers to the individual problems?

 To begin, get your puzzle input.
 */
public class Part1 extends BasePuzzle {

    private static final String EXPECTED_TEST_RESULT = "4277556";

    @Data
    static class Column {
        List<Long> values = new ArrayList<>();
        String operation;

        public long getResult() {
            if (operation.equals("*")) {
                return values.stream().reduce(1L, (a, b) -> a * b);
            } else {
                return values.stream().mapToLong(Long::longValue).sum();
            }
        }
    }


    public String solvePuzzle(List<String> input) {
        List<Column> columns = new ArrayList<>();

        String firstLine = input.get(0);
        StringTokenizer fillSt = new StringTokenizer(firstLine);

        for (int i = 0; i < fillSt.countTokens(); i++) {
            columns.add(new Column());
        }

        for (String line : input) {
            StringTokenizer st = new StringTokenizer(line);

            int colCount = 0;
            boolean isOperationLine = line.startsWith("*");

            while (st.hasMoreTokens()) {
                String nextToken = st.nextToken();

                // Magically i know it's a * ;)
                if (isOperationLine) {
                    columns.get(colCount).setOperation(nextToken);
                } else {
                    columns.get(colCount).getValues().add(Long.parseLong(nextToken));
                }
                colCount++;
            }


        }

        return Long.toString(columns.stream().mapToLong(Column::getResult).sum());
    }


    public Part1() {
        super(EXPECTED_TEST_RESULT);
    }

    public static void main(String... args) {
        new Part1().run();
    }

}
