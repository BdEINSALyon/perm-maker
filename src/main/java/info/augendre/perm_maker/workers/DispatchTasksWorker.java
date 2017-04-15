package info.augendre.perm_maker.workers;

import info.augendre.perm_maker.data.Planning;
import info.augendre.perm_maker.data.Resource;
import info.augendre.perm_maker.data.Task;
import info.augendre.perm_maker.gui.MainPanel;
import info.augendre.perm_maker.utils.Utils;

import javax.swing.*;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * @author gaugendre
 */
public class DispatchTasksWorker extends SwingWorker<Planning, Void> {

    private int iterations;
    private MainPanel mainPanel;

    public DispatchTasksWorker(int iterations, MainPanel mainPanel) {
        this.iterations = iterations;
        this.mainPanel = mainPanel;
    }

    @Override
    protected Planning doInBackground() throws Exception {
        Planning p = this.mainPanel.getPlanning();
        int bestMatchCount = p.getNumberOfResourcesNeeded();

        Planning bestPlanning = null;

        for (int i = 0; i < iterations; i++) {
            Planning planning = this.mainPanel.getPlanning();
            planning.clearAssignments();

            for (Task t : planning) {
                for (DayOfWeek d : t.getDays()) {
                    ArrayList<Resource> allResources = new ArrayList<>(mainPanel.getResources());
                    ArrayList<Resource> notMuchAvailableResources = allResources.stream().filter(r -> r.getNumberOfAvailabilities() <= 4).collect(Collectors.toCollection(ArrayList::new));
                    notMuchAvailableResources.sort(Comparator.comparingInt(Resource::getNumberOfAvailabilities));

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
                        allResources.removeAll(notMuchAvailableResources);
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

            int missing = planning.getNumberOfMissingResources();
            if (missing < bestMatchCount) {
                bestPlanning = (Planning) Utils.copy(planning);
                bestMatchCount = missing;
            }
        }

        return bestPlanning;
    }

    @Override
    protected void done() {
        Planning bestPlanning;
        try {
            bestPlanning = get();
        }
        catch (InterruptedException | ExecutionException e) {
            bestPlanning = null;
            System.err.println("Error retrieving value from Excel parsing. Thread may have been interrupted.");
        }
        if (bestPlanning != null) {
            this.mainPanel.setPlanning(bestPlanning);
            this.mainPanel.refreshPlanningDisplay();
        }
    }

    private boolean canBeAssigned(Resource r, Task t, DayOfWeek d, Planning p, int countAssigned) {
        return countAssigned < t.getNumberOfResources() && p.isResourceAvailableForTask(r, t, d);
    }
}
