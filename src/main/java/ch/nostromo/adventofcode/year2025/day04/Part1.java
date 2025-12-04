package ch.nostromo.adventofcode.year2025.day04;

import ch.nostromo.adventofcode.BasePuzzle;

import java.util.List;

/**
 * --- Day 4: Printing Department ---
 * You ride the escalator down to the printing department. They're clearly getting ready for Christmas; they have lots of large rolls of paper everywhere, and there's even a massive printer in the corner (to handle the really big print jobs).
 * <p>
 * Decorating here will be easy: they can make their own decorations. What you really need is a way to get further into the North Pole base while the elevators are offline.
 * <p>
 * "Actually, maybe we can help with that," one of the Elves replies when you ask for help. "We're pretty sure there's a cafeteria on the other side of the back wall. If we could break through the wall, you'd be able to keep moving. It's too bad all of our forklifts are so busy moving those big rolls of paper around."
 * <p>
 * If you can optimize the work the forklifts are doing, maybe they would have time to spare to break through the wall.
 * <p>
 * The rolls of paper (@) are arranged on a large grid; the Elves even have a helpful diagram (your puzzle input) indicating where everything is located.
 * <p>
 * For example:
 * <p>
 * ..@@.@@@@.
 *
 * @@@.@.@.@@
 * @@@@@.@.@@
 * @.@@@@..@.
 * @@.@@@@.@@ .@@@@@@@.@
 * .@.@.@.@@@
 * @.@@@.@@@@ .@@@@@@@@.
 * @.@.@@@.@. The forklifts can only access a roll of paper if there are fewer than four rolls of paper in the eight adjacent positions. If you can figure out which rolls of paper the forklifts can access, they'll spend less time looking and more time breaking down the wall to the cafeteria.
 * <p>
 * In this example, there are 13 rolls of paper that can be accessed by a forklift (marked with x):
 * <p>
 * ..xx.xx@x.
 * x@@.@.@.@@
 * @@@@@.x.@@
 * @.@@@@..@. x@.@@@@.@x
 * .@@@@@@@.@
 * .@.@.@.@@@
 * x.@@@.@@@@
 * .@@@@@@@@.
 * x.x.@@@.x.
 * Consider your complete diagram of the paper roll locations. How many rolls of paper can be accessed by a forklift?
 * <p>
 * To begin, get your puzzle input.
 */
public class Part1 extends BasePuzzle {

    private static final String EXPECTED_TEST_RESULT = "13";

    private static final char ROLL = '@';

    public String solvePuzzle(List<String> input) {
        int result = 0;

        for (int y = 0; y < input.size(); y++) {
            for (int x = 0; x < input.get(y).length(); x++) {

                if (input.get(y).charAt(x) == ROLL) {
                    int forkable = forkable(input, x, y);

                    if (forkable < 4) {
                        result ++;
                    }

                }

            }

        }


        return Integer.toString(result);
    }

    public static int forkable(List<String> map, int x, int y) {
        int maxY = map.size() - 1;
        int maxX = map.get(0).length() - 1;

        int result = 0;

        // N
        if (y > 0 && map.get(y - 1).charAt(x) == ROLL) {
            result++;
        }

        // NE
        if (y > 0 && x < maxX && map.get(y - 1).charAt(x + 1) == ROLL) {
            result++;
        }

        // E
        if (x < maxX && map.get(y).charAt(x + 1) == ROLL) {
            result++;
        }


        // SE
        if (y < maxY && x < maxX && map.get(y + 1).charAt(x + 1) == ROLL) {
            result++;
        }


        // S
        if (y < maxY && map.get(y + 1).charAt(x) == ROLL) {
            result++;
        }

        // SW
        if (y < maxY && x > 0 && map.get(y + 1).charAt(x - 1) == ROLL) {
            result++;
        }

        // W
        if (x > 0 && map.get(y).charAt(x - 1) == ROLL) {
            result++;
        }

        // NW
        if (y > 0 && x > 0 && map.get(y -1).charAt(x - 1) == ROLL) {
            result++;
        }


        return result;


    }

    public Part1() {
        super(EXPECTED_TEST_RESULT);
    }

    public static void main(String... args) {
        new Part1().run();
    }

}
