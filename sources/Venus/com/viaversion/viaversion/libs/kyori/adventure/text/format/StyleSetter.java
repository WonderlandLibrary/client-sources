/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.ApiStatus$NonExtendable
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.format;

import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEventSource;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ApiStatus.NonExtendable
public interface StyleSetter<T extends StyleSetter<?>> {
    @NotNull
    public T font(@Nullable Key var1);

    @NotNull
    public T color(@Nullable TextColor var1);

    @NotNull
    public T colorIfAbsent(@Nullable TextColor var1);

    @NotNull
    default public T decorate(@NotNull TextDecoration textDecoration) {
        return this.decoration(textDecoration, TextDecoration.State.TRUE);
    }

    @NotNull
    default public T decorate(@NotNull @NotNull TextDecoration @NotNull ... textDecorationArray) {
        EnumMap<TextDecoration, TextDecoration.State> enumMap = new EnumMap<TextDecoration, TextDecoration.State>(TextDecoration.class);
        int n = textDecorationArray.length;
        for (int i = 0; i < n; ++i) {
            enumMap.put(textDecorationArray[i], TextDecoration.State.TRUE);
        }
        return this.decorations(enumMap);
    }

    @NotNull
    default public T decoration(@NotNull TextDecoration textDecoration, boolean bl) {
        return this.decoration(textDecoration, TextDecoration.State.byBoolean(bl));
    }

    @NotNull
    public T decoration(@NotNull TextDecoration var1, @NotNull TextDecoration.State var2);

    @NotNull
    public T decorationIfAbsent(@NotNull TextDecoration var1, @NotNull TextDecoration.State var2);

    @NotNull
    public T decorations(@NotNull Map<TextDecoration, TextDecoration.State> var1);

    @NotNull
    default public T decorations(@NotNull Set<TextDecoration> set, boolean bl) {
        return this.decorations(set.stream().collect(Collectors.toMap(Function.identity(), arg_0 -> StyleSetter.lambda$decorations$0(bl, arg_0))));
    }

    @NotNull
    public T clickEvent(@Nullable ClickEvent var1);

    @NotNull
    public T hoverEvent(@Nullable HoverEventSource<?> var1);

    @NotNull
    public T insertion(@Nullable String var1);

    private static TextDecoration.State lambda$decorations$0(boolean bl, TextDecoration textDecoration) {
        return TextDecoration.State.byBoolean(bl);
    }
}

