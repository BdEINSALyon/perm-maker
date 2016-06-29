package info.augendre.perm_maker.data;

import java.util.ArrayList;

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
}
