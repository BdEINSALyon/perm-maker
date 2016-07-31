package info.augendre.perm_maker.actions;

import info.augendre.perm_maker.data.Planning;
import info.augendre.perm_maker.gui.DefinePlanningDialog;

import java.io.Serializable;

/**
 * Created by gaugendre on 31/07/2016 13:37
 */
public class DeserializePlanningAction extends AbstractDeserializeAction {
    private static final long serialVersionUID = -1147713428016357223L;
    private DefinePlanningDialog dialog;

    public DeserializePlanningAction(DefinePlanningDialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public void update() throws ClassCastException {
        Object element = getDeserialized();
        if (!(element instanceof Planning)) {
            throw new ClassCastException();
        }

        Planning newPlanning = (Planning) element;
        dialog.resetTasks();
        dialog.addAllTasks(newPlanning);
    }
}
