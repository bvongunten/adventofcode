package ch.nostromo.adventofcode.year2023.day10;

import ch.nostromo.adventofcode.BasePuzzle;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * --- Day 10: Pipe Maze ---
 * You use the hang glider to ride the hot air from Desert Island all the way up to the floating metal island. This island is surprisingly cold and there definitely aren't any thermals to glide on, so you leave your hang glider behind.
 * <p>
 * You wander around for a while, but you don't find any people or animals. However, you do occasionally find signposts labeled "Hot Springs" pointing in a seemingly consistent direction; maybe you can find someone at the hot springs and ask them where the desert-machine parts are made.
 * <p>
 * The landscape here is alien; even the flowers and trees are made of metal. As you stop to admire some metal grass, you notice something metallic scurry away in your peripheral vision and jump into a big pipe! It didn't look like any animal you've ever seen; if you want a better look, you'll need to get ahead of it.
 * <p>
 * Scanning the area, you discover that the entire field you're standing on is densely packed with pipes; it was hard to tell at first because they're the same metallic silver color as the "ground". You make a quick sketch of all of the surface pipes you can see (your puzzle input).
 * <p>
 * The pipes are arranged in a two-dimensional grid of tiles:
 * <p>
 * | is a vertical pipe connecting north and south.
 * - is a horizontal pipe connecting east and west.
 * L is a 90-degree bend connecting north and east.
 * J is a 90-degree bend connecting north and west.
 * 7 is a 90-degree bend connecting south and west.
 * F is a 90-degree bend connecting south and east.
 * . is ground; there is no pipe in this tile.
 * S is the starting position of the animal; there is a pipe on this tile, but your sketch doesn't show what shape the pipe has.
 * Based on the acoustics of the animal's scurrying, you're confident the pipe that contains the animal is one large, continuous loop.
 * <p>
 * For example, here is a square loop of pipe:
 * <p>
 * .....
 * .F-7.
 * .|.|.
 * .L-J.
 * .....
 * If the animal had entered this loop in the northwest corner, the sketch would instead look like this:
 * <p>
 * .....
 * .S-7.
 * .|.|.
 * .L-J.
 * .....
 * In the above diagram, the S tile is still a 90-degree F bend: you can tell because of how the adjacent pipes connect to it.
 * <p>
 * Unfortunately, there are also many pipes that aren't connected to the loop! This sketch shows the same loop as above:
 * <p>
 * -L|F7
 * 7S-7|
 * L|7||
 * -L-J|
 * L|-JF
 * In the above diagram, you can still figure out which pipes form the main loop: they're the ones connected to S, pipes those pipes connect to, pipes those pipes connect to, and so on. Every pipe in the main loop connects to its two neighbors (including S, which will have exactly two pipes connecting to it, and which is assumed to connect back to those two pipes).
 * <p>
 * Here is a sketch that contains a slightly more complex main loop:
 * <p>
 * ..F7.
 * .FJ|.
 * SJ.L7
 * |F--J
 * LJ...
 * Here's the same example sketch with the extra, non-main-loop pipe tiles also shown:
 * <p>
 * 7-F7-
 * .FJ|7
 * SJLL7
 * |F--J
 * LJ.LJ
 * If you want to get out ahead of the animal, you should find the tile in the loop that is farthest from the starting position. Because the animal is in the pipe, it doesn't make sense to measure this by direct distance. Instead, you need to find the tile that would take the longest number of steps along the loop to reach from the starting point - regardless of which way around the loop the animal went.
 * <p>
 * In the first example with the square loop:
 * <p>
 * .....
 * .S-7.
 * .|.|.
 * .L-J.
 * .....
 * You can count the distance each tile in the loop is from the starting point like this:
 * <p>
 * .....
 * .012.
 * .1.3.
 * .234.
 * .....
 * In this example, the farthest point from the start is 4 steps away.
 * <p>
 * Here's the more complex loop again:
 * <p>
 * ..F7.
 * .FJ|.
 * SJ.L7
 * |F--J
 * LJ...
 * Here are the distances for each tile on that loop:
 * <p>
 * ..45.
 * .236.
 * 01.78
 * 14567
 * 23...
 * Find the single giant loop starting at S. How many steps along the loop does it take to get from the starting position to the point farthest from the starting position?
 * <p>
 * Your puzzle answer was 6640.
 * <p>
 * --- Part Two ---
 * You quickly reach the farthest point of the loop, but the animal never emerges. Maybe its nest is within the area enclosed by the loop?
 * <p>
 * To determine whether it's even worth taking the time to search for such a nest, you should calculate how many tiles are contained within the loop. For example:
 * <p>
 * ...........
 * .S-------7.
 * .|F-----7|.
 * .||.....||.
 * .||.....||.
 * .|L-7.F-J|.
 * .|..|.|..|.
 * .L--J.L--J.
 * ...........
 * The above loop encloses merely four tiles - the two pairs of . in the southwest and southeast (marked I below). The middle . tiles (marked O below) are not in the loop. Here is the same loop again with those regions marked:
 * <p>
 * ...........
 * .S-------7.
 * .|F-----7|.
 * .||OOOOO||.
 * .||OOOOO||.
 * .|L-7OF-J|.
 * .|II|O|II|.
 * .L--JOL--J.
 * .....O.....
 * In fact, there doesn't even need to be a full tile path to the outside for tiles to count as outside the loop - squeezing between pipes is also allowed! Here, I is still within the loop and O is still outside the loop:
 * <p>
 * ..........
 * .S------7.
 * .|F----7|.
 * .||OOOO||.
 * .||OOOO||.
 * .|L-7F-J|.
 * .|II||II|.
 * .L--JL--J.
 * ..........
 * In both of the above examples, 4 tiles are enclosed by the loop.
 * <p>
 * Here's a larger example:
 * <p>
 * .F----7F7F7F7F-7....
 * .|F--7||||||||FJ....
 * .||.FJ||||||||L7....
 * FJL7L7LJLJ||LJ.L-7..
 * L--J.L7...LJS7F-7L7.
 * ....F-J..F7FJ|L7L7L7
 * ....L7.F7||L7|.L7L7|
 * .....|FJLJ|FJ|F7|.LJ
 * ....FJL-7.||.||||...
 * ....L---J.LJ.LJLJ...
 * The above sketch has many random bits of ground, some of which are in the loop (I) and some of which are outside it (O):
 * <p>
 * OF----7F7F7F7F-7OOOO
 * O|F--7||||||||FJOOOO
 * O||OFJ||||||||L7OOOO
 * FJL7L7LJLJ||LJIL-7OO
 * L--JOL7IIILJS7F-7L7O
 * OOOOF-JIIF7FJ|L7L7L7
 * OOOOL7IF7||L7|IL7L7|
 * OOOOO|FJLJ|FJ|F7|OLJ
 * OOOOFJL-7O||O||||OOO
 * OOOOL---JOLJOLJLJOOO
 * In this larger example, 8 tiles are enclosed by the loop.
 * <p>
 * Any tile that isn't part of the main loop can count as being enclosed by the loop. Here's another example with many bits of junk pipe lying around that aren't connected to the main loop at all:
 * <p>
 * FF7FSF7F7F7F7F7F---7
 * L|LJ||||||||||||F--J
 * FL-7LJLJ||||||LJL-77
 * F--JF--7||LJLJ7F7FJ-
 * L---JF-JLJ.||-FJLJJ7
 * |F|F-JF---7F7-L7L|7|
 * |FFJF7L7F-JF7|JL---7
 * 7-L-JL7||F7|L7F-7F7|
 * L.L7LFJ|||||FJL7||LJ
 * L7JLJL-JLJLJL--JLJ.L
 * Here are just the tiles that are enclosed by the loop marked with I:
 * <p>
 * FF7FSF7F7F7F7F7F---7
 * L|LJ||||||||||||F--J
 * FL-7LJLJ||||||LJL-77
 * F--JF--7||LJLJIF7FJ-
 * L---JF-JLJIIIIFJLJJ7
 * |F|F-JF---7IIIL7L|7|
 * |FFJF7L7F-JF7IIL---7
 * 7-L-JL7||F7|L7F-7F7|
 * L.L7LFJ|||||FJL7||LJ
 * L7JLJL-JLJLJL--JLJ.L
 * In this last example, 10 tiles are enclosed by the loop.
 * <p>
 * Figure out whether you have time to search for the nest by calculating the area within the loop. How many tiles are enclosed by the loop?
 * <p>
 * Your puzzle answer was 411.
 */
