/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.ApiStatus$Internal
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.json;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public final class JSONComponentConstants {
    public static final String TEXT = "text";
    public static final String TRANSLATE = "translate";
    public static final String TRANSLATE_FALLBACK = "fallback";
    public static final String TRANSLATE_WITH = "with";
    public static final String SCORE = "score";
    public static final String SCORE_NAME = "name";
    public static final String SCORE_OBJECTIVE = "objective";
    @Deprecated
    public static final String SCORE_VALUE = "value";
    public static final String SELECTOR = "selector";
    public static final String KEYBIND = "keybind";
    public static final String EXTRA = "extra";
    public static final String NBT = "nbt";
    public static final String NBT_INTERPRET = "interpret";
    public static final String NBT_BLOCK = "block";
    public static final String NBT_ENTITY = "entity";
    public static final String NBT_STORAGE = "storage";
    public static final String SEPARATOR = "separator";
    public static final String FONT = "font";
    public static final String COLOR = "color";
    public static final String INSERTION = "insertion";
    public static final String CLICK_EVENT = "clickEvent";
    public static final String CLICK_EVENT_ACTION = "action";
    public static final String CLICK_EVENT_VALUE = "value";
    public static final String HOVER_EVENT = "hoverEvent";
    public static final String HOVER_EVENT_ACTION = "action";
    public static final String HOVER_EVENT_CONTENTS = "contents";
    @Deprecated
    public static final String HOVER_EVENT_VALUE = "value";
    public static final String SHOW_ENTITY_TYPE = "type";
    public static final String SHOW_ENTITY_ID = "id";
    public static final String SHOW_ENTITY_NAME = "name";
    public static final String SHOW_ITEM_ID = "id";
    public static final String SHOW_ITEM_COUNT = "count";
    public static final String SHOW_ITEM_TAG = "tag";

    private JSONComponentConstants() {
        throw new IllegalStateException("Cannot instantiate");
    }
}

