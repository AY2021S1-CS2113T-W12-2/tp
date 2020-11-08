package athena.task;

import athena.exceptions.command.InvalidDeadlineException;
import athena.exceptions.command.InvalidRecurrenceException;
import athena.exceptions.command.TaskDuringSleepTimeException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Objects;

/*
 * Stores information related to time.
 * examples include startTime, duration, deadline
 * Recurrence and recurrenceDate can also be stored here
 * setters and getter are needed
 * new things to add ifFlexibleTime to let the TimeAllocate change the time values
 */
public class Time implements Comparable<Time> {

    private static final int DATE_TIME_FORMAT = 5;
    private static final LocalTime WAKE_TIME = LocalTime.of(8,0);
    private static final LocalTime SLEEP_TIME = LocalTime.of(0,0);
    private boolean isFlexible;
    private LocalTime startTime;
    private int duration;
    private LocalTime endTime;

    private String deadline;
    private LocalDate deadlineDate;

    private String recurrence;
    private ArrayList<LocalDate> recurrenceDates = new ArrayList<>();

    /**
     * Constructor for time class.
     *
     * @param isFlexible    time flexibility
     * @param startTime     starting time of task
     * @param duration      how long the task is scheduled to last for
     * @param deadline      when the task is due
     * @param recurrence    when the task occurs/repeats
     * @throws TaskDuringSleepTimeException Exception thrown when task clashes with sleep time
     * @throws InvalidRecurrenceException   Exception thrown when user mistypes recurrence
     * @throws InvalidDeadlineException     Exception thrown when user mistypes deadline
     */
    public Time(boolean isFlexible, LocalTime startTime, int duration, String deadline, String recurrence)
            throws TaskDuringSleepTimeException, InvalidDeadlineException, InvalidRecurrenceException {

        setIsFlexible(isFlexible);
        setDuration(duration);
        setDeadline(deadline);
        setDeadlineDate(deadline);
        setRecurrence(recurrence);
        setTime(startTime);
    }

    /**
     * Constructor for time class.
     *
     * @param isFlexible    time flexibility
     * @param startTime     starting time of task
     * @param duration      how long the task is scheduled to last for
     * @param deadline      when the task is due
     * @param recurrence    when the task occurs/repeats
     * @throws TaskDuringSleepTimeException Exception thrown when task clashes with sleep time
     * @throws InvalidRecurrenceException   Exception thrown when user mistypes recurrence
     * @throws InvalidDeadlineException     Exception thrown when user mistypes deadline
     */
    public Time(Boolean isFlexible, String startTime, String duration, String deadline, String recurrence)
            throws TaskDuringSleepTimeException,
            InvalidRecurrenceException, InvalidDeadlineException {
        setIsFlexible(isFlexible);
        setDuration(duration);
        setDeadline(deadline);
        setDeadlineDate(deadline);
        setRecurrence(recurrence);

        if (isNotEmpty(startTime)) {
            setTime(startTime);
        }

    }

    private boolean isNotEmpty(String startTime) {
        return startTime.length() > 0;
    }

    private void setTime(String startTime) throws TaskDuringSleepTimeException {
        this.startTime = LocalTime.parse(startTime, DateTimeFormatter.ofPattern("HHmm"));
        setEndTime();
    }

    public void setTime(LocalTime startTime) throws TaskDuringSleepTimeException {
        if (startTime != null) {
            this.startTime = startTime;
            setEndTime();
        }
    }

    private void setEndTime() throws TaskDuringSleepTimeException {
        endTime = startTime.plusHours(duration);
        if (isClashWithSleep()) {
            throw new TaskDuringSleepTimeException();
        }
    }

    private void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    private void setDuration(String duration) {
        this.duration = Integer.parseInt(duration);
    }

    private void setDuration(int duration) {
        this.duration = duration;
    }

    private void setIsFlexible(Boolean isFlexible) {
        this.isFlexible = isFlexible;
    }

    private boolean isClashWithSleep() {
        return !isNoClashWithSleep();
    }

