package ch.nostromo.adventofcode.year2023.day11;

import ch.nostromo.adventofcode.BasePuzzle;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

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
 */
public class Part1 extends BasePuzzle {

    private static final String EXPECTED_TEST_RESULT = "374";


    public String solvePuzzle(List<String> input) {
        int result = 0;

        // bloat Map
        List<String> map = bloatUniverse(input);

        int galaxyCounter = 0;
        List<Galaxy> galaxies = new ArrayList<>();
        // Find planets
        for (int y = 0; y < map.size(); y++) {
            for (int x = 0; x < map.get(y).length(); x++) {
                if (map.get(y).charAt(x) == '#') {

                    galaxies.add(new Galaxy(galaxyCounter, x, y));
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

        public int getDistance() {
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
        int x;
        int y;

        int galaxyId;

        public Galaxy(int galaxyId, int x, int y) {
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
            int result = x;
            result = 31 * result + y;
            result = 31 * result + galaxyId;
            return result;
        }
    }

    public List<String> bloatUniverse(List<String> input) {
        List<String> result = new ArrayList(input);

        for (int i = input.size() - 1; i >= 0; i--) {
            if (input.get(i).indexOf("#") == -1) {
                result.add(i, input.get(i));
            }
        }

        for (int x = input.get(0).length() - 1; x >= 0; x--) {
            boolean planetFound = false;
            for (int i = 0; i < input.size(); i++) {
                if (input.get(i).charAt(x) == '#') {
                    planetFound = true;
                }
            }

            if (!planetFound) {
                for (int i = 0; i < result.size(); i++) {
                    result.set(i, result.get(i).substring(0, x) + "." + result.get(i).substring(x));
                }
            }
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
