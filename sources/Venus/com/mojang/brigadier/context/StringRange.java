/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.brigadier.context;

import com.mojang.brigadier.ImmutableStringReader;
import java.util.Objects;

public class StringRange {
    private final int start;
    private final int end;

    public StringRange(int n, int n2) {
        this.start = n;
        this.end = n2;
    }

    public static StringRange at(int n) {
        return new StringRange(n, n);
    }

    public static StringRange between(int n, int n2) {
        return new StringRange(n, n2);
    }

    public static StringRange encompassing(StringRange stringRange, StringRange stringRange2) {
        return new StringRange(Math.min(stringRange.getStart(), stringRange2.getStart()), Math.max(stringRange.getEnd(), stringRange2.getEnd()));
    }

    public int getStart() {
        return this.start;
    }

    public int getEnd() {
        return this.end;
    }

    public String get(ImmutableStringReader immutableStringReader) {
        return immutableStringReader.getString().substring(this.start, this.end);
    }

    public String get(String string) {
        return string.substring(this.start, this.end);
    }

    public boolean isEmpty() {
        return this.start == this.end;
    }

    public int getLength() {
        return this.end - this.start;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof StringRange)) {
            return true;
        }
        StringRange stringRange = (StringRange)object;
        return this.start == stringRange.start && this.end == stringRange.end;
    }

    public int hashCode() {
        return Objects.hash(this.start, this.end);
    }

    public String toString() {
        return "StringRange{start=" + this.start + ", end=" + this.end + '}';
    }
}

