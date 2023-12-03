package ch.nostromo.adventofcode.year2022.day15;

import ch.nostromo.adventofcode.BasePuzzle;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * --- Day 15: Beacon Exclusion Zone ---
 * You feel the ground rumble again as the distress signal leads you to a large network of subterranean tunnels. You don't have time to search them all, but you don't need to: your pack contains a set of deployable sensors that you imagine were originally built to locate lost Elves.
 * <p>
 * The sensors aren't very powerful, but that's okay; your handheld device indicates that you're close enough to the source of the distress signal to use them. You pull the emergency sensor system out of your pack, hit the big button on top, and the sensors zoom off down the tunnels.
 * <p>
 * Once a sensor finds a spot it thinks will give it a good reading, it attaches itself to a hard surface and begins monitoring for the nearest signal source beacon. Sensors and beacons always exist at integer coordinates. Each sensor knows its own position and can determine the position of a beacon precisely; however, sensors can only lock on to the one beacon closest to the sensor as measured by the Manhattan distance. (There is never a tie where two beacons are the same distance to a sensor.)
 * <p>
 * It doesn't take long for the sensors to report back their positions and closest beacons (your puzzle input). For example:
 * <p>
 * Sensor at x=2, y=18: closest beacon is at x=-2, y=15
 * Sensor at x=9, y=16: closest beacon is at x=10, y=16
 * Sensor at x=13, y=2: closest beacon is at x=15, y=3
 * Sensor at x=12, y=14: closest beacon is at x=10, y=16
 * Sensor at x=10, y=20: closest beacon is at x=10, y=16
 * Sensor at x=14, y=17: closest beacon is at x=10, y=16
 * Sensor at x=8, y=7: closest beacon is at x=2, y=10
 * Sensor at x=2, y=0: closest beacon is at x=2, y=10
 * Sensor at x=0, y=11: closest beacon is at x=2, y=10
 * Sensor at x=20, y=14: closest beacon is at x=25, y=17
 * Sensor at x=17, y=20: closest beacon is at x=21, y=22
 * Sensor at x=16, y=7: closest beacon is at x=15, y=3
 * Sensor at x=14, y=3: closest beacon is at x=15, y=3
 * Sensor at x=20, y=1: closest beacon is at x=15, y=3
 * So, consider the sensor at 2,18; the closest beacon to it is at -2,15. For the sensor at 9,16, the closest beacon to it is at 10,16.
 * <p>
 * Drawing sensors as S and beacons as B, the above arrangement of sensors and beacons looks like this:
 * <p>
 * 1    1    2    2
 * 0    5    0    5    0    5
 * 0 ....S.......................
 * 1 ......................S.....
 * 2 ...............S............
 * 3 ................SB..........
 * 4 ............................
 * 5 ............................
 * 6 ............................
 * 7 ..........S.......S.........
 * 8 ............................
 * 9 ............................
 * 10 ....B.......................
 * 11 ..S.........................
 * 12 ............................
 * 13 ............................
 * 14 ..............S.......S.....
 * 15 B...........................
 * 16 ...........SB...............
 * 17 ................S..........B
 * 18 ....S.......................
 * 19 ............................
 * 20 ............S......S........
 * 21 ............................
 * 22 .......................B....
 * This isn't necessarily a comprehensive map of all beacons in the area, though. Because each sensor only identifies its closest beacon, if a sensor detects a beacon, you know there are no other beacons that close or closer to that sensor. There could still be beacons that just happen to not be the closest beacon to any sensor. Consider the sensor at 8,7:
 * <p>
 * 1    1    2    2
 * 0    5    0    5    0    5
 * -2 ..........#.................
 * -1 .........###................
 * 0 ....S...#####...............
 * 1 .......#######........S.....
 * 2 ......#########S............
 * 3 .....###########SB..........
 * 4 ....#############...........
 * 5 ...###############..........
 * 6 ..#################.........
 * 7 .#########S#######S#........
 * 8 ..#################.........
 * 9 ...###############..........
 * 10 ....B############...........
 * 11 ..S..###########............
 * 12 ......#########.............
 * 13 .......#######..............
 * 14 ........#####.S.......S.....
 * 15 B........###................
 * 16 ..........#SB...............
 * 17 ................S..........B
 * 18 ....S.......................
 * 19 ............................
 * 20 ............S......S........
 * 21 ............................
 * 22 .......................B....
 * This sensor's closest beacon is at 2,10, and so you know there are no beacons that close or closer (in any positions marked #).
 * <p>
 * None of the detected beacons seem to be producing the distress signal, so you'll need to work out where the distress beacon is by working out where it isn't. For now, keep things simple by counting the positions where a beacon cannot possibly be along just a single row.
 * <p>
 * So, suppose you have an arrangement of beacons and sensors like in the example above and, just in the row where y=10, you'd like to count the number of positions a beacon cannot possibly exist. The coverage from all sensors near that row looks like this:
 * <p>
 * 1    1    2    2
 * 0    5    0    5    0    5
 * 9 ...#########################...
 * 10 ..####B######################..
 * 11 .###S#############.###########.
 * In this example, in the row where y=10, there are 26 positions where a beacon cannot be present.
 * <p>
 * Consult the report from the sensors you just deployed. In the row where y=2000000, how many positions cannot contain a beacon?
 * <p>
 * --- Part Two ---
 * Your handheld device indicates that the distress signal is coming from a beacon nearby. The distress beacon is not detected by any sensor, but the distress beacon must have x and y coordinates each no lower than 0 and no larger than 4000000.
 * <p>
 * To isolate the distress beacon's signal, you need to determine its tuning frequency, which can be found by multiplying its x coordinate by 4000000 and then adding its y coordinate.
 * <p>
 * In the example above, the search space is smaller: instead, the x and y coordinates can each be at most 20. With this reduced search area, there is only a single position that could have a beacon: x=14, y=11. The tuning frequency for this distress beacon is 56000011.
 * <p>
 * Find the only possible position for the distress beacon. What is its tuning frequency?
 */
