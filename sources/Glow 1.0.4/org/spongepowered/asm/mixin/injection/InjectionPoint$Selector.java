package org.spongepowered.asm.mixin.injection;

public enum Selector
{
    FIRST, 
    LAST, 
    ONE;
    
    public static final Selector DEFAULT;
    private static final Selector[] $VALUES;
    
    public static Selector[] values() {
        return Selector.$VALUES.clone();
    }
    
    public static Selector valueOf(final String s) {
        return Enum.valueOf(Selector.class, s);
    }
    
    static {
        $VALUES = new Selector[] { Selector.FIRST, Selector.LAST, Selector.ONE };
        DEFAULT = Selector.FIRST;
    }
}
