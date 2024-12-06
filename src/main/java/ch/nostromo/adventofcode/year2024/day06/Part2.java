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
 * --- Part Two ---
 * While The Historians begin working around the guard's patrol route, you borrow their fancy device and step outside the lab. From the safety of a supply closet, you time travel through the last few months and record the nightly status of the lab's guard post on the walls of the closet.
 * <p>
 * Returning after what seems like only a few seconds to The Historians, they explain that the guard's patrol area is simply too large for them to safely search the lab without getting caught.
 * <p>
 * Fortunately, they are pretty sure that adding a single new obstruction won't cause a time paradox. They'd like to place the new obstruction in such a way that the guard will get stuck in a loop, making the rest of the lab safe to search.
 * <p>
 * To have the lowest chance of creating a time paradox, The Historians would like to know all of the possible positions for such an obstruction. The new obstruction can't be placed at the guard's starting position - the guard is there right now and would notice.
 * <p>
 * In the above example, there are only 6 different positions where a new obstruction would cause the guard to get stuck in a loop. The diagrams of these six situations use O to mark the new obstruction, | to show a position where the guard moves up/down, - to show a position where the guard moves left/right, and + to show a position where the guard moves both up/down and left/right.
 * <p>
 * Option one, put a printing press next to the guard's starting position:
 * <p>
 * ....#.....
 * ....+---+#
 * ....|...|.
 * ..#.|...|.
 * ....|..#|.
 * ....|...|.
 * .#.O^---+.
 * ........#.
 * #.........
 * ......#...
 * Option two, put a stack of failed suit prototypes in the bottom right quadrant of the mapped area:
 * <p>
 * <p>
 * ....#.....
 * ....+---+#
 * ....|...|.
 * ..#.|...|.
 * ..+-+-+#|.
 * ..|.|.|.|.
 * .#+-^-+-+.
 * ......O.#.
 * #.........
 * ......#...
 * Option three, put a crate of chimney-squeeze prototype fabric next to the standing desk in the bottom right quadrant:
 * <p>
 * ....#.....
 * ....+---+#
 * ....|...|.
 * ..#.|...|.
 * ..+-+-+#|.
 * ..|.|.|.|.
 * .#+-^-+-+.
 * .+----+O#.
 * #+----+...
 * ......#...
 * Option four, put an alchemical retroencabulator near the bottom left corner:
 * <p>
 * ....#.....
 * ....+---+#
 * ....|...|.
 * ..#.|...|.
 * ..+-+-+#|.
 * ..|.|.|.|.
 * .#+-^-+-+.
 * ..|...|.#.
 * #O+---+...
 * ......#...
 * Option five, put the alchemical retroencabulator a bit to the right instead:
 * <p>
 * ....#.....
 * ....+---+#
 * ....|...|.
 * ..#.|...|.
 * ..+-+-+#|.
 * ..|.|.|.|.
 * .#+-^-+-+.
 * ....|.|.#.
 * #..O+-+...
 * ......#...
 * Option six, put a tank of sovereign glue right next to the tank of universal solvent:
 * <p>
 * ....#.....
 * ....+---+#
 * ....|...|.
 * ..#.|...|.
 * ..+-+-+#|.
 * ..|.|.|.|.
 * .#+-^-+-+.
 * .+----++#.
 * #+----++..
 * ......#O..
 * It doesn't really matter what you choose to use as an obstacle so long as you and The Historians can put it into position without the guard noticing. The important thing is having enough options that you can find one that minimizes time paradoxes, and in this example, there are 6 different positions you could choose.
 * <p>
 * You need to get the guard stuck in a loop by adding a single new obstruction. How many different positions could you choose for this obstruction?
 */
public class Part2 extends BasePuzzle {

    private static final String EXPECTED_TEST_RESULT = "6";


    public String solvePuzzle(List<String> input) {
        int result = 0;

        int width = input.get(0).length();
        int height = input.size();
        String map = String.join("", input);

        for (int i = 0; i < map.length(); i++) {

            String modifiedMap = map.substring(0, i) + '#' + map.substring(i + 1);

            if (!modifiedMap.equals(map) && isParadox(modifiedMap, width, height)) {
                result++;
            }

        }

        return String.valueOf(result);
    }


    boolean isParadox(String map, int width, int height) {
        // North, east, south, west
        int[] directions = {-width, 1, width, -1};

        int currentDirection = 0;
        int currentPosition = map.indexOf("^");

        Set<Integer> visitedFieldsInDirection = new HashSet<>();
        visitedFieldsInDirection.add(currentDirection * 100000 + currentPosition);

        while (true) {
            // calculate next position
            int nextPosition = currentPosition + directions[currentDirection];

            // Is out of bounds north / south
            if (nextPosition < 0 || nextPosition > map.length()) {
                break;
            } else if (currentDirection == 1 && nextPosition % width < currentPosition % width) {
                break;
            } else if (currentDirection == 3 && nextPosition % width >  currentPosition % width) {
                break;
            }

            // Is empty field?
            if (map.charAt(nextPosition) != '#') {
                currentPosition = nextPosition;
            } else {
                currentDirection++;
                if (currentDirection > 3) {
                    currentDirection = 0;
                }

            }

            int nextCheck = currentDirection * 100000 + currentPosition;
            if (visitedFieldsInDirection.contains(nextCheck)) {
                return true;
            } else {
                visitedFieldsInDirection.add(nextCheck);
            }

        }

        return false;
    }

    public Part2() {
        super(EXPECTED_TEST_RESULT);
    }

    public static void main(String... args) {
        new Part2().run();
    }

}
