package athena.commands;

import athena.Importance;
import athena.TaskList;
import athena.Ui;
import athena.exceptions.AddMissingRequiredParametersException;
import java.util.Objects;

/**
 * Handles adding tasks to the Tasks list.
 */
public class AddCommand extends Command {
    private String taskName;
    private String taskStartTime;
    private String taskDuration;
    private String taskDeadline;
    private String taskRecurrence;
    private Importance taskImportance;
    private String taskNotes;

    /**
     * Initializes the object with the parameters.
     *
     * @param name       String representing name of task.
     * @param startTime  String representing start time of task.
     * @param duration   String representing duration of task.
     * @param deadline   String representing deadline of task.
     * @param recurrence String representing recurrence of task.
     * @param importance String representing importance of task.
     * @param notes      String representing additional notes of task.
     */
    public AddCommand(String name, String startTime, String duration, String deadline,
                      String recurrence, String importance, String notes) {
        taskName = name;
        assert !taskName.equals("");
        taskStartTime = startTime;
        assert !taskStartTime.equals("");
        taskDuration = duration;
        taskDeadline = deadline;
        taskRecurrence = recurrence;
        taskImportance = Importance.valueOf(importance.toUpperCase());
        taskNotes = notes;
    }

    /**
     * Adds a task to the Tasks list and
     * calls Ui to print out the task added.
     *
     * @param taskList Tasks list
     * @param ui       Ui
     * @throws AddMissingRequiredParametersException Exception thrown when required parameters are not provided for
     *                                               add command
     */
    @Override
    public void execute(TaskList taskList, Ui ui) throws AddMissingRequiredParametersException {
        if (taskName.equals("") || taskStartTime.equals("")) {
            throw new AddMissingRequiredParametersException();
        }
        taskList.addTask(taskName, taskStartTime, taskDuration, taskDeadline,
                taskRecurrence, taskImportance, taskNotes);
        ui.printTaskAdded(taskName, taskStartTime, taskDuration, taskDeadline,
                taskRecurrence, taskImportance.toString(), taskNotes);
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
        if (!(o instanceof AddCommand)) {
            return false;
        }
        AddCommand that = (AddCommand) o;
        return Objects.equals(taskName, that.taskName)
                && Objects.equals(taskStartTime, that.taskStartTime)
                && Objects.equals(taskDuration, that.taskDuration)
                && Objects.equals(taskDeadline, that.taskDeadline)
                && Objects.equals(taskRecurrence, that.taskRecurrence)
                && taskImportance == that.taskImportance
                && Objects.equals(taskNotes, that.taskNotes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskName, taskStartTime, taskDuration,
                taskDeadline, taskRecurrence, taskImportance, taskNotes);
    }

}