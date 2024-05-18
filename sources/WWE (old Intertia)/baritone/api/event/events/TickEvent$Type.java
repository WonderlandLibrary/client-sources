package baritone.api.event.events;

public enum TickEvent$Type
{
    IN, 
    OUT;
    
    private static final TickEvent$Type[] $VALUES;
    
    public static TickEvent$Type[] values() {
        return TickEvent$Type.$VALUES.clone();
    }
    
    public static TickEvent$Type valueOf(final String s) {
        return Enum.valueOf(TickEvent$Type.class, s);
    }
    
    static {
        $VALUES = new TickEvent$Type[] { TickEvent$Type.IN, TickEvent$Type.OUT };
    }
}
