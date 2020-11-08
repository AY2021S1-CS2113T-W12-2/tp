package athena.task;

import athena.exceptions.InvalidDeadlineException;
import athena.exceptions.InvalidRecurrenceException;
import athena.exceptions.TaskDuringSleepTimeException;
import athena.exceptions.TaskTooLongException;

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
public class TimeData implements Comparable<TimeData> {

    private static final int DATE_TIME_FORMAT = 5;
    private static final LocalTime WAKE_TIME = LocalTime.of(8, 0);
    private static final LocalTime SLEEP_TIME = LocalTime.of(0, 0);
    private boolean isFlexible;
    private LocalTime startTime = null;
    private int duration;
    private LocalTime endTime;
    private String deadline;
    private LocalDate deadlineDate;
    private String recurrence;
    private ArrayList<LocalDate> recurrenceDates = new ArrayList<>();

    public TimeData(boolean isFlexible, LocalTime startTime, int duration, String deadline, String recurrence)
            throws TaskDuringSleepTimeException, InvalidDeadlineException, InvalidRecurrenceException {
        if (startTime != null) {
            this.startTime = startTime;
            this.endTime = startTime.plusHours(duration);
            if (isClashWithSleep()) {
                throw new TaskDuringSleepTimeException();
            }
        }
        this.isFlexible = isFlexible;
        this.duration = duration;
        this.deadline = deadline;
        setDeadlineDate(deadline);
        this.recurrence = recurrence;
        setRecurrence(recurrence);
    }

    public TimeData(Boolean isFlexible, String startTime, String duration, String deadline, String recurrence)
            throws TaskDuringSleepTimeException, DateTimeParseException, TaskTooLongException,
            InvalidRecurrenceException, InvalidDeadlineException {
        this.duration = Integer.parseInt(duration);
        this.isFlexible = isFlexible;
        this.deadline = deadline;
        setDeadlineDate(deadline);
        this.recurrence = recurrence;
        setRecurrence(recurrence);
        if (startTime.length() > 0) {
            this.startTime = LocalTime.parse(startTime, DateTimeFormatter.ofPattern("HHmm"));
            this.endTime = this.startTime.plusHours(this.duration);
            if (isClashWithSleep()) {
                throw new TaskDuringSleepTimeException();
            } else if (this.duration > 16) {
                throw new TaskTooLongException(this.duration);
            }
        }
    }

    private boolean isClashWithSleep() {
        return !isNoClashWithSleep();
    }

    private boolean isNoClashWithSleep() {
        return startTime.compareTo(WAKE_TIME) >= 0
                && !(endTime.compareTo(WAKE_TIME) < 0 && endTime.compareTo(SLEEP_TIME) > 0)
                && duration <= 16;
    }

    public TimeData getClone()
            throws TaskDuringSleepTimeException, InvalidRecurrenceException, InvalidDeadlineException {
        return new TimeData(isFlexible, startTime, duration, deadline, recurrence);
    }

    public LocalDate getDeadlineDate() {
        return deadlineDate;
    }

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
        }
    }

    private void addDates(LocalDate startDate) {
        for (int i = 0; i < 10; i++) { // Max number of weeks now is 10 to prevent infinite recurrence
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
            }
            recurrenceDates.add(date);
        } catch (DateTimeParseException e) {
            throw new InvalidRecurrenceException();
        }
    }

    private void setDeadlineDate(String deadline) throws InvalidDeadlineException {
        if (!deadline.equals("No deadline")) {
            try {
                LocalDate date = getDate(deadline);
                if (deadline.length() == "dd-MM".length()) {
                    this.deadline = deadline + "-" + date.getYear();
                }
                this.deadlineDate = date;
            } catch (DateTimeParseException e) {
                throw new InvalidDeadlineException();
            } catch (NumberFormatException e) {
                throw new InvalidDeadlineException();
            }
        }
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
        if (currentDate.getMonthValue() > month) {
            year = currentDate.getYear() + 1;
        } else if (currentDate.getMonthValue() == month
                && currentDate.getDayOfMonth() > day) {
            year = currentDate.getYear() + 1;
        } else {
            year = currentDate.getYear();
        }
        return year;
    }

    public ArrayList<LocalDate> getRecurrenceDates() {
        return recurrenceDates;
    }

    /**
     * Returns start time of the task.
     *
     * @return Start time of task
     */


    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

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


    public Boolean getFlexible() {
        return isFlexible;
    }

    @Override
    public int compareTo(TimeData o) {
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
        TimeData time = (TimeData) o;

        return isFlexible == time.isFlexible
                && Objects.equals(startTime, time.startTime)
                && Objects.equals(duration, time.duration)
                && Objects.equals(deadline, time.deadline)
                && Objects.equals(recurrenceDates, time.recurrenceDates);
    }

    //    @Override
    //    public int compareTo(Time o) {
    //        if(this.startTime>o.startTime){
    //            return 1;
    //        }
    //        return 0;
    //    }
}