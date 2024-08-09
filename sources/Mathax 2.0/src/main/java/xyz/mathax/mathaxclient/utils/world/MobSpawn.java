package xyz.mathax.mathaxclient.utils.world;

public enum MobSpawn {
    Never("Never"),
    Potential("Potential"),
    Always("Always");

    private final String name;

    MobSpawn(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}