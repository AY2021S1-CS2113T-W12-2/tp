package athena.ui;

import athena.Importance;
import athena.task.Task;
import athena.timetable.Timetable;

/**
 * API of the Ui component.
 */
public interface Ui {
    /**
     * Prints Athena's logo when the app starts.
     */
    void printAthenaLogo();

    /**
     * Prints the first message that the user sees upon launching the application.
     */
    void printWelcomeMessage();

    /**
     * Prints out a message verifying which task the user has added to their list.
     *
     * @param taskName       The name of the task
     * @param taskStartTime  When the task is scheduled to start
     * @param taskDuration   How long the task will last for
     * @param taskDeadline   When the task is due
     * @param taskRecurrence When will the task repeat
     * @param taskImportance What is the importance of the task
     * @param taskNotes      Any additional notes the user has added to the task
     */
    void printTaskAdded(String taskName, String taskStartTime, String taskDuration, String taskDeadline,
                        String taskRecurrence, String taskImportance, String taskNotes, int id);

    /**
     * Prints out a message verifying that the task the user specified has been marked as done.
     *
     * @param task The task that the user marked as done
     */
    void printTaskDone(Task task);

    /**
     * Prints out a message verifying that the task the user specified has been edited, and shows the user the
     * new task details.
     *
     * @param taskIndex      The index of the task on the task list
     * @param taskName       The name of the task
     * @param taskStartTime  When the task is scheduled to start
     * @param taskDuration   How long the task will last for
     * @param taskDeadline   When the task is due
     * @param taskRecurrence When will the task repeat
     * @param taskImportance What is the importance of the task
     * @param taskNotes      Any additional notes the user has added to the task
     */
    void printTaskEdited(int taskIndex, String taskName, String taskStartTime, String taskDuration,
                         String taskDeadline, String taskRecurrence, Importance taskImportance, String taskNotes);

    /**
     * Prints out a message verifying that the task the user specified has been deleted.
     *
     * @param task The task that the user has deleted.
     */
    void printTaskDeleted(Task task, String taskRestore);

    /**
     * Prints out the task details that the user specified to view.
     *
     * @param taskDetails The task that the user wants to view.
     */
    void printTaskDetails(String taskDetails);

    /**
     * Prints out a help menu of all available tasks that ATHENA is able to do.
     */
    void printHelp();

    /**
     * Prints a message when there is an error with a task in the storage file.
     */
    void printInvalidTask();

    /**
     * Prints a message stating the task is not found.
     *
     * @param taskNumber Task number given by the user
     */
    void printTaskNotFound(int taskNumber);

    /**
     * Prints a message telling user they did not specify either the name or start time of the task.
     */
    void printAddMissingRequiredParametersException();

    /**
     * Prints a message telling user they did not provide a valid index for the delete command.
     */
    void printDeleteInvalidIndexException();

    /**
     * Prints a message telling user they did not provide an index for the delete command.
     */
    void printDeleteNoIndexException();

    /**
     * Prints a message telling user they did not provide a valid index for the done command.
     */
    void printDoneInvalidIndexException();

    /**
     * Prints a message telling user they did not provide an index for the done command.
     */
    void printDoneNoIndexException();

    /**
     * Prints a message telling user they did not provide a valid index for the view command.
     */
    void printViewInvalidIndexException();

    /**
     * Prints a message telling user they did not provide an index for the view command.
     */
    void printViewNoIndexException();

    /**
     * Prints a message telling user they did not provide an index for the edit command.
     */
    void printEditNoIndexException();

    /**
     * Prints a message telling user they did not input a command that is recognized by the program.
     */
    void printInvalidCommandException();

    /**
     * Prints a message telling user their task list is empty.
     */
    void printEmptyTaskListException();

    /**
     * Prints a message when the user chooses to exit the program.
     */
    void printExitMessage();

    /**
     * Prints the timetable.
     *
     * @param timetable Timetable to be printed
     */
    void printTimetable(Timetable timetable);
}
