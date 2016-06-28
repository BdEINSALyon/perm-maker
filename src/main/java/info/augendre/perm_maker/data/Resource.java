package info.augendre.perm_maker.data;

/**
 * Created by gaugendre on 28/06/16
 */
public class Resource {
    private String name;
    private Planning availability;

    public Resource() {
        this.name = "";
        this.availability = new Planning();
    }

    public Resource(String name, Planning availability) {
        this.name = name;
        this.availability = availability;
    }
}
