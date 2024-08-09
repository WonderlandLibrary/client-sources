/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.NamedTextColor;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor;
import java.io.IOException;
import java.util.Locale;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
final class TextColorSerializer
extends TypeAdapter<TextColor> {
    static final TypeAdapter<TextColor> INSTANCE = new TextColorSerializer(false).nullSafe();
    static final TypeAdapter<TextColor> DOWNSAMPLE_COLOR = new TextColorSerializer(true).nullSafe();
    private final boolean downsampleColor;

    private TextColorSerializer(boolean bl) {
        this.downsampleColor = bl;
    }

    @Override
    public void write(JsonWriter jsonWriter, TextColor textColor) throws IOException {
        if (textColor instanceof NamedTextColor) {
            jsonWriter.value(NamedTextColor.NAMES.key((NamedTextColor)textColor));
        } else if (this.downsampleColor) {
            jsonWriter.value(NamedTextColor.NAMES.key(NamedTextColor.nearestTo(textColor)));
        } else {
            jsonWriter.value(TextColorSerializer.asUpperCaseHexString(textColor));
        }
    }

    private static String asUpperCaseHexString(TextColor textColor) {
        return String.format(Locale.ROOT, "#%06X", textColor.value());
    }

    @Override
    @Nullable
    public TextColor read(JsonReader jsonReader) throws IOException {
        @Nullable TextColor textColor = TextColorSerializer.fromString(jsonReader.nextString());
        if (textColor == null) {
            return null;
        }
        return this.downsampleColor ? NamedTextColor.nearestTo(textColor) : textColor;
    }

    @Nullable
    static TextColor fromString(@NotNull String string) {
        if (string.startsWith("#")) {
            return TextColor.fromHexString(string);
        }
        return NamedTextColor.NAMES.value(string);
    }

    @Override
    @Nullable
    public Object read(JsonReader jsonReader) throws IOException {
        return this.read(jsonReader);
    }

    @Override
    public void write(JsonWriter jsonWriter, Object object) throws IOException {
        this.write(jsonWriter, (TextColor)object);
    }
}

