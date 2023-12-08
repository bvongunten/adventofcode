
package ch.nostromo.adventofcode.year2023.day08;

import ch.nostromo.adventofcode.BasePuzzle;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * --- Day 8: Haunted Wasteland ---
 * You're still riding a camel across Desert Island when you spot a sandstorm quickly approaching. When you turn to warn the Elf, she disappears before your eyes! To be fair, she had just finished warning you about ghosts a few minutes ago.
 * <p>
 * One of the camel's pouches is labeled "maps" - sure enough, it's full of documents (your puzzle input) about how to navigate the desert. At least, you're pretty sure that's what they are; one of the documents contains a list of left/right instructions, and the rest of the documents seem to describe some kind of network of labeled nodes.
 * <p>
 * It seems like you're meant to use the left/right instructions to navigate the network. Perhaps if you have the camel follow the same instructions, you can escape the haunted wasteland!
 * <p>
 * After examining the maps for a bit, two nodes stick out: AAA and ZZZ. You feel like AAA is where you are now, and you have to follow the left/right instructions until you reach ZZZ.
 * <p>
 * This format defines each node of the network individually. For example:
 * <p>
 * RL
 * <p>
 * AAA = (BBB, CCC)
 * BBB = (DDD, EEE)
 * CCC = (ZZZ, GGG)
 * DDD = (DDD, DDD)
 * EEE = (EEE, EEE)
 * GGG = (GGG, GGG)
 * ZZZ = (ZZZ, ZZZ)
 * Starting with AAA, you need to look up the next element based on the next left/right instruction in your input. In this example, start with AAA and go right (R) by choosing the right element of AAA, CCC. Then, L means to choose the left element of CCC, ZZZ. By following the left/right instructions, you reach ZZZ in 2 steps.
 * <p>
 * Of course, you might not find ZZZ right away. If you run out of left/right instructions, repeat the whole sequence of instructions as necessary: RL really means RLRLRLRLRLRLRLRL... and so on. For example, here is a situation that takes 6 steps to reach ZZZ:
 * <p>
 * LLR
 * <p>
 * AAA = (BBB, BBB)
 * BBB = (AAA, ZZZ)
 * ZZZ = (ZZZ, ZZZ)
 * Starting at AAA, follow the left/right instructions. How many steps are required to reach ZZZ?
 * <p>
 * Your puzzle answer was 16579.
 */
public class Part1 extends BasePuzzle {

    private static final String EXPECTED_TEST_RESULT = "6";


    public String solvePuzzle(List<String> input) {

        String commands = input.get(0);


        Map<String, Crossroad> crossroadMap = new HashMap<>();
        for (int i = 2; i < input.size(); i++) {
            Crossroad crossroad = new Crossroad(input.get(i));
            crossroadMap.put(crossroad.getId(), crossroad);

        }

        Crossroad currentCrossRoad = crossroadMap.get("AAA");
        int cmdIdx = 0;
        long result = 0;

        while (true) {
            result++;

            if (cmdIdx == commands.length()) {
                cmdIdx = 0;
            }

            if (commands.charAt(cmdIdx) == 'L') {
                currentCrossRoad = crossroadMap.get(currentCrossRoad.getLeftStr());
            } else {
                currentCrossRoad = crossroadMap.get(currentCrossRoad.getRightStr());
            }


            if (currentCrossRoad.getId().equals("ZZZ")) {
                break;
            }

            cmdIdx++;

        }

        return String.valueOf(result);
    }

    @Data
    public class Crossroad {

        String id;

        String leftStr;
        String rightStr;

        public Crossroad(String line) {
            this.id = line.split("=")[0].trim();
            this.leftStr = line.split("=")[1].substring(2, 5);
            this.rightStr = line.split("=")[1].substring(7, 10);
        }

    }


    public Part1() {
        super(EXPECTED_TEST_RESULT);
    }

    public static void main(String... args) {
        new Part1().run();
    }

}
