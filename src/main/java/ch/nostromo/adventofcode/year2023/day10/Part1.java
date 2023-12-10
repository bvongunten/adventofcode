package ch.nostromo.adventofcode.year2023.day10;

import ch.nostromo.adventofcode.BasePuzzle;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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
 */
public class Part1 extends BasePuzzle {

    private static final String EXPECTED_TEST_RESULT = "8";


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


        int result = 0;

        for (Crossroad targetCrossroad : allCrossroads) {
            if (!targetCrossroad.isStartEnd) {
                int curDistance = getMaxDistance(startCrossroad, targetCrossroad);
                if (curDistance > result) {
                    result = curDistance;
                }
            }
        }

        return String.valueOf(result);
    }

    private int getMaxDistance(Crossroad start, Crossroad target) {
        //
        int distance = 0;
        Crossroad tempCrossroad = start.nextCrossroad;
        while (true) {
            distance++;
            if (tempCrossroad.equals(target)) {
                break;
            }
            tempCrossroad = tempCrossroad.nextCrossroad;
        }

        return distance / 2 + 1;
    }


    @Data
    public class Crossroad {

        int x;
        int y;

        boolean isStartEnd = false;
        boolean isTarget = false;

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

    public Part1() {
        super(EXPECTED_TEST_RESULT);
    }

    public static void main(String... args) {
        new Part1().run();
    }

}
