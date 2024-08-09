/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package joptsimple;

public class ValueConversionException
extends RuntimeException {
    private static final long serialVersionUID = -1L;

    public ValueConversionException(String string) {
        this(string, null);
    }

    public ValueConversionException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