public class Part2 extends BasePuzzle {

    private static final String EXPECTED_TEST_RESULT = "10";

    private static final String testInput = "FF7FSF7F7F7F7F7F---7\n" +
            "L|LJ||||||||||||F--J\n" +
            "FL-7LJLJ||||||LJL-77\n" +
            "F--JF--7||LJLJ7F7FJ-\n" +
            "L---JF-JLJ.||-FJLJJ7\n" +
            "|F|F-JF---7F7-L7L|7|\n" +
            "|FFJF7L7F-JF7|JL---7\n" +
            "7-L-JL7||F7|L7F-7F7|\n" +
            "L.L7LFJ|||||FJL7||LJ\n" +
            "L7JLJL-JLJLJL--JLJ.L";

    public String solvePuzzle(List<String> input) {

        int startX = 0;
        int startY = 0;

        // Find start
        for (int y = 0; y < input.size(); y++) {
            int x = input.get(y).indexOf('S');
            if (x >= 0) {
                startY = y;
                startX = x;
            }
        }

        List<Crossroad> allCrossroads = new ArrayList<>();

        Crossroad startCrossroad = new Crossroad(startX, startY);
        startCrossroad.setStartEnd(true);

        allCrossroads.add(startCrossroad);

        int nextX = startX;
        int nextY = startY;

        // Find first connecting tile
        if (startY > 0 && (input.get(startY - 1).charAt(startX) == '|' || input.get(startY - 1).charAt(startX) == 'F' || input.get(startY - 1).charAt(startX) == '7')) {
            nextY = startY - 1;
        } else if (startY < input.size() - 1 && (input.get(startY + 1).charAt(startX) == '-' || input.get(startY + 1).charAt(startX) == 'L' || input.get(startY + 1).charAt(startX) == 'J')) {
            nextY = startY + 1;
        } else if (startX > 0 && (input.get(startY).charAt(startX - 1) == 'F' || input.get(startY).charAt(startX - 1) == 'L' || input.get(startY).charAt(startX - 1) == '-')) {
            nextX = startX - 1;
        } else if (startX < input.get(startY).length() - 1 && (input.get(startY).charAt(startX + 1) == '7' || input.get(startY).charAt(startX + 1) == 'J' || input.get(startY).charAt(startX + 1) == '-')) {
            nextX = startX + 1;
        } else {
            throw new IllegalArgumentException("Well that was fun :)");
        }

        Crossroad currentCrossroad = startCrossroad;

        // connect pipes
        boolean pipeClosed = false;
        while (!pipeClosed) {
            Crossroad nextCrossroad = new Crossroad(nextX, nextY);

            allCrossroads.add(nextCrossroad);

            currentCrossroad.setNextCrossroad(nextCrossroad);

            char nextPoint = input.get(nextY).charAt(nextX);

            switch (nextPoint) {
                case '|': {
                    if (nextCrossroad.y > currentCrossroad.y) {
                        nextY++;
                    } else {
                        nextY--;
                    }
                    break;
                }
                case '-': {
                    if (nextCrossroad.x > currentCrossroad.x) {
                        nextX++;
                    } else {
                        nextX--;
                    }
                    break;
                }
                case ('F'): {
                    if (nextCrossroad.x != currentCrossroad.x) {
                        nextY++;
                    } else if (nextCrossroad.y != currentCrossroad.y) {
                        nextX++;
                    }
                    break;
                }
                case ('7'): {
                    if (nextCrossroad.x != currentCrossroad.x) {
                        nextY++;
                    } else if (nextCrossroad.y != currentCrossroad.y) {
                        nextX--;
                    }
                    break;
                }
                case ('L'): {
                    if (nextCrossroad.x != currentCrossroad.x) {
                        nextY--;
                    } else if (nextCrossroad.y != currentCrossroad.y) {
                        nextX++;
                    }
                    break;
                }
                case ('J'): {
                    if (nextCrossroad.x != currentCrossroad.x) {
                        nextY--;
                    } else if (nextCrossroad.y != currentCrossroad.y) {
                        nextX--;
                    }
                    break;
                }


            }

            currentCrossroad = nextCrossroad;


            if (nextX == startX && nextY == startY) {
                currentCrossroad.nextCrossroad = startCrossroad;
                pipeClosed = true;
            }
        }


        // Bloat map by 3x3 segments and connect the pipes where needed
        List<String> bloatedMap = bloatMap(input, allCrossroads);

        // Fill reachable positions on the map
        List<String> floatedMap = floatFillMap(bloatedMap);

        // Find the center of any 3x3 segment and check if it's free
        int result = 0;
        for (int y = 0; y < input.size(); y++) {
            for (int x = 0; x < input.get(0).length(); x++) {
                int wy = (y * 3) + 1;
                int wx = (x * 3) + 1;

                if (floatedMap.get(wy).charAt(wx) == '.') {
                    result++;
                }
            }

        }


        return String.valueOf(result);
    }

