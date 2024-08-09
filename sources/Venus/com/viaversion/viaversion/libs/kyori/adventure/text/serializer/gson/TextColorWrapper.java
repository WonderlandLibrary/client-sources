/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.gson.JsonParseException;
import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.TextColorSerializer;
import java.io.IOException;
import org.jetbrains.annotations.Nullable;

final class TextColorWrapper {
    @Nullable
    final TextColor color;
    @Nullable
    final TextDecoration decoration;
    final boolean reset;

    TextColorWrapper(@Nullable TextColor textColor, @Nullable TextDecoration textDecoration, boolean bl) {
        this.color = textColor;
        this.decoration = textDecoration;
        this.reset = bl;
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    static final class Serializer
    extends TypeAdapter<TextColorWrapper> {
        static final Serializer INSTANCE = new Serializer();

        private Serializer() {
        }

        @Override
        public void write(JsonWriter jsonWriter, TextColorWrapper textColorWrapper) {
            throw new JsonSyntaxException("Cannot write TextColorWrapper instances");
        }

        @Override
        public TextColorWrapper read(JsonReader jsonReader) throws IOException {
            boolean bl;
            String string = jsonReader.nextString();
            TextColor textColor = TextColorSerializer.fromString(string);
            TextDecoration textDecoration = TextDecoration.NAMES.value(string);
            boolean bl2 = bl = textDecoration == null && string.equals("reset");
            if (textColor == null && textDecoration == null && !bl) {
                throw new JsonParseException("Don't know how to parse " + string + " at " + jsonReader.getPath());
            }
            return new TextColorWrapper(textColor, textDecoration, bl);
        }

        @Override
        public Object read(JsonReader jsonReader) throws IOException {
            return this.read(jsonReader);
        }

        @Override
        public void write(JsonWriter jsonWriter, Object object) throws IOException {
            this.write(jsonWriter, (TextColorWrapper)object);
        }
    }
}

