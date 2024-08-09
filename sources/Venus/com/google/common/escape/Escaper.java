/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.escape;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;

@GwtCompatible
public abstract class Escaper {
    private final Function<String, String> asFunction = new Function<String, String>(this){
        final Escaper this$0;
        {
            this.this$0 = escaper;
        }

        @Override
        public String apply(String string) {
            return this.this$0.escape(string);
        }

        @Override
        public Object apply(Object object) {
            return this.apply((String)object);
        }
    };

    protected Escaper() {
    }

    public abstract String escape(String var1);

    public final Function<String, String> asFunction() {
        return this.asFunction;
    }
}

