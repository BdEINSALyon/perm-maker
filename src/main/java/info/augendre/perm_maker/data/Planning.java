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

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public static Planning sortByDayTime(Planning p) {
        return p;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public void removeTask(Task task) {
        this.tasks.remove(task);
    }

    public boolean isResourceAvailableForTask(Resource resource, Task task, DayOfWeek day) {
//        System.out.println("=============================");
//        System.out.println(day + " (" + task.getStartTime() + " - " + task.getEndTime() + ")\n");
        if (!resource.isAvailableAt(task, day)) {
//            System.out.println(resource.getName() + " not available this time...");
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

//        System.out.println(resource.getName() + " assigned " + countAssigments + " times => " + (countAssigments < 2));
//        System.out.println(resource.getName() + " already assigned today : " + getResourcesForDay(day).contains(resource));

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
