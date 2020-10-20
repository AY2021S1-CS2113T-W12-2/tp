package athena.commands;

import athena.exceptions.TaskNotFoundException;
import athena.TaskList;
import athena.Ui;

import java.util.Objects;

/**
 * Handles the view command.
 */
public class ViewCommand extends Command {
    private int taskNumber;

    /**
     * Initializes the object with the task number of task to be viewed.
     *
     * @param taskNumber Integer representing the task number of task.
     */
    public ViewCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    /**
     * View a task from the Tasks list and
     * calls Ui to print task details.
     *
     * @param taskList Tasks list
     * @param ui       Ui
     * @throws TaskNotFoundException Exception thrown when the user tries to enter the index of a task that
     *                               does not exist
     */
    @Override
    public void execute(TaskList taskList, Ui ui) throws TaskNotFoundException {
        String taskDescription = taskList.getTaskDescription(taskNumber);
        ui.printTaskDetails(taskDescription);
    }

    /**
     * Determines if two objects have the same attributes.
     * @param o object
     * @return true if the two objects have the same attributes
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ViewCommand)) {
            return false;
        }
        ViewCommand that = (ViewCommand) o;
        return taskNumber == that.taskNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskNumber);
    }
}