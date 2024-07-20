/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy;

import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextFormat;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.CharacterAndFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class CharacterAndFormatSet {
    static final CharacterAndFormatSet DEFAULT = CharacterAndFormatSet.of(CharacterAndFormat.defaults());
    final List<TextFormat> formats;
    final List<TextColor> colors;
    final String characters;

    static CharacterAndFormatSet of(List<CharacterAndFormat> pairs) {
        int size = pairs.size();
        ArrayList<TextColor> colors = new ArrayList<TextColor>();
        ArrayList<TextFormat> formats = new ArrayList<TextFormat>(size);
        StringBuilder characters = new StringBuilder(size);
        for (int i = 0; i < size; ++i) {
            CharacterAndFormat pair = pairs.get(i);
            characters.append(pair.character());
            TextFormat format = pair.format();
            formats.add(format);
            if (!(format instanceof TextColor)) continue;
            colors.add((TextColor)format);
        }
        if (formats.size() != characters.length()) {
            throw new IllegalStateException("formats length differs from characters length");
        }
        return new CharacterAndFormatSet(Collections.unmodifiableList(formats), Collections.unmodifiableList(colors), characters.toString());
    }

    CharacterAndFormatSet(List<TextFormat> formats, List<TextColor> colors, String characters) {
        this.formats = formats;
        this.colors = colors;
        this.characters = characters;
    }
}

