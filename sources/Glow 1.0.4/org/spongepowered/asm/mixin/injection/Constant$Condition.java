package org.spongepowered.asm.mixin.injection;

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
