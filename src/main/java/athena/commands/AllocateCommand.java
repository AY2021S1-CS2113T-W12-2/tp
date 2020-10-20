package athena.commands;

import athena.Importance;
import athena.TaskList;
import athena.TimeAllocator;
import athena.Ui;
import athena.exceptions.AddMissingRequiredParametersException;
import athena.exceptions.TaskNotFoundException;

import java.util.Objects;

public class AllocateCommand extends Command{


    /**
     * Initializes the object with the parameters.
     *
     */
    public AllocateCommand() {

    }

    /**
     * Adds a task to the Tasks list and
     * calls Ui to print out the task added.
     *
     * @param taskList Tasks list
     * @param ui       Ui
     */
    @Override
    public void execute(TaskList taskList, Ui ui) {
        TimeAllocator Allocator = new TimeAllocator(taskList);
        Allocator.runAllocator();
    }



}
