/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.event;

import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent;
import java.util.function.UnaryOperator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface HoverEventSource<V> {
    @Nullable
    public static <V> HoverEvent<V> unbox(@Nullable HoverEventSource<V> hoverEventSource) {
        return hoverEventSource != null ? hoverEventSource.asHoverEvent() : null;
    }

    @NotNull
    default public HoverEvent<V> asHoverEvent() {
        return this.asHoverEvent(UnaryOperator.identity());
    }

    @NotNull
    public HoverEvent<V> asHoverEvent(@NotNull UnaryOperator<V> var1);
}

