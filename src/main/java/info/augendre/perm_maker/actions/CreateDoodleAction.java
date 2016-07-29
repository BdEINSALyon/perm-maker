package info.augendre.perm_maker.actions;

import info.augendre.perm_maker.data.Planning;
import info.augendre.perm_maker.data.Task;
import info.augendre.perm_maker.gui.DefinePlanningDialog;
import info.augendre.perm_maker.utils.Utils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

/**
 * Created by gaugendre on 29/07/2016 13:51
 */
public class CreateDoodleAction extends AbstractAction {
    private DefinePlanningDialog dialog;
    private Planning planning;

    public CreateDoodleAction(DefinePlanningDialog dialog, Planning planning) {
        this.dialog = dialog;
        this.planning = planning;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String link = "https://doodle.com/create?" +
            "type=date" +
            "&name=SG" +
            "&levels=3" +
            "&locale=fr" +
            "&title=Perms";

        if (planning.size() > 0) {
            List<Task> tasks = planning.getAvailabilitiesList();
            Calendar day = Calendar.getInstance();

            // Set calendar to next monday
            while (day.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                day.add(Calendar.DATE, 1);
            }

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
            LocalDate date = Utils.localDateFromDate(day.getTime());

            StringBuilder times = new StringBuilder();
            times.append("&").append(date.format(dateFormatter)).append("=");
            Task previousTask = tasks.get(0);
            boolean writePipes = false;
            for (Task currentTask : tasks) {
                int dayDiff = currentTask.getDays().iterator().next().getValue() - previousTask.getDays().iterator().next().getValue();
                if (dayDiff > 0) {
                    day.add(Calendar.DATE, dayDiff);
                    date = Utils.localDateFromDate(day.getTime());
                    times.append("&").append(date.format(dateFormatter)).append("=");
                    writePipes = false;
                }
                if (writePipes) {
                    times.append("%7C%7C");
                }
                times
                    .append(currentTask.getStartTime().format(timeFormatter))
                    .append("-")
                    .append(currentTask.getEndTime().format(timeFormatter));

                writePipes = true;
                previousTask = currentTask;
            }
            link += times.toString();
        }


        JEditorPane editorPane = Utils.htmlEditorPaneFactory(
            "<a href=\"" + link + "\">Doodle</a>"
        );

        JOptionPane.showMessageDialog(
            dialog,
            editorPane,
            "Doodle",
            JOptionPane.PLAIN_MESSAGE
        );
    }
}
