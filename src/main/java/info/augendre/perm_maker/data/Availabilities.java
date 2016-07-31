package info.augendre.perm_maker.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by gaugendre on 31/07/2016 18:22
 */
public class Availabilities extends ArrayList<Resource> implements Serializable {
    private static final long serialVersionUID = 4036489651363728736L;

    public Availabilities(int initialCapacity) {
        super(initialCapacity);
    }

    public Availabilities() {
    }

    public Availabilities(Collection<? extends Resource> c) {
        super(c);
    }
}
