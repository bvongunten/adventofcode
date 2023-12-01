package ch.nostromo.adventofcode.utils;

public enum AnsiColor {
    RED("\033[0;31m"),
    RED_BRIGHT("\033[0;91m"),
    YELLOW("\033[1;33m"),
    YELLOW_BRIGHT("\033[1;93m"),
    GREEN("\033[0;32m"),
    BLUE("\033[0;34m"),
    WHITE("\033[0;97m"),
    RESET("\033[0;0m");

    private String ansiCmd;

    AnsiColor(String ansiCmd) {
        this.ansiCmd = ansiCmd;
    }

    public String toString() {
        return ansiCmd;
    }

}
