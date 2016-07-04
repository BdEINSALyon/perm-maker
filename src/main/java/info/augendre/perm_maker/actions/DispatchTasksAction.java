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
                int countAssigned = 0;
                ArrayList<Resource> resources = new ArrayList<>(mainPanel.getResources());
                Collections.shuffle(resources);
                for (Resource r : resources) {
                    if (countAssigned < t.getNumberOfResources() && planning.isResourceAvailableForTask(r, t, d)) {
                        t.addAssignedResource(r, d);
                        countAssigned++;
                    }
                }
            }
            System.out.println(t.getAssignedResources());
        }
        mainPanel.refreshPlanningDisplay();
    }
}
