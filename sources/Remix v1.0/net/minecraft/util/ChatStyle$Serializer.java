package net.minecraft.util;

import java.lang.reflect.*;
import net.minecraft.event.*;
import com.google.gson.*;

public static class Serializer implements JsonDeserializer, JsonSerializer
{
    public ChatStyle deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_, final JsonDeserializationContext p_deserialize_3_) {
        if (!p_deserialize_1_.isJsonObject()) {
            return null;
        }
        final ChatStyle var4 = new ChatStyle();
        final JsonObject var5 = p_deserialize_1_.getAsJsonObject();
        if (var5 == null) {
            return null;
        }
        if (var5.has("bold")) {
            ChatStyle.access$002(var4, var5.get("bold").getAsBoolean());
        }
        if (var5.has("italic")) {
            ChatStyle.access$102(var4, var5.get("italic").getAsBoolean());
        }
        if (var5.has("underlined")) {
            ChatStyle.access$202(var4, var5.get("underlined").getAsBoolean());
        }
        if (var5.has("strikethrough")) {
            ChatStyle.access$302(var4, var5.get("strikethrough").getAsBoolean());
        }
        if (var5.has("obfuscated")) {
            ChatStyle.access$402(var4, var5.get("obfuscated").getAsBoolean());
        }
        if (var5.has("color")) {
            ChatStyle.access$502(var4, (EnumChatFormatting)p_deserialize_3_.deserialize(var5.get("color"), (Type)EnumChatFormatting.class));
        }
        if (var5.has("insertion")) {
            ChatStyle.access$602(var4, var5.get("insertion").getAsString());
        }
        if (var5.has("clickEvent")) {
            final JsonObject var6 = var5.getAsJsonObject("clickEvent");
            if (var6 != null) {
                final JsonPrimitive var7 = var6.getAsJsonPrimitive("action");
                final ClickEvent.Action var8 = (var7 == null) ? null : ClickEvent.Action.getValueByCanonicalName(var7.getAsString());
                final JsonPrimitive var9 = var6.getAsJsonPrimitive("value");
                final String var10 = (var9 == null) ? null : var9.getAsString();
                if (var8 != null && var10 != null && var8.shouldAllowInChat()) {
                    ChatStyle.access$702(var4, new ClickEvent(var8, var10));
                }
            }
        }
        if (var5.has("hoverEvent")) {
            final JsonObject var6 = var5.getAsJsonObject("hoverEvent");
            if (var6 != null) {
                final JsonPrimitive var7 = var6.getAsJsonPrimitive("action");
                final HoverEvent.Action var11 = (var7 == null) ? null : HoverEvent.Action.getValueByCanonicalName(var7.getAsString());
                final IChatComponent var12 = (IChatComponent)p_deserialize_3_.deserialize(var6.get("value"), (Type)IChatComponent.class);
                if (var11 != null && var12 != null && var11.shouldAllowInChat()) {
                    ChatStyle.access$802(var4, new HoverEvent(var11, var12));
                }
            }
        }
        return var4;
    }
    
    public JsonElement serialize(final ChatStyle p_serialize_1_, final Type p_serialize_2_, final JsonSerializationContext p_serialize_3_) {
        if (p_serialize_1_.isEmpty()) {
            return null;
        }
        final JsonObject var4 = new JsonObject();
        if (ChatStyle.access$000(p_serialize_1_) != null) {
            var4.addProperty("bold", ChatStyle.access$000(p_serialize_1_));
        }
        if (ChatStyle.access$100(p_serialize_1_) != null) {
            var4.addProperty("italic", ChatStyle.access$100(p_serialize_1_));
        }
        if (ChatStyle.access$200(p_serialize_1_) != null) {
            var4.addProperty("underlined", ChatStyle.access$200(p_serialize_1_));
        }
        if (ChatStyle.access$300(p_serialize_1_) != null) {
            var4.addProperty("strikethrough", ChatStyle.access$300(p_serialize_1_));
        }
        if (ChatStyle.access$400(p_serialize_1_) != null) {
            var4.addProperty("obfuscated", ChatStyle.access$400(p_serialize_1_));
        }
        if (ChatStyle.access$500(p_serialize_1_) != null) {
            var4.add("color", p_serialize_3_.serialize((Object)ChatStyle.access$500(p_serialize_1_)));
        }
        if (ChatStyle.access$600(p_serialize_1_) != null) {
            var4.add("insertion", p_serialize_3_.serialize((Object)ChatStyle.access$600(p_serialize_1_)));
        }
        if (ChatStyle.access$700(p_serialize_1_) != null) {
            final JsonObject var5 = new JsonObject();
            var5.addProperty("action", ChatStyle.access$700(p_serialize_1_).getAction().getCanonicalName());
            var5.addProperty("value", ChatStyle.access$700(p_serialize_1_).getValue());
            var4.add("clickEvent", (JsonElement)var5);
        }
        if (ChatStyle.access$800(p_serialize_1_) != null) {
            final JsonObject var5 = new JsonObject();
            var5.addProperty("action", ChatStyle.access$800(p_serialize_1_).getAction().getCanonicalName());
            var5.add("value", p_serialize_3_.serialize((Object)ChatStyle.access$800(p_serialize_1_).getValue()));
            var4.add("hoverEvent", (JsonElement)var5);
        }
        return (JsonElement)var4;
    }
    
    public JsonElement serialize(final Object p_serialize_1_, final Type p_serialize_2_, final JsonSerializationContext p_serialize_3_) {
        return this.serialize((ChatStyle)p_serialize_1_, p_serialize_2_, p_serialize_3_);
    }
}
