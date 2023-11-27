package ch.nostromo.adventofcode.year2022.day14;

import ch.nostromo.adventofcode.BasePuzzle;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * --- Day 14: Regolith Reservoir ---
 * The distress signal leads you to a giant waterfall! Actually, hang on - the signal seems like it's coming from the waterfall itself, and that doesn't make any sense. However, you do notice a little path that leads behind the waterfall.
 * <p>
 * Correction: the distress signal leads you behind a giant waterfall! There seems to be a large cave system here, and the signal definitely leads further inside.
 * <p>
 * As you begin to make your way deeper underground, you feel the ground rumble for a moment. Sand begins pouring into the cave! If you don't quickly figure out where the sand is going, you could quickly become trapped!
 * <p>
 * Fortunately, your familiarity with analyzing the path of falling material will come in handy here. You scan a two-dimensional vertical slice of the cave above you (your puzzle input) and discover that it is mostly air with structures made of rock.
 * <p>
 * Your scan traces the path of each solid rock structure and reports the x,y coordinates that form the shape of the path, where x represents distance to the right and y represents distance down. Each path appears as a single line of text in your scan. After the first point of each path, each point indicates the end of a straight horizontal or vertical line to be drawn from the previous point. For example:
 * <p>
 * 498,4 -> 498,6 -> 496,6
 * 503,4 -> 502,4 -> 502,9 -> 494,9
 * This scan means that there are two paths of rock; the first path consists of two straight lines, and the second path consists of three straight lines. (Specifically, the first path consists of a line of rock from 498,4 through 498,6 and another line of rock from 498,6 through 496,6.)
 * <p>
 * The sand is pouring into the cave from point 500,0.
 * <p>
 * Drawing rock as #, air as ., and the source of the sand as +, this becomes:
 * <p>
 * <p>
 * 4     5  5
 * 9     0  0
 * 4     0  3
 * 0 ......+...
 * 1 ..........
 * 2 ..........
 * 3 ..........
 * 4 ....#...##
 * 5 ....#...#.
 * 6 ..###...#.
 * 7 ........#.
 * 8 ........#.
 * 9 #########.
 * Sand is produced one unit at a time, and the next unit of sand is not produced until the previous unit of sand comes to rest. A unit of sand is large enough to fill one tile of air in your scan.
 * <p>
 * A unit of sand always falls down one step if possible. If the tile immediately below is blocked (by rock or sand), the unit of sand attempts to instead move diagonally one step down and to the left. If that tile is blocked, the unit of sand attempts to instead move diagonally one step down and to the right. Sand keeps moving as long as it is able to do so, at each step trying to move down, then down-left, then down-right. If all three possible destinations are blocked, the unit of sand comes to rest and no longer moves, at which point the next unit of sand is created back at the source.
 * <p>
 * So, drawing sand that has come to rest as o, the first unit of sand simply falls straight down and then stops:
 * <p>
 * ......+...
 * ..........
 * ..........
 * ..........
 * ....#...##
 * ....#...#.
 * ..###...#.
 * ........#.
 * ......o.#.
 * #########.
 * The second unit of sand then falls straight down, lands on the first one, and then comes to rest to its left:
 * <p>
 * ......+...
 * ..........
 * ..........
 * ..........
 * ....#...##
 * ....#...#.
 * ..###...#.
 * ........#.
 * .....oo.#.
 * #########.
 * After a total of five units of sand have come to rest, they form this pattern:
 * <p>
 * ......+...
 * ..........
 * ..........
 * ..........
 * ....#...##
 * ....#...#.
 * ..###...#.
 * ......o.#.
 * ....oooo#.
 * #########.
 * After a total of 22 units of sand:
 * <p>
 * ......+...
 * ..........
 * ......o...
 * .....ooo..
 * ....#ooo##
 * ....#ooo#.
 * ..###ooo#.
 * ....oooo#.
 * ...ooooo#.
 * #########.
 * Finally, only two more units of sand can possibly come to rest:
 * <p>
 * ......+...
 * ..........
 * ......o...
 * .....ooo..
 * ....#ooo##
 * ...o#ooo#.
 * ..###ooo#.
 * ....oooo#.
 * .o.ooooo#.
 * #########.
 * Once all 24 units of sand shown above have come to rest, all further sand flows out the bottom, falling into the endless void. Just for fun, the path any new sand takes before falling forever is shown here with ~:
 * <p>
 * .......+...
 * .......~...
 * ......~o...
 * .....~ooo..
 * ....~#ooo##
 * ...~o#ooo#.
 * ..~###ooo#.
 * ..~..oooo#.
 * .~o.ooooo#.
 * ~#########.
 * ~..........
 * ~..........
 * ~..........
 * Using your scan, simulate the falling sand. How many units of sand come to rest before sand starts flowing into the abyss below?
 */
