package ch.nostromo.adventofcode.year2025.day05;

import ch.nostromo.adventofcode.BasePuzzle;

import java.util.ArrayList;
import java.util.List;

/**
 * --- Day 5: Cafeteria ---
 * As the forklifts break through the wall, the Elves are delighted to discover that there was a cafeteria on the other side after all.
 * <p>
 * You can hear a commotion coming from the kitchen. "At this rate, we won't have any time left to put the wreaths up in the dining hall!" Resolute in your quest, you investigate.
 * <p>
 * "If only we hadn't switched to the new inventory management system right before Christmas!" another Elf exclaims. You ask what's going on.
 * <p>
 * The Elves in the kitchen explain the situation: because of their complicated new inventory management system, they can't figure out which of their ingredients are fresh and which are spoiled. When you ask how it works, they give you a copy of their database (your puzzle input).
 * <p>
 * The database operates on ingredient IDs. It consists of a list of fresh ingredient ID ranges, a blank line, and a list of available ingredient IDs. For example:
 * <p>
 * 3-5
 * 10-14
 * 16-20
 * 12-18
 * <p>
 * 1
 * 5
 * 8
 * 11
 * 17
 * 32
 * The fresh ID ranges are inclusive: the range 3-5 means that ingredient IDs 3, 4, and 5 are all fresh. The ranges can also overlap; an ingredient ID is fresh if it is in any range.
 * <p>
 * The Elves are trying to determine which of the available ingredient IDs are fresh. In this example, this is done as follows:
 * <p>
 * Ingredient ID 1 is spoiled because it does not fall into any range.
 * Ingredient ID 5 is fresh because it falls into range 3-5.
 * Ingredient ID 8 is spoiled.
 * Ingredient ID 11 is fresh because it falls into range 10-14.
 * Ingredient ID 17 is fresh because it falls into range 16-20 as well as range 12-18.
 * Ingredient ID 32 is spoiled.
 * So, in this example, 3 of the available ingredient IDs are fresh.
 * <p>
 * Process the database file from the new inventory management system. How many of the available ingredient IDs are fresh?
 * <p>
 * To begin, get your puzzle input.
 */
public class Part1 extends BasePuzzle {

    private static final String EXPECTED_TEST_RESULT = "3";

    record Range(long from, long to) {

    }


    public String solvePuzzle(List<String> input) {
        int result = 0;

        List<Range> ranges = new ArrayList<>();
        List<Long> numbers = new ArrayList<>();

        boolean rangesMode = true;
        for (String line : input) {

            if (line.isEmpty()) {
                rangesMode = false;
            } else {
                if (rangesMode) {
                    long from = Long.parseLong(line.split("-")[0]);
                    long to = Long.parseLong(line.split("-")[1]);

                    ranges.add(new Range(from, to));
                } else {
                    numbers.add(Long.parseLong(line));
                }


            }


        }

        for (Long number : numbers) {
            boolean hit = false;

            for (Range range : ranges) {
                if (number >= range.from && number <= range.to && !hit) {
                    hit = true;
                    result++;
                }
            }


        }


        return Integer.toString(result);
    }


    public Part1() {
        super(EXPECTED_TEST_RESULT);
    }

    public static void main(String... args) {
        new Part1().run();
    }

}
