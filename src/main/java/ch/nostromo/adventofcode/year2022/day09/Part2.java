package ch.nostromo.adventofcode.year2022.day09;

import ch.nostromo.adventofcode.BasePuzzle;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


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
 * <p>
 * --- Part Two ---
 * Content with the amount of tree cover available, the Elves just need to know the best spot to build their tree house: they would like to be able to see a lot of trees.
 * <p>
 * To measure the viewing distance from a given tree, look up, down, left, and right from that tree; stop if you reach an edge or at the first tree that is the same height or taller than the tree under consideration. (If a tree is right on the edge, at least one of its viewing distances will be zero.)
 * <p>
 * The Elves don't care about distant trees taller than those found by the rules above; the proposed tree house has large eaves to keep it dry, so they wouldn't be able to see higher than the tree house anyway.
 * <p>
 * In the example above, consider the middle 5 in the second row:
 * <p>
 * 30373
 * 25512
 * 65332
 * 33549
 * 35390
 * Looking up, its view is not blocked; it can see 1 tree (of height 3).
 * Looking left, its view is blocked immediately; it can see only 1 tree (of height 5, right next to it).
 * Looking right, its view is not blocked; it can see 2 trees.
 * Looking down, its view is blocked eventually; it can see 2 trees (one of height 3, then the tree of height 5 that blocks its view).
 * A tree's scenic score is found by multiplying together its viewing distance in each of the four directions. For this tree, this is 4 (found by multiplying 1 * 1 * 2 * 2).
 * <p>
 * However, you can do even better: consider the tree of height 5 in the middle of the fourth row:
 * <p>
 * 30373
 * 25512
 * 65332
 * 33549
 * 35390
 * Looking up, its view is blocked at 2 trees (by another tree with a height of 5).
 * Looking left, its view is not blocked; it can see 2 trees.
 * Looking down, its view is also not blocked; it can see 1 tree.
 * Looking right, its view is blocked at 2 trees (by a massive tree of height 9).
 * This tree's scenic score is 8 (2 * 2 * 1 * 2); this is the ideal spot for the tree house.
 * <p>
 * Consider each tree on your map. What is the highest scenic score possible for any tree?
 * <p>
 * Your puzzle answer was 537600.
 */
public class Part2 extends BasePuzzle {

    private static final String EXPECTED_TEST_RESULT = "1";


    @Data
    @AllArgsConstructor
    private static class Coordinates {
        int x;
        int y;
    }


    public String solvePuzzle(List<String> input) {


        Set<Coordinates> visitedCoordinates = new LinkedHashSet<>();

        List<Coordinates> rope = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            rope.add(new Coordinates(0, 0));
        }


        visitedCoordinates.add(new Coordinates(0, 0));


        for (String line : input) {

            String command = line.split(" ")[0];
            int steps = Integer.valueOf(line.split(" ")[1]);

            switch (command) {
                case "U": {
                    move(visitedCoordinates, rope, false, 1, steps);
                    break;
                }
                case "D": {
                    move(visitedCoordinates, rope, false, -1, steps);
                    break;
                }
                case "R": {
                    move(visitedCoordinates, rope, true, 1, steps);
                    break;
                }
                case "L": {
                    move(visitedCoordinates, rope, true, -1, steps);
                    break;
                }

            }

        }

        int solution = visitedCoordinates.size();


        return String.valueOf(solution);
    }

    private static void move(Set<Coordinates> visitedCoordinates, List<Coordinates> rope, boolean isXAxis, int direction, int steps) {

        for (int i = 0; i < steps; i++) {
            if (isXAxis) {

                rope.get(0).setX(rope.get(0).getX() + direction);

                for (int n = 1; n < rope.size(); n++) {
                    Coordinates previous = rope.get(n - 1);
                    Coordinates current = rope.get(n);

                    if (isDetachted(current, previous)) {
                        // Diagonal?
                        if (current.getY() != previous.getY()) {
                            if (previous.getY() > current.getY()) {
                                current.setY(current.getY() + 1);
                            } else {
                                current.setY(current.getY() - 1);
                            }

                            // Select (needed) direction during diagonal move
                            if (current.getX() < previous.getX()) {
                                current.setX(current.getX() + 1);
                            } else if (current.getX() > previous.getX()) {
                                current.setX(current.getX() - 1);
                            }
                        } else {
                            current.setX(current.getX() + direction);
                        }
                    }
                }

            } else {

                rope.get(0).setY(rope.get(0).getY() + direction);

                for (int n = 1; n < rope.size(); n++) {
                    Coordinates previous = rope.get(n - 1);
                    Coordinates current = rope.get(n);

                    if (isDetachted(current, previous)) {
                        // Diagonal?
                        if (current.getX() != previous.getX()) {
                            if (previous.getX() > current.getX()) {
                                current.setX(current.getX() + 1);
                            } else {
                                current.setX(current.getX() - 1);
                            }

                            // Select (needed) direction during diagonal move
                            if (current.getY() < previous.getY()) {
                                current.setY(current.getY() + 1);
                            } else if (current.getY() > previous.getY()) {
                                current.setY(current.getY() - 1);
                            }
                        } else {
                            current.setY(current.getY() + direction);
                        }
                    }
                }

            }

            visitedCoordinates.add(new Coordinates(rope.get(rope.size() - 1).getX(), rope.get(rope.size() - 1).getY()));

        }

    }

    private static boolean isDetachted(Coordinates coord1, Coordinates coord2) {
        return (Math.abs(coord1.getX() - coord2.getX()) > 1 || Math.abs(coord1.getY() - coord2.getY()) > 1);
    }

    public Part2() {
        super(EXPECTED_TEST_RESULT);
    }

    public static void main(String... args) {
        new Part2().run();
    }

}
