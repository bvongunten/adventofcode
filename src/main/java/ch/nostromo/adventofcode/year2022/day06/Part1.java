package ch.nostromo.adventofcode.year2022.day06;

import ch.nostromo.adventofcode.BasePuzzle;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * --- Day 6: Tuning Trouble ---
 * The preparations are finally complete; you and the Elves leave camp on foot and begin to make your way toward the star fruit grove.
 * <p>
 * As you move through the dense undergrowth, one of the Elves gives you a handheld device. He says that it has many fancy features, but the most important one to set up right now is the communication system.
 * <p>
 * However, because he's heard you have significant experience dealing with signal-based systems, he convinced the other Elves that it would be okay to give you their one malfunctioning device - surely you'll have no problem fixing it.
 * <p>
 * As if inspired by comedic timing, the device emits a few colorful sparks.
 * <p>
 * To be able to communicate with the Elves, the device needs to lock on to their signal. The signal is a series of seemingly-random characters that the device receives one at a time.
 * <p>
 * To fix the communication system, you need to add a subroutine to the device that detects a start-of-packet marker in the datastream. In the protocol being used by the Elves, the start of a packet is indicated by a sequence of four characters that are all different.
 * <p>
 * The device will send your subroutine a datastream buffer (your puzzle input); your subroutine needs to identify the first position where the four most recently received characters were all different. Specifically, it needs to report the number of characters from the beginning of the buffer to the end of the first such four-character marker.
 * <p>
 * For example, suppose you receive the following datastream buffer:
 * <p>
 * mjqjpqmgbljsphdztnvjfqwrcgsmlb
 * After the first three characters (mjq) have been received, there haven't been enough characters received yet to find the marker. The first time a marker could occur is after the fourth character is received, making the most recent four characters mjqj. Because j is repeated, this isn't a marker.
 * <p>
 * The first time a marker appears is after the seventh character arrives. Once it does, the last four characters received are jpqm, which are all different. In this case, your subroutine should report the value 7, because the first start-of-packet marker is complete after 7 characters have been processed.
 * <p>
 * Here are a few more examples:
 * <p>
 * bvwbjplbgvbhsrlpgdmjqwftvncz: first marker after character 5
 * nppdvjthqldpwncqszvftbrmjlhg: first marker after character 6
 * nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg: first marker after character 10
 * zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw: first marker after character 11
 * How many characters need to be processed before the first start-of-packet marker is detected?
 * <p>
 * Your puzzle answer was 1658.
 */
public class Part1 extends BasePuzzle {

    private static final String EXPECTED_TEST_RESULT = "11";

    public String solvePuzzle(List<String> input) {

        String line = input.get(0);

        int blockSize = 4;

        for (int i = 0; i < line.length() - blockSize; i++) {
            Set<String> block = new HashSet<>();

            for (int x = 0; x < blockSize; x++) {
                block.add(line.substring(i + x, i + x + 1));
            }

            if (block.size() == blockSize) {
                return String.valueOf( i + blockSize);
            }
        }

        throw new IllegalStateException("Unsafe end");
    }


    public Part1() {
        super(EXPECTED_TEST_RESULT);
    }

    public static void main(String... args) {
        new Part1().run();
    }

}
