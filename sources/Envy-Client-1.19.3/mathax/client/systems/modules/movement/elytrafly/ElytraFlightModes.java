package mathax.client.systems.modules.movement.elytrafly;

public enum ElytraFlightModes {
    Vanilla("Vanilla"),
    Packet("Packet"),
    Pitch40("Pitch 40");

    private final String title;

    ElytraFlightModes(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
