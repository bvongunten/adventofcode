package ch.nostromo.adventofcode.year2024.day12;

import ch.nostromo.adventofcode.BasePuzzle;
import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * --- Day 12: Garden Groups ---
 * Why not search for the Chief Historian near the gardener and his massive farm? There's plenty of food, so The Historians grab something to eat while they search.
 * <p>
 * You're about to settle near a complex arrangement of garden plots when some Elves ask if you can lend a hand. They'd like to set up fences around each region of garden plots, but they can't figure out how much fence they need to order or how much it will cost. They hand you a map (your puzzle input) of the garden plots.
 * <p>
 * Each garden plot grows only a single type of plant and is indicated by a single letter on your map. When multiple garden plots are growing the same type of plant and are touching (horizontally or vertically), they form a region. For example:
 * <p>
 * AAAA
 * BBCD
 * BBCC
 * EEEC
 * This 4x4 arrangement includes garden plots growing five different types of plants (labeled A, B, C, D, and E), each grouped into their own region.
 * <p>
 * In order to accurately calculate the cost of the fence around a single region, you need to know that region's area and perimeter.
 * <p>
 * The area of a region is simply the number of garden plots the region contains. The above map's type A, B, and C plants are each in a region of area 4. The type E plants are in a region of area 3; the type D plants are in a region of area 1.
 * <p>
 * Each garden plot is a square and so has four sides. The perimeter of a region is the number of sides of garden plots in the region that do not touch another garden plot in the same region. The type A and C plants are each in a region with perimeter 10. The type B and E plants are each in a region with perimeter 8. The lone D plot forms its own region with perimeter 4.
 * <p>
 * Visually indicating the sides of plots in each region that contribute to the perimeter using - and |, the above map's regions' perimeters are measured as follows:
 * <p>
 * +-+-+-+-+
 * |A A A A|
 * +-+-+-+-+     +-+
 * |D|
 * +-+-+   +-+   +-+
 * |B B|   |C|
 * +   +   + +-+
 * |B B|   |C C|
 * +-+-+   +-+ +
 * |C|
 * +-+-+-+   +-+
 * |E E E|
 * +-+-+-+
 * Plants of the same type can appear in multiple separate regions, and regions can even appear within other regions. For example:
 * <p>
 * OOOOO
 * OXOXO
 * OOOOO
 * OXOXO
 * OOOOO
 * The above map contains five regions, one containing all of the O garden plots, and the other four each containing a single X plot.
 * <p>
 * The four X regions each have area 1 and perimeter 4. The region containing 21 type O plants is more complicated; in addition to its outer edge contributing a perimeter of 20, its boundary with each X region contributes an additional 4 to its perimeter, for a total perimeter of 36.
 * <p>
 * Due to "modern" business practices, the price of fence required for a region is found by multiplying that region's area by its perimeter. The total price of fencing all regions on a map is found by adding together the price of fence for every region on the map.
 * <p>
 * In the first example, region A has price 4 * 10 = 40, region B has price 4 * 8 = 32, region C has price 4 * 10 = 40, region D has price 1 * 4 = 4, and region E has price 3 * 8 = 24. So, the total price for the first example is 140.
 * <p>
 * In the second example, the region with all of the O plants has price 21 * 36 = 756, and each of the four smaller X regions has price 1 * 4 = 4, for a total price of 772 (756 + 4 + 4 + 4 + 4).
 * <p>
 * Here's a larger example:
 * <p>
 * RRRRIICCFF
 * RRRRIICCCF
 * VVRRRCCFFF
 * VVRCCCJFFF
 * VVVVCJJCFE
 * VVIVCCJJEE
 * VVIIICJJEE
 * MIIIIIJJEE
 * MIIISIJEEE
 * MMMISSJEEE
 * It contains:
 * <p>
 * A region of R plants with price 12 * 18 = 216.
 * A region of I plants with price 4 * 8 = 32.
 * A region of C plants with price 14 * 28 = 392.
 * A region of F plants with price 10 * 18 = 180.
 * A region of V plants with price 13 * 20 = 260.
 * A region of J plants with price 11 * 20 = 220.
 * A region of C plants with price 1 * 4 = 4.
 * A region of E plants with price 13 * 18 = 234.
 * A region of I plants with price 14 * 22 = 308.
 * A region of M plants with price 5 * 12 = 60.
 * A region of S plants with price 3 * 8 = 24.
 * So, it has a total price of 1930.
 * <p>
 * What is the total price of fencing all regions on your map?
 * <p>
 * Your puzzle answer was 1319878.
 * <p>
 * --- Part Two ---
 * Fortunately, the Elves are trying to order so much fence that they qualify for a bulk discount!
 * <p>
 * Under the bulk discount, instead of using the perimeter to calculate the price, you need to use the number of sides each region has. Each straight section of fence counts as a side, regardless of how long it is.
 * <p>
 * Consider this example again:
 * <p>
 * AAAA
 * BBCD
 * BBCC
 * EEEC
 * The region containing type A plants has 4 sides, as does each of the regions containing plants of type B, D, and E. However, the more complex region containing the plants of type C has 8 sides!
 * <p>
 * Using the new method of calculating the per-region price by multiplying the region's area by its number of sides, regions A through E have prices 16, 16, 32, 4, and 12, respectively, for a total price of 80.
 * <p>
 * The second example above (full of type X and O plants) would have a total price of 436.
 * <p>
 * Here's a map that includes an E-shaped region full of type E plants:
 * <p>
 * EEEEE
 * EXXXX
 * EEEEE
 * EXXXX
 * EEEEE
 * The E-shaped region has an area of 17 and 12 sides for a price of 204. Including the two regions full of type X plants, this map has a total price of 236.
 * <p>
 * This map has a total price of 368:
 * <p>
 * AAAAAA
 * AAABBA
 * AAABBA
 * ABBAAA
 * ABBAAA
 * AAAAAA
 * It includes two regions full of type B plants (each with 4 sides) and a single region full of type A plants (with 4 sides on the outside and 8 more sides on the inside, a total of 12 sides). Be especially careful when counting the fence around regions like the one full of type A plants; in particular, each section of fence has an in-side and an out-side, so the fence does not connect across the middle of the region (where the two B regions touch diagonally). (The Elves would have used the Möbius Fencing Company instead, but their contract terms were too one-sided.)
 * <p>
 * The larger example from before now has the following updated prices:
 * <p>
 * A region of R plants with price 12 * 10 = 120.
 * A region of I plants with price 4 * 4 = 16.
 * A region of C plants with price 14 * 22 = 308.
 * A region of F plants with price 10 * 12 = 120.
 * A region of V plants with price 13 * 10 = 130.
 * A region of J plants with price 11 * 12 = 132.
 * A region of C plants with price 1 * 4 = 4.
 * A region of E plants with price 13 * 8 = 104.
 * A region of I plants with price 14 * 16 = 224.
 * A region of M plants with price 5 * 6 = 30.
 * A region of S plants with price 3 * 6 = 18.
 * Adding these together produces its new total price of 1206.
 * <p>
 * What is the new total price of fencing all regions on your map?
 * <p>
 * Your puzzle answer was 784982.
 */
