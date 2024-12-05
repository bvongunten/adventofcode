package ch.nostromo.adventofcode.year2024.day04;

import ch.nostromo.adventofcode.BasePuzzle;

import java.util.List;

/**
 --- Day 4: Ceres Search ---
 "Looks like the Chief's not here. Next!" One of The Historians pulls out a device and pushes the only button on it. After a brief flash, you recognize the interior of the Ceres monitoring station!

 As the search for the Chief continues, a small Elf who lives on the station tugs on your shirt; she'd like to know if you could help her with her word search (your puzzle input). She only has to find one word: XMAS.

 This word search allows words to be horizontal, vertical, diagonal, written backwards, or even overlapping other words. It's a little unusual, though, as you don't merely need to find one instance of XMAS - you need to find all of them. Here are a few ways XMAS might appear, where irrelevant characters have been replaced with .:


 ..X...
 .SAMX.
 .A..A.
 XMAS.S
 .X....
 The actual word search will be full of letters instead. For example:

 MMMSXXMASM
 MSAMXMSMSA
 AMXSXMAAMM
 MSAMASMSMX
 XMASAMXAMM
 XXAMMXXAMA
 SMSMSASXSS
 SAXAMASAAA
 MAMMMXMMMM
 MXMXAXMASX
 In this word search, XMAS occurs a total of 18 times; here's the same word search again, but where letters not involved in any XMAS have been replaced with .:

 ....XXMAS.
 .SAMXMS...
 ...S..A...
 ..A.A.MS.X
 XMASAMX.MM
 X.....XA.A
 S.S.S.S.SS
 .A.A.A.A.A
 ..M.M.M.MM
 .X.X.XMASX
 Take a look at the little Elf's word search. How many times does XMAS appear?

 Your puzzle answer was 2583.
 */
public class Part1 extends BasePuzzle {

    private static final String WENN_ICH_GROSS_BIN_WILL_ICH_SEIN_WIE_OSI_UND_ROLE = "18";

    private static String XMAS = "XMAS";

    private static int[][] DIRECTIONS = {
            {0, 1},
            {1, 0},
            {1, 1},
            {1, -1},
            {0, -1},
            {-1, 0},
            {-1, -1},
            {-1, 1}
    };

    public String solvePuzzle(List<String> input) {
        char[][] grid = new char[input.size()][input.get(0).length()];

        for (int i = 0; i < input.size(); i++) {
            for (int n = 0; n < input.get(i).length(); n++) {
                grid[i][n] = input.get(i).charAt(n);
            }
        }

        return String.valueOf(countOccurrences(grid));
    }


    private int countOccurrences(char[][] grid) {
        int result = 0;

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {

                // Find possible strating point
                if (grid[row][col] == XMAS.charAt(0)) {
                    for (int[] direction : DIRECTIONS) {
                        int directionRow = direction[0];
                        int directionCol = direction[1];

                        int tempRow = row;
                        int tempCol = col;

                        int matchIndex = 0;

                        while (matchIndex < XMAS.length() && tempRow >= 0 && tempRow < grid.length && tempCol >= 0 && tempCol < grid[0].length && grid[tempRow][tempCol] == XMAS.charAt(matchIndex)) {
                            matchIndex++;
                            tempRow += directionRow;
                            tempCol += directionCol;
                        }

                        if (matchIndex == XMAS.length()) {
                            result++;
                        }
                    }
                }
            }
        }

        return result;
    }


    public Part1() {
        super(WENN_ICH_GROSS_BIN_WILL_ICH_SEIN_WIE_OSI_UND_ROLE);
    }

    public static void main(String... args) {
        new Part1().run();
    }

}
