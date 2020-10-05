package athena;

/**
 * Handles the list command.
 */
public class ListCommand extends Command {
    protected static String taskImportance;

    public ListCommand(String importance) {
        taskImportance = importance;
    }
    /**
     * Calls Ui to print the list of tasks.
     * @param taskList Tasks List
     * @param ui Ui
     */
    @Override
    public void execute(TaskList taskList, Ui ui) {
        taskList.displayList(taskImportance);
    }
}
