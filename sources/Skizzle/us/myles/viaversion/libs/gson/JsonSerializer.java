/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.gson;

import java.lang.reflect.Type;
import us.myles.viaversion.libs.gson.JsonElement;
import us.myles.viaversion.libs.gson.JsonSerializationContext;

public interface JsonSerializer<T> {
    public JsonElement serialize(T var1, Type var2, JsonSerializationContext var3);
}

