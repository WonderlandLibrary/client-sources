/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.text;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import java.lang.reflect.Type;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ResourceLocationException;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

public class Style {
    public static final Style EMPTY = new Style(null, null, null, null, null, null, null, null, null, null);
    public static final ResourceLocation DEFAULT_FONT = new ResourceLocation("minecraft", "default");
    @Nullable
    private final Color color;
    @Nullable
    private final Boolean bold;
    @Nullable
    private final Boolean italic;
    @Nullable
    private final Boolean underlined;
    @Nullable
    private final Boolean strikethrough;
    @Nullable
    private final Boolean obfuscated;
    @Nullable
    private final ClickEvent clickEvent;
    @Nullable
    private final HoverEvent hoverEvent;
    @Nullable
    private final String insertion;
    @Nullable
    private final ResourceLocation fontId;

    private Style(@Nullable Color color, @Nullable Boolean bl, @Nullable Boolean bl2, @Nullable Boolean bl3, @Nullable Boolean bl4, @Nullable Boolean bl5, @Nullable ClickEvent clickEvent, @Nullable HoverEvent hoverEvent, @Nullable String string, @Nullable ResourceLocation resourceLocation) {
        this.color = color;
        this.bold = bl;
        this.italic = bl2;
        this.underlined = bl3;
        this.strikethrough = bl4;
        this.obfuscated = bl5;
        this.clickEvent = clickEvent;
        this.hoverEvent = hoverEvent;
        this.insertion = string;
        this.fontId = resourceLocation;
    }

    @Nullable
    public Color getColor() {
        return this.color;
    }

    public boolean getBold() {
        return this.bold == Boolean.TRUE;
    }

    public boolean getItalic() {
        return this.italic == Boolean.TRUE;
    }

    public boolean getStrikethrough() {
        return this.strikethrough == Boolean.TRUE;
    }

    public boolean getUnderlined() {
        return this.underlined == Boolean.TRUE;
    }

    public boolean getObfuscated() {
        return this.obfuscated == Boolean.TRUE;
    }

    public boolean isEmpty() {
        return this == EMPTY;
    }

    @Nullable
    public ClickEvent getClickEvent() {
        return this.clickEvent;
    }

    @Nullable
    public HoverEvent getHoverEvent() {
        return this.hoverEvent;
    }

    @Nullable
    public String getInsertion() {
        return this.insertion;
    }

    public ResourceLocation getFontId() {
        return this.fontId != null ? this.fontId : DEFAULT_FONT;
    }

    public Style setColor(@Nullable Color color) {
        return new Style(color, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion, this.fontId);
    }

    public Style setFormatting(@Nullable TextFormatting textFormatting) {
        return this.setColor(textFormatting != null ? Color.fromTextFormatting(textFormatting) : null);
    }

    public Style setBold(@Nullable Boolean bl) {
        return new Style(this.color, bl, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion, this.fontId);
    }

    public Style setItalic(@Nullable Boolean bl) {
        return new Style(this.color, this.bold, bl, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion, this.fontId);
    }

    public Style func_244282_c(@Nullable Boolean bl) {
        return new Style(this.color, this.bold, this.italic, bl, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion, this.fontId);
    }

    public Style setClickEvent(@Nullable ClickEvent clickEvent) {
        return new Style(this.color, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, clickEvent, this.hoverEvent, this.insertion, this.fontId);
    }

    public Style setHoverEvent(@Nullable HoverEvent hoverEvent) {
        return new Style(this.color, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, hoverEvent, this.insertion, this.fontId);
    }

    public Style setInsertion(@Nullable String string) {
        return new Style(this.color, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, string, this.fontId);
    }

    public Style setFontId(@Nullable ResourceLocation resourceLocation) {
        return new Style(this.color, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion, resourceLocation);
    }

    public Style applyFormatting(TextFormatting textFormatting) {
        Color color = this.color;
        Boolean bl = this.bold;
        Boolean bl2 = this.italic;
        Boolean bl3 = this.strikethrough;
        Boolean bl4 = this.underlined;
        Boolean bl5 = this.obfuscated;
        switch (1.$SwitchMap$net$minecraft$util$text$TextFormatting[textFormatting.ordinal()]) {
            case 1: {
                bl5 = true;
                break;
            }
            case 2: {
                bl = true;
                break;
            }
            case 3: {
                bl3 = true;
                break;
            }
            case 4: {
                bl4 = true;
                break;
            }
            case 5: {
                bl2 = true;
                break;
            }
            case 6: {
                return EMPTY;
            }
            default: {
                color = Color.fromTextFormatting(textFormatting);
            }
        }
        return new Style(color, bl, bl2, bl4, bl3, bl5, this.clickEvent, this.hoverEvent, this.insertion, this.fontId);
    }

