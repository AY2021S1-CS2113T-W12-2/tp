package athena;

import athena.task.Task;
import athena.task.taskfilter.FlexibleTimeFilter;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class TimeAllocator {
    ArrayList<Task> flexibleTask;
    ArrayList<Task> fixedTask;
    LocalDateTime currentTime;
    LocalDateTime nextTime;
    int duration;

    /**
     * This is run manually by the user to generate a timetable that they like.
     * option to auto assign at every input, reassignment at the end is possible
     * need to maintain the flexibility of the tasks to allow for multiple reruns
     *
     * @param tasks tasks in the current TaskList
     */
    public TimeAllocator(TaskList tasks) {
        this.flexibleTask = tasks.getFilteredList(new FlexibleTimeFilter(true)).getTasks();
        this.fixedTask = tasks.getFilteredList(new FlexibleTimeFilter(false)).getTasks();

    }

    public TimeAllocator() {

    }

    public void runAllocator() {
        fixedTask.sort(new SortByStartTime());
        System.out.print(fixedTask);
    }


}

// Psuedocode for allocation runs might refactor into a command class
//    public void runAllocate() {
//        Collections.sort(fixedTask, earlier());
//        Collections.sort(flexibleTask,priority());
//        //priority sorts according to importance(high to low) then duration(high to low)
//        //might need toposort
//
//        for (int i = 0; i < fixedTask.size() - 1; i++) {
//            currentTime = fixedTask.get(i).getStartTime() + fixedTask.get(i).getDuration();
//            nextTime = fixedTask.get(i + 1).getStartTime();
//            duration = nextTime-currentTime;
//
//            // select task with best fit into the time
//            // select important task then long tasks then check for space
//            // if there is no time left then continue
//            // if not then choose another task that can go in
//            // ?? not sure if want to go through the entire list to get 0 time wastage
//            // failure cases will be unable to meet deadline
//            // set time for flexible tasks
//            // repeat until all flexible tasks are assigned
//
//
//        }
//    }



