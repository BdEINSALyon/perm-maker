package info.augendre.perm_maker.data;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by gaugendre on 28/06/16
 */
public class Planning extends ArrayList<Task> implements Serializable {

    private static final long serialVersionUID = 6838374758027598445L;

    public Planning() {
        super();
    }

    public ArrayList<Task> getTasks() {
        return this;
    }

    public void resetTasks() {
        this.clear();
    }

    public void addTask(Task task) {
        this.add(task);
    }

    public void removeTask(Task task) {
        this.remove(task);
    }

    public boolean isResourceAvailableForTask(Resource resource, Task task, DayOfWeek day) {
        if (!resource.isAvailableAt(task, day)) {
            return false;
        }
        int countAssigments = 0;
        for (Task t : this) {
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
        return this.stream().filter(t -> t.getDays().contains(day)).collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Resource> getResourcesForDay(DayOfWeek day) {
        ArrayList<Resource> resources = new ArrayList<>();
        this.stream().filter(t -> t.getDays().contains(day)).forEach(t -> {
            ArrayList<Resource> res = t.getAssignedResources().get(day);
            if (res != null) {
                resources.addAll(t.getAssignedResources().get(day));
            }
        });
        return resources;
    }

    public void clearAssignments() {
        this.forEach(Task::clearAssignments);
    }

    public List<Task> getAvailabilitiesList() {
        Set<Task> tasksSet = new HashSet<>();
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
