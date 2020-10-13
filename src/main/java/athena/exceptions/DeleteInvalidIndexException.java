package athena.exceptions;

import athena.Ui;

/**
 * Exception that is thrown when the user enters an index without a task when using the delete command.
 */
public class DeleteInvalidIndexException extends CommandException {
    public DeleteInvalidIndexException() {

    }

    /**
     * Prints an error message telling user to enter a valid index number of a task to delete.
     */
    @Override
    public void printErrorMessage() {
        Ui ui = new Ui();
        ui.printDeleteInvalidIndexException();
    }
}
