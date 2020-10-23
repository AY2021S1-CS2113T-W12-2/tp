package athena.timetable;

import athena.Forecast;
import athena.TaskList;
import athena.task.Task;
import athena.task.taskfilter.ForecastFilter;
import athena.task.taskfilter.ImportanceFilter;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;
import java.util.TreeMap;
import java.util.function.Function;

/**
 * Takes a TaskList to generate a timetable for the user.
 */
public class Timetable {
    public static final String DAY_BOX_HORIZONTAL_BORDER = "-------";
    public static final String BOX_CORNER = "+";
    public static final String TIME_HEADER_HORIZONTAL_BORDER = "---------";
    public static final String TASK_BOX_HORIZONTAL_BORDER = TIME_HEADER_HORIZONTAL_BORDER + "-";
    public static final String DATE_BOX = "| %02d/%02d |";
    public static final String TASK_NUMBER_LABEL = "[%d]";
    public static final String DAY_BOX = "|  %s  |";
    public static final String EMPTY_TASK_BOX = "          |";
    public static final String TASK_BOX = " %s|";

    private TaskList taskList;
    private ArrayList<TimetableDay> timetableDays;
    private TreeMap<LocalDate, TimetableDay> timetableDayMap;

    private Forecast forecast;

    private int wakeUpHour = 8;
    private int sleepHour = 22;

    /**
     * Creates a timetable object from a TaskList object.
     *
     * @param taskList Task list
     */
    public Timetable(TaskList taskList) {
        this(taskList, 8, 22);
    }

    /**
     * Creates a timetable object from a TaskList object.
     *
     * @param taskList   Task list
     * @param sleepHour  Hour to sleep
     * @param wakeUpHour Hour to wake up
     */
    public Timetable(TaskList taskList, int wakeUpHour, int sleepHour) {
        assert taskList != null;
        this.taskList = taskList;
        populateTimetable();

        this.wakeUpHour = wakeUpHour;
        this.sleepHour = sleepHour;

        this.forecast = Forecast.WEEK;
    }

    /**
     * Creates a timetable object from a TaskList, ImportanceFilter and ForecastFilter object.
     *
     * @param taskList         Task list
     * @param importanceFilter Filters tasks of a certain importance
     * @param forecastFilter   Filters tasks based on forecast
     */
    public Timetable(TaskList taskList, ImportanceFilter importanceFilter, ForecastFilter forecastFilter) {
        assert taskList != null;
        this.taskList = taskList.getFilteredList(importanceFilter).getFilteredList(forecastFilter);
        this.forecast = forecastFilter.getForecast();
        populateTimetable();
    }

    public int getWakeUpHour() {
        return wakeUpHour;
    }

    public void setWakeUpHour(int wakeUpHour) {
        this.wakeUpHour = wakeUpHour;
    }

    public int getSleepHour() {
        return sleepHour;
    }

    public void setSleepHour(int sleepHour) {
        this.sleepHour = sleepHour;
    }

    /**
     * Getter for timetableDays.
     *
     * @return timetableDays.
     */
    public ArrayList<TimetableDay> getTimetableDays() {
        return timetableDays;
    }

    /**
     * Setter for timetableDays.
     */
    public void setTimetableDays(ArrayList<TimetableDay> timetableDays) {
        this.timetableDays = timetableDays;
    }

    /**
     * Populates the timetable, represented by a list of TimetableDays with the information from the task list.
     * For this version, we only populate the timetable with the tasks for this week (starting from Monday).
     */
    private void populateTimetable() {
        this.timetableDays = new ArrayList<TimetableDay>();

        timetableDayMap = new TreeMap<LocalDate, TimetableDay>();

        for (Task task : taskList.getTasks()) {
            ArrayList<LocalDate> dates = task.getDates();
            for (LocalDate date : dates) {
                assert date != null;

                TimetableDay timetableDay;
                if (timetableDayMap.containsKey(date)) {
                    timetableDay = timetableDayMap.get(date);
                } else {
                    timetableDay = new TimetableDay(date);
                    timetableDayMap.put(date, timetableDay);
                }

                timetableDay.addTask(task);
            }
        }

        for (LocalDate key : timetableDayMap.keySet()) {
            timetableDays.add(timetableDayMap.get(key));
        }
    }

