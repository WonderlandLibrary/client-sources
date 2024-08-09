/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.gson.JsonParseException;
import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponent;
import java.io.IOException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
final class BlockNBTComponentPosSerializer
extends TypeAdapter<BlockNBTComponent.Pos> {
    static final TypeAdapter<BlockNBTComponent.Pos> INSTANCE = new BlockNBTComponentPosSerializer().nullSafe();

    private BlockNBTComponentPosSerializer() {
    }

    @Override
    public BlockNBTComponent.Pos read(JsonReader jsonReader) throws IOException {
        String string = jsonReader.nextString();
        try {
            return BlockNBTComponent.Pos.fromString(string);
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new JsonParseException("Don't know how to turn " + string + " into a Position");
        }
    }

    @Override
    public void write(JsonWriter jsonWriter, BlockNBTComponent.Pos pos) throws IOException {
        jsonWriter.value(pos.asString());
    }

    @Override
    public Object read(JsonReader jsonReader) throws IOException {
        return this.read(jsonReader);
    }

    @Override
    public void write(JsonWriter jsonWriter, Object object) throws IOException {
        this.write(jsonWriter, (BlockNBTComponent.Pos)object);
    }
}

