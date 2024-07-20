/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import com.google.gson.JsonElement;

public interface IJsonSerializable {
    public void fromJson(JsonElement var1);

    public JsonElement getSerializableElement();
}

