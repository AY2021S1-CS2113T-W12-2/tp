package athena;

import athena.exceptions.command.CommandException;
import athena.task.Task;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TimeAllocatorTest {

    @Test
    void runAllocate_overCapacityDay_validSchedule() throws CommandException {
        TaskList taskList = new TaskList();
        taskList.addTask(2, "Assignment 3", "0800", "5", "01-01-2021", "today",
                Importance.MEDIUM, "Tough assignment", true);
        taskList.addTask(3, "Assignment 4", "1300", "2", "01-01-2021", "today",
                Importance.MEDIUM, "Tough assignment", false);
        taskList.addTask(4, "Assignment 5", "2000", "2", "01-01-2021", "today",
                Importance.MEDIUM, "Tough assignment", true);
        taskList.addTask(6, "Assignment 7", "1700", "3", "01-01-2021", "today",
                Importance.MEDIUM, "Tough assignment", false);
        taskList.addTask(7, "Assignment 8", "1500", "1", "01-01-2021", "today",
                Importance.MEDIUM, "Tough assignment", true);

        TaskList messyTaskList = new TaskList();

        messyTaskList.addTask(2, "Assignment 3", "1900", "5", "01-01-2021", "today",
                Importance.MEDIUM, "Tough assignment", true);
        messyTaskList.addTask(3, "Assignment 4", "1300", "2", "01-01-2021", "today",
                Importance.MEDIUM, "Tough assignment", false);
        messyTaskList.addTask(4, "Assignment 5", "1600", "2", "01-01-2021", "today",
                Importance.MEDIUM, "Tough assignment", true);
        messyTaskList.addTask(6, "Assignment 7", "1700", "3", "01-01-2021", "today",
                Importance.MEDIUM, "Tough assignment", false);
        messyTaskList.addTask(7, "Assignment 8", "1500", "1", "01-01-2021", "today",
                Importance.MEDIUM, "Tough assignment", true);

        TimeAllocator allocator = new TimeAllocator(messyTaskList);
        allocator.runAllocate();
        ArrayList<Task> a = taskList.getTasks();
        ArrayList<Task> b = messyTaskList.getTasks();
        for (int i =0; i < taskList.getTaskListSize();i++){
            Task a1 = a.get(i);
            Task b1 = b.get(i);
            assertEquals(a.get(i),b.get(i));
        }

        assertEquals(messyTaskList, taskList);


    }
}