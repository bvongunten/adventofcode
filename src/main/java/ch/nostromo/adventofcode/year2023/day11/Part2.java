package ch.nostromo.adventofcode.year2023.day11;

import ch.nostromo.adventofcode.BasePuzzle;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * --- Day 11: Cosmic Expansion ---
 * You continue following signs for "Hot Springs" and eventually come across an observatory. The Elf within turns out to be a researcher studying cosmic expansion using the giant telescope here.
 * <p>
 * He doesn't know anything about the missing machine parts; he's only visiting for this research project. However, he confirms that the hot springs are the next-closest area likely to have people; he'll even take you straight there once he's done with today's observation analysis.
 * <p>
 * Maybe you can help him with the analysis to speed things up?
 * <p>
 * The researcher has collected a bunch of data and compiled the data into a single giant image (your puzzle input). The image includes empty space (.) and galaxies (#). For example:
 * <p>
 * ...#......
 * .......#..
 * #.........
 * ..........
 * ......#...
 * .#........
 * .........#
 * ..........
 * .......#..
 * #...#.....
 * The researcher is trying to figure out the sum of the lengths of the shortest path between every pair of galaxies. However, there's a catch: the universe expanded in the time it took the light from those galaxies to reach the observatory.
 * <p>
 * Due to something involving gravitational effects, only some space expands. In fact, the result is that any rows or columns that contain no galaxies should all actually be twice as big.
 * <p>
 * In the above example, three columns and two rows contain no galaxies:
 * <p>
 * v  v  v
 * ...#......
 * .......#..
 * #.........
 * >..........<
 * ......#...
 * .#........
 * .........#
 * >..........<
 * .......#..
 * #...#.....
 * ^  ^  ^
 * These rows and columns need to be twice as big; the result of cosmic expansion therefore looks like this:
 * <p>
 * ....#........
 * .........#...
 * #............
 * .............
 * .............
 * ........#....
 * .#...........
 * ............#
 * .............
 * .............
 * .........#...
 * #....#.......
 * Equipped with this expanded universe, the shortest path between every pair of galaxies can be found. It can help to assign every galaxy a unique number:
 * <p>
 * ....1........
 * .........2...
 * 3............
 * .............
 * .............
 * ........4....
 * .5...........
 * ............6
 * .............
 * .............
 * .........7...
 * 8....9.......
 * In these 9 galaxies, there are 36 pairs. Only count each pair once; order within the pair doesn't matter. For each pair, find any shortest path between the two galaxies using only steps that move up, down, left, or right exactly one . or # at a time. (The shortest path between two galaxies is allowed to pass through another galaxy.)
 * <p>
 * For example, here is one of the shortest paths between galaxies 5 and 9:
 * <p>
 * ....1........
 * .........2...
 * 3............
 * .............
 * .............
 * ........4....
 * .5...........
 * .##.........6
 * ..##.........
 * ...##........
 * ....##...7...
 * 8....9.......
 * This path has length 9 because it takes a minimum of nine steps to get from galaxy 5 to galaxy 9 (the eight locations marked # plus the step onto galaxy 9 itself). Here are some other example shortest path lengths:
 * <p>
 * Between galaxy 1 and galaxy 7: 15
 * Between galaxy 3 and galaxy 6: 17
 * Between galaxy 8 and galaxy 9: 5
 * In this example, after expanding the universe, the sum of the shortest path between all 36 pairs of galaxies is 374.
 * <p>
 * Expand the universe, then find the length of the shortest path between every pair of galaxies. What is the sum of these lengths?
 * <p>
 * Your puzzle answer was 9599070.
 * <p>
 * --- Part Two ---
 * The galaxies are much older (and thus much farther apart) than the researcher initially estimated.
 * <p>
 * Now, instead of the expansion you did before, make each empty row or column one million times larger. That is, each empty row should be replaced with 1000000 empty rows, and each empty column should be replaced with 1000000 empty columns.
 * <p>
 * (In the example above, if each empty row or column were merely 10 times larger, the sum of the shortest paths between every pair of galaxies would be 1030. If each empty row or column were merely 100 times larger, the sum of the shortest paths between every pair of galaxies would be 8410. However, your universe will need to expand far beyond these values.)
 * <p>
 * Starting with the same initial image, expand the universe according to these new rules, then find the length of the shortest path between every pair of galaxies. What is the sum of these lengths?
 * <p>
 * Your puzzle answer was 842645913794.
 */
public class Part2 extends BasePuzzle {

    private static final String EXPECTED_TEST_RESULT = "1030";

    public String solvePuzzle(List<String> input) {
        long result = 0;

        int expansion = 1000000;

        if (isTestRun()) {
            expansion = 10;
        }

        // Get horizontal & vertical extensions
        List<Integer> verticalExtensions = getVerticalExtensions(input);
        List<Integer> horizontalExtension = getHorizontalExtensions(input);


        int galaxyCounter = 0;
        List<Galaxy> galaxies = new ArrayList<>();
        // Find planets
        for (int y = 0; y < input.size(); y++) {
            for (int x = 0; x < input.get(y).length(); x++) {
                if (input.get(y).charAt(x) == '#') {

                    long tempX = x;
                    long tempY = y;

                    // Add vertical extension
                    for (Integer v : verticalExtensions) {
                        if (v <= y) {
                            tempY += expansion - 1;
                        }
                    }
                    // Add horizontal extension
                    for (Integer h : horizontalExtension) {
                        if (h <= x) {
                            tempX += expansion - 1;
                        }
                    }

                    galaxies.add(new Galaxy(galaxyCounter, tempX, tempY));
                    galaxyCounter++;
                }
            }
        }

        // Create pairs
        Set<Pair> pairs = new HashSet<>();

        for (Galaxy galaxy : galaxies) {
            for (Galaxy otherGalaxy : galaxies) {
                if (!otherGalaxy.equals(galaxy)) {
                    pairs.add(new Pair(galaxy, otherGalaxy));
                }
            }
        }

        for (Pair pair : pairs) {
            result += pair.getDistance();
        }

        return String.valueOf(result);
    }


    @Data
    public class Pair {
        Galaxy galaxy1;
        Galaxy galaxy2;

        public Pair(Galaxy galaxyParameter1, Galaxy galaxyParameter2) {
            if (galaxyParameter1.galaxyId < galaxyParameter2.getGalaxyId()) {
                galaxy1 = galaxyParameter1;
                galaxy2 = galaxyParameter2;
            } else {
                galaxy1 = galaxyParameter2;
                galaxy2 = galaxyParameter1;
            }
        }

        public long getDistance() {
            return Math.abs(galaxy1.getX() - galaxy2.getX()) + Math.abs(galaxy1.getY() - galaxy2.getY());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Pair pair = (Pair) o;

            if (!Objects.equals(galaxy1, pair.galaxy1)) return false;
            return Objects.equals(galaxy2, pair.galaxy2);
        }

        @Override
        public int hashCode() {
            int result = galaxy1 != null ? galaxy1.hashCode() : 0;
            result = 31 * result + (galaxy2 != null ? galaxy2.hashCode() : 0);
            return result;
        }
    }

    @Data
    public class Galaxy {
        long x;
        long y;

        int galaxyId;

        public Galaxy(int galaxyId, long x, long y) {
            this.galaxyId = galaxyId;
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Galaxy galaxy = (Galaxy) o;

            if (x != galaxy.x) return false;
            if (y != galaxy.y) return false;
            return galaxyId == galaxy.galaxyId;
        }

        @Override
        public int hashCode() {
            int result = (int) (x ^ (x >>> 32));
            result = 31 * result + (int) (y ^ (y >>> 32));
            result = 31 * result + galaxyId;
            return result;
        }
    }

    public List<Integer> getVerticalExtensions(List<String> input) {
        List<Integer> result = new ArrayList();

        for (int i = input.size() - 1; i >= 0; i--) {
            if (input.get(i).indexOf("#") == -1) {
                result.add(i);
            }
        }
        return result;
    }

    public List<Integer> getHorizontalExtensions(List<String> input) {
        List<Integer> result = new ArrayList();

        for (int x = input.get(0).length() - 1; x >= 0; x--) {
            boolean planetFound = false;
            for (int i = 0; i < input.size(); i++) {
                if (input.get(i).charAt(x) == '#') {
                    planetFound = true;
                }
            }

            if (!planetFound) {
                result.add(x);
            }
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
