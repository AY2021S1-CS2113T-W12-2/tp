package athena.exceptions;

import athena.ui.AthenaUi;

public class InvalidImportanceException extends CommandException {
    public InvalidImportanceException() {

    }

    /**
     * Prints an error message telling user that they entered an invalid forecast.
     */
    @Override
    public void printErrorMessage() {
        AthenaUi athenaUi = new AthenaUi();
        athenaUi.printInvalidImportanceException();
    }
}
