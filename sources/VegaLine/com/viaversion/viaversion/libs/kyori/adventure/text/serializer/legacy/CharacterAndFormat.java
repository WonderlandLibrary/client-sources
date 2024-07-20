/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.ApiStatus$NonExtendable
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Unmodifiable
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy;

import com.viaversion.viaversion.libs.kyori.adventure.text.format.NamedTextColor;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextFormat;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.CharacterAndFormatImpl;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.Reset;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.List;
import java.util.stream.Stream;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

@ApiStatus.NonExtendable
public interface CharacterAndFormat
extends Examinable {
    public static final CharacterAndFormat BLACK = CharacterAndFormat.characterAndFormat('0', NamedTextColor.BLACK);
    public static final CharacterAndFormat DARK_BLUE = CharacterAndFormat.characterAndFormat('1', NamedTextColor.DARK_BLUE);
    public static final CharacterAndFormat DARK_GREEN = CharacterAndFormat.characterAndFormat('2', NamedTextColor.DARK_GREEN);
    public static final CharacterAndFormat DARK_AQUA = CharacterAndFormat.characterAndFormat('3', NamedTextColor.DARK_AQUA);
    public static final CharacterAndFormat DARK_RED = CharacterAndFormat.characterAndFormat('4', NamedTextColor.DARK_RED);
    public static final CharacterAndFormat DARK_PURPLE = CharacterAndFormat.characterAndFormat('5', NamedTextColor.DARK_PURPLE);
    public static final CharacterAndFormat GOLD = CharacterAndFormat.characterAndFormat('6', NamedTextColor.GOLD);
    public static final CharacterAndFormat GRAY = CharacterAndFormat.characterAndFormat('7', NamedTextColor.GRAY);
    public static final CharacterAndFormat DARK_GRAY = CharacterAndFormat.characterAndFormat('8', NamedTextColor.DARK_GRAY);
    public static final CharacterAndFormat BLUE = CharacterAndFormat.characterAndFormat('9', NamedTextColor.BLUE);
    public static final CharacterAndFormat GREEN = CharacterAndFormat.characterAndFormat('a', NamedTextColor.GREEN);
    public static final CharacterAndFormat AQUA = CharacterAndFormat.characterAndFormat('b', NamedTextColor.AQUA);
    public static final CharacterAndFormat RED = CharacterAndFormat.characterAndFormat('c', NamedTextColor.RED);
    public static final CharacterAndFormat LIGHT_PURPLE = CharacterAndFormat.characterAndFormat('d', NamedTextColor.LIGHT_PURPLE);
    public static final CharacterAndFormat YELLOW = CharacterAndFormat.characterAndFormat('e', NamedTextColor.YELLOW);
    public static final CharacterAndFormat WHITE = CharacterAndFormat.characterAndFormat('f', NamedTextColor.WHITE);
    public static final CharacterAndFormat OBFUSCATED = CharacterAndFormat.characterAndFormat('k', TextDecoration.OBFUSCATED);
    public static final CharacterAndFormat BOLD = CharacterAndFormat.characterAndFormat('l', TextDecoration.BOLD);
    public static final CharacterAndFormat STRIKETHROUGH = CharacterAndFormat.characterAndFormat('m', TextDecoration.STRIKETHROUGH);
    public static final CharacterAndFormat UNDERLINED = CharacterAndFormat.characterAndFormat('n', TextDecoration.UNDERLINED);
    public static final CharacterAndFormat ITALIC = CharacterAndFormat.characterAndFormat('o', TextDecoration.ITALIC);
    public static final CharacterAndFormat RESET = CharacterAndFormat.characterAndFormat('r', Reset.INSTANCE);

    @NotNull
    public static CharacterAndFormat characterAndFormat(char character, @NotNull TextFormat format) {
        return new CharacterAndFormatImpl(character, format);
    }

    public static @Unmodifiable @NotNull List<CharacterAndFormat> defaults() {
        return CharacterAndFormatImpl.Defaults.DEFAULTS;
    }

    public char character();

    @NotNull
    public TextFormat format();

    @Override
    @NotNull
    default public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("character", this.character()), ExaminableProperty.of("format", this.format()));
    }
}

