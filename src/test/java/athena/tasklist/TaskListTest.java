package athena.tasklist;

import athena.Forecast;
import athena.Importance;
import athena.TaskList;
import athena.common.utils.DateUtils;
import athena.exceptions.ClashInTaskException;
import athena.exceptions.TaskNotFoundException;
import athena.task.Task;
import athena.task.taskfilter.ForecastFilter;
import athena.task.taskfilter.ImportanceFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests methods of TaskList.
 */
class TaskListTest {

    private TaskList testTaskList;

    /**
     * Creates a new task list before every test.
     */
    @BeforeEach
    public void setUp() {
        setupTestTaskList();
    }

    /**
     * Tests that the max number is updated when adding a task with a larger task number through a Task object.
     */
    @Test
    void addTask_givenTaskWithLargerTaskNumber_correctMaxNumber() {
        int testMaxNumber = 5;
        testTaskList.addTask(new Task("tres", "1100",
                "2", "16-09-2020", "13-11-2020", Importance.LOW,
                "Refer to slides", testMaxNumber, false));
        assertEquals(testTaskList.getMaxNumber(), testMaxNumber);
    }

    /**
     * Tests that the max number is updated when adding a task with a larger task number through providing the
     * parameters.
     */
    @Test
    void addTask_givenLargerTaskNumber_correctMaxNumber() throws ClashInTaskException {
        String todayDateString = LocalDate.now().toString();
        int testMaxNumber = 100;
        testTaskList.addTask(testMaxNumber, "big number", "1100",
                "2", "16-09-2020", "13-11-2020", Importance.HIGH,
                "Refer to slides", false);
        assertEquals(testTaskList.getMaxNumber(), testMaxNumber);
    }

    /**
     * Tests that the max number is incremented when adding a task without providing a task number.
     */
    @Test
    void addTask_noGivenTaskNumber_maxNumberIncremented() throws ClashInTaskException {
        String todayDateString = LocalDate.now().toString();
        testTaskList.addTask("big number", "0200",
                "2", todayDateString, todayDateString, Importance.HIGH,
                "Refer to slides", false);
        assertEquals(testTaskList.getMaxNumber(), 3);
    }

    /**
     * Asserts if the deleted task at a certain index is the same task that is added to the task list.
     *
     * @throws TaskNotFoundException Exception thrown when the given task number is not in the list
     */
    @Test
    void deleteTask_validTaskIndex_correctTaskDeleted() throws TaskNotFoundException {
        Task expectedTask = new Task("Assignment1", "1100",
                "2", "16-09-2020", "13-10-2020", Importance.HIGH,
                "Refer to slides", 12, false);
        testTaskList.addTask(expectedTask);
        Task actualTask = testTaskList.deleteTask(12);
        assertEquals(expectedTask, actualTask);
    }

    /**
     * Asserts if the edited task at a certain index is the same task that is expected.
     *
     * @throws TaskNotFoundException Exception thrown when the given task number is not in the list
     */
    @Test
    void editTask_givenAttributes_attributeChanged() throws TaskNotFoundException, ClashInTaskException {
        int index = 0;
        Task task = new Task("Assignment1", "1100",
                "2", "16-09-2020", "13-10-2020", Importance.HIGH,
                "Refer to slides", index, false);
        testTaskList.addTask(task);

        Task expectedTask = new Task("Assignment2", "1200",
                "4", "16-11-2020", "13-10-2020", Importance.LOW,
                "I have changed", index, false);

        testTaskList.editTask(index, "Assignment2", "1200",
                "4", "16-11-2020", "13-10-2020", Importance.LOW,
                "I have changed");

        assertEquals(testTaskList.getTaskFromNumber(index), expectedTask);
    }

    @Test
    void getFilteredList_highImportance_returnTasksWithHighImportance() {
        // Filter list using high, low, medium importance
        // Filter list using today, week, all forecast
        // TODO ^^
        TaskList expectedTaskList = getImportanceTestExpectedTasks(Importance.HIGH);
        ImportanceFilter highFilter = new ImportanceFilter(Importance.HIGH);
        assertEquals(testTaskList.getFilteredList(highFilter), expectedTaskList);
    }

    @Test
    void getFilteredList_mediumImportance_returnTasksWithMediumImportance() {
        TaskList expectedTaskList = getImportanceTestExpectedTasks(Importance.MEDIUM);
        ImportanceFilter mediumFilter = new ImportanceFilter(Importance.MEDIUM);
        assertEquals(testTaskList.getFilteredList(mediumFilter), expectedTaskList);
    }

