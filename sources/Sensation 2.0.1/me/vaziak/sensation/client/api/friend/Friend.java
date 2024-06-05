package me.vaziak.sensation.client.api.friend;

public class Friend {
    private final String name, alias;

    public Friend(String name, String alias) {
        this.name = name;
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }
}
