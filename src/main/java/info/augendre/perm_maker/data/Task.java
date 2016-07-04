package info.augendre.perm_maker.data;

import info.augendre.perm_maker.utils.Utils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.*;

/**
 * Created by gaugendre on 28/06/16
 */
public class Task {
    private Map<DayOfWeek, ArrayList<Resource>> assignedResources;
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
        this.assignedResources = new HashMap<>();
    }

    public Task(String label, Set<DayOfWeek> days, LocalTime startTime, LocalTime endTime, int numberOfResources, Map<DayOfWeek, ArrayList<Resource>> assignedResources) {
        this.label = label;
        this.days = days;
        this.startTime = startTime;
        this.endTime = endTime;
        this.numberOfResources = numberOfResources;
        this.assignedResources = assignedResources;
    }

    @Override
    public String toString() {
        return label + " (" + days + " " +
                startTime.get(ChronoField.HOUR_OF_DAY) + ":" + startTime.get(ChronoField.MINUTE_OF_HOUR) + " - " +
                endTime.get(ChronoField.HOUR_OF_DAY) + ":" + endTime.get(ChronoField.MINUTE_OF_HOUR) + ")";
    }

    public String toTableString() {
        return label + " (" + startTime.get(ChronoField.HOUR_OF_DAY) + ":" + startTime.get(ChronoField.MINUTE_OF_HOUR) + " - " +
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

    public Map<DayOfWeek, ArrayList<Resource>> getAssignedResources() {
        return assignedResources;
    }

    public void setAssignedResources(Map<DayOfWeek, ArrayList<Resource>> assignedResources) {
        this.assignedResources = assignedResources;
    }

    public void addAssignedResource(Resource resource, DayOfWeek day) {
        ArrayList<Resource> dayResources = this.assignedResources.get(day);
        if (dayResources == null) {
            dayResources = new ArrayList<>(this.numberOfResources);
            this.assignedResources.put(day, dayResources);
        }
        dayResources.add(resource);
    }

    public void clearAssignments() {
        this.assignedResources.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        return new EqualsBuilder()
                .append(numberOfResources, task.numberOfResources)
                .append(label, task.label)
                .append(days, task.days)
                .append(startTime, task.startTime)
                .append(endTime, task.endTime)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(label)
                .append(days)
                .append(startTime)
                .append(endTime)
                .append(numberOfResources)
                .toHashCode();
    }
}