    /**
     * Check if a task is scheduled to be between 12am and 8am.
     *
     * @return whether the task clashes with sleep time
     */
    private boolean isNoClashWithSleep() {
        return startTime.compareTo(WAKE_TIME) >= 0
                && !(endTime.compareTo(WAKE_TIME) < 0 && endTime.compareTo(SLEEP_TIME) > 0)
                && duration <= 16;
    }

    public Time getClone()
            throws TaskDuringSleepTimeException, InvalidRecurrenceException, InvalidDeadlineException {
        return new Time(isFlexible, startTime, duration, deadline, recurrence);
    }


    /**
     * Add all dates for when task is supposed to occur in recurrenceDates.
     *
     * @param recurrence when the task occurs/repeats
     * @throws InvalidRecurrenceException   Exception thrown when user mistypes recurrence
     */
    public void setRecurrence(String recurrence) throws InvalidRecurrenceException {
        switch (recurrence.toUpperCase()) {
        case "MONDAY":
            LocalDate mondayDate = getFirstDateMatchingDay(DayOfWeek.MONDAY);
            addDates(mondayDate);
            break;
        case "TUESDAY":
            LocalDate tuesdayDate = getFirstDateMatchingDay(DayOfWeek.TUESDAY);
            addDates(tuesdayDate);
            break;
        case "WEDNESDAY":
            LocalDate wednesdayDate = getFirstDateMatchingDay(DayOfWeek.WEDNESDAY);
            addDates(wednesdayDate);
            break;
        case "THURSDAY":
            LocalDate thursdayDate = getFirstDateMatchingDay(DayOfWeek.THURSDAY);
            addDates(thursdayDate);
            break;
        case "FRIDAY":
            LocalDate fridayDate = getFirstDateMatchingDay(DayOfWeek.FRIDAY);
            addDates(fridayDate);
            break;
        case "SATURDAY":
            LocalDate saturdayDate = getFirstDateMatchingDay(DayOfWeek.SATURDAY);
            addDates(saturdayDate);
            break;
        case "SUNDAY":
            LocalDate sundayDate = getFirstDateMatchingDay(DayOfWeek.SUNDAY);
            addDates(sundayDate);
            break;
        default:
            setRecurrenceDate(recurrence);
            return;
        }
        this.recurrence = recurrence;
    }

    /**
     * Add dates of tasks in recurrenceDates for 10 weeks.
     *
     * @param startDate the start date of the task
     */
    private void addDates(LocalDate startDate) {
        for (int i = 0; i < 10; i++) {
            recurrenceDates.add(startDate.plusWeeks(i));
        }
    }

    private LocalDate getFirstDateMatchingDay(DayOfWeek dayOfWeek) {
        LocalDate startDate = LocalDate.now();
        for (int i = 0; i < 6; i++) {
            if (startDate.getDayOfWeek().equals(dayOfWeek)) {
                break;
            } else {
                startDate = startDate.plusDays(1);
            }
        }
        return startDate;
    }

    private void setRecurrenceDate(String recurrence) throws InvalidRecurrenceException {
        try {
            LocalDate date = getDate(recurrence);
            if (recurrence.length() == "dd-MM".length()) {
                this.recurrence = recurrence + "-" + date.getYear();
            } else {
                this.recurrence = recurrence;
            }
            recurrenceDates.add(date);
        } catch (DateTimeParseException e) {
            throw new InvalidRecurrenceException();
        } catch (NumberFormatException e) {
            throw new InvalidRecurrenceException();
        }
    }

    private void setDeadlineDate(String deadline) throws InvalidDeadlineException {
        if (!deadline.equals("No deadline")) {
            trySetHardDeadline(deadline);
        }
    }

    private void trySetHardDeadline(String deadline) throws InvalidDeadlineException {
        try {
            setHardDeadline(deadline);
        } catch (DateTimeParseException e) {
            throw new InvalidDeadlineException();
        } catch (NumberFormatException e) {
            throw new InvalidDeadlineException();
        }
    }

