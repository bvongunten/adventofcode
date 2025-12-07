package ch.nostromo.adventofcode.year2025.day05;

import ch.nostromo.adventofcode.BasePuzzle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
 * <p>
 * Your puzzle answer was 525.
 * <p>
 * The first half of this puzzle is complete! It provides one gold star: *
 * <p>
 * --- Part Two ---
 * The Elves start bringing their spoiled inventory to the trash chute at the back of the kitchen.
 * <p>
 * So that they can stop bugging you when they get new inventory, the Elves would like to know all of the IDs that the fresh ingredient ID ranges consider to be fresh. An ingredient ID is still considered fresh if it is in any range.
 * <p>
 * Now, the second section of the database (the available ingredient IDs) is irrelevant. Here are the fresh ingredient ID ranges from the above example:
 * <p>
 * 3-5
 * 10-14
 * 16-20
 * 12-18
 * The ingredient IDs that these ranges consider to be fresh are 3, 4, 5, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, and 20. So, in this example, the fresh ingredient ID ranges consider a total of 14 ingredient IDs to be fresh.
 * <p>
 * Process the database file again. How many ingredient IDs are considered to be fresh according to the fresh ingredient ID ranges?
 * <p>
 * Answer:
 */
public class Part2 extends BasePuzzle {

    private static final String EXPECTED_TEST_RESULT = "14";

    record Range(long from, long to) {

    }


    public String solvePuzzle(List<String> input) {
        long result = 0;

        List<Range> ranges = new ArrayList<>();

        boolean rangesMode = true;
        for (String line : input) {

            if (line.isEmpty()) {
                rangesMode = false;
            } else {
                if (rangesMode) {
                    long from = Long.parseLong(line.split("-")[0]);
                    long to = Long.parseLong(line.split("-")[1]);

                    ranges.add(new Range(from, to));
                }
            }


        }

        List<Range> mergedRanges = mergeRanges(ranges);

        for (Range range : mergedRanges) {
            result += range.to - range.from + 1;
        }




        return Long.toString(result);
    }

    private List<Range> mergeRanges(List<Range> ranges) {
        if (ranges.isEmpty()) {
            return new ArrayList<>();
        }

        // Sort ranges
        ranges.sort((a, b) -> Long.compare(a.from(), b.from()));

        List<Range> result = new ArrayList<>();

        long currentFrom = ranges.get(0).from();
        long currentTo = ranges.get(0).to();

        for (int i = 1; i < ranges.size(); i++) {
            Range range = ranges.get(i);

            if (range.from() <= currentTo) {
                currentTo = Math.max(currentTo, range.to());
            } else {
                result.add(new Range(currentFrom, currentTo));

                currentFrom = range.from();
                currentTo = range.to();
            }
        }

        result.add(new Range(currentFrom, currentTo));

        return result;
    }


    public Part2() {
        super(EXPECTED_TEST_RESULT);
    }

    public static void main(String... args) {
        new Part2().run();
    }

}
