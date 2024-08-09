/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.opennbt.conversion;

public class ConversionException
extends RuntimeException {
    private static final long serialVersionUID = -2022049594558041160L;

    public ConversionException() {
    }

    public ConversionException(String string) {
        super(string);
    }

    public ConversionException(Throwable throwable) {
        super(throwable);
    }

    public ConversionException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

