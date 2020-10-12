package athena.commands;

import athena.Forecast;
import athena.Importance;
import athena.TaskList;
import athena.Ui;
import athena.task.taskfilter.ForecastFilter;
import athena.task.taskfilter.ImportanceFilter;
import athena.task.taskfilter.TaskFilter;
import athena.timetable.Timetable;

import java.util.Objects;

/**
 * Handles the list command.
 */
public class ListCommand extends Command {
    private Importance taskImportance;
    private Forecast taskForecast;

    public ListCommand(Importance importance, Forecast forecast) {
        taskImportance = importance;
        taskForecast = forecast;
    }


    /**
     * Calls TaskList to filter the list based on importance and
     * calls Ui to print the list of tasks.
     *
     * @param taskList Tasks List
     * @param ui Ui
     */
    @Override
    public void execute(TaskList taskList, Ui ui) {
        ImportanceFilter importanceFilter = new ImportanceFilter(taskImportance);
        ForecastFilter forecastFilter = new ForecastFilter(taskForecast);
        Timetable timetable = new Timetable(taskList, importanceFilter, forecastFilter);
        ui.printTimetable(timetable);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ListCommand)) {
            return false;
        }
        ListCommand that = (ListCommand) o;
        return taskImportance == that.taskImportance && taskForecast == that.taskForecast;
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskImportance, taskForecast);
    }
}
