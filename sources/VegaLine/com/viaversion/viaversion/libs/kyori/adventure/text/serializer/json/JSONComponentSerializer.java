/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.ApiStatus$Internal
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.json;

import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.ComponentSerializer;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.json.JSONComponentSerializerAccessor;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.json.LegacyHoverEventSerializer;
import com.viaversion.viaversion.libs.kyori.adventure.util.PlatformAPI;
import java.util.function.Supplier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface JSONComponentSerializer
extends ComponentSerializer<Component, Component, String> {
    @NotNull
    public static JSONComponentSerializer json() {
        return JSONComponentSerializerAccessor.Instances.INSTANCE;
    }

    public static @NotNull Builder builder() {
        return JSONComponentSerializerAccessor.Instances.BUILDER_SUPPLIER.get();
    }

    @PlatformAPI
    @ApiStatus.Internal
    public static interface Provider {
        @PlatformAPI
        @ApiStatus.Internal
        @NotNull
        public JSONComponentSerializer instance();

        @PlatformAPI
        @ApiStatus.Internal
        @NotNull
        public @NotNull Supplier<@NotNull Builder> builder();
    }

    public static interface Builder {
        @NotNull
        public Builder downsampleColors();

        @NotNull
        public Builder legacyHoverEventSerializer(@Nullable LegacyHoverEventSerializer var1);

        @NotNull
        public Builder emitLegacyHoverEvent();

        public JSONComponentSerializer build();
    }
}

