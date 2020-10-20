package athena;

import athena.task.Task;

import java.util.Comparator;

public class SortByStartTime implements Comparator<Task> {

    @Override
    public int compare(Task o1, Task o2) {
        if (Integer.parseInt(o1.getTimeInfo().getStartTime()) > Integer.parseInt(o2.getTimeInfo().getStartTime())) {
            return 1;
        } else if (Integer.parseInt(o1.getTimeInfo().getStartTime()) < Integer.parseInt(o2.getTimeInfo().getStartTime())) {
            return -1;
        }
        return 0;
    }
}
