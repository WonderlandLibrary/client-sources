/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.gson;

import java.lang.reflect.Type;
import us.myles.viaversion.libs.gson.JsonElement;
import us.myles.viaversion.libs.gson.JsonParseException;

public interface JsonDeserializationContext {
    public <T> T deserialize(JsonElement var1, Type var2) throws JsonParseException;
}

