package com.ibm.icu.impl.number.parse;

public class RequireAffixValidator extends ValidationMatcher
{
    @Override
    public void postProcess(final ParsedNumber result) {
        if (result.prefix == null || result.suffix == null) {
            result.flags |= 0x100;
        }
    }
    
    @Override
    public String toString() {
        return "<RequireAffix>";
    }
}
