package com.ibm.icu.impl.number.parse;

import com.ibm.icu.number.*;
import com.ibm.icu.impl.number.*;

public class MultiplierParseHandler extends ValidationMatcher
{
    private final Scale multiplier;
    
    public MultiplierParseHandler(final Scale multiplier) {
        this.multiplier = multiplier;
    }
    
    @Override
    public void postProcess(final ParsedNumber result) {
        if (result.quantity != null) {
            this.multiplier.applyReciprocalTo(result.quantity);
        }
    }
    
    @Override
    public String toString() {
        return "<MultiplierHandler " + this.multiplier + ">";
    }
}
