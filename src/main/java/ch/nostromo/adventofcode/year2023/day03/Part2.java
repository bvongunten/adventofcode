package ch.nostromo.adventofcode.year2023.day03;

import ch.nostromo.adventofcode.BasePuzzle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * <p>
 * --- Part Two ---
 * The engineer finds the missing part and installs it in the engine! As the engine springs to life, you jump in the closest gondola, finally ready to ascend to the water source.
 * <p>
 * You don't seem to be going very fast, though. Maybe something is still wrong? Fortunately, the gondola has a phone labeled "help", so you pick it up and the engineer answers.
 * <p>
 * Before you can explain the situation, she suggests that you look out the window. There stands the engineer, holding a phone in one hand and waving with the other. You're going so slowly that you haven't even left the station. You exit the gondola.
 * <p>
 * The missing part wasn't the only issue - one of the gears in the engine is wrong. A gear is any * symbol that is adjacent to exactly two part numbers. Its gear ratio is the result of multiplying those two numbers together.
 * <p>
 * This time, you need to find the gear ratio of every gear and add them all up so that the engineer can figure out which gear needs to be replaced.
 * <p>
 * Consider the same engine schematic again:
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
 * In this schematic, there are two gears. The first is in the top left; it has part numbers 467 and 35, so its gear ratio is 16345. The second gear is in the lower right; its gear ratio is 451490. (The * adjacent to 617 is not a gear because it is only adjacent to one part number.) Adding up all of the gear ratios produces 467835.
 * <p>
 * What is the sum of all of the gear ratios in your engine schematic?
 * <p>
 * Your puzzle answer was 81709807.
 */
public class Part2 extends BasePuzzle {

    private static final String EXPECTED_TEST_RESULT = "467835";

    public String solvePuzzle(List<String> input) {
        long result = 0;

        Map<String, List<Integer>> gearPositions = new HashMap<>();

        for (int lineIdx = 0; lineIdx < input.size(); lineIdx++) {

            String currentNumber = "";

            for (int posIdx = 0; posIdx < input.get(lineIdx).length(); posIdx++) {
                if (Character.isDigit(input.get(lineIdx).charAt(posIdx))) {
                    currentNumber += input.get(lineIdx).charAt(posIdx);
                } else {
                    if (!currentNumber.isEmpty()) {
                        addToGearList(gearPositions, getGearPos(input, lineIdx, posIdx - currentNumber.length(), posIdx - 1), currentNumber);
                        currentNumber = "";
                    }
                }
            }

            if (!currentNumber.isEmpty()) {
                addToGearList(gearPositions, getGearPos(input, lineIdx, input.get(lineIdx).length() - currentNumber.length(), input.get(lineIdx).length() - 1), currentNumber);
            }

        }

        for (List<Integer> values : gearPositions.values()) {
            if (values.size() == 2) {
                result += (values.get(0) * values.get(1));
            }
        }


        return String.valueOf(result);
    }

    private void addToGearList(Map<String, List<Integer>> gearPositions, String gearPos, String currentNumber) {
        if (gearPos != null) {
            if (!gearPositions.containsKey(gearPos)) {
                gearPositions.put(gearPos, new ArrayList<>());
            }
            gearPositions.get(gearPos).add(Integer.valueOf(currentNumber));
        }
    }


    private String getGearPos(List<String> input, int numberLine, int numberFrom, int numberTo) {
        int startLine = Math.max(numberLine - 1, 0);
        int startPos = Math.max(numberFrom - 1, 0);
        int endLine = Math.min(numberLine + 1, input.size() - 1);
        int endPos = Math.min(numberTo + 1, input.get(numberLine).length() - 1);

        for (int lineIdx = startLine; lineIdx <= endLine; lineIdx++) {
            for (int posIdx = startPos; posIdx <= endPos; posIdx++) {
                if (!Character.isDigit(input.get(lineIdx).charAt(posIdx)) && input.get(lineIdx).charAt(posIdx) == '*') {
                    return lineIdx + ":" + posIdx;
                }
            }
        }

        return null;
    }

    public Part2() {
        super(EXPECTED_TEST_RESULT);
    }

    public static void main(String... args) {
        new Part2().run();
    }

}