    @Test
    void getFilteredList_lowImportance_returnTasksWithLowImportance() {
        TaskList expectedTaskList = getImportanceTestExpectedTasks(Importance.LOW);
        ImportanceFilter lowFilter = new ImportanceFilter(Importance.LOW);
        assertEquals(testTaskList.getFilteredList(lowFilter), expectedTaskList);
    }

    @Test
    void getFilteredList_allForecast_returnAllTasks() {
        TaskList expectedTaskList = getForecastTestExpectedTasks(Forecast.ALL);
        ForecastFilter allFilter = new ForecastFilter(Forecast.ALL);
        assertEquals(testTaskList.getFilteredList(allFilter), expectedTaskList);
    }

    @Test
    void getFilteredList_weekForecast_returnTasksForWeek() {
        TaskList expectedTaskList = getForecastTestExpectedTasks(Forecast.WEEK);
        ForecastFilter weekFilter = new ForecastFilter(Forecast.WEEK);
        assertEquals(testTaskList.getFilteredList(weekFilter), expectedTaskList);
    }

    @Test
    void getFilteredList_todayForecast_returnTasksForToday() {
        TaskList expectedTaskList = getForecastTestExpectedTasks(Forecast.DAY);
        ForecastFilter todayFilter = new ForecastFilter(Forecast.DAY);
        assertEquals(testTaskList.getFilteredList(todayFilter), expectedTaskList);
    }

    private TaskList getImportanceTestExpectedTasks(Importance importance) {
        String dateInWeek = DateUtils.formatDate(getDateInWeek());
        String todayDateString = DateUtils.formatDate(LocalDate.now());
        TaskList taskList = new TaskList();
        Task task1 = new Task("uno", "1100",
                "2", todayDateString, todayDateString, Importance.HIGH,
                "Refer to slides", 0, false);
        Task task2 = new Task("dos", "1100",
                "2", "16-09-2020", dateInWeek, Importance.MEDIUM,
                "Refer to slides", 1, false);
        Task task3 = new Task("tres", "1100",
                "2", "16-09-2020", "13-11-2019", Importance.LOW,
                "Refer to slides", 2, false);

        if (importance == Importance.HIGH) {
            taskList.addTask(task1);
        } else if (importance == Importance.MEDIUM) {
            taskList.addTask(task2);
        } else if (importance == Importance.LOW) {
            taskList.addTask(task3);
        }
        return taskList;
    }

    private TaskList getForecastTestExpectedTasks(Forecast forecast) {
        String dateInWeek = DateUtils.formatDate(getDateInWeek());
        String todayDateString = DateUtils.formatDate(LocalDate.now());
        TaskList taskList = new TaskList();
        Task task1 = new Task("uno", "1100",
                "2", todayDateString, todayDateString, Importance.HIGH,
                "Refer to slides", 0, false);
        Task task2 = new Task("dos", "1100",
                "2", "16-09-2020", dateInWeek, Importance.MEDIUM,
                "Refer to slides", 1, false);
        Task task3 = new Task("tres", "1100",
                "2", "16-09-2020", "13-11-2019", Importance.LOW,
                "Refer to slides", 2, false);

        if (forecast == Forecast.ALL) {
            taskList.addTask(task1);
            taskList.addTask(task2);
            taskList.addTask(task3);
        } else if (forecast == Forecast.WEEK) {
            taskList.addTask(task1);
            taskList.addTask(task2);
        } else if (forecast == Forecast.DAY) {
            taskList.addTask(task1);
        }
        return taskList;
    }


    private void setupTestTaskList() {
        String dateInWeek = DateUtils.formatDate(getDateInWeek());
        String todayDateString = DateUtils.formatDate(LocalDate.now());
        testTaskList = new TaskList();
        int index = 0;
        testTaskList.addTask(new Task("uno", "1100",
                "2", todayDateString, todayDateString, Importance.HIGH,
                "Refer to slides", index++, false));
        testTaskList.addTask(new Task("dos", "1100",
                "2", "16-09-2020", dateInWeek, Importance.MEDIUM,
                "Refer to slides", index++, false));
        testTaskList.addTask(new Task("tres", "1100",
                "2", "16-09-2020", "13-11-2019", Importance.LOW,
                "Refer to slides", index++, false));
    }

    private LocalDate getDateInWeek() {
        LocalDate date = DateUtils.getFirstDayOfWeek();
        if (date.equals(LocalDate.now())) {
            date = date.plusDays(1);
        }
        return date;
    }
}
