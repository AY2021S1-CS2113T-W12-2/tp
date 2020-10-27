package athena.logic;

import athena.Importance;
import athena.TaskList;
import athena.exceptions.CommandException;
import athena.exceptions.DeleteNoIndexException;
import athena.exceptions.DoneNoIndexException;
import athena.exceptions.EditNoIndexException;
import athena.exceptions.InvalidCommandException;
import athena.exceptions.InvalidForecastException;
import athena.exceptions.InvalidImportanceException;
import athena.exceptions.TaskNotFoundException;
import athena.exceptions.ViewNoIndexException;
import athena.logic.commands.AddCommand;
import athena.logic.commands.Command;
import athena.logic.commands.DeleteCommand;
import athena.logic.commands.DoneCommand;
import athena.logic.commands.EditCommand;
import athena.logic.commands.ExitCommand;
import athena.logic.commands.HelpCommand;
import athena.logic.commands.ListCommand;
import athena.logic.commands.ViewCommand;
import athena.task.taskfilter.FilterCalculator;

import java.util.HashMap;

/**
 * Handles parsing of user input.
 */
public class Parser {

    public static final String COMMAND_WORD_DELIMITER = " ";
    public static final String NAME_DELIMITER = "n/";
    public static final String TIME_DELIMITER = "t/";
    public static final String DURATION_DELIMITER = "d/";
    public static final String DEADLINE_DELIMITER = "D/";
    public static final String RECURRENCE_DELIMITER = "r/";
    public static final String IMPORTANCE_DELIMITER = "i/";
    public static final String ADDITIONAL_NOTES_DELIMITER = "a/";
    public static final String FORECAST_DELIMITER = "f/";

    /**
     * Get parameters description.
     *
     * @param taskInformation String representing task information
     * @param delimiter       String representing parameter delimiter
     * @param paramPosition   Integer representing position of parameter
     * @param defaultValue    String representing default value
     * @return Description of parameter
     */
    public static String getParameterDesc(String taskInformation, String delimiter, int paramPosition,
                                          String defaultValue) throws InvalidCommandException {
        String param;
        if (paramPosition == -1) {
            param = defaultValue;
        } else {
            String[] retrieveParamInfo = taskInformation.split(delimiter, 2);
            String retrievedParamInfo = retrieveParamInfo[1];
            int paramNextSlash = retrievedParamInfo.indexOf("/");
            if (paramNextSlash == -1) {
                param = retrievedParamInfo;
            } else {
                try {
                    param = retrievedParamInfo.substring(0, (paramNextSlash - 2));
                } catch (StringIndexOutOfBoundsException e) {
                    throw new InvalidCommandException();
                }
            }
        }
        return param;
    }

    /**
     * Parses user input when command is add.
     *
     * @param taskInfo      String representing task information
     * @param namePos       Integer representing position of name parameter
     * @param timePos       Integer representing position of time parameter
     * @param durationPos   Integer representing position of duration parameter
     * @param deadlinePos   Integer representing position of deadline parameter
     * @param recurrencePos Integer representing position of recurrence parameter
     * @param importancePos Integer representing position of importance parameter
     * @param addNotesPos   Integer representing position of additional notes parameter
     * @return command object
     */
    public static Command parseAddCommand(String taskInfo, int namePos, int timePos, int durationPos, int deadlinePos,
                                          int recurrencePos, int importancePos, int addNotesPos)
            throws InvalidCommandException {
        String nullDefault = "";
        String name = getParameterDesc(taskInfo, NAME_DELIMITER, namePos, nullDefault);
        //TODO: allow for empty string, assign flexible attribute, true if string is null, false if filled
        String time = getParameterDesc(taskInfo, TIME_DELIMITER, timePos, nullDefault);
        boolean isFlexible = (time == nullDefault);
        String durationDefault = "1";
        String duration = getParameterDesc(taskInfo, DURATION_DELIMITER, durationPos, durationDefault);
        String deadlineDefault = "No deadline";
        String deadline = getParameterDesc(taskInfo, DEADLINE_DELIMITER, deadlinePos, deadlineDefault);
        String recurrenceDefault = "today";
        String recurrence = getParameterDesc(taskInfo, RECURRENCE_DELIMITER, recurrencePos, recurrenceDefault);
        String importanceDefault = "medium";
        String importance = getParameterDesc(taskInfo, IMPORTANCE_DELIMITER, importancePos, importanceDefault);
        String notesDefault = "No notes";
        String notes = getParameterDesc(taskInfo, ADDITIONAL_NOTES_DELIMITER, addNotesPos, notesDefault);
        Command command = new AddCommand(name, time, duration, deadline, recurrence, importance, notes, isFlexible);

        return command;
    }

