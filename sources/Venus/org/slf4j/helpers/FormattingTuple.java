/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.slf4j.helpers;

public class FormattingTuple {
    public static FormattingTuple NULL = new FormattingTuple(null);
    private final String message;
    private final Throwable throwable;
    private final Object[] argArray;

    public FormattingTuple(String string) {
        this(string, null, null);
    }

    public FormattingTuple(String string, Object[] objectArray, Throwable throwable) {
        this.message = string;
        this.throwable = throwable;
        this.argArray = objectArray;
    }

    public String getMessage() {
        return this.message;
    }

    public Object[] getArgArray() {
        return this.argArray;
    }

    public Throwable getThrowable() {
        return this.throwable;
    }
}

