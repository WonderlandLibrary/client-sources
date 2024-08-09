/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.gson;

import com.viaversion.viaversion.libs.gson.JsonParseException;

public final class JsonIOException
extends JsonParseException {
    private static final long serialVersionUID = 1L;

    public JsonIOException(String string) {
        super(string);
    }

    public JsonIOException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public JsonIOException(Throwable throwable) {
        super(throwable);
    }
}

