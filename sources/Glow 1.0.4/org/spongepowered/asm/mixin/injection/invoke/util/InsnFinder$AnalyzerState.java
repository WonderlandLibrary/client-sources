package org.spongepowered.asm.mixin.injection.invoke.util;

enum AnalyzerState
{
    SEARCH, 
    ANALYSE, 
    COMPLETE;
    
    private static final AnalyzerState[] $VALUES;
    
    public static AnalyzerState[] values() {
        return AnalyzerState.$VALUES.clone();
    }
    
    public static AnalyzerState valueOf(final String s) {
        return Enum.valueOf(AnalyzerState.class, s);
    }
    
    static {
        $VALUES = new AnalyzerState[] { AnalyzerState.SEARCH, AnalyzerState.ANALYSE, AnalyzerState.COMPLETE };
    }
}
