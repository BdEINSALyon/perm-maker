package info.augendre.perm_maker.data;

import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * Created by gaugendre on 28/06/16
 */
public class Task {
    private String label;
    private DayOfWeek day;
    private LocalTime time_start;
    private LocalTime time_end;
    private int numberOfResources;

    public Task() {
        this.label = "";
        this.day = DayOfWeek.SUNDAY;
        this.time_start = LocalTime.MIDNIGHT;
        this.time_end = LocalTime.MIDNIGHT;
        this.numberOfResources = 0;
    }

    public Task(String label, DayOfWeek day, LocalTime time_start, LocalTime time_end, int numberOfResources) {
        this.label = label;
        this.day = day;
        this.time_start = time_start;
        this.time_end = time_end;
        this.numberOfResources = numberOfResources;
    }

    @Override
    public String toString() {
        return label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    public LocalTime getTime_start() {
        return time_start;
    }

    public void setTime_start(LocalTime time_start) {
        this.time_start = time_start;
    }

    public LocalTime getTime_end() {
        return time_end;
    }

    public void setTime_end(LocalTime time_end) {
        this.time_end = time_end;
    }

    public int getNumberOfResources() {
        return numberOfResources;
    }

    public void setNumberOfResources(int numberOfResources) {
        this.numberOfResources = numberOfResources;
    }
}
