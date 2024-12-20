package ch.nostromo.adventofcode.year2024.day10;

import ch.nostromo.adventofcode.BasePuzzle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * --- Day 10: Hoof It ---
 * You all arrive at a Lava Production Facility on a floating island in the sky. As the others begin to search the massive industrial complex, you feel a small nose boop your leg and look down to discover a reindeer wearing a hard hat.
 * <p>
 * The reindeer is holding a book titled "Lava Island Hiking Guide". However, when you open the book, you discover that most of it seems to have been scorched by lava! As you're about to ask how you can help, the reindeer brings you a blank topographic map of the surrounding area (your puzzle input) and looks up at you excitedly.
 * <p>
 * Perhaps you can help fill in the missing hiking trails?
 * <p>
 * The topographic map indicates the height at each position using a scale from 0 (lowest) to 9 (highest). For example:
 * <p>
 * 0123
 * 1234
 * 8765
 * 9876
 * Based on un-scorched scraps of the book, you determine that a good hiking trail is as long as possible and has an even, gradual, uphill slope. For all practical purposes, this means that a hiking trail is any path that starts at height 0, ends at height 9, and always increases by a height of exactly 1 at each step. Hiking trails never include diagonal steps - only up, down, left, or right (from the perspective of the map).
 * <p>
 * You look up from the map and notice that the reindeer has helpfully begun to construct a small pile of pencils, markers, rulers, compasses, stickers, and other equipment you might need to update the map with hiking trails.
 * <p>
 * A trailhead is any position that starts one or more hiking trails - here, these positions will always have height 0. Assembling more fragments of pages, you establish that a trailhead's score is the number of 9-height positions reachable from that trailhead via a hiking trail. In the above example, the single trailhead in the top left corner has a score of 1 because it can reach a single 9 (the one in the bottom left).
 * <p>
 * This trailhead has a score of 2:
 * <p>
 * ...0...
 * ...1...
 * ...2...
 * 6543456
 * 7.....7
 * 8.....8
 * 9.....9
 * (The positions marked . are impassable tiles to simplify these examples; they do not appear on your actual topographic map.)
 * <p>
 * This trailhead has a score of 4 because every 9 is reachable via a hiking trail except the one immediately to the left of the trailhead:
 * <p>
 * ..90..9
 * ...1.98
 * ...2..7
 * 6543456
 * 765.987
 * 876....
 * 987....
 * This topographic map contains two trailheads; the trailhead at the top has a score of 1, while the trailhead at the bottom has a score of 2:
 * <p>
 * 10..9..
 * 2...8..
 * 3...7..
 * 4567654
 * ...8..3
 * ...9..2
 * .....01
 * Here's a larger example:
 * <p>
 * 89010123
 * 78121874
 * 87430965
 * 96549874
 * 45678903
 * 32019012
 * 01329801
 * 10456732
 * This larger example has 9 trailheads. Considering the trailheads in reading order, they have scores of 5, 6, 5, 3, 1, 3, 5, 3, and 5. Adding these scores together, the sum of the scores of all trailheads is 36.
 * <p>
 * The reindeer gleefully carries over a protractor and adds it to the pile. What is the sum of the scores of all trailheads on your topographic map?
 */
public class Part2 extends BasePuzzle {

    private static final String EXPECTED_TEST_RESULT = "81";


    public String solvePuzzle(List<String> input) {

        int[][] map = parseMap(input);

        return String.valueOf(findHikes(map));

    }


    @Data
    @AllArgsConstructor
    @EqualsAndHashCode
    class Tuple {
        int x;
        int y;

    }

    int findHikes(int[][] map) {

        int result = 0;

        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] == 0) {
                    List<Tuple> visitedNines = new ArrayList<>();
                    result += startFrom(map, new Tuple(x, y), 0, 0, visitedNines);
                }
            }

        }

        return result;

    }

    int startFrom(int[][] map, Tuple tuple, int currentHeight, int currentScore, List<Tuple> visitedNines) {

        int result = currentScore;

        if (currentHeight == 9 && !visitedNines.contains(tuple)) {
            //  visitedNines.add(tuple);

            return currentScore + 1;
        }


        // North
        if (tuple.y > 0 && map[tuple.y - 1][tuple.x] == currentHeight + 1) {
            map[tuple.y][tuple.x] = -1;

            result = startFrom(map, new Tuple(tuple.x, tuple.y - 1), currentHeight + 1, result, visitedNines);

            map[tuple.y][tuple.x] = currentHeight;
        }

        // South
        if (tuple.y < map.length - 1 && map[tuple.y + 1][tuple.x] == currentHeight + 1) {
            map[tuple.y][tuple.x] = -1;
            result = startFrom(map, new Tuple(tuple.x, tuple.y + 1), currentHeight + 1, result, visitedNines);
            map[tuple.y][tuple.x] = currentHeight;
        }

        // West
        if (tuple.x > 0 && map[tuple.y][tuple.x - 1] == currentHeight + 1) {
            map[tuple.y][tuple.x] = -1;
            result = startFrom(map, new Tuple(tuple.x - 1, tuple.y), currentHeight + 1, result, visitedNines);
            map[tuple.y][tuple.x] = currentHeight;
        }

        // East
        if (tuple.x < map[tuple.y].length - 1 && map[tuple.y][tuple.x + 1] == currentHeight + 1) {
            map[tuple.y][tuple.x] = -1;
            result = startFrom(map, new Tuple(tuple.x + 1, tuple.y), currentHeight + 1, result, visitedNines);
            map[tuple.y][tuple.x] = currentHeight;
        }


        return result;

    }


    int[][] parseMap(List<String> input) {
        int[][] map = new int[input.size()][input.get(0).length()];

        for (int i = 0; i < input.size(); i++) {
            for (int c = 0; c < input.get(i).length(); c++) {
                map[i][c] = Integer.parseInt(input.get(i).substring(c, c + 1));
            }
        }


        return map;
    }


    public Part2() {
        super(EXPECTED_TEST_RESULT);
    }

    public static void main(String... args) {
        new Part2().run();
    }
}