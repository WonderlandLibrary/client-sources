/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.event;

import com.viaversion.viaversion.libs.kyori.adventure.audience.Audience;
import com.viaversion.viaversion.libs.kyori.adventure.permission.PermissionChecker;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickCallback;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickEvent;
import com.viaversion.viaversion.libs.kyori.adventure.util.Services;
import com.viaversion.viaversion.libs.kyori.adventure.util.TriState;
import org.jetbrains.annotations.NotNull;

final class ClickCallbackInternals {
    static final PermissionChecker ALWAYS_FALSE = PermissionChecker.always(TriState.FALSE);
    static final ClickCallback.Provider PROVIDER = Services.service(ClickCallback.Provider.class).orElseGet(Fallback::new);

    private ClickCallbackInternals() {
    }

    static final class Fallback
    implements ClickCallback.Provider {
        Fallback() {
        }

        @Override
        @NotNull
        public ClickEvent create(@NotNull ClickCallback<Audience> callback, @NotNull ClickCallback.Options options) {
            return ClickEvent.suggestCommand("Callbacks are not supported on this platform!");
        }
    }
}

