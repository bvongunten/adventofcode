package ch.nostromo.adventofcode.year2022.day04;

import ch.nostromo.adventofcode.BasePuzzle;

import java.time.temporal.ValueRange;
import java.util.List;

/**
 * --- Day 4: Camp Cleanup ---
 * Space needs to be cleared before the last supplies can be unloaded from the ships, and so several Elves have been assigned the job of cleaning up sections of the camp. Every section has a unique ID number, and each Elf is assigned a range of section IDs.
 * <p>
 * However, as some of the Elves compare their section assignments with each other, they've noticed that many of the assignments overlap. To try to quickly find overlaps and reduce duplicated effort, the Elves pair up and make a big list of the section assignments for each pair (your puzzle input).
 * <p>
 * For example, consider the following list of section assignment pairs:
 * <p>
 * 2-4,6-8
 * 2-3,4-5
 * 5-7,7-9
 * 2-8,3-7
 * 6-6,4-6
 * 2-6,4-8
 * For the first few pairs, this list means:
 * <p>
 * Within the first pair of Elves, the first Elf was assigned sections 2-4 (sections 2, 3, and 4), while the second Elf was assigned sections 6-8 (sections 6, 7, 8).
 * The Elves in the second pair were each assigned two sections.
 * The Elves in the third pair were each assigned three sections: one got sections 5, 6, and 7, while the other also got 7, plus 8 and 9.
 * This example list uses single-digit section IDs to make it easier to draw; your actual list might contain larger numbers. Visually, these pairs of section assignments look like this:
 * <p>
 * .234.....  2-4
 * .....678.  6-8
 * <p>
 * .23......  2-3
 * ...45....  4-5
 * <p>
 * ....567..  5-7
 * ......789  7-9
 * <p>
 * .2345678.  2-8
 * ..34567..  3-7
 * <p>
 * .....6...  6-6
 * ...456...  4-6
 * <p>
 * .23456...  2-6
 * ...45678.  4-8
 * Some of the pairs have noticed that one of their assignments fully contains the other. For example, 2-8 fully contains 3-7, and 6-6 is fully contained by 4-6. In pairs where one assignment fully contains the other, one Elf in the pair would be exclusively cleaning sections their partner will already be cleaning, so these seem like the most in need of reconsideration. In this example, there are 2 such pairs.
 * <p>
 * In how many assignment pairs does one range fully contain the other?
 */
public class Part1 extends BasePuzzle {

   private static final String EXPECTED_TEST_RESULT = "2";

    public String solvePuzzle(List<String> input) {

        int result = 0;

        for (String line : input) {
            ValueRange range1 = ValueRange.of(Integer.valueOf(line.split(",")[0].split("-")[0]), Integer.valueOf(line.split(",")[0].split("-")[1]));
            ValueRange range2 = ValueRange.of(Integer.valueOf(line.split(",")[1].split("-")[0]), Integer.valueOf(line.split(",")[1].split("-")[1]));

            if (isRedundant(range1, range2)) {
                result++;
            }

        }

        return String.valueOf(result);
    }
    private boolean isRedundant(ValueRange range1, ValueRange range2) {
        return range1.isValidIntValue(range2.getMinimum()) && range1.isValidIntValue(range2.getMaximum()) ||
                range2.isValidIntValue(range1.getMinimum()) && range2.isValidIntValue(range1.getMaximum());
    }

    public Part1() {
        super(EXPECTED_TEST_RESULT);
    }

    public static void main(String...args) {
        new Part1().run();
    }

}
