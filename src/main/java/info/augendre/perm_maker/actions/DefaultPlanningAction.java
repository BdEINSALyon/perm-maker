package info.augendre.perm_maker.actions;

import info.augendre.perm_maker.data.Task;
import info.augendre.perm_maker.gui.DefinePlanningDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by gaugendre on 29/07/2016 11:57
 */
public class DefaultPlanningAction extends AbstractAction {
    private DefinePlanningDialog dialog;

    public DefaultPlanningAction(DefinePlanningDialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dialog.resetTasks();
        Set<DayOfWeek> weekDays = new HashSet<>();
        weekDays.add(DayOfWeek.MONDAY);
        weekDays.add(DayOfWeek.TUESDAY);
        weekDays.add(DayOfWeek.WEDNESDAY);
        weekDays.add(DayOfWeek.THURSDAY);
        weekDays.add(DayOfWeek.FRIDAY);

        LocalTime startTime = LocalTime.of(12, 0);
        LocalTime endTime = LocalTime.of(13, 0);
        dialog.addTask(new Task("Accueil", new HashSet<>(weekDays), startTime, endTime, 2));
        dialog.addTask(new Task("COOP", new HashSet<>(weekDays), startTime, endTime, 1));

        startTime = LocalTime.of(13, 0);
        endTime = LocalTime.of(14, 0);
        dialog.addTask(new Task("Accueil", new HashSet<>(weekDays), startTime, endTime, 1));
        dialog.addTask(new Task("COOP", new HashSet<>(weekDays), startTime, endTime, 1));

        weekDays.clear();
        weekDays.add(DayOfWeek.MONDAY);
        weekDays.add(DayOfWeek.TUESDAY);
        weekDays.add(DayOfWeek.WEDNESDAY);

        startTime = LocalTime.of(18, 0);
        endTime = LocalTime.of(19, 0);
        dialog.addTask(new Task("Accueil", new HashSet<>(weekDays), startTime, endTime, 1));
        dialog.addTask(new Task("COOP", new HashSet<>(weekDays), startTime, endTime, 1));

        weekDays.clear();
        weekDays.add(DayOfWeek.THURSDAY);

        startTime = LocalTime.of(14, 0);
        endTime = LocalTime.of(15, 0);
        dialog.addTask(new Task("COOP", new HashSet<>(weekDays), startTime, endTime, 1));

        startTime = LocalTime.of(15, 0);
        endTime = LocalTime.of(16, 0);
        dialog.addTask(new Task("COOP", new HashSet<>(weekDays), startTime, endTime, 1));
    }
}
