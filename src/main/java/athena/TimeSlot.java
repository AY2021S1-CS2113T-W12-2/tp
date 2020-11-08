package athena;

import athena.exceptions.allocator.NoNextSlotException;

public class TimeSlot {
    private Log dayLog;
    private int wake = 8;
    private final int sleep = 22;
    private int start = wake;
    private int end = wake;

    public TimeSlot(Log dayLog) {
        this.dayLog = dayLog;
    }

    public void findNextSlot() throws NoNextSlotException {
        if (start == sleep) {
            throw new NoNextSlotException();
        }
        start = end;
        for (; start < sleep; start++) {
            if (dayLog.getStart(start) == -1) {
                break;
            }
        }
        end = start;
        for (; end < sleep; end++) {
            if (dayLog.getStart(end) != -1) {
                break;
            }
        }
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public void setWake(int wake) {
        this.wake = wake;
        this.start = wake;
        this.end = wake;
    }
}
