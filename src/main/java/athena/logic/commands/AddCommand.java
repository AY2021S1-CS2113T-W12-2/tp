package athena.logic.commands;

import athena.Importance;
import athena.TaskList;
import athena.exceptions.CommandException;
import athena.exceptions.TaskDuringSleepTimeException;
import athena.exceptions.AddMissingRequiredParametersException;
import athena.exceptions.ClashInTaskException;
import athena.exceptions.AddDateWrongFormatException;
import athena.ui.AthenaUi;

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
    private Boolean isTaskFlexible;

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
     * @param isFlexible  Boolean representing if task time is flexible
     */
    public AddCommand(String name, String startTime, String duration, String deadline,
                      String recurrence, String importance, String notes, boolean isFlexible) {
        taskName = name;
        assert !taskName.equals("");
        taskStartTime = startTime;
        //assert !taskStartTime.equals("");
        taskDuration = duration;
        taskDeadline = deadline;
        taskRecurrence = recurrence;
        taskImportance = Importance.valueOf(importance.toUpperCase());
        taskNotes = notes;
        isTaskFlexible = isFlexible;
    }

    /**
     * Adds a task to the Tasks list and
     * calls Ui to print out the task added.
     *
     * @param taskList Tasks list
     * @param athenaUi       Ui
     * @throws AddMissingRequiredParametersException Exception thrown when required parameters are not provided for
     *                                               add command
     */
    @Override
    public void execute(TaskList taskList, AthenaUi athenaUi)
            throws CommandException {
        if (taskName.equals("")) {
            throw new AddMissingRequiredParametersException();
        }
        try {
            int id = taskList.addTask(taskName, taskStartTime, taskDuration, taskDeadline,
                    taskRecurrence, taskImportance, taskNotes, isTaskFlexible);
            athenaUi.printTaskAdded(taskName, taskStartTime, taskDuration, taskDeadline,
                    taskRecurrence, taskImportance.toString(), taskNotes, id);
        } catch (NumberFormatException e) {
            throw new AddDateWrongFormatException();
        }

    }

    /**
     * Determines if two objects have the same attributes.
     *
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
