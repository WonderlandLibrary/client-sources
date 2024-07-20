/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.legacyimpl;

import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.LegacyHoverEventSerializer;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.json.legacyimpl.NBTLegacyHoverEventSerializer;
import com.viaversion.viaversion.libs.kyori.adventure.util.Codec;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;

@Deprecated
final class NBTLegacyHoverEventSerializerImpl
implements LegacyHoverEventSerializer {
    static final NBTLegacyHoverEventSerializerImpl INSTANCE = new NBTLegacyHoverEventSerializerImpl();
    static final com.viaversion.viaversion.libs.kyori.adventure.text.serializer.json.LegacyHoverEventSerializer NEW_INSTANCE = NBTLegacyHoverEventSerializer.get();

    private NBTLegacyHoverEventSerializerImpl() {
    }

    @Override
    public @NotNull HoverEvent.ShowItem deserializeShowItem(@NotNull Component input) throws IOException {
        return NEW_INSTANCE.deserializeShowItem(input);
    }

    @Override
    public @NotNull HoverEvent.ShowEntity deserializeShowEntity(@NotNull Component input, Codec.Decoder<Component, String, ? extends RuntimeException> componentCodec) throws IOException {
        return NEW_INSTANCE.deserializeShowEntity(input, componentCodec);
    }

    @Override
    @NotNull
    public Component serializeShowItem(@NotNull HoverEvent.ShowItem input) throws IOException {
        return NEW_INSTANCE.serializeShowItem(input);
    }

    @Override
    @NotNull
    public Component serializeShowEntity(@NotNull HoverEvent.ShowEntity input, Codec.Encoder<Component, String, ? extends RuntimeException> componentCodec) throws IOException {
        return NEW_INSTANCE.serializeShowEntity(input, componentCodec);
    }
}

