/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.brigadier.context;

import com.mojang.brigadier.context.StringRange;
import java.util.Objects;

public class ParsedArgument<S, T> {
    private final StringRange range;
    private final T result;

    public ParsedArgument(int n, int n2, T t) {
        this.range = StringRange.between(n, n2);
        this.result = t;
    }

    public StringRange getRange() {
        return this.range;
    }

    public T getResult() {
        return this.result;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof ParsedArgument)) {
            return true;
        }
        ParsedArgument parsedArgument = (ParsedArgument)object;
        return Objects.equals(this.range, parsedArgument.range) && Objects.equals(this.result, parsedArgument.result);
    }

    public int hashCode() {
        return Objects.hash(this.range, this.result);
    }
}

