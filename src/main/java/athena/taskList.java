package athena;

import java.util.ArrayList;

public class TaskList {
    public static final String NO_FILTER = "";
    private ArrayList<Task> tasks;
    private Ui ui = new Ui();

    public TaskList() {
        tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> taskList) {
        tasks = new ArrayList<>();
        for (Task task: taskList) {
            tasks.add(task);
        }
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    private ArrayList<Task> getFilteredTasks(String filter) {
        ArrayList<Task> filteredTasks = new ArrayList<>();
        for (Task task: tasks) {
            if (task.getImportance().equals(filter)) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }

    private Task createTask(String name, String startTime,
            String duration, String deadline, String recurrence, String importance, String notes) {
        Task task = new Task(name, startTime, duration,
                deadline, recurrence, importance, notes);
        return task;
    }

    /**
     * Prints the task list.
     */
    public void printList() {
        ui.printList(tasks);
    }

    /**
     * Returns size of the task list.
     *
     * @return Size of the task list
     */
    public int getTaskListSize() {
        return tasks.size();
    }

    /**
     * Marks specified task as done.
     * Lets the user know specified task has been marked as done.
     *
     * @param taskNumber Position of task in task list
     */
    public void markTaskAsDone(int taskNumber) {
        tasks.get(taskNumber).setDone();
    }


    /**
     * Adds a task to the task list.
     *
     * @param name Name of task
     * @param startTime Start time of task
     * @param duration Duration of task
     * @param deadline Deadline of task
     * @param recurrence Recurrence of task
     * @param importance Importance of task
     * @param notes Additional notes of task
     */
    public void addTask(String name, String startTime, String duration,
                String deadline, String recurrence, String importance, String notes) {

        Task task = createTask(name, startTime, duration, deadline, recurrence, importance, notes);
        tasks.add(task);
        ui.printTaskAdded(task);
    }

    /**
     * Returns the task description at the specified position in task list.
     *
     * @param index Position of task in the task list
     * @return Task description
     */
    public String getDescription(int index) {
        return tasks.get(index).toString();
    }

    /**
     * Deletes the task at the specified position in the task list.
     *
     * @param taskNumber Position of task in task list
     */
    public void deleteTask(int taskNumber) {
        Task taskToDelete = tasks.get(taskNumber);
        tasks.remove(taskNumber);
        ui.printTaskDeleted(taskToDelete);
    }

    /**
     * Edits a task in the task list.
     *
     * @param taskNumber Index of task
     * @param name Name of task
     * @param startTime Start time of task
     * @param duration Duration of task
     * @param deadline Deadline of task
     * @param recurrence Recurrence of task
     * @param importance Importance of task
     * @param notes Additional notes of task
     */
    public void editTask(int taskNumber, String name, String startTime, String duration,
                         String deadline, String recurrence, String importance, String notes) {

        tasks.get(taskNumber).edit(name, startTime, duration,
                deadline, recurrence, importance, notes);
        ui.printTaskEdited(tasks.get(taskNumber));
    }

    /**
     * Displays a filtered task list based on importance.
     *
     * @param importanceFilter The filter that decides which tasks are printed
     */
    public void displayList(String importanceFilter) {

        if (importanceFilter.equals(NO_FILTER)) {
            printList();
        } else {
            TaskList filteredTasks = new TaskList(getFilteredTasks(importanceFilter));
            filteredTasks.printList();
        }
    }

}