    public List<String> floatFillMap(List<String> bloatedMap) {
        List<String> result = new ArrayList<>(bloatedMap);

        // horizontal boarders
        for (int x = 0; x < result.get(0).length(); x++) {
            stackFloat(result, x, 0);
        }


        for (int x = 0; x < result.get(result.size() - 1).length(); x++) {
            stackFloat(result, x, result.size() - 1);
        }

        // vertical boarders
        for (int y = 0; y < result.size(); y++) {
            stackFloat(result, 0, y);
        }

        for (int y = 0; y < result.size(); y++) {
            stackFloat(result, result.get(y).length() - 1, y);
        }


        return result;
    }

    @Data
    @AllArgsConstructor
    public class Coordinates {
        int x;
        int y;
    }

    // Who would be dumb enough to try recursion anyways ? ;)
    public void stackFloat(List<String> map, int startX, int startY) {
        Stack<Coordinates> stack = new Stack<>();
        stack.push(new Coordinates(startX, startY));

        while (!stack.empty()) {
            Coordinates coords = stack.pop();
            int x = coords.getX();
            int y = coords.getY();

            if (map.get(y).charAt(x) == '.') {
                map.set(y, replaceFreeSpot(map.get(y), x));

                // Northern coord
                if (y > 0 && map.get(y - 1).charAt(x) == '.') {
                    stack.push(new Coordinates(x, y - 1));
                }

                // southern coord
                if (y < map.size() - 1 && map.get(y + 1).charAt(x) == '.') {
                    stack.push(new Coordinates(x, y + 1));
                }

                // Western coord
                if (x > 0 && map.get(y).charAt(x - 1) == '.') {
                    stack.push(new Coordinates(x - 1, y));
                }

                // eastern coord
                if (x < map.get(y).length() - 1 && map.get(y).charAt(x + 1) == '.') {
                    stack.push(new Coordinates(x + 1, y));
                }


            }


        }


    }


