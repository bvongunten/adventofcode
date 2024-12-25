package ch.nostromo.adventofcode.year2024.day13;

import ch.nostromo.adventofcode.BasePuzzle;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * --- Day 13: Claw Contraption ---
 * Next up: the lobby of a resort on a tropical island. The Historians take a moment to admire the hexagonal floor tiles before spreading out.
 * <p>
 * Fortunately, it looks like the resort has a new arcade! Maybe you can win some prizes from the claw machines?
 * <p>
 * The claw machines here are a little unusual. Instead of a joystick or directional buttons to control the claw, these machines have two buttons labeled A and B. Worse, you can't just put in a token and play; it costs 3 tokens to push the A button and 1 token to push the B button.
 * <p>
 * With a little experimentation, you figure out that each machine's buttons are configured to move the claw a specific amount to the right (along the X axis) and a specific amount forward (along the Y axis) each time that button is pressed.
 * <p>
 * Each machine contains one prize; to win the prize, the claw must be positioned exactly above the prize on both the X and Y axes.
 * <p>
 * You wonder: what is the smallest number of tokens you would have to spend to win as many prizes as possible? You assemble a list of every machine's button behavior and prize location (your puzzle input). For example:
 * <p>
 * Button A: X+94, Y+34
 * Button B: X+22, Y+67
 * Prize: X=8400, Y=5400
 * <p>
 * Button A: X+26, Y+66
 * Button B: X+67, Y+21
 * Prize: X=12748, Y=12176
 * <p>
 * Button A: X+17, Y+86
 * Button B: X+84, Y+37
 * Prize: X=7870, Y=6450
 * <p>
 * Button A: X+69, Y+23
 * Button B: X+27, Y+71
 * Prize: X=18641, Y=10279
 * This list describes the button configuration and prize location of four different claw machines.
 * <p>
 * For now, consider just the first claw machine in the list:
 * <p>
 * Pushing the machine's A button would move the claw 94 units along the X axis and 34 units along the Y axis.
 * Pushing the B button would move the claw 22 units along the X axis and 67 units along the Y axis.
 * The prize is located at X=8400, Y=5400; this means that from the claw's initial position, it would need to move exactly 8400 units along the X axis and exactly 5400 units along the Y axis to be perfectly aligned with the prize in this machine.
 * The cheapest way to win the prize is by pushing the A button 80 times and the B button 40 times. This would line up the claw along the X axis (because 80*94 + 40*22 = 8400) and along the Y axis (because 80*34 + 40*67 = 5400). Doing this would cost 80*3 tokens for the A presses and 40*1 for the B presses, a total of 280 tokens.
 * <p>
 * For the second and fourth claw machines, there is no combination of A and B presses that will ever win a prize.
 * <p>
 * For the third claw machine, the cheapest way to win the prize is by pushing the A button 38 times and the B button 86 times. Doing this would cost a total of 200 tokens.
 * <p>
 * So, the most prizes you could possibly win is two; the minimum tokens you would have to spend to win all (two) prizes is 480.
 * <p>
 * You estimate that each button would need to be pressed no more than 100 times to win a prize. How else would someone be expected to play?
 * <p>
 * Figure out how to win as many prizes as possible. What is the fewest tokens you would have to spend to win all possible prizes?
 */
public class Part1 extends BasePuzzle {

    static final String EXPECTED_TEST_RESULT = "480";


    public String solvePuzzle(List<String> input) {

        List<ClawMachine> machines = parseMachines(input);

        int totalTokens = 0;

        for (ClawMachine machine : machines) {
            int cost = findMinimumCost(machine);
            if (cost != -1) { // If prize is winnable
                totalTokens += cost;
            }
        }

        return String.valueOf(totalTokens);
    }

    @Data
    @AllArgsConstructor
    class ClawMachine {
        int ax;
        int ay;
        int bx;
        int by;
        int prizeX;
        int prizeY;
    }


    int findMinimumCost(ClawMachine machine) {
        int minCost = Integer.MAX_VALUE;

        for (int aPresses = 0; aPresses <= 100; aPresses++) {
            for (int bPresses = 0; bPresses <= 100; bPresses++) {
                int totalX = aPresses * machine.ax + bPresses * machine.bx;
                int totalY = aPresses * machine.ay + bPresses * machine.by;

                if (totalX == machine.prizeX && totalY == machine.prizeY) {
                    int cost = aPresses * 3 + bPresses * 1;
                    minCost = Math.min(minCost, cost);
                }
            }
        }

        return minCost == Integer.MAX_VALUE ? -1 : minCost;
    }



    List<ClawMachine> parseMachines(List<String> input) {

        List<ClawMachine> machines = new ArrayList<>();

        Pattern buttonPattern = Pattern.compile("X\\+(-?\\d+), Y\\+(-?\\d+)");
        Pattern prizePattern = Pattern.compile("X=([0-9]+), Y=([0-9]+)");

        Matcher matcher;

        for (int i = 0; i < input.size(); i+= 4) {
            String strBtnA = input.get(i);
            String strBtnB = input.get(i+1);
            String strPrize = input.get(i+2);

            matcher = buttonPattern.matcher(strBtnA);
            matcher.find();

            int ax = Integer.parseInt(matcher.group(1));
            int ay = Integer.parseInt(matcher.group(2));

            matcher = buttonPattern.matcher(strBtnB);
            matcher.find();

            int bx = Integer.parseInt(matcher.group(1));
            int by = Integer.parseInt(matcher.group(2));

            matcher = prizePattern.matcher(strPrize);
            matcher.find();

            int prizeX = Integer.parseInt(matcher.group(1));
            int prizeY = Integer.parseInt(matcher.group(2));

            machines.add(new ClawMachine(ax, ay, bx, by, prizeX, prizeY));

        }


        return machines;
    }



    public Part1() {
        super(EXPECTED_TEST_RESULT);
    }


    public Part1(String expectedTestResult) {
        super(expectedTestResult);
    }


    public static void main(String... args) {
        new Part1().run();
    }
}