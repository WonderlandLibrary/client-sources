/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.ApiStatus$NonExtendable
 *  org.jetbrains.annotations.Unmodifiable
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.format;

import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.DecorationMap;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
import java.util.EnumMap;
import java.util.Map;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

@ApiStatus.NonExtendable
public interface StyleGetter {
    @Nullable
    public Key font();

    @Nullable
    public TextColor color();

    default public boolean hasDecoration(@NotNull TextDecoration textDecoration) {
        return this.decoration(textDecoration) == TextDecoration.State.TRUE;
    }

    public @NotNull TextDecoration.State decoration(@NotNull TextDecoration var1);

    default public @Unmodifiable @NotNull Map<TextDecoration, TextDecoration.State> decorations() {
        EnumMap<TextDecoration, TextDecoration.State> enumMap = new EnumMap<TextDecoration, TextDecoration.State>(TextDecoration.class);
        for (TextDecoration textDecoration : DecorationMap.DECORATIONS) {
            TextDecoration.State state = this.decoration(textDecoration);
            enumMap.put(textDecoration, state);
        }
        return enumMap;
    }

    @Nullable
    public ClickEvent clickEvent();

    @Nullable
    public HoverEvent<?> hoverEvent();

    @Nullable
    public String insertion();
}

