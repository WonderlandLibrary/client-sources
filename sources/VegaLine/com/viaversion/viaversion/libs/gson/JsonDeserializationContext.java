/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.gson;

import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonParseException;
import java.lang.reflect.Type;

public interface JsonDeserializationContext {
    public <T> T deserialize(JsonElement var1, Type var2) throws JsonParseException;
}