    /**
     * Generates the timetable header containing hour marks.
     * For example, +------08------09------10------11------+
     *
     * @param startHour The starting hour in 24-hour representation.
     * @param endHour   The ending hour in 24-hour representation.
     * @return A string containing the timetable header with hour marks.
     */
    private String drawTimetableTimeHeader(int startHour, int endHour) {
        String header = "";

        header += BOX_CORNER + DAY_BOX_HORIZONTAL_BORDER;
        for (int hour = startHour; hour < endHour; hour++) {
            String paddedHourString = String.format("%02d", hour);
            header += paddedHourString + TIME_HEADER_HORIZONTAL_BORDER;
        }
        header += BOX_CORNER + "\n";

        return header;
    }

    /**
     * Generates the bottom border for a day in the timetable.
     *
     * @param startHour The starting hour in 24-hour representation.
     * @param endHour   The ending hour in 24-hour representation.
     * @return A string that represents the bottom border for a day in the timetable.
     */
    private String drawBottomBorder(int startHour, int endHour) {
        String row = "";
        row += BOX_CORNER + DAY_BOX_HORIZONTAL_BORDER + BOX_CORNER;
        for (int i = startHour; i < endHour; i++) {
            row += TASK_BOX_HORIZONTAL_BORDER + BOX_CORNER;
        }
        row += "\n";
        return row;
    }

    /**
     * Utility method to fit a string into a given length.
     * If the string is shorter than desired, pad it with spaces.
     * If the string is longer than desired, truncate it and replace the end with "..".
     *
     * @param string The string to fit.
     * @param maxLen The desired length.
     * @return The string that is modified to fit into a given length.
     */
    private String shortenOrPadString(String string, int maxLen) {
        if (string.length() == maxLen) {
            return string;
        } else if (string.length() < maxLen) {
            return String.format("%" + -maxLen + "s", string);
        }
        return String.format("%s..", string.substring(0, maxLen - 2));
    }

    /**
     * Finds a task in a task list that starts at the given hour.
     *
     * @param taskList Task list to search through.
     * @param hour     Starting time of the task.
     * @return The task that starts at the given hour. Null if doesn't exist.
     */
    private Task findTaskAtHour(TaskList taskList, int hour) {
        for (Task task : taskList.getTasks()) {
            int taskHour = task.getTimeInfo().getStartTime().getHour();
            if (taskHour == hour) {
                return task;
            }
        }
        return null;
    }

    /**
     * Generates a row for a day in the timetable containing the task information desired.
     *
     * @param day            The TimetableDay object for this day.
     * @param startHour      The starting hour in 24-hour representation.
     * @param endHour        The ending hour in 24-hour representation.
     * @param taskInfoWriter A Function that extracts the information desired from a task.
     * @return A string containing the task information desired.
     */
    private String drawTimetableDayRow(TimetableDay day, int startHour, int endHour,
                                       Function<Task, String> taskInfoWriter) {
        String row = "";

        for (int hour = startHour; hour < endHour; hour++) {
            Task task = findTaskAtHour(day.getTaskList(), hour);
            if (task == null) {
                row += EMPTY_TASK_BOX;
                continue;
            }

            int duration = task.getTimeInfo().getDuration();
            // TODO: better handle tasks exceeding sleep time, currently it just cuts off at sleep time
            duration = Math.min(duration, endHour - hour);
            hour += duration - 1;
            int boxWidth = duration * (TASK_BOX_HORIZONTAL_BORDER + BOX_CORNER).length() - 2;
            row += String.format(TASK_BOX, shortenOrPadString(taskInfoWriter.apply(task), boxWidth));
        }

        return row;
    }

    /**
     * Generates the first row for a day in the timetable, which contains the name of the day and the task names.
     *
     * @param day       The TimetableDay object for this day.
     * @param startHour The starting hour in 24-hour representation.
     * @param endHour   The ending hour in 24-hour representation.
     * @return A string containing name of the day and the task names.
     */
    private String drawTimetableDayFirstRow(TimetableDay day, int startHour, int endHour) {
        String dayShortName = day.getDate().getDayOfWeek().toString().substring(0, 3).toUpperCase();
        String row = String.format(DAY_BOX, dayShortName);
        row += drawTimetableDayRow(day, startHour, endHour, new Function<Task, String>() {
            @Override
            public String apply(Task task) {
                return task.getName();
            }
        });
        row += "\n";
        return row;
    }

