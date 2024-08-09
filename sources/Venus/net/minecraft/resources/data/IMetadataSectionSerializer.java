/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.resources.data;

import com.google.gson.JsonObject;

public interface IMetadataSectionSerializer<T> {
    public String getSectionName();

    public T deserialize(JsonObject var1);
}

