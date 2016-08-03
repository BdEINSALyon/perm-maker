package info.augendre.perm_maker.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Holds the resources and their availabilities.
 * @see Resource
 * @author gaugendre
 */
public class Availabilities extends ArrayList<Resource> implements Serializable {
    private static final long serialVersionUID = 4036489651363728736L;

    /**
     * Constructor with initial capacity.
     * @param initialCapacity The initial capacity.
     * @see ArrayList#ArrayList(int)
     */
    public Availabilities(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Default constructor.
     * @see ArrayList#ArrayList()
     */
    public Availabilities() {
    }

    /**
     * Constructor from existing collection.
     * @param c Existing collection.
     * @see ArrayList#ArrayList(Collection)
     */
    public Availabilities(Collection<? extends Resource> c) {
        super(c);
    }
}
