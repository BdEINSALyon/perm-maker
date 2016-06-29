package info.augendre.perm_maker.data;

import info.augendre.perm_maker.utils.Utils;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by gaugendre on 28/06/16
 */
public class Task {
    private String label;
    private Set<DayOfWeek> days;
    private LocalTime startTime;
    private LocalTime endTime;
    private int numberOfResources;

    public Task() {
        this.label = "";
        this.days = new HashSet<>();
        this.startTime = LocalTime.MIDNIGHT;
        this.endTime = LocalTime.MIDNIGHT;
        this.numberOfResources = 0;
    }

    public Task(String label, Set<DayOfWeek> days, LocalTime startTime, LocalTime endTime, int numberOfResources) {
        this.label = label;
        this.days = days;
        this.startTime = startTime;
        this.endTime = endTime;
        this.numberOfResources = numberOfResources;
    }

    @Override
    public String toString() {
        return label + " (" + days + " " +
                startTime.get(ChronoField.HOUR_OF_DAY) + ":" + startTime.get(ChronoField.MINUTE_OF_HOUR) + " - " +
                endTime.get(ChronoField.HOUR_OF_DAY) + ":" + endTime.get(ChronoField.MINUTE_OF_HOUR) + ")";
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Set<DayOfWeek> getDays() {
        return days;
    }

    public void setDays(Set<DayOfWeek> days) {
        this.days = days;
    }

    public void addDay(DayOfWeek day) {
        this.days.add(day);
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
