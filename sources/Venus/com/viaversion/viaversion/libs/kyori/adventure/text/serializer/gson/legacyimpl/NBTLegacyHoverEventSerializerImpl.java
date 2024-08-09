/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.legacyimpl;

import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.TagStringIO;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.api.BinaryTagHolder;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.LegacyHoverEventSerializer;
import com.viaversion.viaversion.libs.kyori.adventure.util.Codec;
import java.io.IOException;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class NBTLegacyHoverEventSerializerImpl
implements LegacyHoverEventSerializer {
    static final NBTLegacyHoverEventSerializerImpl INSTANCE = new NBTLegacyHoverEventSerializerImpl();
    private static final TagStringIO SNBT_IO = TagStringIO.get();
    private static final Codec<CompoundBinaryTag, String, IOException, IOException> SNBT_CODEC = Codec.codec(SNBT_IO::asCompound, SNBT_IO::asString);
    static final String ITEM_TYPE = "id";
    static final String ITEM_COUNT = "Count";
    static final String ITEM_TAG = "tag";
    static final String ENTITY_NAME = "name";
    static final String ENTITY_TYPE = "type";
    static final String ENTITY_ID = "id";

    private NBTLegacyHoverEventSerializerImpl() {
    }

    @Override
    public @NotNull HoverEvent.ShowItem deserializeShowItem(@NotNull Component component) throws IOException {
        NBTLegacyHoverEventSerializerImpl.assertTextComponent(component);
        CompoundBinaryTag compoundBinaryTag = SNBT_CODEC.decode(((TextComponent)component).content());
        CompoundBinaryTag compoundBinaryTag2 = compoundBinaryTag.getCompound(ITEM_TAG);
        return HoverEvent.ShowItem.of(Key.key(compoundBinaryTag.getString("id")), (int)compoundBinaryTag.getByte(ITEM_COUNT, (byte)1), compoundBinaryTag2 == CompoundBinaryTag.empty() ? null : BinaryTagHolder.encode(compoundBinaryTag2, SNBT_CODEC));
    }

    @Override
    public @NotNull HoverEvent.ShowEntity deserializeShowEntity(@NotNull Component component, Codec.Decoder<Component, String, ? extends RuntimeException> decoder) throws IOException {
        NBTLegacyHoverEventSerializerImpl.assertTextComponent(component);
        CompoundBinaryTag compoundBinaryTag = SNBT_CODEC.decode(((TextComponent)component).content());
        return HoverEvent.ShowEntity.of(Key.key(compoundBinaryTag.getString(ENTITY_TYPE)), UUID.fromString(compoundBinaryTag.getString("id")), decoder.decode(compoundBinaryTag.getString(ENTITY_NAME)));
    }

    private static void assertTextComponent(Component component) {
        if (!(component instanceof TextComponent) || !component.children().isEmpty()) {
            throw new IllegalArgumentException("Legacy events must be single Component instances");
        }
    }

    @Override
    @NotNull
    public Component serializeShowItem(@NotNull HoverEvent.ShowItem showItem) throws IOException {
        CompoundBinaryTag.Builder builder = (CompoundBinaryTag.Builder)((CompoundBinaryTag.Builder)CompoundBinaryTag.builder().putString("id", showItem.item().asString())).putByte(ITEM_COUNT, (byte)showItem.count());
        @Nullable BinaryTagHolder binaryTagHolder = showItem.nbt();
        if (binaryTagHolder != null) {
            builder.put(ITEM_TAG, binaryTagHolder.get(SNBT_CODEC));
        }
        return Component.text(SNBT_CODEC.encode(builder.build()));
    }

    @Override
    @NotNull
    public Component serializeShowEntity(@NotNull HoverEvent.ShowEntity showEntity, Codec.Encoder<Component, String, ? extends RuntimeException> encoder) throws IOException {
        CompoundBinaryTag.Builder builder = (CompoundBinaryTag.Builder)((CompoundBinaryTag.Builder)CompoundBinaryTag.builder().putString("id", showEntity.id().toString())).putString(ENTITY_TYPE, showEntity.type().asString());
        @Nullable Component component = showEntity.name();
        if (component != null) {
            builder.putString(ENTITY_NAME, encoder.encode(component));
        }
        return Component.text(SNBT_CODEC.encode(builder.build()));
    }
}

