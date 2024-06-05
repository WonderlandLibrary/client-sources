/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonElement;
import io.netty.buffer.ByteBuf;

public class OptionalComponentType
extends Type<JsonElement> {
    public OptionalComponentType() {
        super(JsonElement.class);
    }

    @Override
    public JsonElement read(ByteBuf buffer) throws Exception {
        boolean present = buffer.readBoolean();
        if (!present) return null;
        JsonElement jsonElement = (JsonElement)Type.COMPONENT.read(buffer);
        return jsonElement;
    }

    @Override
    public void write(ByteBuf buffer, JsonElement object) throws Exception {
        if (object == null) {
            buffer.writeBoolean(false);
            return;
        }
        buffer.writeBoolean(true);
        Type.COMPONENT.write(buffer, object);
    }
}

