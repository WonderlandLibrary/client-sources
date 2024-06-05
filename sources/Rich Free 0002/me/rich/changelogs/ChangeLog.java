package me.rich.changelogs;


public class ChangeLog {
    private String changeName;
    private final ChangelogType type;

    public ChangeLog(String name, ChangelogType type) {
        this.changeName = name;
        this.type = type;

        switch (type) {
            case NONE:
                changeName = ": " + changeName;
                break;
            case ADD:
                changeName = "\2477[\247a+\2477] added \247f" + changeName;
                break;
            case DELETE:
                changeName = "\2477[\247c-\2477] deleted \247f" + changeName;
                break;
            case IMPROVED:
                changeName = "\2477[§6~\2477] improved \247f" + changeName;
                break;
            case FIXED:
                changeName = "\2477[\247b/\2477] fixed \247f" + changeName;
                break;
            case PROTOTYPE:
                changeName = "\2477[\247e?\2477] prototype \247f" + changeName;
                break;
            case NEW:
                changeName = "\2477[\2476!\2477] new \247f" + changeName;
                break;
        }
    }

    public String getChangeName() {
        return changeName;
    }

    public ChangelogType getType() {
        return type;
    }
}