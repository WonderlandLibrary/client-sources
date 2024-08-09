package mathax.client.events.render;

public class GetFovEvent {
    private static final GetFovEvent INSTANCE = new GetFovEvent();

    public double fov;

    public static GetFovEvent get(double fov) {
        INSTANCE.fov = fov;
        return INSTANCE;
    }
}
