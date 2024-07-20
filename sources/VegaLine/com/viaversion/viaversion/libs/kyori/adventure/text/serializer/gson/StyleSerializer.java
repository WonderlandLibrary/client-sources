/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.gson.Gson;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonParseException;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonToken;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.SerializerFactory;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.TextColorWrapper;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.json.LegacyHoverEventSerializer;
import com.viaversion.viaversion.libs.kyori.adventure.util.Codec;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.EnumSet;
import org.jetbrains.annotations.Nullable;

final class StyleSerializer
extends TypeAdapter<Style> {
    private static final TextDecoration[] DECORATIONS = new TextDecoration[]{TextDecoration.BOLD, TextDecoration.ITALIC, TextDecoration.UNDERLINED, TextDecoration.STRIKETHROUGH, TextDecoration.OBFUSCATED};
    private final LegacyHoverEventSerializer legacyHover;
    private final boolean emitLegacyHover;
    private final Gson gson;

    static TypeAdapter<Style> create(@Nullable LegacyHoverEventSerializer legacyHover, boolean emitLegacyHover, Gson gson) {
        return new StyleSerializer(legacyHover, emitLegacyHover, gson).nullSafe();
    }

    private StyleSerializer(@Nullable LegacyHoverEventSerializer legacyHover, boolean emitLegacyHover, Gson gson) {
        this.legacyHover = legacyHover;
        this.emitLegacyHover = emitLegacyHover;
        this.gson = gson;
    }

    @Override
    public Style read(JsonReader in) throws IOException {
        in.beginObject();
        Style.Builder style = Style.style();
        while (in.hasNext()) {
            String fieldName = in.nextName();
            if (fieldName.equals("font")) {
                style.font((Key)this.gson.fromJson(in, SerializerFactory.KEY_TYPE));
                continue;
            }
            if (fieldName.equals("color")) {
                TextColorWrapper color = (TextColorWrapper)this.gson.fromJson(in, SerializerFactory.COLOR_WRAPPER_TYPE);
                if (color.color != null) {
                    style.color(color.color);
                    continue;
                }
                if (color.decoration == null) continue;
                style.decoration(color.decoration, TextDecoration.State.TRUE);
                continue;
            }
            if (TextDecoration.NAMES.keys().contains(fieldName)) {
                style.decoration(TextDecoration.NAMES.value(fieldName), this.readBoolean(in));
                continue;
            }
            if (fieldName.equals("insertion")) {
                style.insertion(in.nextString());
                continue;
            }
            if (fieldName.equals("clickEvent")) {
                in.beginObject();
                ClickEvent.Action action = null;
                String value = null;
                while (in.hasNext()) {
                    String clickEventField = in.nextName();
                    if (clickEventField.equals("action")) {
                        action = (ClickEvent.Action)((Object)this.gson.fromJson(in, SerializerFactory.CLICK_ACTION_TYPE));
                        continue;
                    }
                    if (clickEventField.equals("value")) {
                        value = in.peek() == JsonToken.NULL ? null : in.nextString();
                        continue;
                    }
                    in.skipValue();
                }
                if (action != null && action.readable() && value != null) {
                    style.clickEvent(ClickEvent.clickEvent(action, value));
                }
                in.endObject();
                continue;
            }
            if (fieldName.equals("hoverEvent")) {
                Object value;
                HoverEvent.Action action;
                JsonPrimitive serializedAction;
                JsonObject hoverEventObject = (JsonObject)this.gson.fromJson(in, (Type)((Object)JsonObject.class));
                if (hoverEventObject == null || (serializedAction = hoverEventObject.getAsJsonPrimitive("action")) == null || !(action = this.gson.fromJson((JsonElement)serializedAction, SerializerFactory.HOVER_ACTION_TYPE)).readable()) continue;
                Class actionType = action.type();
                if (hoverEventObject.has("contents")) {
                    @Nullable JsonElement rawValue = hoverEventObject.get("contents");
                    value = StyleSerializer.isNullOrEmpty(rawValue) ? null : (SerializerFactory.COMPONENT_TYPE.isAssignableFrom(actionType) ? this.gson.fromJson(rawValue, SerializerFactory.COMPONENT_TYPE) : (SerializerFactory.SHOW_ITEM_TYPE.isAssignableFrom(actionType) ? this.gson.fromJson(rawValue, SerializerFactory.SHOW_ITEM_TYPE) : (SerializerFactory.SHOW_ENTITY_TYPE.isAssignableFrom(actionType) ? this.gson.fromJson(rawValue, SerializerFactory.SHOW_ENTITY_TYPE) : null)));
                } else if (hoverEventObject.has("value")) {
                    JsonElement element = hoverEventObject.get("value");
                    if (StyleSerializer.isNullOrEmpty(element)) {
                        value = null;
                    } else if (SerializerFactory.COMPONENT_TYPE.isAssignableFrom(actionType)) {
                        Component rawValue = this.gson.fromJson(element, SerializerFactory.COMPONENT_TYPE);
                        value = this.legacyHoverEventContents(action, rawValue);
                    } else {
                        value = SerializerFactory.STRING_TYPE.isAssignableFrom(actionType) ? this.gson.fromJson(element, SerializerFactory.STRING_TYPE) : null;
                    }
                } else {
                    value = null;
                }
                if (value == null) continue;
                style.hoverEvent(HoverEvent.hoverEvent(action, value));
                continue;
            }
            in.skipValue();
        }
        in.endObject();
        return style.build();
    }

    private static boolean isNullOrEmpty(@Nullable JsonElement element) {
        return element == null || element.isJsonNull() || element.isJsonArray() && element.getAsJsonArray().size() == 0 || element.isJsonObject() && element.getAsJsonObject().size() == 0;
    }

    private boolean readBoolean(JsonReader in) throws IOException {
        JsonToken peek = in.peek();
        if (peek == JsonToken.BOOLEAN) {
            return in.nextBoolean();
        }
        if (peek == JsonToken.STRING || peek == JsonToken.NUMBER) {
            return Boolean.parseBoolean(in.nextString());
        }
        throw new JsonParseException("Token of type " + (Object)((Object)peek) + " cannot be interpreted as a boolean");
    }

    private Object legacyHoverEventContents(HoverEvent.Action<?> action, Component rawValue) {
        if (action == HoverEvent.Action.SHOW_TEXT) {
            return rawValue;
        }
        if (this.legacyHover != null) {
            try {
                if (action == HoverEvent.Action.SHOW_ENTITY) {
                    return this.legacyHover.deserializeShowEntity(rawValue, this.decoder());
                }
                if (action == HoverEvent.Action.SHOW_ITEM) {
                    return this.legacyHover.deserializeShowItem(rawValue);
                }
            } catch (IOException ex) {
                throw new JsonParseException(ex);
            }
        }
        throw new UnsupportedOperationException();
    }

    private Codec.Decoder<Component, String, JsonParseException> decoder() {
        return string -> this.gson.fromJson((String)string, SerializerFactory.COMPONENT_TYPE);
    }

    private Codec.Encoder<Component, String, JsonParseException> encoder() {
        return component -> this.gson.toJson(component, SerializerFactory.COMPONENT_TYPE);
    }

    @Override
    public void write(JsonWriter out, Style value) throws IOException {
        Key font;
        HoverEvent<?> hoverEvent;
        ClickEvent clickEvent;
        String insertion;
        out.beginObject();
        for (TextDecoration decoration : DECORATIONS) {
            TextDecoration.State state = value.decoration(decoration);
            if (state == TextDecoration.State.NOT_SET) continue;
            String name = TextDecoration.NAMES.key(decoration);
            assert (name != null);
            out.name(name);
            out.value(state == TextDecoration.State.TRUE);
        }
        @Nullable TextColor color = value.color();
        if (color != null) {
            out.name("color");
            this.gson.toJson((Object)color, SerializerFactory.COLOR_TYPE, out);
        }
        if ((insertion = value.insertion()) != null) {
            out.name("insertion");
            out.value(insertion);
        }
        if ((clickEvent = value.clickEvent()) != null) {
            out.name("clickEvent");
            out.beginObject();
            out.name("action");
            this.gson.toJson((Object)clickEvent.action(), SerializerFactory.CLICK_ACTION_TYPE, out);
            out.name("value");
            out.value(clickEvent.value());
            out.endObject();
        }
        if ((hoverEvent = value.hoverEvent()) != null && (hoverEvent.action() != HoverEvent.Action.SHOW_ACHIEVEMENT || this.emitLegacyHover)) {
            out.name("hoverEvent");
            out.beginObject();
            out.name("action");
            HoverEvent.Action<?> action = hoverEvent.action();
            this.gson.toJson(action, SerializerFactory.HOVER_ACTION_TYPE, out);
            if (action != HoverEvent.Action.SHOW_ACHIEVEMENT) {
                out.name("contents");
                if (action == HoverEvent.Action.SHOW_ITEM) {
                    this.gson.toJson(hoverEvent.value(), SerializerFactory.SHOW_ITEM_TYPE, out);
                } else if (action == HoverEvent.Action.SHOW_ENTITY) {
                    this.gson.toJson(hoverEvent.value(), SerializerFactory.SHOW_ENTITY_TYPE, out);
                } else if (action == HoverEvent.Action.SHOW_TEXT) {
                    this.gson.toJson(hoverEvent.value(), SerializerFactory.COMPONENT_TYPE, out);
                } else {
                    throw new JsonParseException("Don't know how to serialize " + hoverEvent.value());
                }
            }
            if (this.emitLegacyHover) {
                out.name("value");
                this.serializeLegacyHoverEvent(hoverEvent, out);
            }
            out.endObject();
        }
        if ((font = value.font()) != null) {
            out.name("font");
            this.gson.toJson((Object)font, SerializerFactory.KEY_TYPE, out);
        }
        out.endObject();
    }

    private void serializeLegacyHoverEvent(HoverEvent<?> hoverEvent, JsonWriter out) throws IOException {
        if (hoverEvent.action() == HoverEvent.Action.SHOW_TEXT) {
            this.gson.toJson(hoverEvent.value(), SerializerFactory.COMPONENT_TYPE, out);
        } else if (hoverEvent.action() == HoverEvent.Action.SHOW_ACHIEVEMENT) {
            this.gson.toJson(hoverEvent.value(), (Type)((Object)String.class), out);
        } else if (this.legacyHover != null) {
            Component serialized = null;
            try {
                if (hoverEvent.action() == HoverEvent.Action.SHOW_ENTITY) {
                    serialized = this.legacyHover.serializeShowEntity((HoverEvent.ShowEntity)hoverEvent.value(), this.encoder());
                } else if (hoverEvent.action() == HoverEvent.Action.SHOW_ITEM) {
                    serialized = this.legacyHover.serializeShowItem((HoverEvent.ShowItem)hoverEvent.value());
                }
            } catch (IOException ex) {
                throw new JsonSyntaxException(ex);
            }
            if (serialized != null) {
                this.gson.toJson((Object)serialized, SerializerFactory.COMPONENT_TYPE, out);
            } else {
                out.nullValue();
            }
        } else {
            out.nullValue();
        }
    }

    static {
        EnumSet<TextDecoration> knownDecorations = EnumSet.allOf(TextDecoration.class);
        for (TextDecoration decoration : DECORATIONS) {
            knownDecorations.remove(decoration);
        }
        if (!knownDecorations.isEmpty()) {
            throw new IllegalStateException("Gson serializer is missing some text decorations: " + knownDecorations);
        }
    }
}