    /**
     * Generates the second row for a day in the timetable, which contains the date of the day and the task numbers.
     *
     * @param day       The TimetableDay object for this day.
     * @param startHour The starting hour in 24-hour representation.
     * @param endHour   The ending hour in 24-hour representation.
     * @return A string containing date of the day and the task numbers.
     */
    private String drawTimetableDaySecondRow(TimetableDay day, int startHour, int endHour) {
        LocalDate date = day.getDate();
        String row = String.format(DATE_BOX, date.getDayOfMonth(), date.getMonthValue());

        row += drawTimetableDayRow(day, startHour, endHour, new Function<Task, String>() {
            @Override
            public String apply(Task task) {
                return String.format(TASK_NUMBER_LABEL, task.getNumber());
            }
        });

        row += "\n";
        return row;
    }

    /**
     * Generates the row representing a day in the timetable.
     *
     * @param day       The TimetableDay object for this day.
     * @param startHour The starting hour in 24-hour representation.
     * @param endHour   The ending hour in 24-hour representation.
     * @return A string containing information about a day in the timetable.
     */
    private String drawTimetableDay(TimetableDay day, int startHour, int endHour) {
        String row = "";
        row += drawTimetableDayFirstRow(day, startHour, endHour);
        row += drawTimetableDaySecondRow(day, startHour, endHour);
        row += drawBottomBorder(startHour, endHour);
        return row;
    }

    private String getTaskListForDates(ArrayList<LocalDate> dates) {
        String list = "Your task list: \n";
        for (LocalDate date : dates) {
            if (timetableDayMap.containsKey(date)) {
                list += timetableDayMap.get(date);
            } else {
                list += new TimetableDay(date);
            }
            list += "\n";
        }
        return list;
    }

    String drawTimetable(ArrayList<LocalDate> datesInWeek) {
        String result = drawTimetableTimeHeader(wakeUpHour, sleepHour);
        for (LocalDate date : datesInWeek) {
            if (timetableDayMap.containsKey(date)) {
                result += drawTimetableDay(timetableDayMap.get(date), wakeUpHour, sleepHour);
            } else {
                result += drawTimetableDay(new TimetableDay(date), wakeUpHour, sleepHour);
            }
        }
        return result;
    }

    /**
     * Generates a string to show the user the timetable.
     *
     * @return A string representing the timetable.
     */
    @Override
    public String toString() {
        ArrayList<LocalDate> dates = getDatesBasedOnForecast();
        String output = drawTimetable(dates);
        output += "\n";
        output += getTaskListForDates(dates);

        return output.trim();
    }

    /**
     * Utility method to get the first day of this week.
     *
     * @return The first day of this week.
     */
    private LocalDate getFirstDayOfWeek() {
        LocalDate now = LocalDate.now();
        TemporalField weekField = WeekFields.of(Locale.forLanguageTag("en_SG")).dayOfWeek();
        return now.with(weekField, 1);
    }

    /**
     * Utility method to get the first day of this month.
     *
     * @return The first day of this month.
     */
    private LocalDate getFirstDayOfMonth() {
        LocalDate now = LocalDate.now();
        return now.withDayOfMonth(1);
    }

    private ArrayList<LocalDate> getDatesBasedOnForecast() {
        ArrayList<LocalDate> dates = new ArrayList<LocalDate>();
        LocalDate startDate;
        LocalDate endDate;

        if (forecast == Forecast.WEEK) {
            startDate = getFirstDayOfWeek();
            endDate = startDate.plusWeeks(1);
        } else if (forecast == Forecast.TODAY) {
            startDate = LocalDate.now();
            endDate = startDate.plusDays(1);
        } else {
            startDate = getFirstDayOfMonth();
            endDate = startDate.plusMonths(1);
        }

        for (LocalDate currentDate = startDate; currentDate.compareTo(endDate) != 0;
             currentDate = currentDate.plusDays(1)) {
            dates.add(currentDate);
        }

        return dates;
    }
}
