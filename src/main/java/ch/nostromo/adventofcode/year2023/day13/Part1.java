package ch.nostromo.adventofcode.year2023.day13;

import ch.nostromo.adventofcode.BasePuzzle;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class Part1 extends BasePuzzle {

    private static final String EXPECTED_TEST_RESULT = "405";

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
            return findReflection(lines);
        }

        public int getFirstVerticalMirror() {
            return findReflection(convertColsToRows());
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
                    if (solveSides(lines, i)) {
                        return i;
                    }
                    // continue to next line as mirror does not hold up
                }
                prevLine = lines.get(i);
            }

            return -1;
        }


        private boolean solveSides(List<String> lines, int posIdx) {

            int count = 0;
            while (true) {
                int leftPos = posIdx -(count+1);
                int rightPos = posIdx + count;

                while (leftPos >= 0 && rightPos < lines.size()) {

                    String left = lines.get(posIdx - (count + 1));
                    String right = lines.get(posIdx + count);
                    if (!left.equals(right)) {
                        return false;
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



    public Part1() {
        super(EXPECTED_TEST_RESULT);
    }

    public static void main(String... args) {
        new Part1().run();
    }

}
