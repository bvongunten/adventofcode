package ch.nostromo.adventofcode.year2024.day06;

import ch.nostromo.adventofcode.BasePuzzle;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * --- Day 6: Guard Gallivant ---
 * The Historians use their fancy device again, this time to whisk you all away to the North Pole prototype suit manufacturing lab... in the year 1518! It turns out that having direct access to history is very convenient for a group of historians.
 * <p>
 * You still have to be careful of time paradoxes, and so it will be important to avoid anyone from 1518 while The Historians search for the Chief. Unfortunately, a single guard is patrolling this part of the lab.
 * <p>
 * Maybe you can work out where the guard will go ahead of time so that The Historians can search safely?
 * <p>
 * You start by making a map (your puzzle input) of the situation. For example:
 * <p>
 * ....#.....
 * .........#
 * ..........
 * ..#.......
 * .......#..
 * ..........
 * .#..^.....
 * ........#.
 * #.........
 * ......#...
 * The map shows the current position of the guard with ^ (to indicate the guard is currently facing up from the perspective of the map). Any obstructions - crates, desks, alchemical reactors, etc. - are shown as #.
 * <p>
 * Lab guards in 1518 follow a very strict patrol protocol which involves repeatedly following these steps:
 * <p>
 * If there is something directly in front of you, turn right 90 degrees.
 * Otherwise, take a step forward.
 * Following the above protocol, the guard moves up several times until she reaches an obstacle (in this case, a pile of failed suit prototypes):
 * <p>
 * ....#.....
 * ....^....#
 * ..........
 * ..#.......
 * .......#..
 * ..........
 * .#........
 * ........#.
 * #.........
 * ......#...
 * Because there is now an obstacle in front of the guard, she turns right before continuing straight in her new facing direction:
 * <p>
 * ....#.....
 * ........>#
 * ..........
 * ..#.......
 * .......#..
 * ..........
 * .#........
 * ........#.
 * #.........
 * ......#...
 * Reaching another obstacle (a spool of several very long polymers), she turns right again and continues downward:
 * <p>
 * ....#.....
 * .........#
 * ..........
 * ..#.......
 * .......#..
 * ..........
 * .#......v.
 * ........#.
 * #.........
 * ......#...
 * This process continues for a while, but the guard eventually leaves the mapped area (after walking past a tank of universal solvent):
 * <p>
 * ....#.....
 * .........#
 * ..........
 * ..#.......
 * .......#..
 * ..........
 * .#........
 * ........#.
 * #.........
 * ......#v..
 * By predicting the guard's route, you can determine which specific positions in the lab will be in the patrol path. Including the guard's starting position, the positions visited by the guard before leaving the area are marked with an X:
 * <p>
 * ....#.....
 * ....XXXXX#
 * ....X...X.
 * ..#.X...X.
 * ..XXXXX#X.
 * ..X.X.X.X.
 * .#XXXXXXX.
 * .XXXXXXX#.
 * #XXXXXXX..
 * ......#X..
 * In this example, the guard will visit 41 distinct positions on your map.
 * <p>
 * Predict the path of the guard. How many distinct positions will the guard visit before leaving the mapped area?
 * <p>
 * To begin, get your puzzle input.
 */
public class Part1 extends BasePuzzle {

    private static final String EXPECTED_TEST_RESULT = "41";


    public String solvePuzzle(List<String> input) {
        int width = input.get(0).length();
        return String.valueOf(travel(String.join("", input), width));
    }


    int travel(String map, int width) {
        // North, east, south, west
        int[] directions = {-width, 1, width, -1};

        int currentDirection = 0;
        int currentPosition = map.indexOf("^");

        Set<Integer> visitedFields = new HashSet<>();
        visitedFields.add(currentPosition);

        while (true) {
            int nextPosition = currentPosition + directions[currentDirection];

            // Is out of bounds
            if (nextPosition < 0 || nextPosition > map.length()) {
                break;
            } else if (currentDirection == 1 && nextPosition % width < currentPosition % width) {
                break;
            } else if (currentDirection == 3 && nextPosition % width >  currentPosition % width) {
                break;
            }

            if (map.charAt(nextPosition) != '#') {
                currentPosition = nextPosition;
                visitedFields.add(currentPosition);
            } else {
                currentDirection++;
                if (currentDirection > 3) {
                    currentDirection = 0;
                }

            }

        }

        return visitedFields.size();
    }

    public Part1() {
        super(EXPECTED_TEST_RESULT);
    }

    public static void main(String... args) {
        new Part1().run();
    }

}
