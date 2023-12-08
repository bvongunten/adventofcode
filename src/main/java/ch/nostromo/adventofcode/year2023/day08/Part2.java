
package ch.nostromo.adventofcode.year2023.day08;

import ch.nostromo.adventofcode.BasePuzzle;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * --- Part Two ---
 * The sandstorm is upon you and you aren't any closer to escaping the wasteland. You had the camel follow the instructions, but you've barely left your starting position. It's going to take significantly more steps to escape!
 * <p>
 * What if the map isn't for people - what if the map is for ghosts? Are ghosts even bound by the laws of spacetime? Only one way to find out.
 * <p>
 * After examining the maps a bit longer, your attention is drawn to a curious fact: the number of nodes with names ending in A is equal to the number ending in Z! If you were a ghost, you'd probably just start at every node that ends with A and follow all of the paths at the same time until they all simultaneously end up at nodes that end with Z.
 * <p>
 * For example:
 * <p>
 * LR
 * <p>
 * 11A = (11B, XXX)
 * 11B = (XXX, 11Z)
 * 11Z = (11B, XXX)
 * 22A = (22B, XXX)
 * 22B = (22C, 22C)
 * 22C = (22Z, 22Z)
 * 22Z = (22B, 22B)
 * XXX = (XXX, XXX)
 * Here, there are two starting nodes, 11A and 22A (because they both end with A). As you follow each left/right instruction, use that instruction to simultaneously navigate away from both nodes you're currently on. Repeat this process until all of the nodes you're currently on end with Z. (If only some of the nodes you're on end with Z, they act like any other node and you continue as normal.) In this example, you would proceed as follows:
 * <p>
 * Step 0: You are at 11A and 22A.
 * Step 1: You choose all of the left paths, leading you to 11B and 22B.
 * Step 2: You choose all of the right paths, leading you to 11Z and 22C.
 * Step 3: You choose all of the left paths, leading you to 11B and 22Z.
 * Step 4: You choose all of the right paths, leading you to 11Z and 22B.
 * Step 5: You choose all of the left paths, leading you to 11B and 22C.
 * Step 6: You choose all of the right paths, leading you to 11Z and 22Z.
 * So, in this example, you end up entirely on nodes that end in Z after 6 steps.
 * <p>
 * Simultaneously start on every node that ends with A. How many steps does it take before you're only on nodes that end with Z?
 * <p>
 * Your puzzle answer was 12927600769609.
 */
public class Part2 extends BasePuzzle {

    private static final String EXPECTED_TEST_RESULT = "6";


    public String solvePuzzle(List<String> input) {

        String commands = input.get(0);

        Map<String, Crossroad> crossroadMap = new HashMap<>();
        for (int i = 2; i < input.size(); i++) {
            Crossroad crossroad = new Crossroad(input.get(i));
            crossroadMap.put(crossroad.getId(), crossroad);

        }

        List<Long> results = new ArrayList<>();

        for (String key : crossroadMap.keySet()) {
            if (key.endsWith("A")) {
                Crossroad currentCrossRoad = crossroadMap.get(key);
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

                    if (currentCrossRoad.getId().endsWith("Z")) {
                        results.add(result);
                        break;

                    }

                    cmdIdx++;

                }

            }
        }


        // Too many spoilers in the air today, lcm ftw :)

        return String.valueOf(lcm(results));
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

    // Google, give me a java lcm algorithm for list of numbers :)
    static long lcm(List<Long> numbers) {
        return numbers.stream().reduce(1L, (x, y) -> (x * y) / gcd(x, y));
    }

    static long gcd(long a, long b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }


    public Part2() {
        super(EXPECTED_TEST_RESULT);
    }

    public static void main(String... args) {
        new Part2().run();
    }

}
