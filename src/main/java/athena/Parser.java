package athena;

import athena.commands.*;
import athena.exceptions.CommandException;
import athena.exceptions.DeleteNoIndexException;
import athena.exceptions.DoneNoIndexException;
import athena.exceptions.EditNoIndexException;
import athena.exceptions.InvalidCommandException;
import athena.exceptions.TaskNotFoundException;

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
        String durationDefault = "1 hour";
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
                taskList.getTaskFromNumber(number).getStartTime());
        String duration = getParameterDesc(taskInfo, DURATION_DELIMITER, durationPos,
                taskList.getTaskFromNumber(number).getDuration());
        String deadline = getParameterDesc(taskInfo, DEADLINE_DELIMITER, deadlinePos,
                taskList.getTaskFromNumber(number).getDeadline());
        String recurrence = getParameterDesc(taskInfo, RECURRENCE_DELIMITER, recurrencePos,
                taskList.getTaskFromNumber(number).getRecurrence());
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
            throws InvalidCommandException {
        String importanceDefault = "ALL";
        String forecastDefault = "TODAY";
        String importance = getParameterDesc(taskInfo, IMPORTANCE_DELIMITER, importancePos, importanceDefault);
        String forecast = getParameterDesc(taskInfo, FORECAST_DELIMITER, forecastPos, forecastDefault);
        Command command = new ListCommand(Importance.valueOf(importance.toUpperCase()),
                Forecast.valueOf(forecast.toUpperCase()));
        return command;
    }

    /**
     * Parses user input and recognises what type of command
     * and parameters the user typed.
     *
     * @param input    String representing user input
     * @param taskList Tasks list
     * @return new Command object based on what the user input is
     * @throws CommandException Exception thrown when there is an error when the user inputs a command
     */
    public static Command parse(String input, TaskList taskList) throws CommandException {
        String[] commandAndDetails = input.split(COMMAND_WORD_DELIMITER, 2);
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
            try {
                int taskIndex = Integer.parseInt(taskInfo);
                return new DoneCommand(taskIndex);
            } catch (NumberFormatException e) {
                throw new DoneNoIndexException();
            }
        }

        case "delete": {
            try {
                int taskIndex = Integer.parseInt(taskInfo);
                return new DeleteCommand(taskIndex);
            } catch (NumberFormatException e) {
                throw new DeleteNoIndexException();
            }
        }

        case "sort": {
            try{
                return new AllocateCommand();
            } catch (Exception e) {
                
            }
        }

        case "exit": {
            return new ExitCommand();
        }

        default: {
            return new HelpCommand();
        }
        }
    }
}