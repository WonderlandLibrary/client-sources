/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.gson.Gson;
import com.viaversion.viaversion.libs.gson.JsonParseException;
import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.SerializerFactory;
import java.io.IOException;
import java.util.UUID;
import org.jetbrains.annotations.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
final class ShowEntitySerializer
extends TypeAdapter<HoverEvent.ShowEntity> {
    static final String TYPE = "type";
    static final String ID = "id";
    static final String NAME = "name";
    private final Gson gson;

    static TypeAdapter<HoverEvent.ShowEntity> create(Gson gson) {
        return new ShowEntitySerializer(gson).nullSafe();
    }

    private ShowEntitySerializer(Gson gson) {
        this.gson = gson;
    }

    @Override
    public HoverEvent.ShowEntity read(JsonReader jsonReader) throws IOException {
        jsonReader.beginObject();
        Key key = null;
        UUID uUID = null;
        Component component = null;
        while (jsonReader.hasNext()) {
            String string = jsonReader.nextName();
            if (string.equals(TYPE)) {
                key = (Key)this.gson.fromJson(jsonReader, SerializerFactory.KEY_TYPE);
                continue;
            }
            if (string.equals(ID)) {
                uUID = UUID.fromString(jsonReader.nextString());
                continue;
            }
            if (string.equals(NAME)) {
                component = (Component)this.gson.fromJson(jsonReader, SerializerFactory.COMPONENT_TYPE);
                continue;
            }
            jsonReader.skipValue();
        }
        if (key == null || uUID == null) {
            throw new JsonParseException("A show entity hover event needs type and id fields to be deserialized");
        }
        jsonReader.endObject();
        return HoverEvent.ShowEntity.of(key, uUID, component);
    }

    @Override
    public void write(JsonWriter jsonWriter, HoverEvent.ShowEntity showEntity) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name(TYPE);
        this.gson.toJson((Object)showEntity.type(), SerializerFactory.KEY_TYPE, jsonWriter);
        jsonWriter.name(ID);
        jsonWriter.value(showEntity.id().toString());
        @Nullable Component component = showEntity.name();
        if (component != null) {
            jsonWriter.name(NAME);
            this.gson.toJson((Object)component, SerializerFactory.COMPONENT_TYPE, jsonWriter);
        }
        jsonWriter.endObject();
    }

    @Override
    public Object read(JsonReader jsonReader) throws IOException {
        return this.read(jsonReader);
    }

    @Override
    public void write(JsonWriter jsonWriter, Object object) throws IOException {
        this.write(jsonWriter, (HoverEvent.ShowEntity)object);
    }
}

