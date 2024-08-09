/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.json;

public class JSONException
extends RuntimeException {
    private static final long serialVersionUID = 0L;

    public JSONException(String string) {
        super(string);
    }

    public JSONException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public JSONException(Throwable throwable) {
        super(throwable.getMessage(), throwable);
    }
}

