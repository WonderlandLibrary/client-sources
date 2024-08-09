/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.scanner;

import org.yaml.snakeyaml.error.Mark;

final class SimpleKey {
    private final int tokenNumber;
    private final boolean required;
    private final int index;
    private final int line;
    private final int column;
    private final Mark mark;

    public SimpleKey(int n, boolean bl, int n2, int n3, int n4, Mark mark) {
        this.tokenNumber = n;
        this.required = bl;
        this.index = n2;
        this.line = n3;
        this.column = n4;
        this.mark = mark;
    }

    public int getTokenNumber() {
        return this.tokenNumber;
    }

    public int getColumn() {
        return this.column;
    }

    public Mark getMark() {
        return this.mark;
    }

    public int getIndex() {
        return this.index;
    }

    public int getLine() {
        return this.line;
    }

    public boolean isRequired() {
        return this.required;
    }

    public String toString() {
        return "SimpleKey - tokenNumber=" + this.tokenNumber + " required=" + this.required + " index=" + this.index + " line=" + this.line + " column=" + this.column;
    }
}

