package info.augendre.perm_maker.data;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Holds the list of tasks that need resource dispatching.
 * @author gaugendre
 */
public class Planning extends ArrayList<Task> implements Serializable {

    private static final long serialVersionUID = 6838374758027598445L;

    public Planning() {
        super();
    }

    @Deprecated
    public ArrayList<Task> getTasks() {
        return this;
    }

    @Deprecated
    public void resetTasks() {
        this.clear();
    }

    @Deprecated
    public void addTask(Task task) {
        this.add(task);
    }

    @Deprecated
    public void removeTask(Task task) {
        this.remove(task);
    }

    /**
     * Determines if a resource is available for the given task on a specific day of week.
     * @param resource The resource to determine if available or not.
     * @param task The task (holds the start and end time).
     * @param day The day of week where the resource should be available.
     * @return true if the resource is available, false otherwise.
     * @see Resource#isAvailableAt(Task, DayOfWeek)
     */
    public boolean isResourceAvailableForTask(Resource resource, Task task, DayOfWeek day) {
        // First check if the resource has availability at this time.
        if (!resource.isAvailableAt(task, day)) {
            return false;
        }

        // Then, we need to count its assignments in the planning.
        int countAssigments = 0;
        for (Task t : this) {
            // For each task in this planning,
            for (ArrayList<Resource> resourcesList : t.getAssignedResources().values()) {
                // Get the resources assigned to each day
                for (Resource r : resourcesList) {
                    // Count the assignments of our resource
                    if (r.equals(resource))
                        countAssigments++;
                }
            }
        }

        // Our resource is available if it has not been already assigned twice this week
        // and it's not already assigned today.
        return countAssigments < 2 && !getResourcesForDay(day).contains(resource);
    }

    /**
     * Get all tasks that have an occurrence at the given day of week.
     * @param day The day of week to check.
     * @return A {@link List} containing all tasks in this planning that have an occurrence the given {@code day}
     */
    public List<Task> getTasksForDay(DayOfWeek day) {
        return this.stream().filter(t -> t.getDays().contains(day)).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Get all resources that have been assigned at the given day of week.
     * @param day The day of week to check.
     * @return A {@link List} containing all resources in this planning that been assigned at the given {@code day}
     */
    public List<Resource> getResourcesForDay(DayOfWeek day) {
        ArrayList<Resource> resources = new ArrayList<>();
        this.stream().filter(t -> t.getDays().contains(day)).forEach(t -> {
            ArrayList<Resource> res = t.getAssignedResources().get(day);
            if (res != null) {
                resources.addAll(res);
            }
        });
        return resources;
    }

    /**
     * Clear all assignments to all tasks in this planning.
     */
    public void clearAssignments() {
        this.forEach(Task::clearAssignments);
    }

    /**
     * <p>
     *     Get a sorted list of availabilities ready to use in {@link info.augendre.perm_maker.gui.ResourceDialog}.
     * </p>
     * <p>
     *     These availabilities are the same tasks as in this planning but with only one day of occurrence.
     *     Therefore, if in this planning a task has occurrences on Monday and Tuesday, the resulting list will contain
     *     two tasks : one that has an occurrence on Monday and the other on Tuesday.
     * </p>
     * <p>
     *     The availabilities are sorted by day of week, then by start time.
     * </p>
     *
     * @return A sorted {@link List} of {@link Task}, one per day of occurrence of tasks in this planning.
     */
    public List<Task> getAvailabilitiesList() {
        Set<Task> tasksSet = new HashSet<>();
        // Create tasks based on tasks in this planning with only one day of occurrence
        // but with same start time and end time
        for (Task t : this) {
            for (DayOfWeek d : t.getDays()) {
                Task otherTask = new Task();
                otherTask.setLabel("dispo");
                otherTask.setNumberOfResources(0);
                otherTask.addDay(d);
                otherTask.setStartTime(t.getStartTime());
                otherTask.setEndTime(t.getEndTime());
                tasksSet.add(otherTask);
            }
        }

        Task[] tasks = tasksSet.toArray(new Task[0]);
        ArrayList<Task> tasksList = new ArrayList<>(Arrays.asList(tasks));

        // Sort the array by day then start time
        tasksList.sort((task, t1) -> {
            DayOfWeek taskDay = task.getDays().iterator().next();
            DayOfWeek t1Day = t1.getDays().iterator().next();

            if (taskDay.getValue() < t1Day.getValue()) {
                return -1;
            } else if (taskDay.getValue() == t1Day.getValue()) {
                return task.getStartTime().compareTo(t1.getStartTime());
            } else {
                return 1;
            }
        });

        return tasksList;
    }
}
