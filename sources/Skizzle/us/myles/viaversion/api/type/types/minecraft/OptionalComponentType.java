/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package us.myles.ViaVersion.api.type.types.minecraft;

import io.netty.buffer.ByteBuf;
import us.myles.ViaVersion.api.type.Type;
import us.myles.viaversion.libs.gson.JsonElement;

public class OptionalComponentType
extends Type<JsonElement> {
    public OptionalComponentType() {
        super(JsonElement.class);
    }

    @Override
    public JsonElement read(ByteBuf buffer) throws Exception {
        boolean present = buffer.readBoolean();
        return present ? (JsonElement)Type.COMPONENT.read(buffer) : null;
    }

    @Override
    public void write(ByteBuf buffer, JsonElement object) throws Exception {
        if (object == null) {
            buffer.writeBoolean(false);
        } else {
            buffer.writeBoolean(true);
            Type.COMPONENT.write(buffer, object);
        }
    }
}