public class Part2 extends BasePuzzle {

    private static final String EXPECTED_TEST_RESULT = "56000011";


    public String solvePuzzle(List<String> input) {

        int maxCoordRange = 4000000;

        if (isTestRun()) {
            maxCoordRange = 20;
        }

        List<Coordinates> sensors = new ArrayList<>();
        for (String line : input) {
            sensors.add(getSensor(line));
        }

        Coordinates freeCoordinates = null;

        for (Coordinates sensor : sensors) {

            freeCoordinates = travelDiamondSide(sensors, maxCoordRange, sensor, -1, +1);
            if (freeCoordinates != null) {
                break;
            }

            freeCoordinates = travelDiamondSide(sensors, maxCoordRange, sensor, -1, -1);
            if (freeCoordinates != null) {
                break;
            }

            freeCoordinates = travelDiamondSide(sensors, maxCoordRange, sensor, +1, -1);
            if (freeCoordinates != null) {
                break;
            }

            freeCoordinates = travelDiamondSide(sensors, maxCoordRange, sensor, +1, +1);
            if (freeCoordinates != null) {
                break;
            }

        }

        return String.valueOf((long) freeCoordinates.getX() * 4000000L + (long) freeCoordinates.getY());

    }

    private Coordinates travelDiamondSide(List<Coordinates> sensors, int maxCoordRange, Coordinates sensor, int xMulti, int yMulti) {
        int x = sensor.getX() + (sensor.getDistanceToBeacon() + 1);
        int y = sensor.getY();

        for (int i = 0; i < sensor.getDistanceToBeacon() + 2; i++) {
            if (!isReachableByOtherSensors(sensors, sensor, x, y, maxCoordRange)) {
                return new Coordinates(x, y, null);
            }
            y += (1 * xMulti);
            x += (1 * yMulti);
        }
        return null;
    }


    boolean isReachableByOtherSensors(List<Coordinates> sensors, Coordinates currentSensor, int x, int y, int maxCoords) {
        for (Coordinates sensor : sensors) {
            if (!currentSensor.equals(sensor)) {
                if (sensor.isInReach(x, y)) {
                    return true;
                }
            }
        }

        if (x >= 0 && x <= maxCoords && y >= 0 && y <= maxCoords) {
            return false;
        }

        // Well it is out of range, though ;)
        return true;
    }


    private Coordinates getBeacon(String line) {
        int xPos = line.indexOf("x=", line.indexOf(":"));
        int yPos = line.indexOf("y=", xPos);
        return new Coordinates(Integer.valueOf(line.substring(xPos + 2, line.indexOf(",", xPos))), Integer.valueOf(line.substring(yPos + 2)), null);
    }

    private Coordinates getSensor(String line) {
        int xPos = line.indexOf("x=");
        int yPos = line.indexOf("y=");
        return new Coordinates(Integer.valueOf(line.substring(xPos + 2, line.indexOf(",", xPos))), Integer.valueOf(line.substring(yPos + 2, line.indexOf(":"))), getBeacon(line));
    }

    @Data
    public static class Coordinates {
        int x;
        int y;
        Coordinates closestBeacon;

        int distanceToBeacon = 0;


        public Coordinates(int x, int y, Coordinates closestBeacon) {
            this.x = x;
            this.y = y;

            this.closestBeacon = closestBeacon;

            if (closestBeacon != null) {
                this.distanceToBeacon = Math.abs(getX() - getClosestBeacon().getX()) + Math.abs(getY() - getClosestBeacon().getY());
            }
        }

        public boolean isInReach(int x, int y) {
            int distanceToCoords = Math.abs(getX() - x) + Math.abs(getY() - y);

            return this.distanceToBeacon >= distanceToCoords;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Coordinates that = (Coordinates) o;

            if (x != that.x) return false;
            return y == that.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
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
