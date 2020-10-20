package athena.commands;

import athena.TaskList;
import athena.Ui;
import athena.exceptions.CommandException;

/**
 * Abstract Command class for Command objects.
 */
public abstract class Command {
    protected boolean isExit;

    /**
     * Set isExit to be false initially.
     */
    public Command() {
        isExit = false;
    }

    /**
     * For Commands execution.
     *
     * @param taskList Tasks list
     * @param ui       Ui
     * @throws CommandException Exception thrown when there is an error in user command
     */
    public abstract void execute(TaskList taskList, Ui ui) throws CommandException, Exception;

    /**
     * Check if the command is exit.
     *
     * @return true if exit, false if not exit.
     */
    public boolean getIsExit() {
        return isExit;
    }
}