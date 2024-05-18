package baritone.api.event.events;

public enum RotationMoveEvent$Type
{
    MOTION_UPDATE, 
    JUMP;
    
    private static final RotationMoveEvent$Type[] $VALUES;
    
    public static RotationMoveEvent$Type[] values() {
        return RotationMoveEvent$Type.$VALUES.clone();
    }
    
    public static RotationMoveEvent$Type valueOf(final String s) {
        return Enum.valueOf(RotationMoveEvent$Type.class, s);
    }
    
    static {
        $VALUES = new RotationMoveEvent$Type[] { RotationMoveEvent$Type.MOTION_UPDATE, RotationMoveEvent$Type.JUMP };
    }
}
