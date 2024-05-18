package org.spongepowered.asm.mixin.injection;

public enum Shift
{
    NONE, 
    BEFORE, 
    AFTER, 
    BY;
    
    private static final Shift[] $VALUES;
    
    public static Shift[] values() {
        return Shift.$VALUES.clone();
    }
    
    public static Shift valueOf(final String s) {
        return Enum.valueOf(Shift.class, s);
    }
    
    static {
        $VALUES = new Shift[] { Shift.NONE, Shift.BEFORE, Shift.AFTER, Shift.BY };
    }
}
