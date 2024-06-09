package org.spongepowered.asm.mixin.injection;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
public @interface Constant {
    boolean nullValue() default false;
    
    int intValue() default 0;
    
    float floatValue() default 0.0f;
    
    long longValue() default 0L;
    
    double doubleValue() default 0.0;
    
    String stringValue() default "";
    
    Class<?> classValue() default Object.class;
    
    int ordinal() default -1;
    
    String slice() default "";
    
    Condition[] expandZeroConditions() default {};
    
    boolean log() default false;
    
    public enum Condition
    {
        LESS_THAN_ZERO(new int[] { 155, 156 }), 
        LESS_THAN_OR_EQUAL_TO_ZERO(new int[] { 158, 157 }), 
        GREATER_THAN_OR_EQUAL_TO_ZERO(Condition.LESS_THAN_ZERO), 
        GREATER_THAN_ZERO(Condition.LESS_THAN_OR_EQUAL_TO_ZERO);
        
        private final int[] opcodes;
        private final Condition equivalence;
        private static final Condition[] $VALUES;
        
        public static Condition[] values() {
            return Condition.$VALUES.clone();
        }
        
        public static Condition valueOf(final String s) {
            return Enum.valueOf(Condition.class, s);
        }
        
        private Condition(final int[] array) {
            this(null, array);
        }
        
        private Condition(final Condition condition) {
            this(condition, condition.opcodes);
        }
        
        private Condition(final Condition condition, final int[] opcodes) {
            this.equivalence = ((condition != null) ? condition : this);
            this.opcodes = opcodes;
        }
        
        public Condition getEquivalentCondition() {
            return this.equivalence;
        }
        
        public int[] getOpcodes() {
            return this.opcodes;
        }
        
        static {
            $VALUES = new Condition[] { Condition.LESS_THAN_ZERO, Condition.LESS_THAN_OR_EQUAL_TO_ZERO, Condition.GREATER_THAN_OR_EQUAL_TO_ZERO, Condition.GREATER_THAN_ZERO };
        }
    }
}
