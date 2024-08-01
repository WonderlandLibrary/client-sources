package wtf.diablo.client.friend;

public final class Friend {
    private String name;
    private String alias;

    public Friend(final String name, final String alias) {
        this.name = name;
        this.alias = alias;
    }

    public Friend(final String name) {
        this.name = name;
        this.alias = name;
    }

    public String getName() {
        return this.name;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setAlias(final String alias) {
        this.alias = alias;
    }
}
