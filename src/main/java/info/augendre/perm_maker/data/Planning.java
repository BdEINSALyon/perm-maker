package info.augendre.perm_maker.data;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by gaugendre on 28/06/16
 */
public class Planning {
    private ArrayList<Task> tasks;

    public Planning() {
        this.tasks = new ArrayList<>();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void resetTasks() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public void removeTask(Task task) {
        this.tasks.remove(task);
    }

    public boolean isResourceAvailableForTask(Resource resource, Task task, DayOfWeek day) {
        if (!resource.isAvailableAt(task, day)) {
            return false;
        }
        int countAssigments = 0;
        for (Task t : this.tasks) {
            for (ArrayList<Resource> resourcesList : t.getAssignedResources().values()) {
                for (Resource r : resourcesList) {
                    if (r.equals(resource))
                        countAssigments++;
                }
            }
        }

        return countAssigments < 2 && !getResourcesForDay(day).contains(resource);
    }

    public List<Task> getTasksForDay(DayOfWeek day) {
        return this.tasks.stream().filter(t -> t.getDays().contains(day)).collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Resource> getResourcesForDay(DayOfWeek day) {
        ArrayList<Resource> resources = new ArrayList<>();
        this.tasks.stream().filter(t -> t.getDays().contains(day)).forEach(t -> {
            ArrayList<Resource> res = t.getAssignedResources().get(day);
            if (res != null) {
                resources.addAll(t.getAssignedResources().get(day));
            }
        });
        return resources;
    }

    public void clearAssignments() {
        this.tasks.forEach(Task::clearAssignments);
    }
}
