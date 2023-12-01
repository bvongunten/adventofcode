package ch.nostromo.adventofcode.year2023.day01;

import ch.nostromo.adventofcode.BasePuzzle;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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
 */
public class Part1 extends BasePuzzle {

    private static final String EXPECTED_TEST_RESULT = "142";

    public String solvePuzzle(List<String> input) {

        int result = 0;

        for (String line : input) {
            result += parseLine(line);
        }

        return String.valueOf(result);
    }

    private int parseLine(String line) {
        Character firstDigit = null;
        Character lastDigit = null;

        for (char c : line.toCharArray()) {
            if (Character.isDigit(c)) {
                if (firstDigit == null) {
                    firstDigit = c;
                }
                lastDigit = c;
            }
        }

        return Integer.valueOf(firstDigit + "" + lastDigit);
    }


    public Part1() {
        super(EXPECTED_TEST_RESULT);
    }

    public static void main(String... args) {
        new Part1().run();
    }

}
