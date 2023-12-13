package ch.nostromo.adventofcode.year2023.day13;

import ch.nostromo.adventofcode.BasePuzzle;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class Part2 extends BasePuzzle {

    private static final String EXPECTED_TEST_RESULT = "400";

    public String solvePuzzle(List<String> input) {
        long result = 0;

        List<Block> blocks = new ArrayList<>();

        List<String> blockLines = new ArrayList<>();
        for (String line : input) {
            if (line.isEmpty()) {
                blocks.add(new Block(blockLines));
                blockLines = new ArrayList<>();
            } else {
                blockLines.add(line);
            }
        }
        if (blockLines.size() > 0) {
            blocks.add(new Block(blockLines));
        }


        for (Block block : blocks) {
            int horizontalResult = block.getFirstHorizontalMirror();
            int vertialResult = block.getFirstVerticalMirror();

            if (horizontalResult >= 0) {
                result += horizontalResult * 100;
            }

            if (vertialResult >= 0) {
                result += vertialResult;
            }

        }

        return String.valueOf(result);
    }

    @Data
    @AllArgsConstructor
    public class Block {
        List<String> lines;

        public int getFirstHorizontalMirror() {
            int originalRefleciton = findReflection(lines);
            int differentReflection = findDifferentReflection(lines, originalRefleciton);

            return differentReflection;
        }

        public int getFirstVerticalMirror() {
            int originalRefleciton = findReflection(convertColsToRows());
            int differentReflection = findDifferentReflection(convertColsToRows(), originalRefleciton);


            return differentReflection;
        }

        private List<String> convertColsToRows() {
            List<String> verticalLines = new ArrayList<>();

            for (int i = 0; i < lines.get(0).length(); i++) {
                String line = "";
                for (int y = 0; y< lines.size();y++) {
                    line += lines.get(y).charAt(i);
                }
                verticalLines.add(line);
            }

            return verticalLines;
        }


        public int findReflection(List<String> lines) {
            String prevLine = "";
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).equals(prevLine)) {
                    if (solveSides(lines, i, false)) {
                        return i;
                    }
                    // continue to next line as mirror does not hold up
                }
                prevLine = lines.get(i);
            }

            return -1;
        }

        public int findDifferentReflection(List<String> lines, int original) {
            String prevLine = "";
            for (int i = 0; i < lines.size(); i++) {
                String currentLine = lines.get(i);
                if  (isPatcheable(prevLine, currentLine)) {
                    List<String> patchedList = new ArrayList<>(lines);
                    patchedList.set(i, prevLine);
                    if (solveSides(patchedList, i, false)) {
                        return i;
                    }

                } else if (lines.get(i).equals(prevLine)) {
                    if (solveSides(lines, i, true) && i != original) {
                        return i;
                    }
                    // continue to next line as mirror does not hold up
                }
                prevLine = lines.get(i);
            }

            return -1;
        }

        public boolean isPatcheable(String previousLine, String currentLine) {
            int diffs = 0;
            for (int i = 0; i < previousLine.length(); i++) {
                if (previousLine.charAt(i) != currentLine.charAt(i)) {
                    diffs ++;
                }
            }
            return diffs == 1;
        }


        private boolean solveSides(List<String> lines, int posIdx, boolean smudgeAvailable) {

            int count = 0;
            while (true) {
                int leftPos = posIdx -(count+1);
                int rightPos = posIdx + count;

                while (leftPos >= 0 && rightPos < lines.size()) {

                    String left = lines.get(posIdx - (count + 1));
                    String right = lines.get(posIdx + count);
                    if (!left.equals(right)) {

                        if (smudgeAvailable && isPatcheable(left, right) ) {
                            smudgeAvailable = false;
                        } else {
                            return false;
                        }


                    }

                    count ++;

                    leftPos = posIdx -(count+1);
                    rightPos = posIdx + count;

                }

                if (leftPos == -1 || rightPos == lines.size()) {
                    return true;
                } else {
                    return false;
                }
            }

        }

    }


    public Part2() {
        super(EXPECTED_TEST_RESULT);
    }

    public static void main(String... args) {
        new Part2().run();
    }

}
