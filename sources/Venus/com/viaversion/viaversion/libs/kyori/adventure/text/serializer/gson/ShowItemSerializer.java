/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.gson.Gson;
import com.viaversion.viaversion.libs.gson.JsonParseException;
import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonToken;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.api.BinaryTagHolder;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.SerializerFactory;
import java.io.IOException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
final class ShowItemSerializer
extends TypeAdapter<HoverEvent.ShowItem> {
    static final String ID = "id";
    static final String COUNT = "count";
    static final String TAG = "tag";
    private final Gson gson;

    static TypeAdapter<HoverEvent.ShowItem> create(Gson gson) {
        return new ShowItemSerializer(gson).nullSafe();
    }

    private ShowItemSerializer(Gson gson) {
        this.gson = gson;
    }

    @Override
    public HoverEvent.ShowItem read(JsonReader jsonReader) throws IOException {
        jsonReader.beginObject();
        Key key = null;
        int n = 1;
        BinaryTagHolder binaryTagHolder = null;
        while (jsonReader.hasNext()) {
            String string = jsonReader.nextName();
            if (string.equals(ID)) {
                key = (Key)this.gson.fromJson(jsonReader, SerializerFactory.KEY_TYPE);
                continue;
            }
            if (string.equals(COUNT)) {
                n = jsonReader.nextInt();
                continue;
            }
            if (string.equals(TAG)) {
                JsonToken jsonToken = jsonReader.peek();
                if (jsonToken == JsonToken.STRING || jsonToken == JsonToken.NUMBER) {
                    binaryTagHolder = BinaryTagHolder.binaryTagHolder(jsonReader.nextString());
                    continue;
                }
                if (jsonToken == JsonToken.BOOLEAN) {
                    binaryTagHolder = BinaryTagHolder.binaryTagHolder(String.valueOf(jsonReader.nextBoolean()));
                    continue;
                }
                if (jsonToken == JsonToken.NULL) {
                    jsonReader.nextNull();
                    continue;
                }
                throw new JsonParseException("Expected tag to be a string");
            }
            jsonReader.skipValue();
        }
        if (key == null) {
            throw new JsonParseException("Not sure how to deserialize show_item hover event");
        }
        jsonReader.endObject();
        return HoverEvent.ShowItem.of(key, n, binaryTagHolder);
    }

    @Override
    public void write(JsonWriter jsonWriter, HoverEvent.ShowItem showItem) throws IOException {
        BinaryTagHolder binaryTagHolder;
        jsonWriter.beginObject();
        jsonWriter.name(ID);
        this.gson.toJson((Object)showItem.item(), SerializerFactory.KEY_TYPE, jsonWriter);
        int n = showItem.count();
        if (n != 1) {
            jsonWriter.name(COUNT);
            jsonWriter.value(n);
        }
        if ((binaryTagHolder = showItem.nbt()) != null) {
            jsonWriter.name(TAG);
            jsonWriter.value(binaryTagHolder.string());
        }
        jsonWriter.endObject();
    }

    @Override
    public Object read(JsonReader jsonReader) throws IOException {
        return this.read(jsonReader);
    }

    @Override
    public void write(JsonWriter jsonWriter, Object object) throws IOException {
        this.write(jsonWriter, (HoverEvent.ShowItem)object);
    }
}

