/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.gson.Gson;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonParseException;
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
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.LegacyHoverEventSerializer;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.SerializerFactory;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.TextColorWrapper;
import com.viaversion.viaversion.libs.kyori.adventure.util.Codec;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.EnumSet;
import org.jetbrains.annotations.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
final class StyleSerializer
extends TypeAdapter<Style> {
    private static final TextDecoration[] DECORATIONS;
    static final String FONT = "font";
    static final String COLOR = "color";
    static final String INSERTION = "insertion";
    static final String CLICK_EVENT = "clickEvent";
    static final String CLICK_EVENT_ACTION = "action";
    static final String CLICK_EVENT_VALUE = "value";
    static final String HOVER_EVENT = "hoverEvent";
    static final String HOVER_EVENT_ACTION = "action";
    static final String HOVER_EVENT_CONTENTS = "contents";
    @Deprecated
    static final String HOVER_EVENT_VALUE = "value";
    private final LegacyHoverEventSerializer legacyHover;
    private final boolean emitLegacyHover;
    private final Gson gson;
    static final boolean $assertionsDisabled;

    static TypeAdapter<Style> create(@Nullable LegacyHoverEventSerializer legacyHoverEventSerializer, boolean bl, Gson gson) {
        return new StyleSerializer(legacyHoverEventSerializer, bl, gson).nullSafe();
    }

    private StyleSerializer(@Nullable LegacyHoverEventSerializer legacyHoverEventSerializer, boolean bl, Gson gson) {
        this.legacyHover = legacyHoverEventSerializer;
        this.emitLegacyHover = bl;
        this.gson = gson;
    }

    @Override
    public Style read(JsonReader jsonReader) throws IOException {
        jsonReader.beginObject();
        Style.Builder builder = Style.style();
        while (jsonReader.hasNext()) {
            Object object;
            Object object2;
            Object object3;
            String string = jsonReader.nextName();
            if (string.equals(FONT)) {
                builder.font((Key)this.gson.fromJson(jsonReader, SerializerFactory.KEY_TYPE));
                continue;
            }
            if (string.equals(COLOR)) {
                object3 = (TextColorWrapper)this.gson.fromJson(jsonReader, SerializerFactory.COLOR_WRAPPER_TYPE);
                if (((TextColorWrapper)object3).color != null) {
                    builder.color(((TextColorWrapper)object3).color);
                    continue;
                }
                if (((TextColorWrapper)object3).decoration == null) continue;
                builder.decoration(((TextColorWrapper)object3).decoration, TextDecoration.State.TRUE);
                continue;
            }
            if (TextDecoration.NAMES.keys().contains(string)) {
                builder.decoration(TextDecoration.NAMES.value(string), this.readBoolean(jsonReader));
                continue;
            }
            if (string.equals(INSERTION)) {
                builder.insertion(jsonReader.nextString());
                continue;
            }
            if (string.equals(CLICK_EVENT)) {
                jsonReader.beginObject();
                object3 = null;
                object2 = null;
                while (jsonReader.hasNext()) {
                    object = jsonReader.nextName();
                    if (((String)object).equals("action")) {
                        object3 = (ClickEvent.Action)((Object)this.gson.fromJson(jsonReader, SerializerFactory.CLICK_ACTION_TYPE));
                        continue;
                    }
                    if (((String)object).equals("value")) {
                        object2 = jsonReader.peek() == JsonToken.NULL ? null : jsonReader.nextString();
                        continue;
                    }
                    jsonReader.skipValue();
                }
                if (object3 != null && ((ClickEvent.Action)((Object)object3)).readable() && object2 != null) {
                    builder.clickEvent(ClickEvent.clickEvent((ClickEvent.Action)((Object)object3), (String)object2));
                }
                jsonReader.endObject();
                continue;
            }
            if (string.equals(HOVER_EVENT)) {
                Object object4;
                Object object5;
                JsonElement jsonElement;
                object3 = (JsonObject)this.gson.fromJson(jsonReader, (Type)((Object)JsonObject.class));
                if (object3 == null || (object2 = ((JsonObject)object3).getAsJsonPrimitive("action")) == null || !((HoverEvent.Action)(object = this.gson.fromJson((JsonElement)object2, SerializerFactory.HOVER_ACTION_TYPE))).readable()) continue;
                if (((JsonObject)object3).has(HOVER_EVENT_CONTENTS)) {
                    jsonElement = ((JsonObject)object3).get(HOVER_EVENT_CONTENTS);
                    object5 = ((HoverEvent.Action)object).type();
                    object4 = StyleSerializer.isNullOrEmpty(jsonElement) ? null : (SerializerFactory.COMPONENT_TYPE.isAssignableFrom((Class<?>)object5) ? this.gson.fromJson(jsonElement, SerializerFactory.COMPONENT_TYPE) : (SerializerFactory.SHOW_ITEM_TYPE.isAssignableFrom((Class<?>)object5) ? this.gson.fromJson(jsonElement, SerializerFactory.SHOW_ITEM_TYPE) : (SerializerFactory.SHOW_ENTITY_TYPE.isAssignableFrom((Class<?>)object5) ? this.gson.fromJson(jsonElement, SerializerFactory.SHOW_ENTITY_TYPE) : null)));
                } else if (((JsonObject)object3).has("value")) {
                    jsonElement = ((JsonObject)object3).get("value");
                    if (StyleSerializer.isNullOrEmpty(jsonElement)) {
                        object4 = null;
                    } else {
                        object5 = this.gson.fromJson(jsonElement, SerializerFactory.COMPONENT_TYPE);
                        object4 = this.legacyHoverEventContents((HoverEvent.Action<?>)object, (Component)object5);
                    }
                } else {
                    object4 = null;
                }
                if (object4 == null) continue;
                builder.hoverEvent(HoverEvent.hoverEvent(object, object4));
                continue;
            }
            jsonReader.skipValue();
        }
        jsonReader.endObject();
        return builder.build();
    }

    private static boolean isNullOrEmpty(@Nullable JsonElement jsonElement) {
        return jsonElement == null || jsonElement.isJsonNull() || jsonElement.isJsonArray() && jsonElement.getAsJsonArray().size() == 0 || jsonElement.isJsonObject() && jsonElement.getAsJsonObject().size() == 0;
    }

    private boolean readBoolean(JsonReader jsonReader) throws IOException {
        JsonToken jsonToken = jsonReader.peek();
        if (jsonToken == JsonToken.BOOLEAN) {
            return jsonReader.nextBoolean();
        }
        if (jsonToken == JsonToken.STRING || jsonToken == JsonToken.NUMBER) {
            return Boolean.parseBoolean(jsonReader.nextString());
        }
        throw new JsonParseException("Token of type " + (Object)((Object)jsonToken) + " cannot be interpreted as a boolean");
    }

    private Object legacyHoverEventContents(HoverEvent.Action<?> action, Component component) {
        if (action == HoverEvent.Action.SHOW_TEXT) {
            return component;
        }
        if (this.legacyHover != null) {
            try {
                if (action == HoverEvent.Action.SHOW_ENTITY) {
                    return this.legacyHover.deserializeShowEntity(component, this.decoder());
                }
                if (action == HoverEvent.Action.SHOW_ITEM) {
                    return this.legacyHover.deserializeShowItem(component);
                }
            } catch (IOException iOException) {
                throw new JsonParseException(iOException);
            }
        }
        throw new UnsupportedOperationException();
    }

    private Codec.Decoder<Component, String, JsonParseException> decoder() {
        return this::lambda$decoder$0;
    }

    private Codec.Encoder<Component, String, JsonParseException> encoder() {
        return this::lambda$encoder$1;
    }

    @Override
    public void write(JsonWriter jsonWriter, Style style) throws IOException {
        ClickEvent clickEvent;
        String string;
        HoverEvent.Action action;
        Object object;
        jsonWriter.beginObject();
        for (TextDecoration styleBuilderApplicable2 : DECORATIONS) {
            object = style.decoration(styleBuilderApplicable2);
            if (object == TextDecoration.State.NOT_SET) continue;
            action = TextDecoration.NAMES.key(styleBuilderApplicable2);
            if (!$assertionsDisabled && action == null) {
                throw new AssertionError();
            }
            jsonWriter.name((String)((Object)action));
            jsonWriter.value(object == TextDecoration.State.TRUE);
        }
        @Nullable TextColor textColor = style.color();
        if (textColor != null) {
            jsonWriter.name(COLOR);
            this.gson.toJson((Object)textColor, SerializerFactory.COLOR_TYPE, jsonWriter);
        }
        if ((string = style.insertion()) != null) {
            jsonWriter.name(INSERTION);
            jsonWriter.value(string);
        }
        if ((clickEvent = style.clickEvent()) != null) {
            jsonWriter.name(CLICK_EVENT);
            jsonWriter.beginObject();
            jsonWriter.name("action");
            this.gson.toJson((Object)clickEvent.action(), SerializerFactory.CLICK_ACTION_TYPE, jsonWriter);
            jsonWriter.name("value");
            jsonWriter.value(clickEvent.value());
            jsonWriter.endObject();
        }
        if ((object = style.hoverEvent()) != null) {
            jsonWriter.name(HOVER_EVENT);
            jsonWriter.beginObject();
            jsonWriter.name("action");
            action = ((HoverEvent)object).action();
            this.gson.toJson(action, SerializerFactory.HOVER_ACTION_TYPE, jsonWriter);
            jsonWriter.name(HOVER_EVENT_CONTENTS);
            if (action == HoverEvent.Action.SHOW_ITEM) {
                this.gson.toJson(((HoverEvent)object).value(), SerializerFactory.SHOW_ITEM_TYPE, jsonWriter);
            } else if (action == HoverEvent.Action.SHOW_ENTITY) {
                this.gson.toJson(((HoverEvent)object).value(), SerializerFactory.SHOW_ENTITY_TYPE, jsonWriter);
            } else if (action == HoverEvent.Action.SHOW_TEXT) {
                this.gson.toJson(((HoverEvent)object).value(), SerializerFactory.COMPONENT_TYPE, jsonWriter);
            } else {
                throw new JsonParseException("Don't know how to serialize " + ((HoverEvent)object).value());
            }
            if (this.emitLegacyHover) {
                jsonWriter.name("value");
                this.serializeLegacyHoverEvent((HoverEvent<?>)object, jsonWriter);
            }
            jsonWriter.endObject();
        }
        if ((action = style.font()) != null) {
            jsonWriter.name(FONT);
            this.gson.toJson(action, SerializerFactory.KEY_TYPE, jsonWriter);
        }
        jsonWriter.endObject();
    }

    private void serializeLegacyHoverEvent(HoverEvent<?> hoverEvent, JsonWriter jsonWriter) throws IOException {
        if (hoverEvent.action() == HoverEvent.Action.SHOW_TEXT) {
            this.gson.toJson(hoverEvent.value(), SerializerFactory.COMPONENT_TYPE, jsonWriter);
        } else if (this.legacyHover != null) {
            Component component = null;
            try {
                if (hoverEvent.action() == HoverEvent.Action.SHOW_ENTITY) {
                    component = this.legacyHover.serializeShowEntity((HoverEvent.ShowEntity)hoverEvent.value(), this.encoder());
                } else if (hoverEvent.action() == HoverEvent.Action.SHOW_ITEM) {
                    component = this.legacyHover.serializeShowItem((HoverEvent.ShowItem)hoverEvent.value());
                }
            } catch (IOException iOException) {
                throw new JsonSyntaxException(iOException);
            }
            if (component != null) {
                this.gson.toJson((Object)component, SerializerFactory.COMPONENT_TYPE, jsonWriter);
            } else {
                jsonWriter.nullValue();
            }
        } else {
            jsonWriter.nullValue();
        }
    }

    @Override
    public Object read(JsonReader jsonReader) throws IOException {
        return this.read(jsonReader);
    }

    @Override
    public void write(JsonWriter jsonWriter, Object object) throws IOException {
        this.write(jsonWriter, (Style)object);
    }

    private String lambda$encoder$1(Component component) throws JsonParseException {
        return this.gson.toJson((Object)component, SerializerFactory.COMPONENT_TYPE);
    }

    private Component lambda$decoder$0(String string) throws JsonParseException {
        return this.gson.fromJson(string, SerializerFactory.COMPONENT_TYPE);
    }

    static {
        $assertionsDisabled = !StyleSerializer.class.desiredAssertionStatus();
        DECORATIONS = new TextDecoration[]{TextDecoration.BOLD, TextDecoration.ITALIC, TextDecoration.UNDERLINED, TextDecoration.STRIKETHROUGH, TextDecoration.OBFUSCATED};
        EnumSet<TextDecoration> enumSet = EnumSet.allOf(TextDecoration.class);
        for (TextDecoration textDecoration : DECORATIONS) {
            enumSet.remove(textDecoration);
        }
        if (!enumSet.isEmpty()) {
            throw new IllegalStateException("Gson serializer is missing some text decorations: " + enumSet);
        }
    }
}