    /**
     * Parses user input when command is edit.
     *
     * @param taskInfo      String representing task information
     * @param namePos       Integer representing position of name parameter
     * @param timePos       Integer representing position of time parameter
     * @param durationPos   Integer representing position of duration parameter
     * @param deadlinePos   Integer representing position of deadline parameter
     * @param recurrencePos Integer representing position of recurrence parameter
     * @param importancePos Integer representing position of importance parameter
     * @param addNotesPos   Integer representing position of additional notes parameter
     * @return command object
     * @throws TaskNotFoundException Exception thrown when the program is unable to find a task at the index
     *                               specified by the user
     * @throws EditNoIndexException  Exception thrown when the user does not specify an index of the task they
     *                               want to edit
     */
    public static Command parseEditCommand(String taskInfo, int namePos, int timePos, int durationPos, int deadlinePos,
                                           int recurrencePos, int importancePos, int addNotesPos,
                                           TaskList taskList) throws TaskNotFoundException, EditNoIndexException,
            InvalidCommandException {
        int number = getNumber(taskInfo);

        String name = getParameterDesc(taskInfo, NAME_DELIMITER, namePos,
                taskList.getTaskFromNumber(number).getName());
        String time = getParameterDesc(taskInfo, TIME_DELIMITER, timePos,
                taskList.getTaskFromNumber(number).getTimeInfo().getStartTimeString());
        String duration = getParameterDesc(taskInfo, DURATION_DELIMITER, durationPos,
                taskList.getTaskFromNumber(number).getTimeInfo().getDurationString());
        String deadline = getParameterDesc(taskInfo, DEADLINE_DELIMITER, deadlinePos,
                taskList.getTaskFromNumber(number).getTimeInfo().getDeadline());
        String recurrence = getParameterDesc(taskInfo, RECURRENCE_DELIMITER, recurrencePos,
                taskList.getTaskFromNumber(number).getTimeInfo().getRecurrence());
        String importance = getParameterDesc(taskInfo, IMPORTANCE_DELIMITER, importancePos,
                taskList.getTaskFromNumber(number).getImportance().toString()).toUpperCase();
        String notes = getParameterDesc(taskInfo, ADDITIONAL_NOTES_DELIMITER, addNotesPos,
                taskList.getTaskFromNumber(number).getNotes());

        Command command = new EditCommand(number, name, time, duration, deadline, recurrence,
                Importance.valueOf(importance.toUpperCase()), notes);

        return command;
    }

    /**
     * Parses task information to get task number.
     *
     * @param taskInfo String representing task information
     * @return task number
     * @throws EditNoIndexException Exception thrown when the user does not specify an index of the task they
     *                              want to edit
     */
    private static int getNumber(String taskInfo) throws EditNoIndexException {
        try {
            int numberNextSlash = taskInfo.indexOf("/");
            int number = Integer.parseInt(taskInfo.substring(0, (numberNextSlash - 2)));
            return number;
        } catch (StringIndexOutOfBoundsException | NumberFormatException e) {
            throw new EditNoIndexException();
        }
    }

    /**
     * Parses user input when command is list.
     *
     * @param taskInfo      String representing task information
     * @param importancePos Integer representing position of importance parameter
     * @param forecastPos   Integer representing position of forecast parameter
     * @return command object
     */
    public static Command parseListCommand(String taskInfo, int importancePos, int forecastPos)
            throws InvalidCommandException, InvalidForecastException, InvalidImportanceException {
        String importanceDefault = "ALL";
        String forecastDefault = "WEEK";
        String importanceString = getParameterDesc(taskInfo, IMPORTANCE_DELIMITER, importancePos, importanceDefault);
        String forecastString = getParameterDesc(taskInfo, FORECAST_DELIMITER, forecastPos, forecastDefault);
        FilterCalculator filterCalculator = new FilterCalculator(importanceString, forecastString);
        Command command = new ListCommand(filterCalculator.getImportance(),
                filterCalculator.getForecast());
        return command;
    }

    /**
     * Parses user input when command is done.
     *
     * @param taskInfo      String representing task information
     * @return command object
     */
    public static Command parseDoneCommand(String taskInfo) throws CommandException {
        try {
            int taskIndex = Integer.parseInt(taskInfo);
            return new DoneCommand(taskIndex);
        } catch (NumberFormatException e) {
            throw new DoneNoIndexException();
        }
    }

    /**
     * Parses user input when command is delete.
     *
     * @param taskInfo      String representing task information
     * @return command object
     */
    public static Command parseDeleteCommand(String taskInfo) throws CommandException {
        try {
            int taskIndex = Integer.parseInt(taskInfo);
            return new DeleteCommand(taskIndex);
        } catch (NumberFormatException e) {
            throw new DeleteNoIndexException();
        }
    }

