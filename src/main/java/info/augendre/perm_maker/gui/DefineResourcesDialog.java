package info.augendre.perm_maker.gui;

import info.augendre.perm_maker.actions.AddResourceAction;
import info.augendre.perm_maker.data.Resource;
import info.augendre.perm_maker.data.Task;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class DefineResourcesDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JList<Object> resourcesList;
    private JButton addResourceButton;
    private JButton removeResourceButton;
    private List<Resource> resources;

    public DefineResourcesDialog(List<Resource> resources) {
        this.resources = resources;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onOK();
            }
        });

        contentPane.registerKeyboardAction(e -> onOK(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        addResourceButton.addActionListener(new AddResourceAction(this));

        removeResourceButton.addActionListener(actionEvent -> {
            if (!resourcesList.isSelectionEmpty()) {
                removeResource((Resource) resourcesList.getSelectedValue());
            }
        });
    }

    private void refreshResourcesList() {
        resourcesList.setListData(resources.toArray());
        this.pack();
        this.setLocationRelativeTo(null);
    }

    public void addResource(Resource r) {
        resources.add(r);
        refreshResourcesList();
    }

    public void removeResource(Resource r) {
        resources.remove(r);
        refreshResourcesList();
    }

    private void onOK() {
// add your code here
        dispose();
    }

    private void createUIComponents() {
        resourcesList = new JList<>();
    }
}
