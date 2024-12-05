package ch.nostromo.adventofcode.year2024.day05;

import ch.nostromo.adventofcode.BasePuzzle;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;

/**
 * --- Day 5: Print Queue ---
 * Satisfied with their search on Ceres, the squadron of scholars suggests subsequently scanning the stationery stacks of sub-basement 17.
 * <p>
 * The North Pole printing department is busier than ever this close to Christmas, and while The Historians continue their search of this historically significant facility, an Elf operating a very familiar printer beckons you over.
 * <p>
 * The Elf must recognize you, because they waste no time explaining that the new sleigh launch safety manual updates won't print correctly. Failure to update the safety manuals would be dire indeed, so you offer your services.
 * <p>
 * Safety protocols clearly indicate that new pages for the safety manuals must be printed in a very specific order. The notation X|Y means that if both page number X and page number Y are to be produced as part of an update, page number X must be printed at some point before page number Y.
 * <p>
 * The Elf has for you both the page ordering rules and the pages to produce in each update (your puzzle input), but can't figure out whether each update has the pages in the right order.
 * <p>
 * For example:
 * <p>
 * 47|53
 * 97|13
 * 97|61
 * 97|47
 * 75|29
 * 61|13
 * 75|53
 * 29|13
 * 97|29
 * 53|29
 * 61|53
 * 97|53
 * 61|29
 * 47|13
 * 75|47
 * 97|75
 * 47|61
 * 75|61
 * 47|29
 * 75|13
 * 53|13
 * <p>
 * 75,47,61,53,29
 * 97,61,53,29,13
 * 75,29,13
 * 75,97,47,61,53
 * 61,13,29
 * 97,13,75,29,47
 * The first section specifies the page ordering rules, one per line. The first rule, 47|53, means that if an update includes both page number 47 and page number 53, then page number 47 must be printed at some point before page number 53. (47 doesn't necessarily need to be immediately before 53; other pages are allowed to be between them.)
 * <p>
 * The second section specifies the page numbers of each update. Because most safety manuals are different, the pages needed in the updates are different too. The first update, 75,47,61,53,29, means that the update consists of page numbers 75, 47, 61, 53, and 29.
 * <p>
 * To get the printers going as soon as possible, start by identifying which updates are already in the right order.
 * <p>
 * In the above example, the first update (75,47,61,53,29) is in the right order:
 * <p>
 * 75 is correctly first because there are rules that put each other page after it: 75|47, 75|61, 75|53, and 75|29.
 * 47 is correctly second because 75 must be before it (75|47) and every other page must be after it according to 47|61, 47|53, and 47|29.
 * 61 is correctly in the middle because 75 and 47 are before it (75|61 and 47|61) and 53 and 29 are after it (61|53 and 61|29).
 * 53 is correctly fourth because it is before page number 29 (53|29).
 * 29 is the only page left and so is correctly last.
 * Because the first update does not include some page numbers, the ordering rules involving those missing page numbers are ignored.
 * <p>
 * The second and third updates are also in the correct order according to the rules. Like the first update, they also do not include every page number, and so only some of the ordering rules apply - within each update, the ordering rules that involve missing page numbers are not used.
 * <p>
 * The fourth update, 75,97,47,61,53, is not in the correct order: it would print 75 before 97, which violates the rule 97|75.
 * <p>
 * The fifth update, 61,13,29, is also not in the correct order, since it breaks the rule 29|13.
 * <p>
 * The last update, 97,13,75,29,47, is not in the correct order due to breaking several rules.
 * <p>
 * For some reason, the Elves also need to know the middle page number of each update being printed. Because you are currently only printing the correctly-ordered updates, you will need to find the middle page number of each correctly-ordered update. In the above example, the correctly-ordered updates are:
 * <p>
 * 75,47,61,53,29
 * 97,61,53,29,13
 * 75,29,13
 * These have middle page numbers of 61, 53, and 29 respectively. Adding these page numbers together gives 143.
 * <p>
 * Of course, you'll need to be careful: the actual list of page ordering rules is bigger and more complicated than the above example.
 * <p>
 * Determine which updates are already in the correct order. What do you get if you add up the middle page number from those correctly-ordered updates?
 */
