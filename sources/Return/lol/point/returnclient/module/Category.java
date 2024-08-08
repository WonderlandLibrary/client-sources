package lol.point.returnclient.module;

public enum Category {
    COMBAT("Combat", false, true),
    MOVEMENT("Movement", false, true),
    RENDER("Render", false, true),
    PLAYER("Player", false, true),
    MISC("Misc", false, true),
    CLIENT("Client", false, true);

    public final String name;
    public final boolean hidden;
    public boolean expanded;

    Category(String name, boolean hidden, boolean expanded) {
        this.name = name;
        this.hidden = hidden;
        this.expanded = expanded;
    }
}
