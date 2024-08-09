/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.StringType;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonParser;
import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
import io.netty.buffer.ByteBuf;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ComponentType
extends Type<JsonElement> {
    private static final StringType STRING_TAG = new StringType(262144);

    public ComponentType() {
        super(JsonElement.class);
    }

    @Override
    public JsonElement read(ByteBuf byteBuf) throws Exception {
        String string = STRING_TAG.read(byteBuf);
        try {
            return JsonParser.parseString(string);
        } catch (JsonSyntaxException jsonSyntaxException) {
            Via.getPlatform().getLogger().severe("Error when trying to parse json: " + string);
            throw jsonSyntaxException;
        }
    }

    @Override
    public void write(ByteBuf byteBuf, JsonElement jsonElement) throws Exception {
        STRING_TAG.write(byteBuf, jsonElement.toString());
    }

    @Override
    public Object read(ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }

    @Override
    public void write(ByteBuf byteBuf, Object object) throws Exception {
        this.write(byteBuf, (JsonElement)object);
    }

    public static final class OptionalComponentType
    extends OptionalType<JsonElement> {
        public OptionalComponentType() {
            super(Type.COMPONENT);
        }
    }
}

