package club.mega.gui.changelog;

import club.mega.Mega;
import club.mega.gui.changelog.Changelog.Type;

public class Change {

    private final String change;
    private final Type type;

    public Change(final String change, final Type type) {
        this.change = change;
        this.type = type;
    }

    public Change(final String version) {
        type = Type.VERSION;
        change = version;
    }

    public Change() {
        type = Type.EMPTY;
        change = "";
    }

    public final String getChange() {
        return change;
    }

    public final Type getType() {
        return type;
    }

}
