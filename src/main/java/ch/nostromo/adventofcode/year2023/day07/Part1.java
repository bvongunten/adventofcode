package ch.nostromo.adventofcode.year2023.day07;

import ch.nostromo.adventofcode.BasePuzzle;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 */
public class Part1 extends BasePuzzle {

    private static final String EXPECTED_TEST_RESULT = "6440";


    public String solvePuzzle(List<String> input) {

        List<Hand> hands = new ArrayList<>();

        for (String line : input) {
            hands.add(new Hand(line));
        }

        Collections.sort(hands);

        int result = 0;
        for (int i = 0; i < hands.size(); i++)  {
            result += (hands.get(i).getBet() * (i+1));
        }


        return String.valueOf(result);
    }

    @Data
    public class Hand implements Comparable<Hand> {
        String cardsInHand;
        int bet;

        public Hand(String line) {
            cardsInHand = line.split("\\s+")[0];
            bet = Integer.valueOf(line.split("\\s+")[1]);
        }

        @Override
        public int compareTo(Hand otherHand) {
            if (getHandScore() == otherHand.getHandScore()) {
                for (int i = 0; i < 5; i++) {
                    char myCard = getCardsInHand().toCharArray()[i];
                    char otherCard = otherHand.getCardsInHand().toCharArray()[i];
                    if (myCard != otherCard) {
                        return Integer.compare(CardType.getCardScore(myCard), CardType.getCardScore(otherCard));
                    }

                }

                throw new IllegalArgumentException("Cards are same: " + this.getCardsInHand() + " " + otherHand.getCardsInHand());
            } else {
                return Integer.compare(getHandScore(), otherHand.getHandScore());
            }
        }

        public int getHandScore() {
            if (isNOfAKind(5)) {
                return 1000;
            } else if (isNOfAKind(4)) {
                return 900;
            } else if (isFullHouse()) {
                return 800;
            } else if (isNOfAKind(3)) {
                return 700;
            } else if (isTwoPair()) {
                return 600;
            } else if (isNOfAKind(2)) {
                return 500;
            } else {
                return -1;
            }
        }

        private boolean isFullHouse() {
            CardType three = null;
            CardType two = null;

            for (CardType type : CardType.values()) {
                if (countDuplicates(type) == 3) {
                    three = type;
                }
            }

            for (CardType type : CardType.values()) {
                if (countDuplicates(type) == 2 && !type.equals(three)) {
                    two = type;
                }
            }


            return three != null && two != null;
        }

        private boolean isTwoPair() {
            CardType two1 = null;
            CardType two2 = null;

            for (CardType type : CardType.values()) {
                if (countDuplicates(type) == 2) {
                    two1 = type;
                }
            }

            for (CardType type : CardType.values()) {
                if (countDuplicates(type) == 2 && !two1.equals(type)) {
                    two2 = type;
                }
            }


            return two1 != null && two2 != null;
        }

        private boolean isNOfAKind(int n) {
            for (CardType type : CardType.values()) {
                if (countDuplicates(type) == n) {
                    return true;
                }
            }
            return false;
        }


        private long countDuplicates(CardType cardType) {
            return cardsInHand.chars().filter(ch -> ch == cardType.sign).count();
        }



    }

    public enum CardType {
        Ace('A'),
        King('K'),
        Queen('Q'),
        Jack('J'),
        Ten('T'),
        Nine('9'),
        Eight('8'),
        Seven('7'),
        Six('6'),
        Five('5'),
        Four('4'),
        Three('3'),

        Two('2');


        private char sign;

        CardType(char sign) {
            this.sign = sign;
        }

        public static int getCardScore(char c) {
            for (int i =0; i < values().length; i++) {
                if (values()[i].sign == c) {
                    return 14-i;
                }
            }
            throw new IllegalArgumentException("Unknown card type: " + c);
        }


    }


    public Part1() {
        super(EXPECTED_TEST_RESULT);
    }

    public static void main(String... args) {
        new Part1().run();
    }

}
