package org.spongepowered.asm.mixin.injection;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
public @interface At {
    String id() default "";
    
    String value();
    
    String slice() default "";
    
    Shift shift() default Shift.NONE;
    
    int by() default 0;
    
    String[] args() default {};
    
    String target() default "";
    
    int ordinal() default -1;
    
    int opcode() default -1;
    
    boolean remap() default true;
    
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
}
