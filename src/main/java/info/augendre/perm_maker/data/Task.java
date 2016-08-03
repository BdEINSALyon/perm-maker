package info.augendre.perm_maker.data;

import info.augendre.perm_maker.utils.Utils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.*;

/**
 * A task is something a resource may be assigned to, in order to be completed.
 * A task may repeat itself multiple times in a week.
 */
public class Task implements Serializable {
    private static final long serialVersionUID = -7058683052879161619L;

    /**
     * A map holding the resources assigned to the task for each day of week.
     */
    private Map<DayOfWeek, ArrayList<Resource>> assignedResources;

    /**
     * The task's label (name).
     */
    private String label;

    /**
     * The task's occurrences in the week.
     */
    private Set<DayOfWeek> days;

    /**
     * The task's start time.
     */
    private LocalTime startTime;

    /**
     * The task's end time.
     */
    private LocalTime endTime;

    /**
     * The needed number of resources.
     */
    private int numberOfResources;

    /**
     * Constructs a default task.
     * <ul>
     *     <li>Empty string for label</li>
     *     <li>Empty set for days</li>
     *     <li>Midnight as start and end time</li>
     *     <li>No (0) resource needed</li>
     *     <li>Empty map of assigned resources</li>
     * </ul>
     */
    public Task() {
        this.label = "";
        this.days = new HashSet<>();
        this.startTime = LocalTime.MIDNIGHT;
        this.endTime = LocalTime.MIDNIGHT;
        this.numberOfResources = 0;
        this.assignedResources = new HashMap<>();
    }

    /**
     * Constructs a task given the parameters.
     * @param label The task's label.
     * @param days The task's occurrences in the week.
     * @param startTime The task's start time.
     * @param endTime The task's end time.
     * @param numberOfResources The number of resources needed for the task.
     */
    public Task(String label, Set<DayOfWeek> days, LocalTime startTime, LocalTime endTime, int numberOfResources) {
        this.label = label;
        this.days = days;
        this.startTime = startTime;
        this.endTime = endTime;
        this.numberOfResources = numberOfResources;
        this.assignedResources = new HashMap<>();
    }

    @Override
    public String toString() {
        return label + " (" + days + " " +
                startTime.get(ChronoField.HOUR_OF_DAY) + ":" + startTime.get(ChronoField.MINUTE_OF_HOUR) + " - " +
                endTime.get(ChronoField.HOUR_OF_DAY) + ":" + endTime.get(ChronoField.MINUTE_OF_HOUR) + ")";
    }

    /**
     * Returns a string description without the day of week in order to put in {@link info.augendre.perm_maker.gui.MainPanel}'s table.
     * @return A string description without the day of week.
     */
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

    /**
     * Adds a resource to this task's assignments at the given day.
     * @param resource The resource to add.
     * @param day The day the resource is assigned.
     */
    public void addAssignedResource(Resource resource, DayOfWeek day) {
        ArrayList<Resource> dayResources = this.assignedResources.get(day);
        if (dayResources == null) {
            dayResources = new ArrayList<>(this.numberOfResources);
            this.assignedResources.put(day, dayResources);
        }
        dayResources.add(resource);
    }

    /**
     * Clears the assignments map.
     * @see Map#clear()
     */
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
