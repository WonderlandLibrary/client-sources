package host.kix.uzi.command;

/**
 * Created by Kix on 2/3/2017.
 */
public class Command {

    private String label;
    private String[] alias;
    private String description;

    public Command(String command, String description, String... alias) {
        this.label = command;
        this.description = description;
        this.alias = alias;
    }

    public String getDescription() {
        return description;
    }

    public String getLabel() {
        return label;
    }

    public String[] getAlias() {
        return alias;
    }

    public void dispatch(String message) {
    }

}
