package athena.logic;

import athena.TimeAllocator;
import athena.Storage;
import athena.TaskList;
import athena.ui.AthenaUi;
import athena.exceptions.command.CommandException;
import athena.exceptions.storage.StorageException;
import athena.logic.commands.Command;

/**
 * The main LogicManager of the ATHENA.
 */
public class LogicManager implements Logic {
    private static AthenaUi athenaUi;
    private static Parser parser;
    private static Storage storage;

    public LogicManager() {
        athenaUi = new AthenaUi();
        parser = new Parser();
        storage = new Storage("data.csv");
    }

    /**
     * Executes the command and returns whether exit or not.
     *
     * @param inputString The command as entered by the user.
     * @return true if command is exit, false if not exit.
     * @throws CommandException If an error occurs during command execution.
     * @throws StorageException If an error occurs during storage.
     */
    @Override
    public boolean execute(String inputString) throws CommandException, StorageException {
        Command userCommand;

        TaskList taskList = storage.loadTaskListData();
        userCommand = parser.parse(inputString, taskList);
        userCommand.execute(taskList, athenaUi);

        TimeAllocator timeAllocator = new TimeAllocator(taskList);
        timeAllocator.runAllocate();

        storage.saveTaskListData(taskList);
        return userCommand.getIsExit();
    }
}
