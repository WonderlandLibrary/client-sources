package baritone.api.event.events;

public final class RotationMoveEvent
{
    private final RotationMoveEvent$Type type;
    private float yaw;
    
    public RotationMoveEvent(final RotationMoveEvent$Type type, final float yaw) {
        super();
        this.type = type;
        this.yaw = yaw;
    }
    
    public final void setYaw(final float yaw) {
        this.yaw = yaw;
    }
    
    public final float getYaw() {
        return this.yaw;
    }
    
    public final RotationMoveEvent$Type getType() {
        return this.type;
    }
}
