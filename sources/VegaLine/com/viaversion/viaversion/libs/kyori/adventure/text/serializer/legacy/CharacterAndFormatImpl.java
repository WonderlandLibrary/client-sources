/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy;

import com.viaversion.viaversion.libs.kyori.adventure.internal.Internals;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextFormat;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.CharacterAndFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class CharacterAndFormatImpl
implements CharacterAndFormat {
    private final char character;
    private final TextFormat format;

    CharacterAndFormatImpl(char character, @NotNull TextFormat format) {
        this.character = character;
        this.format = Objects.requireNonNull(format, "format");
    }

    @Override
    public char character() {
        return this.character;
    }

    @Override
    @NotNull
    public TextFormat format() {
        return this.format;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CharacterAndFormatImpl)) {
            return false;
        }
        CharacterAndFormatImpl that = (CharacterAndFormatImpl)other;
        return this.character == that.character && this.format.equals(that.format);
    }

    public int hashCode() {
        int result = this.character;
        result = 31 * result + this.format.hashCode();
        return result;
    }

    @NotNull
    public String toString() {
        return Internals.toString(this);
    }

    static final class Defaults {
        static final List<CharacterAndFormat> DEFAULTS = Defaults.createDefaults();

        private Defaults() {
        }

        static List<CharacterAndFormat> createDefaults() {
            ArrayList<CharacterAndFormat> formats = new ArrayList<CharacterAndFormat>(22);
            formats.add(CharacterAndFormat.BLACK);
            formats.add(CharacterAndFormat.DARK_BLUE);
            formats.add(CharacterAndFormat.DARK_GREEN);
            formats.add(CharacterAndFormat.DARK_AQUA);
            formats.add(CharacterAndFormat.DARK_RED);
            formats.add(CharacterAndFormat.DARK_PURPLE);
            formats.add(CharacterAndFormat.GOLD);
            formats.add(CharacterAndFormat.GRAY);
            formats.add(CharacterAndFormat.DARK_GRAY);
            formats.add(CharacterAndFormat.BLUE);
            formats.add(CharacterAndFormat.GREEN);
            formats.add(CharacterAndFormat.AQUA);
            formats.add(CharacterAndFormat.RED);
            formats.add(CharacterAndFormat.LIGHT_PURPLE);
            formats.add(CharacterAndFormat.YELLOW);
            formats.add(CharacterAndFormat.WHITE);
            formats.add(CharacterAndFormat.OBFUSCATED);
            formats.add(CharacterAndFormat.BOLD);
            formats.add(CharacterAndFormat.STRIKETHROUGH);
            formats.add(CharacterAndFormat.UNDERLINED);
            formats.add(CharacterAndFormat.ITALIC);
            formats.add(CharacterAndFormat.RESET);
            return Collections.unmodifiableList(formats);
        }
    }
}