    /**
     * Parses user input when command is view.
     *
     * @param taskInfo      String representing task information
     * @return command object
     */
    public static Command parseViewCommand(String taskInfo) throws CommandException {
        try {
            int taskIndex = Integer.parseInt(taskInfo);
            return new ViewCommand(taskIndex);
        } catch (NumberFormatException e) {
            throw new ViewNoIndexException();
        }
    }

    /**
     * Parses user input for shortcut commands.
     *
     * @param userInput String representing command and information of task
     * @return actual input meaning string
     */
    public static String parseShortcutCommands(String userInput) {
        HashMap<String, String> shortcutCommandsWithDetails = new HashMap<String, String>();
        shortcutCommandsWithDetails.put("a", "add");
        shortcutCommandsWithDetails.put("e", "edit");
        shortcutCommandsWithDetails.put("l", "list");
        shortcutCommandsWithDetails.put("dn", "done");
        shortcutCommandsWithDetails.put("dl", "delete");
        shortcutCommandsWithDetails.put("v", "view");
        shortcutCommandsWithDetails.put("ex", "exit");
        shortcutCommandsWithDetails.put("l3", "list i/HIGH");
        shortcutCommandsWithDetails.put("l2", "list i/MEDIUM");
        shortcutCommandsWithDetails.put("l1", "list i/LOW");
        shortcutCommandsWithDetails.put("lw", "list f/WEEK");
        shortcutCommandsWithDetails.put("ld", "list f/DAY");
        shortcutCommandsWithDetails.put("lm", "list f/MONTH");

        String actualInputMeaning = userInput;
        if (shortcutCommandsWithDetails.get(actualInputMeaning) != null) {
            actualInputMeaning = shortcutCommandsWithDetails.get(actualInputMeaning);
        }
        return actualInputMeaning;
    }

    /**
     * Parses user input to split shortcut command and task information.
     *
     * @param userInput String representing task information
     * @return task information string
     */
    public static String parseShortcutCommandAndDetails(String userInput) {
        String[] commandAndDetails = userInput.split(COMMAND_WORD_DELIMITER, 2);
        String shortcutInput = parseShortcutCommands(commandAndDetails[0]);
        String remainingTaskInfo = "";
        if (commandAndDetails.length > 1) {
            remainingTaskInfo = commandAndDetails[1];
        }
        String fullInput = shortcutInput + " " + remainingTaskInfo;
        fullInput = fullInput.trim();
        return fullInput;
    }

    /**
     * Parses user input and recognises what type of command
     * and parameters the user typed.
     *
     * @param userInput    String representing user input
     * @param taskList Tasks list
     * @return new Command object based on what the user input is
     * @throws CommandException Exception thrown when there is an error when the user inputs a command
     */
    public static Command parse(String userInput, TaskList taskList) throws CommandException {
        String fullInput = parseShortcutCommandAndDetails(userInput);
        String[] commandAndDetails = fullInput.split(COMMAND_WORD_DELIMITER, 2);
        String commandType = commandAndDetails[0];
        String taskInfo = "";
        if (commandAndDetails.length > 1) {
            taskInfo = commandAndDetails[1];
        }

        int namePos = taskInfo.indexOf(NAME_DELIMITER);
        int timePos = taskInfo.indexOf(TIME_DELIMITER);
        int durationPos = taskInfo.indexOf(DURATION_DELIMITER);
        int deadlinePos = taskInfo.indexOf(DEADLINE_DELIMITER);
        int recurrencePos = taskInfo.indexOf(RECURRENCE_DELIMITER);
        int importancePos = taskInfo.indexOf(IMPORTANCE_DELIMITER);
        int addNotesPos = taskInfo.indexOf(ADDITIONAL_NOTES_DELIMITER);
        int forecastPos = taskInfo.indexOf(FORECAST_DELIMITER);

        switch (commandType) {
        //TODO: add dep, to make 1 task dependent on another. "dep TaskNumber1 Tasknumber2"
        case "add": {
            return parseAddCommand(taskInfo, namePos, timePos, durationPos, deadlinePos,
                    recurrencePos, importancePos, addNotesPos);
        }

        case "edit": {
            return parseEditCommand(taskInfo, namePos, timePos, durationPos, deadlinePos,
                    recurrencePos, importancePos, addNotesPos, taskList);
        }

        case "list": {
            return parseListCommand(taskInfo, importancePos, forecastPos);
        }

        case "done": {
            return parseDoneCommand(taskInfo);
        }

        case "delete": {
            return parseDeleteCommand(taskInfo);
        }

        case "view": {
            return parseViewCommand(taskInfo);
        }

        case "help": {
            return new HelpCommand();
        }

        case "exit": {
            return new ExitCommand();
        }

        default: {
            throw new InvalidCommandException();
        }
        }
    }
}