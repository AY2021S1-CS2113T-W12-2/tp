package athena.ui;

public class ColorText {
    private static final String ANSI_RESET = "\u001B[0m";

    public ColorText() {

    }

    public String toBlue(String inputString) {
        String ansiBlue = "\u001B[34m";

        return ansiBlue + inputString + ANSI_RESET;
    }

    public String toPurple(String inputString) {
        String ansiPurple = "\u001b[35m";

        return ansiPurple + inputString + ANSI_RESET;
    }

    public String toRed(String inputString) {
        String ansiRed = "\u001B[31m";

        return ansiRed + inputString + ANSI_RESET;
    }

    public String toYellow(String inputString) {
        String ansiYellow = "\u001b[33m";

        return ansiYellow + inputString + ANSI_RESET;
    }

    public String toGreen(String inputString) {
        String ansiGreen = "\u001b[32m";

        return ansiGreen + inputString + ANSI_RESET;
    }

    public String bgBlue(String inputString) {
        String ansiBgBlue = "\u001b[44;1m\u001b[30m";

        return ansiBgBlue + inputString + ANSI_RESET;
    }

    public String bgOrange(String inputString) {
        String ansiBgBlue = "\u001b[43;1m\u001b[30m";

        return ansiBgBlue + inputString + ANSI_RESET;
    }

    public String bgGreen(String inputString) {
        String ansiBgBlue = "\u001b[42;1m\u001b[30m";

        return ansiBgBlue + inputString + ANSI_RESET;
    }

    public String bgWhite(String inputString) {
        String ansiBgBlue = "\u001b[47;1m\u001b[30m";

        return ansiBgBlue + inputString + ANSI_RESET;
    }

    public String bgRed(String inputString) {
        String ansiBgBlue = "\u001b[41;1m\u001b[37m";

        return ansiBgBlue + inputString + ANSI_RESET;
    }
}
