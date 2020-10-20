package athena;

import athena.commands.Command;
import athena.exceptions.CommandException;
import athena.task.Time;

import java.util.Scanner;

/**
 * The main class of the ATHENA program.
 */
public class Athena {
    private Ui ui;
    private Parser parser;
    private Storage storage;
    private TaskList taskList;
    private TimeAllocator allocator;

    /**
     * Creates an ATHENA object.
     */
    public Athena() {
        ui = new Ui();
        parser = new Parser();
        storage = new Storage("data.csv", ui);
    }

    public static void main(String[] args) {
        Athena athena = new Athena();
        athena.runProgram();
    }

    /**
     * Runs the main code of the ATHENA program.
     */
    public void runProgram() {
        String inputString;
        Command userCommand;

        ui.printWelcomeMessage();

        taskList = storage.loadTaskListData();
        boolean isExit = false;
        Scanner input = new Scanner(System.in);

        while (!isExit) {
            try {
                inputString = input.nextLine();
                userCommand = parser.parse(inputString, taskList);
                userCommand.execute(taskList, ui);
                storage.saveTaskListData(taskList);
                isExit = userCommand.getIsExit();
            } catch (CommandException e) {
                e.printErrorMessage();
            } catch (Exception e) {
                e.printStackTrace();
            }
            continue;
        }
    }
}
