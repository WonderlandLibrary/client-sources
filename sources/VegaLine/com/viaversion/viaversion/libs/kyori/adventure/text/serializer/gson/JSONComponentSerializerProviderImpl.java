/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.ApiStatus$Internal
 *  org.jetbrains.annotations.NotNull
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.json.JSONComponentSerializer;
import com.viaversion.viaversion.libs.kyori.adventure.util.Services;
import java.util.function.Supplier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Internal
public final class JSONComponentSerializerProviderImpl
implements JSONComponentSerializer.Provider,
Services.Fallback {
    @Override
    @NotNull
    public JSONComponentSerializer instance() {
        return GsonComponentSerializer.gson();
    }

    @Override
    @NotNull
    public @NotNull Supplier<@NotNull JSONComponentSerializer.Builder> builder() {
        return GsonComponentSerializer::builder;
    }

    public String toString() {
        return "JSONComponentSerializerProviderImpl[GsonComponentSerializer]";
    }
}

