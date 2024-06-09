package org.spongepowered.asm.mixin.transformer;

enum InitialiserInjectionMode
{
    DEFAULT, 
    SAFE;
    
    private static final InitialiserInjectionMode[] $VALUES;
    
    public static InitialiserInjectionMode[] values() {
        return InitialiserInjectionMode.$VALUES.clone();
    }
    
    public static InitialiserInjectionMode valueOf(final String s) {
        return Enum.valueOf(InitialiserInjectionMode.class, s);
    }
    
    static {
        $VALUES = new InitialiserInjectionMode[] { InitialiserInjectionMode.DEFAULT, InitialiserInjectionMode.SAFE };
    }
}