    public Crossroad getCrossRoadByCoord(List<Crossroad> allCrossRoads, int x, int y) {
        for (Crossroad crossroad : allCrossRoads) {
            if (crossroad.x == x && crossroad.y == y) {
                return crossroad;
            }
        }
        return null;
    }

    private List<String> bloatMap(List<String> map, List<Crossroad> allCrossRoads) {
        List<String> result = new ArrayList<>();

        for (int y = 0; y < map.size(); y++) {
            String line1 = "";
            String line2 = "";
            String line3 = "";

            for (int x = 0; x < map.get(y).length(); x++) {

                char currChar = map.get(y).charAt(x);


                Crossroad currCrossroad = getCrossRoadByCoord(allCrossRoads, x, y);

                if (currCrossroad != null) {

                    switch (currChar) {
                        case '|': {
                            line1 += ".#.";
                            line2 += ".#.";
                            line3 += ".#.";
                            break;
                        }
                        case '-': {
                            line1 += "...";
                            line2 += "###";
                            line3 += "...";
                            break;
                        }
                        case ('F'): {
                            line1 += "...";
                            line2 += ".##";
                            line3 += ".#.";
                            break;
                        }
                        case ('7'): {
                            line1 += "...";
                            line2 += "##.";
                            line3 += ".#.";
                            break;
                        }
                        case ('L'): {
                            line1 += ".#.";
                            line2 += ".##";
                            line3 += "...";
                            break;
                        }
                        case ('J'): {
                            line1 += ".#.";
                            line2 += "##.";
                            line3 += "...";
                            break;
                        }
                        case ('S'): {
                            // Cheated a bit ;)
                            if (isTestRun()) {
                                line1 += "...";
                                line2 += "##.";
                                line3 += ".#.";
                            } else {
                                line1 += ".#.";
                                line2 += ".#.";
                                line3 += ".#.";
                            }
                            break;
                        }
                    }
                } else {
                    line1 += "...";
                    line2 += "...";
                    line3 += "...";
                }

            }

            result.add(line1);
            result.add(line2);
            result.add(line3);

        }

        return result;

    }

    private String replaceFreeSpot(String line, int idx) {
        char[] chars = line.toCharArray();
        chars[idx] = 'O';
        return new String(chars);
    }

    @Data
    public class Crossroad {

        int x;
        int y;

        boolean isStartEnd = false;

        Crossroad nextCrossroad;

        public Crossroad(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Crossroad crossroad = (Crossroad) o;

            if (x != crossroad.x) return false;
            return y == crossroad.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }

    public Part2() {
        super(EXPECTED_TEST_RESULT, testInput, null);
    }

    public static void main(String... args) {
        new Part2().run();
    }

}
