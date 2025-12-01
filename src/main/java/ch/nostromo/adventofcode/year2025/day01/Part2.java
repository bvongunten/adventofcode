package ch.nostromo.adventofcode.year2025.day01;

import ch.nostromo.adventofcode.BasePuzzle;

import java.util.List;

/**
 * --- Day 1: Secret Entrance ---
 * The Elves have good news and bad news.
 * <p>
 * The good news is that they've discovered project management! This has given them the tools they need to prevent their usual Christmas emergency. For example, they now know that the North Pole decorations need to be finished soon so that other critical tasks can start on time.
 * <p>
 * The bad news is that they've realized they have a different emergency: according to their resource planning, none of them have any time left to decorate the North Pole!
 * <p>
 * To save Christmas, the Elves need you to finish decorating the North Pole by December 12th.
 * <p>
 * Collect stars by solving puzzles. Two puzzles will be made available on each day; the second puzzle is unlocked when you complete the first. Each puzzle grants one star. Good luck!
 * <p>
 * You arrive at the secret entrance to the North Pole base ready to start decorating. Unfortunately, the password seems to have been changed, so you can't get in. A document taped to the wall helpfully explains:
 * <p>
 * "Due to new security protocols, the password is locked in the safe below. Please see the attached document for the new combination."
 * <p>
 * The safe has a dial with only an arrow on it; around the dial are the numbers 0 through 99 in order. As you turn the dial, it makes a small click noise as it reaches each number.
 * <p>
 * The attached document (your puzzle input) contains a sequence of rotations, one per line, which tell you how to open the safe. A rotation starts with an L or R which indicates whether the rotation should be to the left (toward lower numbers) or to the right (toward higher numbers). Then, the rotation has a distance value which indicates how many clicks the dial should be rotated in that direction.
 * <p>
 * So, if the dial were pointing at 11, a rotation of R8 would cause the dial to point at 19. After that, a rotation of L19 would cause it to point at 0.
 * <p>
 * Because the dial is a circle, turning the dial left from 0 one click makes it point at 99. Similarly, turning the dial right from 99 one click makes it point at 0.
 * <p>
 * So, if the dial were pointing at 5, a rotation of L10 would cause it to point at 95. After that, a rotation of R5 could cause it to point at 0.
 * <p>
 * The dial starts by pointing at 50.
 * <p>
 * You could follow the instructions, but your recent required official North Pole secret entrance security training seminar taught you that the safe is actually a decoy. The actual password is the number of times the dial is left pointing at 0 after any rotation in the sequence.
 * <p>
 * For example, suppose the attached document contained the following rotations:
 * <p>
 * L68
 * L30
 * R48
 * L5
 * R60
 * L55
 * L1
 * L99
 * R14
 * L82
 * Following these rotations would cause the dial to move as follows:
 * <p>
 * The dial starts by pointing at 50.
 * The dial is rotated L68 to point at 82.
 * The dial is rotated L30 to point at 52.
 * The dial is rotated R48 to point at 0.
 * The dial is rotated L5 to point at 95.
 * The dial is rotated R60 to point at 55.
 * The dial is rotated L55 to point at 0.
 * The dial is rotated L1 to point at 99.
 * The dial is rotated L99 to point at 0.
 * The dial is rotated R14 to point at 14.
 * The dial is rotated L82 to point at 32.
 * Because the dial points at 0 a total of three times during this process, the password in this example is 3.
 * <p>
 * Analyze the rotations in your attached document. What's the actual password to open the door?
 * Your puzzle answer was 962.
 * <p>
 * The first half of this puzzle is complete! It provides one gold star: *
 * <p>
 * --- Part Two ---
 * You're sure that's the right password, but the door won't open. You knock, but nobody answers. You build a snowman while you think.
 * <p>
 * As you're rolling the snowballs for your snowman, you find another security document that must have fallen into the snow:
 * <p>
 * "Due to newer security protocols, please use password method 0x434C49434B until further notice."
 * <p>
 * You remember from the training seminar that "method 0x434C49434B" means you're actually supposed to count the number of times any click causes the dial to point at 0, regardless of whether it happens during a rotation or at the end of one.
 * <p>
 * Following the same rotations as in the above example, the dial points at zero a few extra times during its rotations:
 * <p>
 * The dial starts by pointing at 50.
 * The dial is rotated L68 to point at 82; during this rotation, it points at 0 once.
 * The dial is rotated L30 to point at 52.
 * The dial is rotated R48 to point at 0.
 * The dial is rotated L5 to point at 95.
 * The dial is rotated R60 to point at 55; during this rotation, it points at 0 once.
 * The dial is rotated L55 to point at 0.
 * The dial is rotated L1 to point at 99.
 * The dial is rotated L99 to point at 0.
 * The dial is rotated R14 to point at 14.
 * The dial is rotated L82 to point at 32; during this rotation, it points at 0 once.
 * In this example, the dial points at 0 three times at the end of a rotation, plus three more times during a rotation. So, in this example, the new password would be 6.
 * <p>
 * Be careful: if the dial were pointing at 50, a single rotation like R1000 would cause the dial to point at 0 ten times before returning back to 50!
 * <p>
 * Using password method 0x434C49434B, what is the password to open the door?
 */
public class Part2 extends BasePuzzle {

    private static final String EXPECTED_TEST_RESULT = "6";

    public String solvePuzzle(List<String> input) {
        int result = 0;

        int currentPosition = 50;
        for (String line : input) {

            int distance = Integer.parseInt(line.substring(1));

            switch (line.substring(0, 1).toUpperCase()) {
                case "L": {

                    for (int i = 1; i <= distance; i++) {
                        currentPosition--;
                        if (currentPosition == 0) {
                            result++;
                        }

                        if (currentPosition == -1) {
                            currentPosition = 99;
                        }
                    }
                    break;
                }
                case "R": {

                    for (int i = 1; i <= distance; i++) {
                        currentPosition++;
                        if (currentPosition == 100) {
                            result++;
                        }

                        if (currentPosition == 101) {
                            currentPosition = 1;
                        }
                    }
                    break;
                }
                default:
                    throw new IllegalStateException("Unexpected value: " + line.substring(0, 1).toUpperCase());
            }


        }

        return String.valueOf(result);
    }


    public Part2() {
        super(EXPECTED_TEST_RESULT);
    }

    public static void main(String... args) {
        new Part2().run();
    }

}
