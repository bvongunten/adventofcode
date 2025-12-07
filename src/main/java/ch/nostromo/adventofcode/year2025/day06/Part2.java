package ch.nostromo.adventofcode.year2025.day06;

import ch.nostromo.adventofcode.BasePuzzle;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * --- Day 6: Trash Compactor ---
 * After helping the Elves in the kitchen, you were taking a break and helping them re-enact a movie scene when you over-enthusiastically jumped into the garbage chute!
 * <p>
 * A brief fall later, you find yourself in a garbage smasher. Unfortunately, the door's been magnetically sealed.
 * <p>
 * As you try to find a way out, you are approached by a family of cephalopods! They're pretty sure they can get the door open, but it will take some time. While you wait, they're curious if you can help the youngest cephalopod with her math homework.
 * <p>
 * Cephalopod math doesn't look that different from normal math. The math worksheet (your puzzle input) consists of a list of problems; each problem has a group of numbers that need to be either added (+) or multiplied (*) together.
 * <p>
 * However, the problems are arranged a little strangely; they seem to be presented next to each other in a very long horizontal list. For example:
 * <p>
 * 123 328  51 64
 * 45 64  387 23
 * 6 98  215 314
 * +   *   +
 * Each problem's numbers are arranged vertically; at the bottom of the problem is the symbol for the operation that needs to be performed. Problems are separated by a full column of only spaces. The left/right alignment of numbers within each problem can be ignored.
 * <p>
 * So, this worksheet contains four problems:
 * <p>
 * 123 * 45 * 6 = 33210
 * 328 + 64 + 98 = 490
 * 51 * 387 * 215 = 4243455
 * 64 + 23 + 314 = 401
 * To check their work, cephalopod students are given the grand total of adding together all of the answers to the individual problems. In this worksheet, the grand total is 33210 + 490 + 4243455 + 401 = 4277556.
 * <p>
 * Of course, the actual worksheet is much wider. You'll need to make sure to unroll it completely so that you can read the problems clearly.
 * <p>
 * Solve the problems on the math worksheet. What is the grand total found by adding together all of the answers to the individual problems?
 * <p>
 * To begin, get your puzzle input.
 * <p>
 * --- Part Two ---
 * The big cephalopods come back to check on how things are going. When they see that your grand total doesn't match the one expected by the worksheet, they realize they forgot to explain how to read cephalopod math.
 * <p>
 * Cephalopod math is written right-to-left in columns. Each number is given in its own column, with the most significant digit at the top and the least significant digit at the bottom. (Problems are still separated with a column consisting only of spaces, and the symbol at the bottom of the problem is still the operator to use.)
 * <p>
 * Here's the example worksheet again:
 * <p>
 * 123 328  51 64
 * 45 64  387 23
 * 6 98  215 314
 * +   *   +
 * Reading the problems right-to-left one column at a time, the problems are now quite different:
 * <p>
 * The rightmost problem is 4 + 431 + 623 = 1058
 * The second problem from the right is 175 * 581 * 32 = 3253600
 * The third problem from the right is 8 + 248 + 369 = 625
 * Finally, the leftmost problem is 356 * 24 * 1 = 8544
 * Now, the grand total is 1058 + 3253600 + 625 + 8544 = 3263827.
 * <p>
 * Solve the problems on the math worksheet again. What is the grand total found by adding together all of the answers to the individual problems?
 */
public class Part2 extends BasePuzzle {

    private static final String EXPECTED_TEST_RESULT = "3263827";

    @Data
    static class Column {
        List<String> stringValues = new ArrayList<>();
        String operation;

        public long getResult() {
            List<Long> values = new ArrayList<>();

            for (int i = 0; i < 4; i++) {
                String numberString = "";

                for (String stringVal : stringValues) {
                    if (stringVal.length() > i) {
                        String part = stringVal.substring(stringVal.length() - i - 1, stringVal.length() - i);
                        if (!part.isBlank()) {
                            numberString += part;
                        }
                    }
                }

                if (!numberString.isEmpty()) {
                    values.add(Long.valueOf(numberString));
                }
            }

            if (operation.equals("*")) {
               return values.stream().reduce(1L, (a, b) -> a * b);
            } else {
                return values.stream().mapToLong(Long::longValue).sum();
            }
        }
    }


    public String solvePuzzle(List<String> input) {
        List<Column> columns = new ArrayList<>();

        String operations = input.get(input.size() - 1);

        int currFrom = 0;
        String currOp = "";

        for (int i = 0; i < operations.length(); i++) {
            if (!operations.substring(i, i + 1).isBlank()) {

                // Not the first ;)
                if (i > 1) {

                    Column col = new Column();
                    col.operation = currOp;

                    for (int s = 0; s < input.size() - 1; s++) {
                        col.stringValues.add(input.get(s).substring(currFrom, i - 1));
                    }

                    columns.add(col);
                    currFrom = i;
                }

                currOp = operations.substring(i, i + 1);

            }
        }

        // Last one
        Column col = new Column();
        col.operation = currOp;
        for (int s = 0; s < input.size() - 1; s++) {
            col.stringValues.add(input.get(s).substring(currFrom));
        }
        columns.add(col);

        return Long.toString(columns.stream().mapToLong(Column::getResult).sum());
    }


    public Part2() {
        super(EXPECTED_TEST_RESULT);
    }

    public static void main(String... args) {
        new Part2().run();
    }

}
