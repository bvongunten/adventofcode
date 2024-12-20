package ch.nostromo.adventofcode.year2024.day11;

import ch.nostromo.adventofcode.BasePuzzle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * --- Day 11: Plutonian Pebbles ---
 * The ancient civilization on Pluto was known for its ability to manipulate spacetime, and while The Historians explore their infinite corridors, you've noticed a strange set of physics-defying stones.
 * <p>
 * At first glance, they seem like normal stones: they're arranged in a perfectly straight line, and each stone has a number engraved on it.
 * <p>
 * The strange part is that every time you blink, the stones change.
 * <p>
 * Sometimes, the number engraved on a stone changes. Other times, a stone might split in two, causing all the other stones to shift over a bit to make room in their perfectly straight line.
 * <p>
 * As you observe them for a while, you find that the stones have a consistent behavior. Every time you blink, the stones each simultaneously change according to the first applicable rule in this list:
 * <p>
 * If the stone is engraved with the number 0, it is replaced by a stone engraved with the number 1.
 * If the stone is engraved with a number that has an even number of digits, it is replaced by two stones. The left half of the digits are engraved on the new left stone, and the right half of the digits are engraved on the new right stone. (The new numbers don't keep extra leading zeroes: 1000 would become stones 10 and 0.)
 * If none of the other rules apply, the stone is replaced by a new stone; the old stone's number multiplied by 2024 is engraved on the new stone.
 * No matter how the stones change, their order is preserved, and they stay on their perfectly straight line.
 * <p>
 * How will the stones evolve if you keep blinking at them? You take a note of the number engraved on each stone in the line (your puzzle input).
 * <p>
 * If you have an arrangement of five stones engraved with the numbers 0 1 10 99 999 and you blink once, the stones transform as follows:
 * <p>
 * The first stone, 0, becomes a stone marked 1.
 * The second stone, 1, is multiplied by 2024 to become 2024.
 * The third stone, 10, is split into a stone marked 1 followed by a stone marked 0.
 * The fourth stone, 99, is split into two stones marked 9.
 * The fifth stone, 999, is replaced by a stone marked 2021976.
 * So, after blinking once, your five stones would become an arrangement of seven stones engraved with the numbers 1 2024 1 0 9 9 2021976.
 */
public class Part1 extends BasePuzzle {

    static final String EXPECTED_TEST_RESULT = "55312";


    public String solvePuzzle(List<String> input) {

        Map<Long, Long> stones = new HashMap<>();

        for (String token : input.get(0).split(" ")) {
            stones.put(Long.parseLong(token), 1L);
        }

        stones = recursiveBlink(stones, new HashMap<>(), 25);

        long result = stones.values().stream().mapToLong(Long::longValue).sum();

        return String.valueOf(result);


    }

    Map<Long, Long> recursiveBlink(Map<Long, Long> stones, Map<String, Map<Long, Long>> cache, int remainingBlinks) {
        if (remainingBlinks == 0) {
            return stones;
        }

        String cacheKey = (stones) + ":" + remainingBlinks;
        if (cache.containsKey(cacheKey)) {
            return cache.get(cacheKey);
        }

        Map<Long, Long> newStones = new HashMap<>();

        for (Map.Entry<Long, Long> entry : stones.entrySet()) {
            long stone = entry.getKey();
            long count = entry.getValue();

            if (stone == 0) {
                newStones.merge(1L, count, Long::sum);
            } else {
                String numStr = Long.toString(stone);

                if (numStr.length() % 2 == 0) {
                    int mid = numStr.length() / 2;
                    long left = Long.parseLong(numStr.substring(0, mid));
                    long right = Long.parseLong(numStr.substring(mid));

                    newStones.merge(left, count, Long::sum);
                    newStones.merge(right, count, Long::sum);
                } else {
                    long newNumber = stone * 2024;
                    newStones.merge(newNumber, count, Long::sum);
                }
            }
        }

        Map<Long, Long> result = recursiveBlink(newStones, cache, remainingBlinks - 1);

        cache.put(cacheKey, result);

        return result;
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