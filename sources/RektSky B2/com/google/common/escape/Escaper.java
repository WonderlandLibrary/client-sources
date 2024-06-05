package com.google.common.escape;

import com.google.common.annotations.*;
import com.google.common.base.*;

@Beta
@GwtCompatible
public abstract class Escaper
{
    private final Function<String, String> asFunction;
    
    protected Escaper() {
        this.asFunction = new Function<String, String>() {
            @Override
            public String apply(final String from) {
                return Escaper.this.escape(from);
            }
        };
    }
    
    public abstract String escape(final String p0);
    
    public final Function<String, String> asFunction() {
        return this.asFunction;
    }
}
