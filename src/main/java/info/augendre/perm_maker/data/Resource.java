package info.augendre.perm_maker.data;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.time.DayOfWeek;

/**
 * A resource is generally a person. It has a name and a set of availabilities and can be assigned to {@link Task}.
 * @author gaugendre
 */
public class Resource implements Serializable {
    private static final long serialVersionUID = -4574717259557632640L;
    /**
     * Resource's name
     */
    private String name;

    /**
     * Resources availabilities.
     */
    private Planning availability;

    /**
     * Constructs a default resource with no name (empty string) and an empty planning (not null).
     */
    public Resource() {
        this.name = "";
        this.availability = new Planning();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Planning getAvailability() {
        return availability;
    }

    @Override
    public String toString() {
        return this.name + " : " + this.availability.size() + " availabilities.";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Resource resource = (Resource) o;

        return new EqualsBuilder()
                .append(name, resource.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .toHashCode();
    }

    /**
     * Determines whether the resource is available at the given day for the given task (used for start and end time).
     * @param task The task the resource should be available for.
     * @param day The day the resource should be available.
     * @return true if the resource is available, false otherwise.
     */
    public boolean isAvailableAt(Task task, DayOfWeek day) {
        for (Task t : this.availability.getTasksForDay(day)) {
            if (task.getStartTime().compareTo(t.getStartTime()) <= 0 && task.getEndTime().compareTo(t.getEndTime()) >= 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the number of availabilities of the resource.
     * @return The number of availabilities of the resource.
     */
    public int getNumberOfAvailabilities() {
        return this.availability.size();
    }
}
