package ch.nostromo.adventofcode;

import ch.nostromo.adventofcode.utils.LogFormatter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public abstract class BasePuzzle {
    public Logger LOG  = Logger.getLogger(BasePuzzle.class.getName());

    private static final String XMASTREE =
            "            *\n" +
            "           ***\n" +
            "          *****\n" +
            "         *******\n" +
            "        *********\n" +
            "       ***********\n" +
            "      *************\n" +
            "     ***************\n" +
            "    *****************\n" +
            "   *******************\n" +
            "           ***\n" +
            "           ***";

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


    public static void logo() {
        BufferedImage image = new BufferedImage(300, 32, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setFont(new Font("Dialog", Font.PLAIN, 24));
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.drawString("Advent of Code", 6, 24);
        // ImageIO.write(image, "png", new File("text.png"));

        for (int y = 0; y < 32; y++) {
            StringBuilder sb = new StringBuilder();
            for (int x = 0; x < 144; x++)
                sb.append(image.getRGB(x, y) == -16777216 ? " " : image.getRGB(x, y) == -1 ? "#" : "*");
            if (sb.toString().trim().isEmpty()) continue;
            System.out.println(sb);
        }
    }

}
