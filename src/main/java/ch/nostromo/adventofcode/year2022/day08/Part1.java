package ch.nostromo.adventofcode.year2022.day08;

import ch.nostromo.adventofcode.BasePuzzle;

import java.util.List;

/**
 * --- Day 8: Treetop Tree House ---
 * The expedition comes across a peculiar patch of tall trees all planted carefully in a grid. The Elves explain that a previous expedition planted these trees as a reforestation effort. Now, they're curious if this would be a good location for a tree house.
 * <p>
 * First, determine whether there is enough tree cover here to keep a tree house hidden. To do this, you need to count the number of trees that are visible from outside the grid when looking directly along a row or column.
 * <p>
 * The Elves have already launched a quadcopter to generate a map with the height of each tree (your puzzle input). For example:
 * <p>
 * 30373
 * 25512
 * 65332
 * 33549
 * 35390
 * Each tree is represented as a single digit whose value is its height, where 0 is the shortest and 9 is the tallest.
 * <p>
 * A tree is visible if all of the other trees between it and an edge of the grid are shorter than it. Only consider trees in the same row or column; that is, only look up, down, left, or right from any given tree.
 * <p>
 * All of the trees around the edge of the grid are visible - since they are already on the edge, there are no trees to block the view. In this example, that only leaves the interior nine trees to consider:
 * <p>
 * The top-left 5 is visible from the left and top. (It isn't visible from the right or bottom since other trees of height 5 are in the way.)
 * The top-middle 5 is visible from the top and right.
 * The top-right 1 is not visible from any direction; for it to be visible, there would need to only be trees of height 0 between it and an edge.
 * The left-middle 5 is visible, but only from the right.
 * The center 3 is not visible from any direction; for it to be visible, there would need to be only trees of at most height 2 between it and an edge.
 * The right-middle 3 is visible from the right.
 * In the bottom row, the middle 5 is visible, but the 3 and 4 are not.
 * With 16 trees visible on the edge and another 5 visible in the interior, a total of 21 trees are visible in this arrangement.
 * <p>
 * Consider your map; how many trees are visible from outside the grid?
 * <p>
 * Your puzzle answer was 1662.
 */
public class Part1 extends BasePuzzle {

    private static final String EXPECTED_TEST_RESULT = "21";

    public String solvePuzzle(List<String> input) {

        int height = input.size();
        int width = input.get(0).length();

        int[][] forrest = createForrest(input, height, width);
        int solution = countVisibleTrees(forrest, height, width);

        return String.valueOf(solution);
    }

    private static int countVisibleTrees(int[][] forrest, int height, int width) {
        int result = 0;

        for (int heightIdx = 0; heightIdx < height; heightIdx++) {
            for (int widthIdx = 0; widthIdx < width; widthIdx++) {
                if (heightIdx == 0 || heightIdx == height - 1 || widthIdx == 0 || widthIdx == height - 1) {
                    result++;
                } else {

                    boolean north = true;
                    for (int i = heightIdx - 1; i >= 0; i--) {
                        if (forrest[i][widthIdx] >= forrest[heightIdx][widthIdx]) {
                            north = false;
                        }
                    }

                    boolean south = true;
                    for (int i = heightIdx + 1; i < height; i++) {
                        if (forrest[i][widthIdx] >= forrest[heightIdx][widthIdx]) {
                            south = false;
                        }
                    }

                    boolean west = true;
                    for (int i = widthIdx - 1; i >= 0; i--) {
                        if (forrest[heightIdx][i] >= forrest[heightIdx][widthIdx]) {
                            west = false;
                        }
                    }

                    boolean east = true;
                    for (int i = widthIdx + 1; i < width; i++) {
                        if (forrest[heightIdx][i] >= forrest[heightIdx][widthIdx]) {
                            east = false;
                        }
                    }

                    if (north || south || west || east) {
                        result++;
                    }

                }
            }
        }


        return result;

    }


    private static int[][] createForrest(List<String> lines, int height, int width) {
        int[][] forrest = new int[height][width];

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int n = 0; n < line.length(); n++) {
                forrest[i][n] = Integer.valueOf(line.substring(n, n + 1));
            }
        }

        return forrest;
    }

    public Part1() {
        super(EXPECTED_TEST_RESULT);
    }

    public static void main(String... args) {
        new Part1().run();
    }

}
