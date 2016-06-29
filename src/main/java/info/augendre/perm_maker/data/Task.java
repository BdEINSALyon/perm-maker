package info.augendre.perm_maker.data;

import info.augendre.perm_maker.utils.Utils;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Date;

/**
 * Created by gaugendre on 28/06/16
 */
public class Task {
    private String label;
    private DayOfWeek day;
    private LocalTime startTime;
    private LocalTime endTime;
    private int numberOfResources;

    public Task() {
        this.label = "";
        this.day = DayOfWeek.SUNDAY;
        this.startTime = LocalTime.MIDNIGHT;
        this.endTime = LocalTime.MIDNIGHT;
        this.numberOfResources = 0;
    }

    public Task(String label, DayOfWeek day, LocalTime startTime, LocalTime endTime, int numberOfResources) {
        this.label = label;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
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

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setStartTime(Date date) {
        this.startTime = Utils.localTimeFromDate(date);
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setEndTime(Date date) {
        this.endTime = Utils.localTimeFromDate(date);
    }

    public int getNumberOfResources() {
        return numberOfResources;
    }

    public void setNumberOfResources(int numberOfResources) {
        this.numberOfResources = numberOfResources;
    }
}
