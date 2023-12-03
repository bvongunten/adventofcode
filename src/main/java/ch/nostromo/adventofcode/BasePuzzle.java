package ch.nostromo.adventofcode;

import ch.nostromo.adventofcode.utils.AnsiColor;
import ch.nostromo.adventofcode.utils.LogFormatter;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public abstract class BasePuzzle {
    public Logger LOG = Logger.getLogger(BasePuzzle.class.getName());


    private String expectedTestResult;

    private String testInput;

    private String fullInput;

    private boolean isTestRun;

    public BasePuzzle(String expectedTestResult) {
        this.expectedTestResult = expectedTestResult;
    }

    public BasePuzzle(String expectedTestResult, String testInput, String fullInput) {
        this.expectedTestResult = expectedTestResult;
        this.testInput = testInput;
        this.fullInput = fullInput;
    }

    public abstract String solvePuzzle(List<String> input);

    protected void run() {
        initializeLogging();

        isTestRun = true;
        String testResult;
        if (testInput == null) {
            testResult = solvePuzzle(readInput("test.txt"));
        } else {
            testResult = solvePuzzle(Arrays.asList(this.testInput.split("\n")));
        }

        isTestRun = false;
        String fullResult;
        if (fullInput == null) {
            fullResult = solvePuzzle(readInput("input.txt"));
        } else {
            fullResult = solvePuzzle(Arrays.asList(fullInput));
        }

        printResult(testResult, fullResult);

    }

    private void printResult(String testResult, String solution) {
        String result = "\n            $\n" +
                "           ***               [1]\n" +
                "          **I**\n" +
                "         **%**o*\n" +
                "        **I**o***            [2]\n" +
                "       ***o***%***\n" +
                "      ***%***I*****          [3]\n" +
                "     **o***o***I****\n" +
                "    ****I***%****o***        [4]\n" +
                "   ****o******I***%***\n" +
                "           ###\n" +
                "           ###\n";

        result = colorizeString(result, "*", AnsiColor.GREEN);
        result = colorizeString(result, "#", AnsiColor.YELLOW);
        result = colorizeString(result, "o", AnsiColor.RED_BRIGHT);
        result = colorizeString(result, "%", AnsiColor.BLUE);
        result = colorizeString(result, "I", AnsiColor.YELLOW_BRIGHT);
        result = colorizeString(result, "$", AnsiColor.YELLOW_BRIGHT);


        result = result.replace("[1]", AnsiColor.YELLOW_BRIGHT + "~ " + AnsiColor.RED + "Advent of Code" + AnsiColor.YELLOW_BRIGHT + " ~" + AnsiColor.RESET);
        result = result.replace("[2]", AnsiColor.WHITE + "Puzzle: " + getPuzzleResource() + "/"+ getClass().getSimpleName() + AnsiColor.RESET);

        if (expectedTestResult.equalsIgnoreCase(testResult)) {
            result = result.replace("[3]", AnsiColor.WHITE + "Test: " + AnsiColor.GREEN + "Passed" + AnsiColor.WHITE + " with expected solution of " + expectedTestResult + AnsiColor.RESET);
        } else {
            result = result.replace("[3]", AnsiColor.WHITE + "Test: " + AnsiColor.RED_BRIGHT + "Failed" + AnsiColor.WHITE + " with solution " + testResult + " instead of expected " + expectedTestResult + AnsiColor.RESET);

        }

        result = result.replace("[4]", AnsiColor.WHITE + "Solution: " + solution);

        System.out.println(result);

    }

    public boolean isTestRun() {
        return isTestRun;
    }

    private String colorizeString(String text, String token, AnsiColor color) {
        return text.replace(token, color + token + AnsiColor.RESET);
    }


    private List<String> readInput(String fileName) {
        try {
            String path = getPuzzleResource() + "/" + fileName;
            return Files.readAllLines(Paths.get(getClass().getClassLoader().getResource(path).toURI()), Charset.defaultCharset());
        } catch (Exception e) {
            throw new RuntimeException("Unable to read input file", e);
        }
    }

    // ***** HELPER ******

    private String getPuzzleResource() {
        return getPuzzlePackage().replaceAll("\\.", "/");
    }

    private String getPuzzlePackage() {
        String packageName = getClass().getPackageName();
        return packageName.substring(nthLastIndexOf(2, ".", packageName) + 1);
    }

    private void initializeLogging() {
        LogManager.getLogManager().reset();
        Logger root = Logger.getLogger("ch.nostromo");
        root.setLevel(Level.FINEST);
        LogManager.getLogManager().addLogger(root);

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.FINEST);
        consoleHandler.setFormatter(new LogFormatter());
        LogManager.getLogManager().getLogger("").addHandler(consoleHandler);

    }

    public static int nthLastIndexOf(int nth, String ch, String string) {
        if (nth <= 0) return string.length();
        return nthLastIndexOf(--nth, ch, string.substring(0, string.lastIndexOf(ch)));
    }

}
