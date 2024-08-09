package xyz.mathax.mathaxclient.systems.modules.movement.speed;

public enum SpeedModes {
    Vanilla("Vanilla"),
    Strafe("Strafe");

    private final String name;

    SpeedModes(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}