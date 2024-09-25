/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Preconditions
 */
package us.myles.viaversion.libs.bungeecordchat.chat;

import com.google.common.base.Preconditions;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Locale;
import us.myles.viaversion.libs.bungeecordchat.api.ChatColor;
import us.myles.viaversion.libs.bungeecordchat.api.chat.BaseComponent;
import us.myles.viaversion.libs.bungeecordchat.api.chat.ClickEvent;
import us.myles.viaversion.libs.bungeecordchat.api.chat.HoverEvent;
import us.myles.viaversion.libs.bungeecordchat.api.chat.hover.content.Content;
import us.myles.viaversion.libs.bungeecordchat.chat.ComponentSerializer;
import us.myles.viaversion.libs.gson.JsonDeserializationContext;
import us.myles.viaversion.libs.gson.JsonElement;
import us.myles.viaversion.libs.gson.JsonObject;
import us.myles.viaversion.libs.gson.JsonParseException;
import us.myles.viaversion.libs.gson.JsonSerializationContext;

public class BaseComponentSerializer {
    protected void deserialize(JsonObject object, BaseComponent component, JsonDeserializationContext context) {
        JsonObject event;
        if (object.has("color")) {
            component.setColor(ChatColor.of(object.get("color").getAsString()));
        }
        if (object.has("font")) {
            component.setFont(object.get("font").getAsString());
        }
        if (object.has("bold")) {
            component.setBold(object.get("bold").getAsBoolean());
        }
        if (object.has("italic")) {
            component.setItalic(object.get("italic").getAsBoolean());
        }
        if (object.has("underlined")) {
            component.setUnderlined(object.get("underlined").getAsBoolean());
        }
        if (object.has("strikethrough")) {
            component.setStrikethrough(object.get("strikethrough").getAsBoolean());
        }
        if (object.has("obfuscated")) {
            component.setObfuscated(object.get("obfuscated").getAsBoolean());
        }
        if (object.has("insertion")) {
            component.setInsertion(object.get("insertion").getAsString());
        }
        if (object.has("extra")) {
            component.setExtra(Arrays.asList((Object[])context.deserialize(object.get("extra"), (Type)((Object)BaseComponent[].class))));
        }
        if (object.has("clickEvent")) {
            event = object.getAsJsonObject("clickEvent");
            component.setClickEvent(new ClickEvent(ClickEvent.Action.valueOf(event.get("action").getAsString().toUpperCase(Locale.ROOT)), event.has("value") ? event.get("value").getAsString() : ""));
        }
        if (object.has("hoverEvent")) {
            event = object.getAsJsonObject("hoverEvent");
            HoverEvent hoverEvent = null;
            HoverEvent.Action action = HoverEvent.Action.valueOf(event.get("action").getAsString().toUpperCase(Locale.ROOT));
            for (String type : Arrays.asList("value", "contents")) {
                if (!event.has(type)) continue;
                JsonElement contents = event.get(type);
                try {
                    BaseComponent[] components = contents.isJsonArray() ? (BaseComponent[])context.deserialize(contents, (Type)((Object)BaseComponent[].class)) : new BaseComponent[]{(BaseComponent)context.deserialize(contents, (Type)((Object)BaseComponent.class))};
                    hoverEvent = new HoverEvent(action, components);
                    break;
                }
                catch (JsonParseException ex) {
                    Content[] list = contents.isJsonArray() ? (Content[])context.deserialize(contents, HoverEvent.getClass(action, true)) : new Content[]{(Content)context.deserialize(contents, HoverEvent.getClass(action, false))};
                    hoverEvent = new HoverEvent(action, new ArrayList<Content>(Arrays.asList(list)));
                    break;
                }
            }
            if (hoverEvent != null) {
                component.setHoverEvent(hoverEvent);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void serialize(JsonObject object, BaseComponent component, JsonSerializationContext context) {
        boolean first = false;
        if (ComponentSerializer.serializedComponents.get() == null) {
            first = true;
            ComponentSerializer.serializedComponents.set(Collections.newSetFromMap(new IdentityHashMap()));
        }
        try {
            Preconditions.checkArgument((!ComponentSerializer.serializedComponents.get().contains(component) ? 1 : 0) != 0, (Object)"Component loop");
            ComponentSerializer.serializedComponents.get().add(component);
            if (component.getColorRaw() != null) {
                object.addProperty("color", component.getColorRaw().getName());
            }
            if (component.getFontRaw() != null) {
                object.addProperty("font", component.getFontRaw());
            }
            if (component.isBoldRaw() != null) {
                object.addProperty("bold", component.isBoldRaw());
            }
            if (component.isItalicRaw() != null) {
                object.addProperty("italic", component.isItalicRaw());
            }
            if (component.isUnderlinedRaw() != null) {
                object.addProperty("underlined", component.isUnderlinedRaw());
            }
            if (component.isStrikethroughRaw() != null) {
                object.addProperty("strikethrough", component.isStrikethroughRaw());
            }
            if (component.isObfuscatedRaw() != null) {
                object.addProperty("obfuscated", component.isObfuscatedRaw());
            }
            if (component.getInsertion() != null) {
                object.addProperty("insertion", component.getInsertion());
            }
            if (component.getExtra() != null) {
                object.add("extra", context.serialize(component.getExtra()));
            }
            if (component.getClickEvent() != null) {
                JsonObject clickEvent = new JsonObject();
                clickEvent.addProperty("action", component.getClickEvent().getAction().toString().toLowerCase(Locale.ROOT));
                clickEvent.addProperty("value", component.getClickEvent().getValue());
                object.add("clickEvent", clickEvent);
            }
            if (component.getHoverEvent() != null) {
                JsonObject hoverEvent = new JsonObject();
                hoverEvent.addProperty("action", component.getHoverEvent().getAction().toString().toLowerCase(Locale.ROOT));
                if (component.getHoverEvent().isLegacy()) {
                    hoverEvent.add("value", context.serialize(component.getHoverEvent().getContents().get(0)));
                } else {
                    hoverEvent.add("contents", context.serialize(component.getHoverEvent().getContents().size() == 1 ? component.getHoverEvent().getContents().get(0) : component.getHoverEvent().getContents()));
                }
                object.add("hoverEvent", hoverEvent);
            }
        }
        finally {
            ComponentSerializer.serializedComponents.get().remove(component);
            if (first) {
                ComponentSerializer.serializedComponents.set(null);
            }
        }
    }
}

