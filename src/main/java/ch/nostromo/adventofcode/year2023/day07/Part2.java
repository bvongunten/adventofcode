package ch.nostromo.adventofcode.year2023.day07;

import ch.nostromo.adventofcode.BasePuzzle;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * --- Day 7: Camel Cards ---
 * Your all-expenses-paid trip turns out to be a one-way, five-minute ride in an airship. (At least it's a cool airship!) It drops you off at the edge of a vast desert and descends back to Island Island.
 * <p>
 * "Did you bring the parts?"
 * <p>
 * You turn around to see an Elf completely covered in white clothing, wearing goggles, and riding a large camel.
 * <p>
 * "Did you bring the parts?" she asks again, louder this time. You aren't sure what parts she's looking for; you're here to figure out why the sand stopped.
 * <p>
 * "The parts! For the sand, yes! Come with me; I will show you." She beckons you onto the camel.
 * <p>
 * After riding a bit across the sands of Desert Island, you can see what look like very large rocks covering half of the horizon. The Elf explains that the rocks are all along the part of Desert Island that is directly above Island Island, making it hard to even get there. Normally, they use big machines to move the rocks and filter the sand, but the machines have broken down because Desert Island recently stopped receiving the parts they need to fix the machines.
 * <p>
 * You've already assumed it'll be your job to figure out why the parts stopped when she asks if you can help. You agree automatically.
 * <p>
 * Because the journey will take a few days, she offers to teach you the game of Camel Cards. Camel Cards is sort of similar to poker except it's designed to be easier to play while riding a camel.
 * <p>
 * In Camel Cards, you get a list of hands, and your goal is to order them based on the strength of each hand. A hand consists of five cards labeled one of A, K, Q, J, T, 9, 8, 7, 6, 5, 4, 3, or 2. The relative strength of each card follows this order, where A is the highest and 2 is the lowest.
 * <p>
 * Every hand is exactly one type. From strongest to weakest, they are:
 * <p>
 * Five of a kind, where all five cards have the same label: AAAAA
 * Four of a kind, where four cards have the same label and one card has a different label: AA8AA
 * Full house, where three cards have the same label, and the remaining two cards share a different label: 23332
 * Three of a kind, where three cards have the same label, and the remaining two cards are each different from any other card in the hand: TTT98
 * Two pair, where two cards share one label, two other cards share a second label, and the remaining card has a third label: 23432
 * One pair, where two cards share one label, and the other three cards have a different label from the pair and each other: A23A4
 * High card, where all cards' labels are distinct: 23456
 * Hands are primarily ordered based on type; for example, every full house is stronger than any three of a kind.
 * <p>
 * If two hands have the same type, a second ordering rule takes effect. Start by comparing the first card in each hand. If these cards are different, the hand with the stronger first card is considered stronger. If the first card in each hand have the same label, however, then move on to considering the second card in each hand. If they differ, the hand with the higher second card wins; otherwise, continue with the third card in each hand, then the fourth, then the fifth.
 * <p>
 * So, 33332 and 2AAAA are both four of a kind hands, but 33332 is stronger because its first card is stronger. Similarly, 77888 and 77788 are both a full house, but 77888 is stronger because its third card is stronger (and both hands have the same first and second card).
 * <p>
 * To play Camel Cards, you are given a list of hands and their corresponding bid (your puzzle input). For example:
 * <p>
 * 32T3K 765
 * T55J5 684
 * KK677 28
 * KTJJT 220
 * QQQJA 483
 * This example shows five hands; each hand is followed by its bid amount. Each hand wins an amount equal to its bid multiplied by its rank, where the weakest hand gets rank 1, the second-weakest hand gets rank 2, and so on up to the strongest hand. Because there are five hands in this example, the strongest hand will have rank 5 and its bid will be multiplied by 5.
 * <p>
 * So, the first step is to put the hands in order of strength:
 * <p>
 * 32T3K is the only one pair and the other hands are all a stronger type, so it gets rank 1.
 * KK677 and KTJJT are both two pair. Their first cards both have the same label, but the second card of KK677 is stronger (K vs T), so KTJJT gets rank 2 and KK677 gets rank 3.
 * T55J5 and QQQJA are both three of a kind. QQQJA has a stronger first card, so it gets rank 5 and T55J5 gets rank 4.
 * Now, you can determine the total winnings of this set of hands by adding up the result of multiplying each hand's bid with its rank (765 * 1 + 220 * 2 + 28 * 3 + 684 * 4 + 483 * 5). So the total winnings in this example are 6440.
 * <p>
 * Find the rank of every hand in your set. What are the total winnings?
 * <p>
 * Your puzzle answer was 247815719.
 * <p>
 * --- Part Two ---
 * To make things a little more interesting, the Elf introduces one additional rule. Now, J cards are jokers - wildcards that can act like whatever card would make the hand the strongest type possible.
 * <p>
 * To balance this, J cards are now the weakest individual cards, weaker even than 2. The other cards stay in the same order: A, K, Q, T, 9, 8, 7, 6, 5, 4, 3, 2, J.
 * <p>
 * J cards can pretend to be whatever card is best for the purpose of determining hand type; for example, QJJQ2 is now considered four of a kind. However, for the purpose of breaking ties between two hands of the same type, J is always treated as J, not the card it's pretending to be: JKKK2 is weaker than QQQQ2 because J is weaker than Q.
 * <p>
 * Now, the above example goes very differently:
 * <p>
 * 32T3K 765
 * T55J5 684
 * KK677 28
 * KTJJT 220
 * QQQJA 483
 * 32T3K is still the only one pair; it doesn't contain any jokers, so its strength doesn't increase.
 * KK677 is now the only two pair, making it the second-weakest hand.
 * T55J5, KTJJT, and QQQJA are now all four of a kind! T55J5 gets rank 3, QQQJA gets rank 4, and KTJJT gets rank 5.
 * With the new joker rule, the total winnings in this example are 5905.
 * <p>
 * Using the new joker rule, find the rank of every hand in your set. What are the new total winnings?
 * <p>
 * Your puzzle answer was 248747492.
 */
public class Part2 extends BasePuzzle {

    private static final String EXPECTED_TEST_RESULT = "5905";


    public String solvePuzzle(List<String> input) {

        List<Hand> hands = new ArrayList<>();

        for (String line : input) {
            hands.add(new Hand(line));
        }

        Collections.sort(hands);

        int result = 0;
        for (int i = 0; i < hands.size(); i++) {
            result += (hands.get(i).getBet() * (i + 1));
        }


        return String.valueOf(result);
    }

    @Data
    public class Hand implements Comparable<Hand> {
        String cardsInHand;

        String cardsInHandNoJacks;

        int jokers;


        int bet;

        public Hand(String line) {
            cardsInHand = line.split("\\s+")[0];

            cardsInHandNoJacks = cardsInHand.replace("J", "");

            jokers = cardsInHand.length() - cardsInHandNoJacks.length();


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

            long availableJockers = jokers;

            for (CardType type : CardType.values()) {
                long duplicates = countDuplicates(type);

                if (duplicates + availableJockers >= 3) {
                    three = type;
                    availableJockers = availableJockers - (3 - duplicates);
                    break;
                }
            }

            for (CardType type : CardType.values()) {
                long duplicates = countDuplicates(type);

                if (duplicates + availableJockers >= 2 && !type.equals(three)) {
                    two = type;
                    break;
                }
            }


            return three != null && two != null;
        }

        private boolean isTwoPair() {
            CardType two1 = null;
            CardType two2 = null;

            long availableJockers = jokers;

            for (CardType type : CardType.values()) {
                long duplicates = countDuplicates(type);

                if (duplicates + availableJockers >= 2) {
                    two1 = type;
                    availableJockers = availableJockers - (2 - duplicates);
                    break;
                }
            }

            for (CardType type : CardType.values()) {
                long duplicates = countDuplicates(type);

                if (duplicates + availableJockers >= 2 && !two1.equals(type)) {
                    two2 = type;
                    break;
                }
            }


            return two1 != null && two2 != null;
        }

        private boolean isNOfAKind(int n) {
            for (CardType type : CardType.values()) {
                if (countDuplicates(type) + jokers >= n) {
                    return true;
                }
            }
            return false;
        }


        private long countDuplicates(CardType cardType) {
            return cardsInHandNoJacks.chars().filter(ch -> ch == cardType.sign).count();
        }


    }

    public enum CardType {
        Ace('A'),
        King('K'),
        Queen('Q'),
        Ten('T'),
        Nine('9'),
        Eight('8'),
        Seven('7'),
        Six('6'),
        Five('5'),
        Four('4'),
        Three('3'),

        Two('2'),

        Jack('J');


        private char sign;

        CardType(char sign) {
            this.sign = sign;
        }

        public static int getCardScore(char c) {
            for (int i = 0; i < values().length; i++) {
                if (values()[i].sign == c) {
                    return 14 - i;
                }
            }
            throw new IllegalArgumentException("Unknown card type: " + c);
        }


    }


    public Part2() {
        super(EXPECTED_TEST_RESULT);
    }

    public static void main(String... args) {
        new Part2().run();
    }

}
