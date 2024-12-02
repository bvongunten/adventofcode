package ch.nostromo.adventofcode.year2024.day02;

import ch.nostromo.adventofcode.BasePuzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 --- Day 2: Red-Nosed Reports ---
 Fortunately, the first location The Historians want to search isn't a long walk from the Chief Historian's office.

 While the Red-Nosed Reindeer nuclear fusion/fission plant appears to contain no sign of the Chief Historian, the engineers there run up to you as soon as they see you. Apparently, they still talk about the time Rudolph was saved through molecular synthesis from a single electron.

 They're quick to add that - since you're already here - they'd really appreciate your help analyzing some unusual data from the Red-Nosed reactor. You turn to check if The Historians are waiting for you, but they seem to have already divided into groups that are currently searching every corner of the facility. You offer to help with the unusual data.

 The unusual data (your puzzle input) consists of many reports, one report per line. Each report is a list of numbers called levels that are separated by spaces. For example:

 7 6 4 2 1
 1 2 7 8 9
 9 7 6 2 1
 1 3 2 4 5
 8 6 4 4 1
 1 3 6 7 9
 This example data contains six reports each containing five levels.

 The engineers are trying to figure out which reports are safe. The Red-Nosed reactor safety systems can only tolerate levels that are either gradually increasing or gradually decreasing. So, a report only counts as safe if both of the following are true:

 The levels are either all increasing or all decreasing.
 Any two adjacent levels differ by at least one and at most three.
 In the example above, the reports can be found safe or unsafe by checking those rules:

 7 6 4 2 1: Safe because the levels are all decreasing by 1 or 2.
 1 2 7 8 9: Unsafe because 2 7 is an increase of 5.
 9 7 6 2 1: Unsafe because 6 2 is a decrease of 4.
 1 3 2 4 5: Unsafe because 1 3 is increasing but 3 2 is decreasing.
 8 6 4 4 1: Unsafe because 4 4 is neither an increase or a decrease.
 1 3 6 7 9: Safe because the levels are all increasing by 1, 2, or 3.
 So, in this example, 2 reports are safe.

 Analyze the unusual data from the engineers. How many reports are safe?

 Your puzzle answer was 218.
 */
public class Part1 extends BasePuzzle {

    private static final String EXPECTED_TEST_RESULT = "2";

    public String solvePuzzle(List<String> input) {

        int result = 0;

        List<int[]> reports = new ArrayList<>();
        for (String line : input) {
            int[] numbers = Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();
            reports.add(numbers);
        }

        for (int[] report : reports) {
            if (isSafeReport(report)) {
                result++;
            }
        }
        return String.valueOf(result);
    }


    private boolean isSafeReport(int[] report) {
        boolean isIncreasing = true;
        boolean isDecreasing = true;

        for (int i = 0; i < report.length - 1; i++) {
            int diff = report[i + 1] - report[i];

            if (Math.abs(diff) > 3 || diff == 0) {
                return false;
            }

            if (diff > 0) {
                isDecreasing = false;
            } else {
                isIncreasing = false;
            }
        }

        return isIncreasing || isDecreasing;
    }


    public Part1() {
        super(EXPECTED_TEST_RESULT);
    }

    public static void main(String... args) {
        new Part1().run();
    }

}
