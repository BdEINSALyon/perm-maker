package info.augendre.perm_maker.actions;

import info.augendre.perm_maker.data.Availabilities;
import info.augendre.perm_maker.data.Planning;
import info.augendre.perm_maker.data.Resource;
import info.augendre.perm_maker.gui.DefinePlanningDialog;
import info.augendre.perm_maker.gui.DefineResourcesDialog;

import java.util.ArrayList;

/**
 * Created by gaugendre on 31/07/2016 13:37
 */
public class DeserializeResourcesAction extends AbstractDeserializeAction<Availabilities> {
    private static final long serialVersionUID = -1147713428016357223L;

    public DeserializeResourcesAction(DefineResourcesDialog dialog) {
        super(dialog);
    }

    @Override
    public void update() throws ClassCastException {
        Object element = getDeserialized();
        if (element == null) {
            throw new ClassCastException();
        }

        Availabilities newResources = (Availabilities) element;
        getDialog().resetResources();
        getDialog().addAllResources(newResources);
    }

    @Override
    public DefineResourcesDialog getDialog() {
        return (DefineResourcesDialog) super.getDialog();
    }
}
