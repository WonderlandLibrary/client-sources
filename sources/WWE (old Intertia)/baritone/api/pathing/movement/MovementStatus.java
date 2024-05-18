package baritone.api.pathing.movement;

public enum MovementStatus
{
    PREPPING(false), 
    WAITING(false), 
    RUNNING(false), 
    SUCCESS(true), 
    UNREACHABLE(true), 
    FAILED(true), 
    CANCELED(true);
    
    private final boolean complete;
    private static final MovementStatus[] $VALUES;
    
    public static MovementStatus[] values() {
        return MovementStatus.$VALUES.clone();
    }
    
    public static MovementStatus valueOf(final String s) {
        return Enum.valueOf(MovementStatus.class, s);
    }
    
    private MovementStatus(final boolean complete) {
        this.complete = complete;
    }
    
    public final boolean isComplete() {
        return this.complete;
    }
    
    static {
        $VALUES = new MovementStatus[] { MovementStatus.PREPPING, MovementStatus.WAITING, MovementStatus.RUNNING, MovementStatus.SUCCESS, MovementStatus.UNREACHABLE, MovementStatus.FAILED, MovementStatus.CANCELED };
    }
}
