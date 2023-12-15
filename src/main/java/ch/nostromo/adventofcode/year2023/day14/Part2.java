package ch.nostromo.adventofcode.year2023.day14;

import ch.nostromo.adventofcode.BasePuzzle;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * --- Day 14: Parabolic Reflector Dish ---
 * You reach the place where all of the mirrors were pointing: a massive parabolic reflector dish attached to the side of another large mountain.
 * <p>
 * The dish is made up of many small mirrors, but while the mirrors themselves are roughly in the shape of a parabolic reflector dish, each individual mirror seems to be pointing in slightly the wrong direction. If the dish is meant to focus light, all it's doing right now is sending it in a vague direction.
 * <p>
 * This system must be what provides the energy for the lava! If you focus the reflector dish, maybe you can go where it's pointing and use the light to fix the lava production.
 * <p>
 * Upon closer inspection, the individual mirrors each appear to be connected via an elaborate system of ropes and pulleys to a large metal platform below the dish. The platform is covered in large rocks of various shapes. Depending on their position, the weight of the rocks deforms the platform, and the shape of the platform controls which ropes move and ultimately the focus of the dish.
 * <p>
 * In short: if you move the rocks, you can focus the dish. The platform even has a control panel on the side that lets you tilt it in one of four directions! The rounded rocks (O) will roll when the platform is tilted, while the cube-shaped rocks (#) will stay in place. You note the positions of all of the empty spaces (.) and rocks (your puzzle input). For example:
 * <p>
 * O....#....
 * O.OO#....#
 * .....##...
 * OO.#O....O
 * .O.....O#.
 * O.#..O.#.#
 * ..O..#O..O
 * .......O..
 * #....###..
 * #OO..#....
 * Start by tilting the lever so all of the rocks will slide north as far as they will go:
 * <p>
 * OOOO.#.O..
 * OO..#....#
 * OO..O##..O
 * O..#.OO...
 * ........#.
 * ..#....#.#
 * ..O..#.O.O
 * ..O.......
 * #....###..
 * #....#....
 * You notice that the support beams along the north side of the platform are damaged; to ensure the platform doesn't collapse, you should calculate the total load on the north support beams.
 * <p>
 * The amount of load caused by a single rounded rock (O) is equal to the number of rows from the rock to the south edge of the platform, including the row the rock is on. (Cube-shaped rocks (#) don't contribute to load.) So, the amount of load caused by each rock in each row is as follows:
 * <p>
 * OOOO.#.O.. 10
 * OO..#....#  9
 * OO..O##..O  8
 * O..#.OO...  7
 * ........#.  6
 * ..#....#.#  5
 * ..O..#.O.O  4
 * ..O.......  3
 * #....###..  2
 * #....#....  1
 * The total load is the sum of the load caused by all of the rounded rocks. In this example, the total load is 136.
 * <p>
 * Tilt the platform so that the rounded rocks all roll north. Afterward, what is the total load on the north support beams?
 * <p>
 * Your puzzle answer was 109098.
 * <p>
 * --- Part Two ---
 * The parabolic reflector dish deforms, but not in a way that focuses the beam. To do that, you'll need to move the rocks to the edges of the platform. Fortunately, a button on the side of the control panel labeled "spin cycle" attempts to do just that!
 * <p>
 * Each cycle tilts the platform four times so that the rounded rocks roll north, then west, then south, then east. After each tilt, the rounded rocks roll as far as they can before the platform tilts in the next direction. After one cycle, the platform will have finished rolling the rounded rocks in those four directions in that order.
 * <p>
 * Here's what happens in the example above after each of the first few cycles:
 * <p>
 * After 1 cycle:
 * .....#....
 * ....#...O#
 * ...OO##...
 * .OO#......
 * .....OOO#.
 * .O#...O#.#
 * ....O#....
 * ......OOOO
 * #...O###..
 * #..OO#....
 * <p>
 * After 2 cycles:
 * .....#....
 * ....#...O#
 * .....##...
 * ..O#......
 * .....OOO#.
 * .O#...O#.#
 * ....O#...O
 * .......OOO
 * #..OO###..
 * #.OOO#...O
 * <p>
 * After 3 cycles:
 * .....#....
 * ....#...O#
 * .....##...
 * ..O#......
 * .....OOO#.
 * .O#...O#.#
 * ....O#...O
 * .......OOO
 * #...O###.O
 * #.OOO#...O
 * This process should work if you leave it running long enough, but you're still worried about the north support beams. To make sure they'll survive for a while, you need to calculate the total load on the north support beams after 1000000000 cycles.
 * <p>
 * In the above example, after 1000000000 cycles, the total load on the north support beams is 64.
 * <p>
 * Run the spin cycle for 1000000000 cycles. Afterward, what is the total load on the north support beams?
 * <p>
 * Your puzzle answer was 100064.
 */
public class Part2 extends BasePuzzle {

    private static final String EXPECTED_TEST_RESULT = "64";

    public String solvePuzzle(List<String> input) {
        Map map = new Map(input);


        List<String> visitedCycleResults = new ArrayList<>();
        List<Long> visitedCycleResultsScore = new ArrayList<>();

        while (true) {
            visitedCycleResults.add(map.createCycleResult());
            visitedCycleResultsScore.add(map.scoreMap());

            Set<String> uniqueResults = new HashSet<>(visitedCycleResults);
            if (uniqueResults.size() != visitedCycleResults.size()) {
                // First repetition
                break;
            }
        }


        int endPos = visitedCycleResults.size() - 1;
        int startPos = visitedCycleResults.indexOf(visitedCycleResults.get(endPos));
        int scoreIdx = ((999999999 - startPos) % (endPos - startPos)) + startPos;
        long result = visitedCycleResultsScore.get(scoreIdx);

        return String.valueOf(result);
    }

    @Data
    @AllArgsConstructor
    public class Map {
        char[][] map;

        public Map(List<String> lines) {
            map = new char[lines.get(0).length()][lines.size()];
            for (int y = 0; y < lines.size(); y++) {
                for (int x = 0; x < lines.get(y).length(); x++) {
                    map[x][y] = lines.get(y).charAt(x);
                }
            }
        }


        public String createCycleResult() {
            tiltNorth();
            tiltWest();
            tiltSouth();
            tiltEast();


            // lame unique key ;)
            StringBuilder sb = new StringBuilder();
            for (int y = 0; y < map.length; y++) {
                for (int x = 0; x < map[y].length; x++) {
                    sb.append(map[x][y]);
                }
            }

            return sb.toString();
        }


        public void tiltNorth() {
            boolean movement = true;
            while (movement) {
                movement = false;
                for (int y = 1; y < map.length; y++) {
                    for (int x = 0; x < map[y].length; x++) {
                        if (map[x][y] == 'O' && map[x][y - 1] == '.') {
                            map[x][y - 1] = 'O';
                            map[x][y] = '.';
                            movement = true;
                        }
                    }
                }
            }
        }

        public void tiltSouth() {
            boolean movement = true;
            while (movement) {
                movement = false;
                for (int y = map.length - 2; y >= 0; y--) {
                    for (int x = 0; x < map[y].length; x++) {
                        if (map[x][y] == 'O' && map[x][y + 1] == '.') {
                            map[x][y + 1] = 'O';
                            map[x][y] = '.';
                            movement = true;
                        }
                    }
                }
            }
        }


        public void tiltEast() {
            boolean movement = true;
            while (movement) {
                movement = false;
                for (int y = 0; y < map.length; y++) {
                    for (int x = map[y].length - 2; x >= 0; x--) {
                        if (map[x][y] == 'O' && map[x + 1][y] == '.') {
                            map[x + 1][y] = 'O';
                            map[x][y] = '.';
                            movement = true;
                        }
                    }
                }
            }
        }


        public void tiltWest() {
            boolean movement = true;
            while (movement) {
                movement = false;
                for (int y = 0; y < map.length; y++) {
                    for (int x = 1; x < map[y].length; x++) {
                        if (map[x][y] == 'O' && map[x - 1][y] == '.') {
                            map[x - 1][y] = 'O';
                            map[x][y] = '.';
                            movement = true;
                        }
                    }
                }
            }
        }


        public long scoreMap() {
            long result = 0;
            for (int y = 0; y < map.length; y++) {
                for (int x = 0; x < map[y].length; x++) {
                    if (map[x][y] == 'O') {
                        result += map.length - y;
                    }
                }
            }
            return result;
        }


    }


    public Part2() {
        super(EXPECTED_TEST_RESULT);
    }

    public static void main(String... args) {
        new Part2().run();
    }

}