    private void setHardDeadline(String deadline) {
        LocalDate date = getDate(deadline);
        if (deadline.length() == "dd-MM".length()) {
            this.deadline = deadline + "-" + date.getYear();
        }
        this.deadlineDate = date;
    }

    private LocalDate getDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date;
        if (dateString.length() == "dd-MM".length()) {
            int year = getYear(dateString);
            date = LocalDate.parse(dateString + "-"
                    + year, formatter);
        } else {
            date = LocalDate.parse(dateString, formatter);
        }
        return date;
    }



    public void resetRecurrence() {
        recurrenceDates.clear();
    }

    private int getMonth(String recurrence) {
        return Integer.parseInt(recurrence.substring(3, 5));
    }

    private int getDay(String recurrence) {
        return Integer.parseInt(recurrence.substring(0, 2));
    }

    private int getYear(String recurrence) {
        LocalDate currentDate = LocalDate.now();
        int month = getMonth(recurrence);
        int day = getDay(recurrence);
        int year;
        if (isCurrentMonthAhead(currentDate, month)) {
            year = currentDate.getYear() + 1;
        } else if (isCurrentDayAhead(currentDate, month, day)) {
            year = currentDate.getYear() + 1;
        } else {
            year = currentDate.getYear();
        }
        return year;
    }

    private boolean isCurrentDayAhead(LocalDate currentDate, int month, int day) {
        return currentDate.getMonthValue() == month
                && currentDate.getDayOfMonth() > day;
    }

    private boolean isCurrentMonthAhead(LocalDate currentDate, int month) {
        return currentDate.getMonthValue() > month;
    }

    /**
     * Returns the dates of when the task will occur.
     *
     * @return dates the task will occur
     */
    public ArrayList<LocalDate> getRecurrenceDates() {
        return recurrenceDates;
    }


    /**
     * Returns the starting time of a task.
     *
     * @return the start time of the task
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Returns the time a task is expected to finish.
     *
     * @return ending time of a task
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Set the start time of the task.
     *
     * @param startTime Start time of task
     */
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }


    /**
     * Converts the start time to a string.
     *
     * @return Start time of task as a string.
     */
    public String getStartTimeString() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
        if (startTime == null) {
            return "";
        }
        return startTime.format(timeFormatter);
    }

    /**
     * Returns the date or occurrence of task.
     *
     * @return when the task occurs/repeats
     */
    public String getRecurrence() {
        return recurrence;
    }

    /**
     * Returns duration of the task.
     *
     * @return Duration of task
     */
    public int getDuration() {
        return duration;
    }


    /**
     * Converts the duration to a string.
     *
     * @return Duration of task as a string.
     */
    public String getDurationString() {
        return Integer.toString(duration);
    }


    /**
     * Returns due date of the task.
     *
     * @return Due date of task
     */
    public String getDeadline() {
        return deadline;
    }


    @Override
    public int compareTo(Time o) {
        return 0;
    }

    public void edit(String startTime, String duration, String deadline, String recurrence)
            throws InvalidRecurrenceException, InvalidDeadlineException {
        this.startTime = LocalTime.parse(startTime, DateTimeFormatter.ofPattern("HHmm"));
        this.duration = Integer.parseInt(duration);
        this.deadline = deadline;
        setDeadlineDate(deadline);

        this.recurrence = recurrence;
        resetRecurrence();
        setRecurrence(recurrence);
        assert !this.recurrenceDates.equals(null);
    }

    public void removeDate(LocalDate date) {
        recurrenceDates.remove(date);
    }

    /**
     * Compare this time with another object.
     *
     * @param o Object to compare with.
     * @return Whether the object compared with is also a time and has the exact same properties.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Time time = (Time) o;

        return isFlexible == time.isFlexible
                && Objects.equals(startTime, time.startTime)
                && Objects.equals(duration, time.duration)
                && Objects.equals(deadline, time.deadline)
                && Objects.equals(recurrenceDates, time.recurrenceDates);
    }

    public boolean getFlexible() {
        return isFlexible;
    }
}
