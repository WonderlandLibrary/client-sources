package zamorozka.ui;

public class Friend {
    private String name;
    private String alias;

    public Friend(String name, String alias) {
        this.name = name;
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public void setName(String s) {
        name = s;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String s) {
        alias = s;
    }
}

