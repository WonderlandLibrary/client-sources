/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilderApplicable;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponentImpl;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.StyleBuilderApplicable;
import org.jetbrains.annotations.NotNull;

public final class LinearComponents {
    private LinearComponents() {
    }

    @NotNull
    public static Component linear(@NotNull @NotNull ComponentBuilderApplicable @NotNull ... componentBuilderApplicableArray) {
        int n;
        int n2 = componentBuilderApplicableArray.length;
        if (n2 == 0) {
            return Component.empty();
        }
        if (n2 == 1) {
            ComponentBuilderApplicable componentBuilderApplicable = componentBuilderApplicableArray[0];
            if (componentBuilderApplicable instanceof ComponentLike) {
                return ((ComponentLike)((Object)componentBuilderApplicable)).asComponent();
            }
            throw LinearComponents.nothingComponentLike();
        }
        TextComponentImpl.BuilderImpl builderImpl = new TextComponentImpl.BuilderImpl();
        Style.Builder builder = null;
        for (n = 0; n < n2; ++n) {
            ComponentBuilderApplicable componentBuilderApplicable = componentBuilderApplicableArray[n];
            if (componentBuilderApplicable instanceof StyleBuilderApplicable) {
                if (builder == null) {
                    builder = Style.style();
                }
                builder.apply((StyleBuilderApplicable)componentBuilderApplicable);
                continue;
            }
            if (builder != null && componentBuilderApplicable instanceof ComponentLike) {
                builderImpl.applicableApply(((ComponentLike)((Object)componentBuilderApplicable)).asComponent().style(builder));
                continue;
            }
            builderImpl.applicableApply(componentBuilderApplicable);
        }
        n = builderImpl.children.size();
        if (n == 0) {
            throw LinearComponents.nothingComponentLike();
        }
        if (n == 1 && !builderImpl.hasStyle()) {
            return (Component)builderImpl.children.get(0);
        }
        return builderImpl.build();
    }

    private static IllegalStateException nothingComponentLike() {
        return new IllegalStateException("Cannot build component linearly - nothing component-like was given");
    }
}

