package info.augendre.perm_maker.actions;

import info.augendre.perm_maker.data.Planning;
import info.augendre.perm_maker.data.Resource;
import info.augendre.perm_maker.data.Task;
import info.augendre.perm_maker.gui.MainPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Created by gaugendre on 04/07/16
 */
public class DispatchTasksAction extends AbstractAction {
    private MainPanel mainPanel;

    public DispatchTasksAction(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Planning planning = this.mainPanel.getPlanning();
        planning.clearAssignments();

        for (Task t : planning.getTasks()) {
            for (DayOfWeek d : t.getDays()) {
                ArrayList<Resource> allResources = new ArrayList<>(mainPanel.getResources());
                ArrayList<Resource> notMuchAvailableResources = allResources.stream().filter(r -> r.getNumberOfAvailabilities() <= 4).collect(Collectors.toCollection(ArrayList::new));
                notMuchAvailableResources.sort((o1, o2) -> o1.getNumberOfAvailabilities() - o2.getNumberOfAvailabilities());

                int numberOfResourcesAssignedToTask = 0;
                boolean assigned = false;
                for (Resource r : notMuchAvailableResources) {
                    if (canBeAssigned(r, t, d, planning, numberOfResourcesAssignedToTask)) {
                        t.addAssignedResource(r, d);
                        numberOfResourcesAssignedToTask++;
                        assigned = true;
                    }
                }
                if (!assigned) {
                    notMuchAvailableResources.forEach(allResources::remove);
                    Collections.shuffle(allResources);
                    for (Resource r : allResources) {
                        if (canBeAssigned(r, t, d, planning, numberOfResourcesAssignedToTask)) {
                            t.addAssignedResource(r, d);
                            numberOfResourcesAssignedToTask++;
                        }
                    }
                }
            }
        }
        mainPanel.refreshPlanningDisplay();
    }

    private boolean canBeAssigned(Resource r, Task t, DayOfWeek d, Planning p, int countAssigned) {
        return countAssigned < t.getNumberOfResources() && p.isResourceAvailableForTask(r, t, d);
    }
}
