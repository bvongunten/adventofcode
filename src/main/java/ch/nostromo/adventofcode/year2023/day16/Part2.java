package ch.nostromo.adventofcode.year2023.day16;

import ch.nostromo.adventofcode.BasePuzzle;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * --- Day 16: The Floor Will Be Lava ---
 * With the beam of light completely focused somewhere, the reindeer leads you deeper still into the Lava Production Facility. At some point, you realize that the steel facility walls have been replaced with cave, and the doorways are just cave, and the floor is cave, and you're pretty sure this is actually just a giant cave.
 * <p>
 * Finally, as you approach what must be the heart of the mountain, you see a bright light in a cavern up ahead. There, you discover that the beam of light you so carefully focused is emerging from the cavern wall closest to the facility and pouring all of its energy into a contraption on the opposite side.
 * <p>
 * Upon closer inspection, the contraption appears to be a flat, two-dimensional square grid containing empty space (.), mirrors (/ and \), and splitters (| and -).
 * <p>
 * The contraption is aligned so that most of the beam bounces around the grid, but each tile on the grid converts some of the beam's light into heat to melt the rock in the cavern.
 * <p>
 * You note the layout of the contraption (your puzzle input). For example:
 * <p>
 * .|...\....
 * |.-.\.....
 * .....|-...
 * ........|.
 * ..........
 * .........\
 * ..../.\\..
 * .-.-/..|..
 * .|....-|.\
 * ..//.|....
 * The beam enters in the top-left corner from the left and heading to the right. Then, its behavior depends on what it encounters as it moves:
 * <p>
 * If the beam encounters empty space (.), it continues in the same direction.
 * If the beam encounters a mirror (/ or \), the beam is reflected 90 degrees depending on the angle of the mirror. For instance, a rightward-moving beam that encounters a / mirror would continue upward in the mirror's column, while a rightward-moving beam that encounters a \ mirror would continue downward from the mirror's column.
 * If the beam encounters the pointy end of a splitter (| or -), the beam passes through the splitter as if the splitter were empty space. For instance, a rightward-moving beam that encounters a - splitter would continue in the same direction.
 * If the beam encounters the flat side of a splitter (| or -), the beam is split into two beams going in each of the two directions the splitter's pointy ends are pointing. For instance, a rightward-moving beam that encounters a | splitter would split into two beams: one that continues upward from the splitter's column and one that continues downward from the splitter's column.
 * Beams do not interact with other beams; a tile can have many beams passing through it at the same time. A tile is energized if that tile has at least one beam pass through it, reflect in it, or split in it.
 * <p>
 * In the above example, here is how the beam of light bounces around the contraption:
 * <p>
 * >|<<<\....
 * |v-.\^....
 * .v...|->>>
 * .v...v^.|.
 * .v...v^...
 * .v...v^..\
 * .v../2\\..
 * <->-/vv|..
 * .|<<<2-|.\
 * .v//.|.v..
 * Beams are only shown on empty tiles; arrows indicate the direction of the beams. If a tile contains beams moving in multiple directions, the number of distinct directions is shown instead. Here is the same diagram but instead only showing whether a tile is energized (#) or not (.):
 * <p>
 * ######....
 * .#...#....
 * .#...#####
 * .#...##...
 * .#...##...
 * .#...##...
 * .#..####..
 * ########..
 * .#######..
 * .#...#.#..
 * Ultimately, in this example, 46 tiles become energized.
 * <p>
 * The light isn't energizing enough tiles to produce lava; to debug the contraption, you need to start by analyzing the current situation. With the beam starting in the top-left heading right, how many tiles end up being energized?
 * <p>
 * Your puzzle answer was 7517.
 * <p>
 * --- Part Two ---
 * As you try to work out what might be wrong, the reindeer tugs on your shirt and leads you to a nearby control panel. There, a collection of buttons lets you align the contraption so that the beam enters from any edge tile and heading away from that edge. (You can choose either of two directions for the beam if it starts on a corner; for instance, if the beam starts in the bottom-right corner, it can start heading either left or upward.)
 * <p>
 * So, the beam could start on any tile in the top row (heading downward), any tile in the bottom row (heading upward), any tile in the leftmost column (heading right), or any tile in the rightmost column (heading left). To produce lava, you need to find the configuration that energizes as many tiles as possible.
 * <p>
 * In the above example, this can be achieved by starting the beam in the fourth tile from the left in the top row:
 * <p>
 * .|<2<\....
 * |v-v\^....
 * .v.v.|->>>
 * .v.v.v^.|.
 * .v.v.v^...
 * .v.v.v^..\
 * .v.v/2\\..
 * <-2-/vv|..
 * .|<<<2-|.\
 * .v//.|.v..
 * Using this configuration, 51 tiles are energized:
 * <p>
 * .#####....
 * .#.#.#....
 * .#.#.#####
 * .#.#.##...
 * .#.#.##...
 * .#.#.##...
 * .#.#####..
 * ########..
 * .#######..
 * .#...#.#..
 * Find the initial beam configuration that energizes the largest number of tiles; how many tiles are energized in that configuration?
 * <p>
 * Your puzzle answer was 7741.
 */
public class Part2 extends BasePuzzle {

    private static final String EXPECTED_TEST_RESULT = "51";

    public String solvePuzzle(List<String> input) {
        long result = Long.MIN_VALUE;


        for (int x = 0; x < input.get(0).length(); x++) {
            Map map = new Map(input);
            lightTheMap(map, x, 0, Direction.SOUTH);
            result = Math.max(result, map.getScore());

            map = new Map(input);
            lightTheMap(map, x, input.size(), Direction.NORTH);
            result = Math.max(result, map.getScore());
        }

        for (int y = 0; y < input.size(); y++) {
            Map map = new Map(input);
            lightTheMap(map, 0, y, Direction.EAST);
            result = Math.max(result, map.getScore());

            map = new Map(input);
            lightTheMap(map, input.get(0).length(), y, Direction.WEST);
            result = Math.max(result, map.getScore());
        }

        return String.valueOf(result);
    }

    private void lightTheMap(Map map, int x, int y, Direction direction) {

        if (y < 0 || y == map.getMap().length || x < 0 || x == map.getMap()[y].length) {
            return;
        }

        map.getHeatedMap()[x][y] = '#';

        map.dumpMap();

        if (map.directionsMap.get(x + "/" + y).contains(direction)) {
            return;
        } else {
            map.directionsMap.get(x + "/" + y).add(direction);
        }

        char descisionPoint = map.getMap()[x][y];

        switch (descisionPoint) {
            case '.' -> {
                switch (direction) {
                    case NORTH -> {
                        lightTheMap(map, x, y - 1, direction);
                    }
                    case SOUTH -> {
                        lightTheMap(map, x, y + 1, direction);
                    }
                    case WEST -> {
                        lightTheMap(map, x - 1, y, direction);
                    }
                    case EAST -> {
                        lightTheMap(map, x + 1, y, direction);
                    }
                }

            }
            case '|' -> {
                switch (direction) {
                    case NORTH -> {
                        lightTheMap(map, x, y - 1, direction);
                    }
                    case SOUTH -> {
                        lightTheMap(map, x, y + 1, direction);
                    }
                    case WEST, EAST -> {
                        lightTheMap(map, x, y - 1, direction.NORTH);
                        lightTheMap(map, x, y + 1, direction.SOUTH);
                    }
                }
            }
            case '-' -> {
                switch (direction) {
                    case WEST -> {
                        lightTheMap(map, x - 1, y, direction);
                    }
                    case EAST -> {
                        lightTheMap(map, x + 1, y, direction);
                    }
                    case NORTH, SOUTH -> {
                        lightTheMap(map, x - 1, y, direction.WEST);
                        lightTheMap(map, x + 1, y, direction.EAST);
                    }
                }
            }
            case '/' -> {

                switch (direction) {
                    case NORTH -> {
                        lightTheMap(map, x + 1, y, direction.EAST);
                    }
                    case SOUTH -> {
                        lightTheMap(map, x - 1, y, direction.WEST);
                    }
                    case WEST -> {
                        lightTheMap(map, x, y + 1, direction.SOUTH);
                    }
                    case EAST -> {
                        lightTheMap(map, x, y - 1, direction.NORTH);
                    }
                }

            }
            case '\\' -> {
                switch (direction) {
                    case NORTH -> {
                        lightTheMap(map, x - 1, y, direction.WEST);
                    }
                    case SOUTH -> {
                        lightTheMap(map, x + 1, y, direction.EAST);
                    }
                    case WEST -> {
                        lightTheMap(map, x, y - 1, direction.NORTH);
                    }
                    case EAST -> {
                        lightTheMap(map, x, y + 1, direction.SOUTH);
                    }


                }

            }

        }


    }


    @Data
    public class Map {
        char[][] map;
        char[][] heatedMap;

        HashMap<String, List<Direction>> directionsMap = new HashMap<>();

        public Map(List<String> lines) {
            map = new char[lines.get(0).length()][lines.size()];
            heatedMap = new char[lines.get(0).length()][lines.size()];
            for (int y = 0; y < lines.size(); y++) {
                for (int x = 0; x < lines.get(y).length(); x++) {
                    map[x][y] = lines.get(y).charAt(x);
                    heatedMap[x][y] = '.';
                    directionsMap.put(x + "/" + y, new ArrayList<>());
                }
            }
        }

        public long getScore() {
            long result = 0;
            for (int y = 0; y < map.length; y++) {
                for (int x = 0; x < map[y].length; x++) {
                    if (heatedMap[x][y] == '#') {
                        result++;
                    }

                }
            }
            return result;
        }

        public void dumpMap() {
            if (true) return;
            for (int y = 0; y < map.length; y++) {
                for (int x = 0; x < map[y].length; x++) {
                    System.out.print(map[x][y]);
                }
                System.out.print("  ");
                for (int x = 0; x < map[y].length; x++) {
                    System.out.print(heatedMap[x][y]);
                }

                System.out.println();
            }
            System.out.println("--");


        }


    }

    public enum Direction {
        NORTH,
        SOUTH,
        WEST,
        EAST;
    }

    public Part2() {
        super(EXPECTED_TEST_RESULT);
    }

    public static void main(String... args) {
        new Part2().run();
    }

}
