package ch.nostromo.adventofcode.year2023.day04;

import ch.nostromo.adventofcode.BasePuzzle;

import java.util.*;

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
public class Part2 extends BasePuzzle {

    private static final String EXPECTED_TEST_RESULT = "30";

    public String solvePuzzle(List<String> input) {
        int result = 0;

        Map<Integer, List<Card>> myCards = new HashMap<>();
        for (int i = 0; i < input.size(); i++) {
            List<Card> subCardsList = new ArrayList<>();
            subCardsList.add(new Card(input.get(i)));
            myCards.put(i, subCardsList);
        }

        for (int i = 0; i < input.size(); i++) {
            List<Card> subCardList = myCards.get(i);
            for (Card card : subCardList) {
                int matches = card.getMatchCount();
                for (int m = 1; m<= matches; m++) {
                    if ((m + i) < input.size()) {
                        myCards.get(m+i).add(myCards.get(m+i).get(0));
                    }
                }
            }
        }

        for (int i = 0; i < input.size(); i++) {
            result += myCards.get(i).size();
        }

        return String.valueOf(result);
    }


    public class Card {
        List<Integer> cardNumbers = new ArrayList<>();
        List<Integer> luckyNumbers = new ArrayList<>();

        public Card(String cardInfo) {

            for (String token : cardInfo.split(":")[1].split("\\|")[0].trim().split("\\s+")) {
                cardNumbers.add(Integer.valueOf(token));
            }

            for (String token : cardInfo.split(":")[1].split("\\|")[1].trim().split("\\s+")) {
                luckyNumbers.add(Integer.valueOf(token));
            }

        }

        public int getMatchCount() {
            int result = 0;
            for (int luckyNumber : luckyNumbers) {
                if (cardNumbers.contains(luckyNumber)) {
                    result++;
                }
            }
            return result;
        }
    }

    public Part2() {
        super(EXPECTED_TEST_RESULT);
    }

    public static void main(String... args) {
        new Part2().run();
    }

}
