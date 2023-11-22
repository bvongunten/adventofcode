package ch.nostromo.adventofcode;

import ch.nostromo.adventofcode.utils.LogFormatter;
import ch.nostromo.adventofcode.year2022.day01.Part1;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public abstract class BasePuzzle {
    Logger LOG  = Logger.getLogger(BasePuzzle.class.getName());

    private String expectedTestResult;

    public BasePuzzle(String expectedTestResult) {
       this.expectedTestResult = expectedTestResult;
    }

    public abstract String solvePuzzle(List<String> input);

    protected void run() {
        initializeLogging();

        LOG.info("Puzzle" + getPuzzleResource());

        LOG.info("Test set ...");
        String testResult = solvePuzzle(readInput("test.txt"));

        if (testResult.equals(expectedTestResult)){
            LOG.info ("Test set finished successfully with expected solution of " + expectedTestResult);
        } else {
            LOG.severe ("Test set failed expected solution of " + expectedTestResult + " but soloutino was " + testResult);
        }

        LOG.info("Full set ... ");

        String fullResult = solvePuzzle(readInput("input.txt"));

        LOG.info ("Full set solution is " + fullResult);
    }

    private List<String> readInput(String fileName)  {
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
