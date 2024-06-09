package org.spongepowered.asm.mixin.injection;

enum ShiftByViolationBehaviour
{
    IGNORE, 
    WARN, 
    ERROR;
    
    private static final ShiftByViolationBehaviour[] $VALUES;
    
    public static ShiftByViolationBehaviour[] values() {
        return ShiftByViolationBehaviour.$VALUES.clone();
    }
    
    public static ShiftByViolationBehaviour valueOf(final String s) {
        return Enum.valueOf(ShiftByViolationBehaviour.class, s);
    }
    
    static {
        $VALUES = new ShiftByViolationBehaviour[] { ShiftByViolationBehaviour.IGNORE, ShiftByViolationBehaviour.WARN, ShiftByViolationBehaviour.ERROR };
    }
}
