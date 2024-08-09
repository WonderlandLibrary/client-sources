/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.gson;

public class JsonParseException
extends RuntimeException {
    static final long serialVersionUID = -4086729973971783390L;

    public JsonParseException(String string) {
        super(string);
    }

    public JsonParseException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public JsonParseException(Throwable throwable) {
        super(throwable);
    }
}

