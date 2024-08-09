/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.renderer;

import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;

public interface ComponentRenderer<C> {
    @NotNull
    public Component render(@NotNull Component var1, @NotNull C var2);

    default public <T> ComponentRenderer<T> mapContext(Function<T, C> function) {
        return (arg_0, arg_1) -> this.lambda$mapContext$0(function, arg_0, arg_1);
    }

    private Component lambda$mapContext$0(Function function, Component component, Object object) {
        return this.render(component, function.apply(object));
    }
}

