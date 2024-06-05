/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.NamedTextColor;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class TextColorSerializer
extends TypeAdapter<TextColor> {
    static final TypeAdapter<TextColor> INSTANCE = new TextColorSerializer(false).nullSafe();
    static final TypeAdapter<TextColor> DOWNSAMPLE_COLOR = new TextColorSerializer(true).nullSafe();
    private final boolean downsampleColor;

    private TextColorSerializer(boolean downsampleColor) {
        this.downsampleColor = downsampleColor;
    }

    @Override
    public void write(JsonWriter out, TextColor value) throws IOException {
        if (value instanceof NamedTextColor) {
            out.value(NamedTextColor.NAMES.key((NamedTextColor)value));
            return;
        }
        if (this.downsampleColor) {
            out.value(NamedTextColor.NAMES.key(NamedTextColor.nearestTo(value)));
            return;
        }
        out.value(value.asHexString());
    }

    @Override
    @Nullable
    public TextColor read(JsonReader in) throws IOException {
        TextColor textColor;
        @Nullable TextColor color = TextColorSerializer.fromString(in.nextString());
        if (color == null) {
            return null;
        }
        if (this.downsampleColor) {
            textColor = NamedTextColor.nearestTo(color);
            return textColor;
        }
        textColor = color;
        return textColor;
    }

    @Nullable
    static TextColor fromString(@NotNull String value) {
        if (!value.startsWith("#")) return NamedTextColor.NAMES.value(value);
        return TextColor.fromHexString(value);
    }
}

