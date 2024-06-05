package com.ibm.icu.impl.number.parse;

public class RequireNumberValidator extends ValidationMatcher
{
    @Override
    public void postProcess(final ParsedNumber result) {
        if (!result.seenNumber()) {
            result.flags |= 0x100;
        }
    }
    
    @Override
    public String toString() {
        return "<RequireNumber>";
    }
}
