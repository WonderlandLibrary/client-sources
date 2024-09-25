/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.gson;

import java.lang.reflect.Type;
import us.myles.viaversion.libs.gson.JsonElement;

public interface JsonSerializationContext {
    public JsonElement serialize(Object var1);

    public JsonElement serialize(Object var1, Type var2);
}

