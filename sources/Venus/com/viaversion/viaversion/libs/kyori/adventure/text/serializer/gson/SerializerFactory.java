/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.gson.Gson;
import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.TypeAdapterFactory;
import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.BlockNBTComponentPosSerializer;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.ClickEventActionSerializer;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.ComponentSerializerImpl;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.HoverEventActionSerializer;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.KeySerializer;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.LegacyHoverEventSerializer;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.ShowEntitySerializer;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.ShowItemSerializer;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.StyleSerializer;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.TextColorSerializer;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.TextColorWrapper;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.TextDecorationSerializer;
import org.jetbrains.annotations.Nullable;

final class SerializerFactory
implements TypeAdapterFactory {
    static final Class<Key> KEY_TYPE = Key.class;
    static final Class<Component> COMPONENT_TYPE = Component.class;
    static final Class<Style> STYLE_TYPE = Style.class;
    static final Class<ClickEvent.Action> CLICK_ACTION_TYPE = ClickEvent.Action.class;
    static final Class<HoverEvent.Action> HOVER_ACTION_TYPE = HoverEvent.Action.class;
    static final Class<HoverEvent.ShowItem> SHOW_ITEM_TYPE = HoverEvent.ShowItem.class;
    static final Class<HoverEvent.ShowEntity> SHOW_ENTITY_TYPE = HoverEvent.ShowEntity.class;
    static final Class<TextColorWrapper> COLOR_WRAPPER_TYPE = TextColorWrapper.class;
    static final Class<TextColor> COLOR_TYPE = TextColor.class;
    static final Class<TextDecoration> TEXT_DECORATION_TYPE = TextDecoration.class;
    static final Class<BlockNBTComponent.Pos> BLOCK_NBT_POS_TYPE = BlockNBTComponent.Pos.class;
    private final boolean downsampleColors;
    private final LegacyHoverEventSerializer legacyHoverSerializer;
    private final boolean emitLegacyHover;

    SerializerFactory(boolean bl, @Nullable LegacyHoverEventSerializer legacyHoverEventSerializer, boolean bl2) {
        this.downsampleColors = bl;
        this.legacyHoverSerializer = legacyHoverEventSerializer;
        this.emitLegacyHover = bl2;
    }

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        Class<T> clazz = typeToken.getRawType();
        if (COMPONENT_TYPE.isAssignableFrom(clazz)) {
            return ComponentSerializerImpl.create(gson);
        }
        if (KEY_TYPE.isAssignableFrom(clazz)) {
            return KeySerializer.INSTANCE;
        }
        if (STYLE_TYPE.isAssignableFrom(clazz)) {
            return StyleSerializer.create(this.legacyHoverSerializer, this.emitLegacyHover, gson);
        }
        if (CLICK_ACTION_TYPE.isAssignableFrom(clazz)) {
            return ClickEventActionSerializer.INSTANCE;
        }
        if (HOVER_ACTION_TYPE.isAssignableFrom(clazz)) {
            return HoverEventActionSerializer.INSTANCE;
        }
        if (SHOW_ITEM_TYPE.isAssignableFrom(clazz)) {
            return ShowItemSerializer.create(gson);
        }
        if (SHOW_ENTITY_TYPE.isAssignableFrom(clazz)) {
            return ShowEntitySerializer.create(gson);
        }
        if (COLOR_WRAPPER_TYPE.isAssignableFrom(clazz)) {
            return TextColorWrapper.Serializer.INSTANCE;
        }
        if (COLOR_TYPE.isAssignableFrom(clazz)) {
            return this.downsampleColors ? TextColorSerializer.DOWNSAMPLE_COLOR : TextColorSerializer.INSTANCE;
        }
        if (TEXT_DECORATION_TYPE.isAssignableFrom(clazz)) {
            return TextDecorationSerializer.INSTANCE;
        }
        if (BLOCK_NBT_POS_TYPE.isAssignableFrom(clazz)) {
            return BlockNBTComponentPosSerializer.INSTANCE;
        }
        return null;
    }
}

