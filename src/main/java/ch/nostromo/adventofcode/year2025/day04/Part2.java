package ch.nostromo.adventofcode.year2025.day04;

import ch.nostromo.adventofcode.BasePuzzle;

import java.util.ArrayList;
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
 * --- Part Two ---
 * Now, the Elves just need help accessing as much of the paper as they can.
 * <p>
 * Once a roll of paper can be accessed by a forklift, it can be removed. Once a roll of paper is removed, the forklifts might be able to access more rolls of paper, which they might also be able to remove. How many total rolls of paper could the Elves remove if they keep repeating this process?
 * <p>
 * Starting with the same example as above, here is one way you could remove as many rolls of paper as possible, using highlighted @ to indicate that a roll of paper is about to be removed, and using x to indicate that a roll of paper was just removed:
 * <p>
 * Initial state:
 * ..@@.@@@@.
 * @@@.@.@.@@
 * @@@@@.@.@@
 * @.@@@@..@.
 * @@.@@@@.@@ .@@@@@@@.@
 * .@.@.@.@@@
 * @.@@@.@@@@ .@@@@@@@@.
 * @.@.@@@.@. Remove 13 rolls of paper:
 * ..xx.xx@x.
 * x@@.@.@.@@
 * @@@@@.x.@@
 * @.@@@@..@. x@.@@@@.@x
 * .@@@@@@@.@
 * .@.@.@.@@@
 * x.@@@.@@@@
 * .@@@@@@@@.
 * x.x.@@@.x.
 * <p>
 * Remove 12 rolls of paper:
 * .......x..
 * .@@.x.x.@x
 * x@@@@...@@
 * x.@@@@..x.
 * .@.@@@@.x.
 * .x@@@@@@.x
 * .x.@.@.@@@
 * ..@@@.@@@@
 * .x@@@@@@@.
 * ....@@@...
 * <p>
 * Remove 7 rolls of paper:
 * ..........
 * .x@.....x.
 * .@@@@...xx
 * ..@@@@....
 * .x.@@@@...
 * ..@@@@@@..
 * ...@.@.@@x
 * ..@@@.@@@@
 * ..x@@@@@@.
 * ....@@@...
 * <p>
 * Remove 5 rolls of paper:
 * ..........
 * ..x.......
 * .x@@@.....
 * ..@@@@....
 * ...@@@@...
 * ..x@@@@@..
 * ...@.@.@@.
 * ..x@@.@@@x
 * ...@@@@@@.
 * ....@@@...
 * <p>
 * Remove 2 rolls of paper:
 * ..........
 * ..........
 * ..x@@.....
 * ..@@@@....
 * ...@@@@...
 * ...@@@@@..
 * ...@.@.@@.
 * ...@@.@@@.
 * ...@@@@@x.
 * ....@@@...
 * <p>
 * Remove 1 roll of paper:
 * ..........
 * ..........
 * ...@@.....
 * ..x@@@....
 * ...@@@@...
 * ...@@@@@..
 * ...@.@.@@.
 * ...@@.@@@.
 * ...@@@@@..
 * ....@@@...
 * <p>
 * Remove 1 roll of paper:
 * ..........
 * ..........
 * ...x@.....
 * ...@@@....
 * ...@@@@...
 * ...@@@@@..
 * ...@.@.@@.
 * ...@@.@@@.
 * ...@@@@@..
 * ....@@@...
 * <p>
 * Remove 1 roll of paper:
 * ..........
 * ..........
 * ....x.....
 * ...@@@....
 * ...@@@@...
 * ...@@@@@..
 * ...@.@.@@.
 * ...@@.@@@.
 * ...@@@@@..
 * ....@@@...
 * <p>
 * Remove 1 roll of paper:
 * ..........
 * ..........
 * ..........
 * ...x@@....
 * ...@@@@...
 * ...@@@@@..
 * ...@.@.@@.
 * ...@@.@@@.
 * ...@@@@@..
 * ....@@@...
 * Stop once no more rolls of paper are accessible by a forklift. In this example, a total of 43 rolls of paper can be removed.
 * <p>
 * Start with your original diagram. How many rolls of paper in total can be removed by the Elves and their forklifts?
 */
public class Part2 extends BasePuzzle {

    private static final String EXPECTED_TEST_RESULT = "43";

    private static final char ROLL = '@';

    public String solvePuzzle(List<String> input) {
        int result = 0;

        List<String> workingMap = new ArrayList<>(input);

        while (true) {
            // Start again with current map
            List<String> map = new ArrayList<>(workingMap);

            int subResult = 0;
            for (int y = 0; y < map.size(); y++) {
                for (int x = 0; x < map.get(y).length(); x++) {
                    if (map.get(y).charAt(x) == ROLL) {
                        int forkable = forkable(map, x, y);

                        if (forkable < 4) {
                            subResult++;
                            StringBuilder newLine = new StringBuilder(workingMap.get(y));
                            newLine.setCharAt(x, '.');
                            workingMap.set(y, newLine.toString());
                        }
                    }
                }
            }

            if (subResult > 0) {
                result += subResult;
            } else {
                break;
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
        if (y > 0 && x > 0 && map.get(y - 1).charAt(x - 1) == ROLL) {
            result++;
        }


        return result;


    }

    public Part2() {
        super(EXPECTED_TEST_RESULT);
    }

    public static void main(String... args) {
        new Part2().run();
    }

}
