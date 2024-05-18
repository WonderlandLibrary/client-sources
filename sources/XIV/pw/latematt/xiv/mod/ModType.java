package pw.latematt.xiv.mod;

public enum ModType {
    COMBAT, MISCELLANEOUS, MOVEMENT, PLAYER, RENDER, WORLD;

    public String getName() {
        return name().substring(0, 1) + name().substring(1, name().length()).toLowerCase();
    }
}
