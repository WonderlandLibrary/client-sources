package club.bluezenith.ui.guis.mainmenu.changelog.fetch;

import java.awt.*;
import java.util.function.Supplier;

public class ChangelogEntry {

    private final String contents;
    private final EntryType type;

    public ChangelogEntry(String contents, EntryType type) {
        this.contents = contents.trim();
        this.type = type;
    }

    public String getContents() {
        return contents;
    }

    public EntryType getType() {
        return type;
    }

    @Override
    public String toString() {
        return getType().getCharacter() + " " + getContents();
    }

    public enum EntryType {
        ADDITION("[+]", () -> new Color(54, 239, 61)),
        FIX("[~]", () -> new Color(255, 225, 99)),
        IMPROVEMENT("[*]", () -> new Color(103, 241, 114)),
        REMOVAL("[-]", () -> new Color(248, 56, 56)),
        OTHER("[?]", () -> new Color(180, 72, 180));

        private final String character;
        private final Supplier<Color> stringColor;

        EntryType(String character, Supplier<Color> stringColor) {
            this.character = character;
            this.stringColor = stringColor;
        }

        public String getCharacter() {
            return this.character;
        }

        public Color getEffectiveColor() {
            final Color suppliedColor = stringColor.get();
            return suppliedColor == null ? Color.WHITE : suppliedColor;
        }
    }
}