public class Part2 extends BasePuzzle {

    private static final String EXPECTED_TEST_RESULT = "123";

    public String solvePuzzle(List<String> input) {

        List<Rule> rules = new ArrayList<>();
        List<List<Integer>> pagesList = new ArrayList<>();

        boolean rulesPart = true;

        for (String line : input) {
            if (line.isEmpty()) {
                rulesPart = false;
            } else {
                if (rulesPart) {
                    rules.add(new Rule(Integer.valueOf(line.split("\\|")[0]), Integer.valueOf(line.split("\\|")[1])));
                } else {
                    List<Integer> numbers = new ArrayList<>();
                    for (String num : line.split(",")) {
                        numbers.add(Integer.parseInt(num.trim()));
                    }
                    pagesList.add(numbers);
                }
            }
        }

        int result = 0;

        for (List<Integer> pages : pagesList) {
            if (!checkPages(pages, rules)) {
                result += reorderPages(pages, rules);
            }
        }

        return String.valueOf(result);
    }


    int reorderPages(List<Integer> pages, List<Rule> rules) {

        List<Rule> applicableRules = new ArrayList<>();
        for (Rule rule : rules) {
            if (pages.contains(rule.start) && pages.contains(rule.end)) {
                applicableRules.add(rule);
            }
        }

        Map<Integer, List<Integer>> rulesMap = createRulesMap(pages, applicableRules);
        Map<Integer, Integer> linksToPages = createLinksToPages(pages,applicableRules);

        Queue<Integer> toproc = new LinkedList<>();
        for (int page : pages) {
            if (linksToPages.get(page) == 0) {
                toproc.add(page);
            }
        }

        List<Integer> result = new ArrayList<>();
        while (!toproc.isEmpty()) {
            int page = toproc.poll();

            result.add(page);
            for (int nextPage : rulesMap.get(page)) {
                // linksToPages.put(neighbor, linksToPages.get(neighbor) + 1);
                linksToPages.put(nextPage, linksToPages.get(nextPage) - 1);
                if (linksToPages.get(nextPage) == 0) {
                    toproc.add(nextPage);
                }
            }
        }

        return result.get(result.size() / 2);

    }

    Map<Integer, Integer> createLinksToPages(List<Integer> pages, List<Rule> rules) {

        Map<Integer, Integer> result = new HashMap<>();
        for (Integer page : pages) {
            result.put(page, 0);
        }

        for (Rule rule : rules) {
            result.put(rule.end, result.get(rule.end) + 1);
        }

        return result;
    }

    Map<Integer, List<Integer>> createRulesMap(List<Integer> pages, List<Rule> rules) {
        Map<Integer, List<Integer>> rulesMap = new HashMap<>();

        for (Integer page : pages) {
            rulesMap.put(page, new ArrayList<>());
        }

        // Add rule ends to any page
        for (Rule rule : rules) {
            rulesMap.get(rule.start).add(rule.end);
        }

        return rulesMap;
    }

    boolean checkPages(List<Integer> pages, List<Rule> rules) {
        for (int i = 0; i < pages.size(); i++) {
            // Check page
            if (!checkRule(pages.get(i), pages.subList(i, pages.size()), rules)) {
                return false;
            }
        }
        return true;
    }

    boolean checkRule(int page, List<Integer> followingPages, List<Rule> rules) {
        for (Integer followingPage : followingPages) {
            for (Rule rule : rules) {
                if (rule.isApplicable(page, followingPage) && !rule.isRespected(page, followingPage)) {
                    return false;
                }
            }
        }
        return true;
    }


    @Data
    @AllArgsConstructor
    class Rule {
        int start;
        int end;


        public boolean isApplicable(int page1, int page2) {
            if (start == page1 && end == page2 || start == page2 && end == page1) {
                return true;
            }
            return false;
        }

        public boolean isRespected(int page1, int page2) {
            return start == page1 && end == page2;
        }

    }


    public Part2() {
        super(EXPECTED_TEST_RESULT);
    }

    public static void main(String... args) {
        new Part2().run();
    }

}