public class Part2 extends BasePuzzle {

    private static final String EXPECTED_TEST_RESULT = "1206";


    public String solvePuzzle(List<String> input) {

        String[][] map = new String[input.size()][input.get(0).length()];
        for (int y = 0; y < input.size(); y++) {
            for (int x = 0; x < input.get(y).length(); x++) {
                map[y][x] = input.get(y).substring(x, x + 1);
            }
        }

        return String.valueOf(findFences(map));
    }


    @Data
    class Tile {
        int x;
        int y;
        boolean northFence = false;
        boolean southFence = false;
        boolean eastFence = false;
        boolean westFence = false;
    }

    long findFences(String[][] map) {
        long result = 0;

        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (!map[y][x].equals("$")) {
                    List<Tile> tiles = new ArrayList<>();

                    parseRegion(map, y, x, map[y][x], tiles);
                    consumeMap(map);

                    result += (countFences(tiles, map.length, map[0].length) * tiles.size());

                }
            }
        }


        return result;

    }

    long countFences(List<Tile> allTiles, int height, int width) {
        long result = 0;


        // North / south fences
        for (int y = 0; y < height; y++) {
            List<Tile> tiles = getTilesByYCoord(allTiles, y);

            int lastNorthX = Integer.MIN_VALUE;
            int lastSouthX = Integer.MIN_VALUE;
            for (Tile tile : tiles) {

                if (tile.northFence) {
                    // continue current fence
                    if (tile.x > lastNorthX + 1) {
                        result++;
                    }

                    lastNorthX = tile.x;

                }

                if (tile.southFence) {
                    // continue current fence
                    if (tile.x > lastSouthX + 1) {
                        result++;
                    }

                    lastSouthX = tile.x;

                }
            }
        }

        // east / west fences
        for (int x = 0; x < width; x++) {
            List<Tile> tiles = getTilesByXCoord(allTiles, x);

            int lastWestY = Integer.MIN_VALUE;
            int lastEastY = Integer.MIN_VALUE;
            for (Tile tile : tiles) {

                if (tile.westFence) {
                    // continue current fence
                    if (tile.y > lastWestY + 1) {
                        result++;
                    }

                    lastWestY = tile.y;
                }

                if (tile.eastFence) {
                    // continue current fence
                    if (tile.y > lastEastY + 1) {
                        result++;
                    }

                    lastEastY = tile.y;
                }
            }
        }

        return result;
    }


    List<Tile> getTilesByYCoord(List<Tile> tiles, int y) {
        List<Tile> result = new ArrayList<>();

        for (Tile tile : tiles) {
            if (tile.y == y) {
                result.add(tile);
            }
        }

        result.sort(Comparator.comparingInt(o -> o.x));

        return result;

    }


    List<Tile> getTilesByXCoord(List<Tile> tiles, int x) {
        List<Tile> result = new ArrayList<>();

        for (Tile tile : tiles) {
            if (tile.x == x) {
                result.add(tile);
            }
        }

        result.sort(Comparator.comparingInt(o -> o.y));

        return result;

    }

    void consumeMap(String[][] map) {

        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x].equals(".")) {
                    map[y][x] = "$";
                }
            }
        }

    }


    void parseRegion(String[][] map, int y, int x, String flowerType, List<Tile> tiles) {

        Tile visitedTile = new Tile();
        visitedTile.x = x;
        visitedTile.y = y;

        tiles.add(visitedTile);

        map[y][x] = ".";

        // North
        if (y > 0) {
            String tile = map[y - 1][x];
            if (tile.equals(flowerType)) {
                parseRegion(map, y - 1, x, flowerType, tiles);
            } else if (!tile.equals(".")) {
                visitedTile.northFence = true;
            }
        } else {
            visitedTile.northFence = true;
        }

        // South
        if (y < map.length - 1) {
            String tile = map[y + 1][x];
            if (tile.equals(flowerType)) {
                parseRegion(map, y + 1, x, flowerType, tiles);
            } else if (!tile.equals(".")) {
                visitedTile.southFence = true;
            }
        } else {
            visitedTile.southFence = true;
        }

        // West
        if (x > 0) {
            String tile = map[y][x - 1];
            if (tile.equals(flowerType)) {
                parseRegion(map, y, x - 1, flowerType, tiles);
            } else if (!tile.equals(".")) {
                visitedTile.westFence = true;
            }
        } else {
            visitedTile.westFence = true;
        }

        // EAST
        if (x < map[y].length - 1) {
            String targetTile = map[y][x + 1];
            if (targetTile.equals(flowerType)) {
                parseRegion(map, y, x + 1, flowerType, tiles);
            } else if (!targetTile.equals(".")) {
                visitedTile.eastFence = true;
            }
        } else {
            visitedTile.eastFence = true;
        }


    }

    public Part2() {
        super(EXPECTED_TEST_RESULT);
    }

    public static void main(String... args) {
        new Part2().run();
    }
}