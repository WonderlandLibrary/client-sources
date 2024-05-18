package host.kix.uzi.friends;

/**
 * Created by myche on 3/1/2017.
 */
public class Friend {

    private final String username;
    private String alias;

    public Friend(String username, String alias) {
        this.username = username;
        this.alias = alias;
    }

    public String getUsername() {
        return username;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

}