    public Style forceFormatting(TextFormatting textFormatting) {
        Color color = this.color;
        Boolean bl = this.bold;
        Boolean bl2 = this.italic;
        Boolean bl3 = this.strikethrough;
        Boolean bl4 = this.underlined;
        Boolean bl5 = this.obfuscated;
        switch (1.$SwitchMap$net$minecraft$util$text$TextFormatting[textFormatting.ordinal()]) {
            case 1: {
                bl5 = true;
                break;
            }
            case 2: {
                bl = true;
                break;
            }
            case 3: {
                bl3 = true;
                break;
            }
            case 4: {
                bl4 = true;
                break;
            }
            case 5: {
                bl2 = true;
                break;
            }
            case 6: {
                return EMPTY;
            }
            default: {
                bl5 = false;
                bl = false;
                bl3 = false;
                bl4 = false;
                bl2 = false;
                color = Color.fromTextFormatting(textFormatting);
            }
        }
        return new Style(color, bl, bl2, bl4, bl3, bl5, this.clickEvent, this.hoverEvent, this.insertion, this.fontId);
    }

    public Style createStyleFromFormattings(TextFormatting ... textFormattingArray) {
        Color color = this.color;
        Boolean bl = this.bold;
        Boolean bl2 = this.italic;
        Boolean bl3 = this.strikethrough;
        Boolean bl4 = this.underlined;
        Boolean bl5 = this.obfuscated;
        block8: for (TextFormatting textFormatting : textFormattingArray) {
            switch (1.$SwitchMap$net$minecraft$util$text$TextFormatting[textFormatting.ordinal()]) {
                case 1: {
                    bl5 = true;
                    continue block8;
                }
                case 2: {
                    bl = true;
                    continue block8;
                }
                case 3: {
                    bl3 = true;
                    continue block8;
                }
                case 4: {
                    bl4 = true;
                    continue block8;
                }
                case 5: {
                    bl2 = true;
                    continue block8;
                }
                case 6: {
                    return EMPTY;
                }
                default: {
                    color = Color.fromTextFormatting(textFormatting);
                }
            }
        }
        return new Style(color, bl, bl2, bl4, bl3, bl5, this.clickEvent, this.hoverEvent, this.insertion, this.fontId);
    }

    public Style mergeStyle(Style style) {
        if (this == EMPTY) {
            return style;
        }
        return style == EMPTY ? this : new Style(this.color != null ? this.color : style.color, this.bold != null ? this.bold : style.bold, this.italic != null ? this.italic : style.italic, this.underlined != null ? this.underlined : style.underlined, this.strikethrough != null ? this.strikethrough : style.strikethrough, this.obfuscated != null ? this.obfuscated : style.obfuscated, this.clickEvent != null ? this.clickEvent : style.clickEvent, this.hoverEvent != null ? this.hoverEvent : style.hoverEvent, this.insertion != null ? this.insertion : style.insertion, this.fontId != null ? this.fontId : style.fontId);
    }

