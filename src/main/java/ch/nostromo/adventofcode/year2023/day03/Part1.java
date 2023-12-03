package ch.nostromo.adventofcode.year2023.day03;

import ch.nostromo.adventofcode.BasePuzzle;

import java.util.List;

/**
 * --- Day 3: Gear Ratios ---
 * You and the Elf eventually reach a gondola lift station; he says the gondola lift will take you up to the water source, but this is as far as he can bring you. You go inside.
 * <p>
 * It doesn't take long to find the gondolas, but there seems to be a problem: they're not moving.
 * <p>
 * "Aaah!"
 * <p>
 * You turn around to see a slightly-greasy Elf with a wrench and a look of surprise. "Sorry, I wasn't expecting anyone! The gondola lift isn't working right now; it'll still be a while before I can fix it." You offer to help.
 * <p>
 * The engineer explains that an engine part seems to be missing from the engine, but nobody can figure out which one. If you can add up all the part numbers in the engine schematic, it should be easy to work out which part is missing.
 * <p>
 * The engine schematic (your puzzle input) consists of a visual representation of the engine. There are lots of numbers and symbols you don't really understand, but apparently any number adjacent to a symbol, even diagonally, is a "part number" and should be included in your sum. (Periods (.) do not count as a symbol.)
 * <p>
 * Here is an example engine schematic:
 * <p>
 * 467..114..
 * ...*......
 * ..35..633.
 * ......#...
 * 617*......
 * .....+.58.
 * ..592.....
 * ......755.
 * ...$.*....
 * .664.598..
 * In this schematic, two numbers are not part numbers because they are not adjacent to a symbol: 114 (top right) and 58 (middle right). Every other number is adjacent to a symbol and so is a part number; their sum is 4361.
 * <p>
 * Of course, the actual engine schematic is much larger. What is the sum of all of the part numbers in the engine schematic?
 * <p>
 * Your puzzle answer was 538046.
 */
public class Part1 extends BasePuzzle {

    private static final String EXPECTED_TEST_RESULT = "4361";

    public String solvePuzzle(List<String> input) {
        int result = 0;

        for (int lineIdx = 0; lineIdx < input.size(); lineIdx++) {
            String currentNumber = "";

            for (int posIdx = 0; posIdx < input.get(lineIdx).length(); posIdx++) {
                if (Character.isDigit(input.get(lineIdx).charAt(posIdx))) {
                    currentNumber += input.get(lineIdx).charAt(posIdx);
                } else {
                    if (!currentNumber.isEmpty()) {
                        if (isMarked(input, lineIdx, posIdx - currentNumber.length(), posIdx - 1)) {
                            result += Integer.valueOf(currentNumber);
                        }
                        currentNumber = "";
                    }
                }
            }

            if (!currentNumber.isEmpty()) {
                if (isMarked(input, lineIdx, input.get(lineIdx).length() - currentNumber.length(), input.get(lineIdx).length() - 1)) {
                    result += Integer.valueOf(currentNumber);
                }
            }
        }


        return String.valueOf(result);
    }

    private boolean isMarked(List<String> input, int numberLine, int numberFrom, int numberTo) {
        int startLine = Math.max(numberLine - 1, 0);
        int startPos = Math.max(numberFrom - 1, 0);
        int endLine = Math.min(numberLine + 1, input.size() - 1);
        int endPos = Math.min(numberTo + 1, input.get(numberLine).length() - 1);

        for (int lineIdx = startLine; lineIdx <= endLine; lineIdx++) {
            for (int posIdx = startPos; posIdx <= endPos; posIdx++) {
                if (!Character.isDigit(input.get(lineIdx).charAt(posIdx)) && input.get(lineIdx).charAt(posIdx) != '.') {
                    return true;
                }
            }
        }

        return false;
    }

    public Part1() {
        super(EXPECTED_TEST_RESULT);
    }

    public static void main(String... args) {
        new Part1().run();
    }

}
