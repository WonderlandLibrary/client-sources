package com.ibm.icu.impl.number.parse;

public class RequireDecimalSeparatorValidator extends ValidationMatcher
{
    private static final RequireDecimalSeparatorValidator A;
    private static final RequireDecimalSeparatorValidator B;
    private final boolean patternHasDecimalSeparator;
    
    public static RequireDecimalSeparatorValidator getInstance(final boolean patternHasDecimalSeparator) {
        return patternHasDecimalSeparator ? RequireDecimalSeparatorValidator.A : RequireDecimalSeparatorValidator.B;
    }
    
    private RequireDecimalSeparatorValidator(final boolean patternHasDecimalSeparator) {
        this.patternHasDecimalSeparator = patternHasDecimalSeparator;
    }
    
    @Override
    public void postProcess(final ParsedNumber result) {
        final boolean parseHasDecimalSeparator = 0x0 != (result.flags & 0x20);
        if (parseHasDecimalSeparator != this.patternHasDecimalSeparator) {
            result.flags |= 0x100;
        }
    }
    
    @Override
    public String toString() {
        return "<RequireDecimalSeparator>";
    }
    
    static {
        A = new RequireDecimalSeparatorValidator(true);
        B = new RequireDecimalSeparatorValidator(false);
    }
}