    public String toString() {
        return "Style{ color=" + this.color + ", bold=" + this.bold + ", italic=" + this.italic + ", underlined=" + this.underlined + ", strikethrough=" + this.strikethrough + ", obfuscated=" + this.obfuscated + ", clickEvent=" + this.getClickEvent() + ", hoverEvent=" + this.getHoverEvent() + ", insertion=" + this.getInsertion() + ", font=" + this.getFontId() + "}";
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof Style)) {
            return true;
        }
        Style style = (Style)object;
        return this.getBold() == style.getBold() && Objects.equals(this.getColor(), style.getColor()) && this.getItalic() == style.getItalic() && this.getObfuscated() == style.getObfuscated() && this.getStrikethrough() == style.getStrikethrough() && this.getUnderlined() == style.getUnderlined() && Objects.equals(this.getClickEvent(), style.getClickEvent()) && Objects.equals(this.getHoverEvent(), style.getHoverEvent()) && Objects.equals(this.getInsertion(), style.getInsertion()) && Objects.equals(this.getFontId(), style.getFontId());
    }

    public int hashCode() {
        return Objects.hash(this.color, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    implements JsonDeserializer<Style>,
    JsonSerializer<Style> {
        @Override
        @Nullable
        public Style deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                if (jsonObject == null) {
                    return null;
                }
                Boolean bl = Serializer.deserializeBooleanValue(jsonObject, "bold");
                Boolean bl2 = Serializer.deserializeBooleanValue(jsonObject, "italic");
                Boolean bl3 = Serializer.deserializeBooleanValue(jsonObject, "underlined");
                Boolean bl4 = Serializer.deserializeBooleanValue(jsonObject, "strikethrough");
                Boolean bl5 = Serializer.deserializeBooleanValue(jsonObject, "obfuscated");
                Color color = Serializer.deserializeColor(jsonObject);
                String string = Serializer.deserializeInsertion(jsonObject);
                ClickEvent clickEvent = Serializer.deserializeClickEvent(jsonObject);
                HoverEvent hoverEvent = Serializer.deserializeHoverEvent(jsonObject);
                ResourceLocation resourceLocation = Serializer.deserializeFont(jsonObject);
                return new Style(color, bl, bl2, bl3, bl4, bl5, clickEvent, hoverEvent, string, resourceLocation);
            }
            return null;
        }

        @Nullable
        private static ResourceLocation deserializeFont(JsonObject jsonObject) {
            if (jsonObject.has("font")) {
                String string = JSONUtils.getString(jsonObject, "font");
                try {
                    return new ResourceLocation(string);
                } catch (ResourceLocationException resourceLocationException) {
                    throw new JsonSyntaxException("Invalid font name: " + string);
                }
            }
            return null;
        }

        @Nullable
        private static HoverEvent deserializeHoverEvent(JsonObject jsonObject) {
            JsonObject jsonObject2;
            HoverEvent hoverEvent;
            if (jsonObject.has("hoverEvent") && (hoverEvent = HoverEvent.deserialize(jsonObject2 = JSONUtils.getJsonObject(jsonObject, "hoverEvent"))) != null && hoverEvent.getAction().shouldAllowInChat()) {
                return hoverEvent;
            }
            return null;
        }

        @Nullable
        private static ClickEvent deserializeClickEvent(JsonObject jsonObject) {
            if (jsonObject.has("clickEvent")) {
                JsonObject jsonObject2 = JSONUtils.getJsonObject(jsonObject, "clickEvent");
                String string = JSONUtils.getString(jsonObject2, "action", null);
                ClickEvent.Action action = string == null ? null : ClickEvent.Action.getValueByCanonicalName(string);
                String string2 = JSONUtils.getString(jsonObject2, "value", null);
                if (action != null && string2 != null && action.shouldAllowInChat()) {
                    return new ClickEvent(action, string2);
                }
            }
            return null;
        }

        @Nullable
        private static String deserializeInsertion(JsonObject jsonObject) {
            return JSONUtils.getString(jsonObject, "insertion", null);
        }

        @Nullable
        private static Color deserializeColor(JsonObject jsonObject) {
            if (jsonObject.has("color")) {
                String string = JSONUtils.getString(jsonObject, "color");
                return Color.fromHex(string);
            }
            return null;
        }

        @Nullable
        private static Boolean deserializeBooleanValue(JsonObject jsonObject, String string) {
            return jsonObject.has(string) ? Boolean.valueOf(jsonObject.get(string).getAsBoolean()) : null;
        }

        @Override
        @Nullable
        public JsonElement serialize(Style style, Type type, JsonSerializationContext jsonSerializationContext) {
            if (style.isEmpty()) {
                return null;
            }
            JsonObject jsonObject = new JsonObject();
            if (style.bold != null) {
                jsonObject.addProperty("bold", style.bold);
            }
            if (style.italic != null) {
                jsonObject.addProperty("italic", style.italic);
            }
            if (style.underlined != null) {
                jsonObject.addProperty("underlined", style.underlined);
            }
            if (style.strikethrough != null) {
                jsonObject.addProperty("strikethrough", style.strikethrough);
            }
            if (style.obfuscated != null) {
                jsonObject.addProperty("obfuscated", style.obfuscated);
            }
            if (style.color != null) {
                jsonObject.addProperty("color", style.color.getName());
            }
            if (style.insertion != null) {
                jsonObject.add("insertion", jsonSerializationContext.serialize(style.insertion));
            }
            if (style.clickEvent != null) {
                JsonObject jsonObject2 = new JsonObject();
                jsonObject2.addProperty("action", style.clickEvent.getAction().getCanonicalName());
                jsonObject2.addProperty("value", style.clickEvent.getValue());
                jsonObject.add("clickEvent", jsonObject2);
            }
            if (style.hoverEvent != null) {
                jsonObject.add("hoverEvent", style.hoverEvent.serialize());
            }
            if (style.fontId != null) {
                jsonObject.addProperty("font", style.fontId.toString());
            }
            return jsonObject;
        }

        @Override
        @Nullable
        public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }

        @Override
        @Nullable
        public JsonElement serialize(Object object, Type type, JsonSerializationContext jsonSerializationContext) {
            return this.serialize((Style)object, type, jsonSerializationContext);
        }
    }
}

