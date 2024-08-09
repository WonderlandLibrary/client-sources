/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.ApiStatus$NonExtendable
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.format;

import com.viaversion.viaversion.libs.kyori.adventure.text.format.StyleSetter;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@ApiStatus.NonExtendable
public interface MutableStyleSetter<T extends MutableStyleSetter<?>>
extends StyleSetter<T> {
    @Override
    @Contract(value="_ -> this")
    @NotNull
    default public T decorate(@NotNull @NotNull TextDecoration @NotNull ... textDecorationArray) {
        int n = textDecorationArray.length;
        for (int i = 0; i < n; ++i) {
            this.decorate(textDecorationArray[i]);
        }
        return (T)this;
    }

    @Override
    @Contract(value="_ -> this")
    @NotNull
    default public T decorations(@NotNull Map<TextDecoration, TextDecoration.State> map) {
        Objects.requireNonNull(map, "decorations");
        for (Map.Entry<TextDecoration, TextDecoration.State> entry : map.entrySet()) {
            this.decoration(entry.getKey(), entry.getValue());
        }
        return (T)this;
    }

    @Override
    @Contract(value="_, _ -> this")
    @NotNull
    default public T decorations(@NotNull Set<TextDecoration> set, boolean bl) {
        TextDecoration.State state = TextDecoration.State.byBoolean(bl);
        set.forEach(arg_0 -> this.lambda$decorations$0(state, arg_0));
        return (T)this;
    }

    @Override
    @Contract(value="_, _ -> this")
    @NotNull
    default public StyleSetter decorations(@NotNull Set set, boolean bl) {
        return this.decorations(set, bl);
    }

    @Override
    @Contract(value="_ -> this")
    @NotNull
    default public StyleSetter decorations(@NotNull Map map) {
        return this.decorations(map);
    }

    @Override
    @Contract(value="_ -> this")
    @NotNull
    default public StyleSetter decorate(@NotNull @NotNull TextDecoration @NotNull [] textDecorationArray) {
        return this.decorate(textDecorationArray);
    }

    private void lambda$decorations$0(TextDecoration.State state, TextDecoration textDecoration) {
        this.decoration(textDecoration, state);
    }
}