public class Part1 extends BasePuzzle {

    private static final String EXPECTED_TEST_RESULT = "24";


    public String solvePuzzle(List<String> input) {

        int result = 0;

        List<Wall> walls = getWalls(input);
        String[][] map = drawMap(walls);

        while (dropSand(map, new Coordinates("500,0"))) {
            result ++;
            // dumpMap(map, walls);
        }


       // dumpMap(map, walls);

        return String.valueOf(result);

    }


    private boolean dropSand(String[][] map, Coordinates drop) {

        boolean placeFound = true;
        while (placeFound) {

            placeFound = false;
            // Check direction below
            if (map[drop.getX()][drop.getY() + 1].equals(".")) {
                drop.setY(drop.getY() + 1);
                placeFound = true;
            } else if (map[drop.getX() -1][drop.getY() + 1].equals(".")) {
                drop.setX(drop.getX() - 1);
                drop.setY(drop.getY() + 1);
                placeFound = true;
            } else if (map[drop.getX() + 1][drop.getY() + 1].equals(".")) {
                drop.setX(drop.getX() + 1);
                drop.setY(drop.getY() + 1);
                placeFound = true;
            }

            if (drop.getY() > 998) {
                return false;
            }

        }

        map[drop.getX()][ drop.getY()] = "o";
        return true;

    }


    @Data
    @AllArgsConstructor
    public class Wall {
        Coordinates from;
        Coordinates to;

        public boolean isHorizontal() {
            return (from.getX() != to.getX());
        }

    }

    @Data
    public class Coordinates {
        int x;
        int y;

        public Coordinates(String coords) {
            this.x = Integer.valueOf(coords.split(",")[0]);
            this.y = Integer.valueOf(coords.split(",")[1]);
        }

    }


    private List<Wall> getWalls(List<String> input) {
        List<Wall> walls = new ArrayList<>();

        for (String line : input) {
            StringTokenizer st = new StringTokenizer(line, " -> ");
            Coordinates fromCords = null;

            while (st.hasMoreTokens()) {
                String coords = st.nextToken();

                if (fromCords == null) {
                    fromCords = new Coordinates(coords);
                } else {
                    Coordinates toCords = new Coordinates(coords);
                    walls.add(new Wall(fromCords, toCords));
                    fromCords = toCords;

                }


            }

        }

        return walls;

    }

    private String[][] drawMap(List<Wall> walls) {
        String[][] map = new String[1000][1000];
        for (int x = 0; x < 1000; x++) {
            for (int y = 0; y < 1000; y++) {
                map[x][y] = ".";
            }
        }

        // Add Walls
        for (Wall wall : walls) {
            if (wall.isHorizontal()) {
                for (int x = Math.min(wall.getTo().getX(), wall.getFrom().getX()); x <= Math.max(wall.getTo().getX(), wall.getFrom().getX()); x++) {
                    map[x][wall.getFrom().getY()] = "#";
                }
            } else {
                for (int y = Math.min(wall.getTo().getY(), wall.getFrom().getY()); y <= Math.max(wall.getTo().getY(), wall.getFrom().getY()); y++) {
                    map[wall.getFrom().getX()][y] = "#";
                }

            }

        }

        return map;
    }

    private void dumpMap(String[][] map, List<Wall> walls) {
        Coordinates minCoords = getMinCoords(walls);
        Coordinates maxCoords = getMaxCoords(walls);

        StringBuilder result = new StringBuilder();
        for (int y = minCoords.getY() - 1; y < maxCoords.getY() + 2; y++) {
            StringBuilder line = new StringBuilder();
            for (int x = minCoords.getX() -1; x < maxCoords.getX() +2; x++) {
                line.append(map[x][y]);
            }

            result.append(line + "\n");
        }

        LOG.info("\n" + result.toString());

    }



    private Coordinates getMinCoords(List<Wall> walls) {
        Coordinates result = new Coordinates("1000,1000");

        for (Wall wall : walls) {
            int minX = Math.min(wall.getFrom().getX(), wall.getTo().getX());
            if (minX < result.getX()) {
                result.setX(minX);
            }

            int minY = Math.min(wall.getFrom().getY(), wall.getTo().getY());
            if (minY < result.getY()) {
                result.setY(minY);
            }
        }

        return result;
    }

    private Coordinates getMaxCoords(List<Wall> walls) {
        Coordinates result = new Coordinates("0,0");

        for (Wall wall : walls) {
            int maxX = Math.max(wall.getFrom().getX(), wall.getTo().getX());
            if (maxX > result.getX()) {
                result.setX(maxX);
            }

            int maxY = Math.max(wall.getFrom().getY(), wall.getTo().getY());
            if (maxY > result.getY()) {
                result.setY(maxY);
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
