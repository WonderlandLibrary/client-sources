/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.gson;

import us.myles.viaversion.libs.gson.Gson;
import us.myles.viaversion.libs.gson.TypeAdapter;
import us.myles.viaversion.libs.gson.reflect.TypeToken;

public interface TypeAdapterFactory {
    public <T> TypeAdapter<T> create(Gson var1, TypeToken<T> var2);
}

