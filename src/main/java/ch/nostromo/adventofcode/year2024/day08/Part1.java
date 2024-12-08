package ch.nostromo.adventofcode.year2024.day08;

import ch.nostromo.adventofcode.BasePuzzle;

import java.util.*;

/**
 * --- Day 8: Resonant Collinearity ---
 * You find yourselves on the roof of a top-secret Easter Bunny installation.
 * <p>
 * While The Historians do their thing, you take a look at the familiar huge antenna. Much to your surprise, it seems to have been reconfigured to emit a signal that makes people 0.1% more likely to buy Easter Bunny brand Imitation Mediocre Chocolate as a Christmas gift! Unthinkable!
 * <p>
 * Scanning across the city, you find that there are actually many such antennas. Each antenna is tuned to a specific frequency indicated by a single lowercase letter, uppercase letter, or digit. You create a map (your puzzle input) of these antennas. For example:
 * <p>
 * ............
 * ........0...
 * .....0......
 * .......0....
 * ....0.......
 * ......A.....
 * ............
 * ............
 * ........A...
 * .........A..
 * ............
 * ............
 * The signal only applies its nefarious effect at specific antinodes based on the resonant frequencies of the antennas. In particular, an antinode occurs at any point that is perfectly in line with two antennas of the same frequency - but only when one of the antennas is twice as far away as the other. This means that for any pair of antennas with the same frequency, there are two antinodes, one on either side of them.
 * <p>
 * So, for these two antennas with frequency a, they create the two antinodes marked with #:
 * <p>
 * ..........
 * ...#......
 * ..........
 * ....a.....
 * ..........
 * .....a....
 * ..........
 * ......#...
 * ..........
 * ..........
 * Adding a third antenna with the same frequency creates several more antinodes. It would ideally add four antinodes, but two are off the right side of the map, so instead it adds only two:
 * <p>
 * ..........
 * ...#......
 * #.........
 * ....a.....
 * ........a.
 * .....a....
 * ..#.......
 * ......#...
 * ..........
 * ..........
 * Antennas with different frequencies don't create antinodes; A and a count as different frequencies. However, antinodes can occur at locations that contain antennas. In this diagram, the lone antenna with frequency capital A creates no antinodes but has a lowercase-a-frequency antinode at its location:
 * <p>
 * ..........
 * ...#......
 * #.........
 * ....a.....
 * ........a.
 * .....a....
 * ..#.......
 * ......A...
 * ..........
 * ..........
 * The first example has antennas with two different frequencies, so the antinodes they create look like this, plus an antinode overlapping the topmost A-frequency antenna:
 * <p>
 * ......#....#
 * ...#....0...
 * ....#0....#.
 * ..#....0....
 * ....0....#..
 * .#....A.....
 * ...#........
 * #......#....
 * ........A...
 * .........A..
 * ..........#.
 * ..........#.
 * Because the topmost A-frequency antenna overlaps with a 0-frequency antinode, there are 14 total unique locations that contain an antinode within the bounds of the map.
 * <p>
 * Calculate the impact of the signal. How many unique locations within the bounds of the map contain an antinode?
 */
public class Part1 extends BasePuzzle {

    private static final String EXPECTED_TEST_RESULT = "14";


    public String solvePuzzle(List<String> input) {

        int width = input.get(0).length();
        String map = String.join("", input);

        Map<Character, List<Integer>> frequenciesAntennas = new HashMap<>();

        for (int i = 0; i < map.length(); i++) {
            Character mapCharacter = map.charAt(i);

            if (mapCharacter != '.') {
                if (!frequenciesAntennas.containsKey(mapCharacter)) {
                    frequenciesAntennas.put(mapCharacter, new ArrayList<>());
                }

                frequenciesAntennas.get(mapCharacter).add(i);
            }
        }

        int result = findAntiNodes(map, frequenciesAntennas, width);


        return String.valueOf(result);

    }


    int findAntiNodes(String map, Map<Character, List<Integer>> frequenciesAntennas, int width) {
        Set<Integer> result = new HashSet<>();

        for (Character antennaCharacter : frequenciesAntennas.keySet()) {
            for (int antennaCoordinates : frequenciesAntennas.get(antennaCharacter)) {
                for (int otherAntennaCoordinates : frequenciesAntennas.get(antennaCharacter)) {
                    if (antennaCoordinates != otherAntennaCoordinates) {
                        int direction = otherAntennaCoordinates - antennaCoordinates;
                        int antiNode = otherAntennaCoordinates + direction;

                        if (!isOutOfBounds(map, antennaCoordinates, otherAntennaCoordinates, antiNode, width)) {
                            result.add(antiNode);
                        }
                    }
                }
            }
        }

        return result.size();
    }

    private boolean isOutOfBounds(String map, int antennaCoordinates, int otherAntennaCoordinates, int antiNode, int width) {
        boolean antennaDirection = otherAntennaCoordinates % width < antiNode % width;
        boolean antiNodeDirection = antennaCoordinates % width < otherAntennaCoordinates% width;

        if (antiNode < 0 || antiNode > map.length() - 1) {
            return true;
        } else {
            return antennaDirection != antiNodeDirection;
        }
    }


    public Part1() {
        super(EXPECTED_TEST_RESULT);
    }

    public static void main(String... args) {
        new Part1().run();
    }
}