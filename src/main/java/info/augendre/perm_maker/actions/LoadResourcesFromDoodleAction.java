package info.augendre.perm_maker.actions;

import info.augendre.perm_maker.data.Planning;
import info.augendre.perm_maker.data.Resource;
import info.augendre.perm_maker.data.Task;
import info.augendre.perm_maker.gui.DefineResourcesDialog;
import info.augendre.perm_maker.utils.Utils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Determines the action to run when loading resources from Doodle (.xls export).
 * Loaded in {@link DefineResourcesDialog}.
 *
 * @author gaugendre
 */
public class LoadResourcesFromDoodleAction extends AbstractAction {
    private DefineResourcesDialog dialog;

    public LoadResourcesFromDoodleAction(DefineResourcesDialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            FileDialog dialog;
            String mHomeDir = System.getProperty("user.home");

            dialog = new FileDialog(this.dialog, "Open", FileDialog.LOAD);
            dialog.setDirectory(mHomeDir);
            dialog.setLocationRelativeTo(null);
            dialog.setModal(true);
            dialog.setVisible(true);

            if (dialog.getFile() != null) {
                String dir = dialog.getDirectory();
                File file = new File(dir + dialog.getFile());
                FileInputStream fis = new FileInputStream(file);

                POIFSFileSystem fs = new POIFSFileSystem(fis);
                Workbook wb = new HSSFWorkbook(fs);
                Sheet sheet = wb.getSheetAt(0);

                // Will hold the link between a column number and the associated day of week.
                // Useful when parsing the tasks.
                Map<Integer, DayOfWeek> colDayMap = new HashMap<>();

                List<CellRangeAddress> merged = sheet.getMergedRegions();
                for (CellRangeAddress range : merged) {
                    int row = range.getFirstRow();
                    int col = range.getFirstColumn();
                    Cell cell = sheet.getRow(row).getCell(col);
                    if (cell != null) {
                        DayOfWeek day = Utils.dayOfWeekFromString(cell.toString());
                        if (day != null) {
                            for (int i = col; i <= range.getLastColumn(); i++) {
                                colDayMap.put(i, day);
                            }
                        }
                    }
                }

                // Will hold the link between a column number and the associated task.
                Map<Integer, Task> colTaskMap = new HashMap<>();

                boolean inHeader = true; // Are we in the header (before answers) ?
                boolean metBCell = false; // Have we met a line where first cell is in B column ?
                boolean metDays = false; // Have we met the days row ?
                boolean passedDays = false; // Have we passed the days row ?

                for (Row row : sheet) {
                    if (row != null) {
                        // In the header, parse the hours and create tasks
                        if (inHeader) {
                            for (Cell cell : row) {
                                if (cell != null) {
                                    if (cell.getAddress().getColumn() == 1) {
                                        metBCell = true;
                                    }
                                    else if (cell.getAddress().getColumn() == 0 && metBCell) {
                                        // We leave the header as soon as we get a first cell in A column.
                                        inHeader = false;
                                    }
                                    if (inHeader && metBCell) {
                                        if (!passedDays) {
                                            boolean isDay = Utils.dayOfWeekFromString(cell.toString()) != null;
                                            if (!metDays) metDays = isDay;
                                            // We leave the days row as soon as we can't parse days anymore
                                            passedDays = metDays && !isDay;
                                        }
                                        // We can only parse hours after days line
                                        if (passedDays) {
                                            String[] hours = cell.toString().split(" – ");
                                            String[] startHour = hours[0].split(":");
                                            LocalTime startTime = LocalTime.of(
                                                Integer.parseInt(startHour[0]),
                                                Integer.parseInt(startHour[1])
                                            );
                                            LocalTime endTime = LocalTime.MIDNIGHT;
                                            if (hours.length == 2) {
                                                String[] endHour = hours[1].split(":");
                                                endTime = LocalTime.of(
                                                    Integer.parseInt(endHour[0]),
                                                    Integer.parseInt(endHour[1])
                                                );
                                            }
                                            // Create a task from cell parsing
                                            Task parsedTask = new Task();
                                            parsedTask.setLabel("dispo");
                                            parsedTask.setNumberOfResources(0);
                                            parsedTask.addDay(colDayMap.get(cell.getAddress().getColumn()));
                                            parsedTask.setStartTime(startTime);
                                            parsedTask.setEndTime(endTime);

                                            colTaskMap.put(cell.getAddress().getColumn(), parsedTask);
                                        }
                                    }
                                }
                            }
                        }
                        if (!inHeader) {
                            // After leaving the header, parse the resources and their availabilities
                            Resource resource = new Resource();
                            for (Cell cell : row) {
                                if (cell != null) {
                                    if (cell.getAddress().getColumn() == 0) {
                                        resource.setName(cell.toString());
                                    }
                                    else {
                                        Planning resourcePlanning = resource.getAvailability();
                                        if (cell.toString().contains("OK")) {
                                            resourcePlanning.add(colTaskMap.get(cell.getAddress().getColumn()));
                                        }
                                    }
                                }
                            }
                            this.dialog.addResource(resource);
                        }
                    }
                }
            }
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}