package info.augendre.perm_maker.gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import info.augendre.perm_maker.actions.DefinePlanningAction;
import info.augendre.perm_maker.actions.DefineResourcesAction;
import info.augendre.perm_maker.actions.DispatchTasksAction;
import info.augendre.perm_maker.data.Planning;
import info.augendre.perm_maker.data.Resource;
import info.augendre.perm_maker.data.Task;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.time.DayOfWeek;
import java.util.*;

/**
 * Created by gaugendre on 28/06/16
 */
public class MainPanel {
    private JTable permTable;
    private JPanel mainPanel;
    private JButton availabilityButton;
    private JButton planningButton;
    private JButton assignTasksButton;
    private JFrame mainFrame;
    private Planning planning;
    private DefaultTableModel permTableModel;
    private Vector<Object> permTableHeaders;
    private ArrayList<Resource> resources;

    public MainPanel(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.planning = new Planning();
        this.resources = new ArrayList<>();
        $$$setupUI$$$();
        availabilityButton.addActionListener(new DefineResourcesAction(this));
        planningButton.addActionListener(new DefinePlanningAction(this));
        assignTasksButton.addActionListener(new DispatchTasksAction(this));
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(JFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public Planning getPlanning() {
        return planning;
    }

    public void setPlanning(Planning planning) {
        this.planning = planning;
        refreshPlanningDisplay();
    }

    public void refreshPlanningDisplay() {
        Vector<Vector<Object>> data = new Vector<>();
        for (Task t : planning.getTasks()) {
            Map<DayOfWeek, Set<Resource>> displayedResources = new TreeMap<>();
            for (int i = 0; i < t.getNumberOfResources(); i++) {
                Vector<Object> line = new Vector<>(permTable.getColumnCount());
                line.add(t.toTableString());

                for (int j = 1; j < 8; j++) {
                    DayOfWeek currentDay = DayOfWeek.of(j);
                    if (t.getDays().contains(currentDay)) {
                        ArrayList<Resource> candidates = t.getAssignedResources().get(currentDay);
                        boolean assigned = false;
                        if (candidates != null) {
                            for (Resource r : candidates) {
                                Set<Resource> resources = displayedResources.get(currentDay);
                                if (resources == null) {
                                    resources = new HashSet<>();
                                    displayedResources.put(currentDay, resources);
                                }
                                if (!resources.contains(r)) {
                                    resources.add(r);
                                    line.add(r.getName());
                                    assigned = true;
                                    break;
                                }
                            }
                        }
                        if (!assigned) {
                            line.add("MISSING");
                        }
                    } else {
                        line.add("--");
                    }
                }
                data.add(line);
            }
        }
        permTableModel.setDataVector(data, permTableHeaders);
    }

    private void createUIComponents() {
        permTableHeaders = new Vector<>();
        permTableHeaders.add("Tasks");
        for (int i = 1; i < 8; i++) {
            permTableHeaders.add(DayOfWeek.of(i));
        }
        permTable = new JTable(new PermTableModel(permTableHeaders, 0)) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);
                String value = (String) getModel().getValueAt(row, column);
                if (value.equals("MISSING")) {
                    comp.setBackground(Color.RED);
                } else if (value.equals("--")) {
                    comp.setBackground(Color.LIGHT_GRAY);
                } else if (column == 0) {
                    comp.setBackground(Color.WHITE);
                } else {
                    comp.setBackground(Color.GREEN);
                }
                return comp;
            }
        };
        permTableModel = (DefaultTableModel) permTable.getModel();
    }

    public ArrayList<Resource> getResources() {
        return resources;
    }

    public void setResources(ArrayList<Resource> resources) {
        this.resources = resources;
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        final JScrollPane scrollPane1 = new JScrollPane();
        mainPanel.add(scrollPane1, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane1.setViewportView(permTable);
        planningButton = new JButton();
        this.$$$loadButtonText$$$(planningButton, ResourceBundle.getBundle("strings").getString("define_planning"));
        mainPanel.add(planningButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        availabilityButton = new JButton();
        this.$$$loadButtonText$$$(availabilityButton, ResourceBundle.getBundle("strings").getString("define_availability"));
        mainPanel.add(availabilityButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        assignTasksButton = new JButton();
        this.$$$loadButtonText$$$(assignTasksButton, ResourceBundle.getBundle("strings").getString("assign"));
        mainPanel.add(assignTasksButton, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private void $$$loadButtonText$$$(AbstractButton component, String text) {
        StringBuffer result = new StringBuffer();
        boolean haveMnemonic = false;
        char mnemonic = '\0';
        int mnemonicIndex = -1;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '&') {
                i++;
                if (i == text.length()) break;
                if (!haveMnemonic && text.charAt(i) != '&') {
                    haveMnemonic = true;
                    mnemonic = text.charAt(i);
                    mnemonicIndex = result.length();
                }
            }
            result.append(text.charAt(i));
        }
        component.setText(result.toString());
        if (haveMnemonic) {
            component.setMnemonic(mnemonic);
            component.setDisplayedMnemonicIndex(mnemonicIndex);
        }
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}
