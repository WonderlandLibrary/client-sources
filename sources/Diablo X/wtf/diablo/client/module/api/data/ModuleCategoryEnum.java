package wtf.diablo.client.module.api.data;

public enum ModuleCategoryEnum {
    COMBAT("Combat", 'c'),
    MOVEMENT("Movement", 'n'),
    PLAYER("Player", 'p'),
    RENDER("Render", 'i'),
    EXPLOIT("Exploit", 'e'),
    MISC("Misc", 'm');

    private final String name;
    private final char iconChar;

    ModuleCategoryEnum(final String name, final char iconChar) {
        this.name = name;
        this.iconChar = iconChar;
    }

    public char getIconChar() {
        return iconChar;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
