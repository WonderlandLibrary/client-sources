/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.gson;

import com.google.gson.JsonElement;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class JsonNull
extends JsonElement {
    public static final JsonNull INSTANCE = new JsonNull();

    @Deprecated
    public JsonNull() {
    }

    @Override
    public JsonNull deepCopy() {
        return INSTANCE;
    }

    public int hashCode() {
        return JsonNull.class.hashCode();
    }

    public boolean equals(Object object) {
        return object instanceof JsonNull;
    }

    @Override
    public JsonElement deepCopy() {
        return this.deepCopy();
    }
}

