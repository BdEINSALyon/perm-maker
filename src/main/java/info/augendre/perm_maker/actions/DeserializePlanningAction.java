package info.augendre.perm_maker.actions;

import info.augendre.perm_maker.data.Planning;
import info.augendre.perm_maker.gui.DefinePlanningDialog;

import javax.swing.*;
import java.io.Serializable;

/**
 * Created by gaugendre on 31/07/2016 13:37
 */
public class DeserializePlanningAction extends AbstractDeserializeAction {
    private static final long serialVersionUID = -1147713428016357223L;

    public DeserializePlanningAction(DefinePlanningDialog dialog) {
        super(dialog);
    }

    @Override
    public void update() throws ClassCastException {
        Object element = getDeserialized();
        if (!(element instanceof Planning)) {
            throw new ClassCastException();
        }

        Planning newPlanning = (Planning) element;
        getDialog().resetTasks();
        getDialog().addAllTasks(newPlanning);
    }

    @Override
    public DefinePlanningDialog getDialog() {
        return (DefinePlanningDialog) super.getDialog();
    }
}
