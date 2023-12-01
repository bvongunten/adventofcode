package ch.nostromo.adventofcode.year2023.day01;

import ch.nostromo.adventofcode.BasePuzzle;
import lombok.Data;

import java.util.List;

/**
 * --- Day 1: Trebuchet?! ---
 * Something is wrong with global snow production, and you've been selected to take a look. The Elves have even given you a map; on it, they've used stars to mark the top fifty locations that are likely to be having problems.
 * <p>
 * You've been doing this long enough to know that to restore snow operations, you need to check all fifty stars by December 25th.
 * <p>
 * Collect stars by solving puzzles. Two puzzles will be made available on each day in the Advent calendar; the second puzzle is unlocked when you complete the first. Each puzzle grants one star. Good luck!
 * <p>
 * You try to ask why they can't just use a weather machine ("not powerful enough") and where they're even sending you ("the sky") and why your map looks mostly blank ("you sure ask a lot of questions") and hang on did you just say the sky ("of course, where do you think snow comes from") when you realize that the Elves are already loading you into a trebuchet ("please hold still, we need to strap you in").
 * <p>
 * As they're making the final adjustments, they discover that their calibration document (your puzzle input) has been amended by a very young Elf who was apparently just excited to show off her art skills. Consequently, the Elves are having trouble reading the values on the document.
 * <p>
 * The newly-improved calibration document consists of lines of text; each line originally contained a specific calibration value that the Elves now need to recover. On each line, the calibration value can be found by combining the first digit and the last digit (in that order) to form a single two-digit number.
 * <p>
 * For example:
 * <p>
 * 1abc2
 * pqr3stu8vwx
 * a1b2c3d4e5f
 * treb7uchet
 * In this example, the calibration values of these four lines are 12, 38, 15, and 77. Adding these together produces 142.
 * <p>
 * Consider your entire calibration document. What is the sum of all of the calibration values?
 * <p>
 * Your puzzle answer was 57346.
 * <p>
 * The first half of this puzzle is complete! It provides one gold star: *
 * <p>
 * --- Part Two ---
 * Your calculation isn't quite right. It looks like some of the digits are actually spelled out with letters: one, two, three, four, five, six, seven, eight, and nine also count as valid "digits".
 * <p>
 * Equipped with this new information, you now need to find the real first and last digit on each line. For example:
 * <p>
 * two1nine
 * eightwothree
 * abcone2threexyz
 * xtwone3four
 * 4nineeightseven2
 * zoneight234
 * 7pqrstsixteen
 * In this example, the calibration values are 29, 83, 13, 24, 42, 14, and 76. Adding these together produces 281.
 */
public class Part2 extends BasePuzzle {

    private static final String EXPECTED_TEST_RESULT = "281";

    enum Digit {
        one("1"),
        two("2"),
        three("3"),
        four("4"),
        five("5"),
        six("6"),
        seven("7"),
        eight("8"),
        nine("9");

        public String digitChar;

        Digit(String digitChar) {
            this.digitChar = digitChar;
        }
    }

    public String solvePuzzle(List<String> input) {
        int result = 0;

        for (String line : input) {
            result += parseLine(line);
        }

        return String.valueOf(result);
    }

    private int parseLine(String line) {
        String firstDigit = null;
        String lastDigit = null;

        for (int i = 0; i < line.length(); i++) {
            for (Digit digit : Digit.values()) {
                if (line.substring(i).startsWith(digit.name()) || line.substring(i).startsWith(digit.digitChar)) {
                    if (firstDigit == null) {
                        firstDigit = digit.digitChar;
                    }
                    lastDigit =digit.digitChar;
                }
            }
        }

        return Integer.valueOf(firstDigit + lastDigit);
    }


    // specific test input for part 2
    private static final String TEST_INPUT = "two1nine\n" +
            "eightwothree\n" +
            "abcone2threexyz\n" +
            "xtwone3four\n" +
            "4nineeightseven2\n" +
            "zoneight234\n" +
            "7pqrstsixteen";

    public Part2() {
        super(EXPECTED_TEST_RESULT, TEST_INPUT, null);
    }

    public static void main(String... args) {
        new Part2().run();
    }

}
