package baritone.api.process;

public interface IBaritoneProcess
{
    public static final double DEFAULT_PRIORITY = -1.0;
    
    boolean isActive();
    
    PathingCommand onTick(final boolean p0, final boolean p1);
    
    boolean isTemporary();
    
    void onLostControl();
    
    default double priority() {
        return -1.0;
    }
    
    default String displayName() {
        if (!this.isActive()) {
            return "INACTIVE";
        }
        return this.displayName0();
    }
    
    String displayName0();
}
