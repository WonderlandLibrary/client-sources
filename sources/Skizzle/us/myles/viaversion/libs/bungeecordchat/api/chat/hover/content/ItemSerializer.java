/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.bungeecordchat.api.chat.hover.content;

import java.lang.reflect.Type;
import us.myles.viaversion.libs.bungeecordchat.api.chat.ItemTag;
import us.myles.viaversion.libs.bungeecordchat.api.chat.hover.content.Item;
import us.myles.viaversion.libs.gson.JsonDeserializationContext;
import us.myles.viaversion.libs.gson.JsonDeserializer;
import us.myles.viaversion.libs.gson.JsonElement;
import us.myles.viaversion.libs.gson.JsonObject;
import us.myles.viaversion.libs.gson.JsonParseException;
import us.myles.viaversion.libs.gson.JsonPrimitive;
import us.myles.viaversion.libs.gson.JsonSerializationContext;
import us.myles.viaversion.libs.gson.JsonSerializer;

public class ItemSerializer
implements JsonSerializer<Item>,
JsonDeserializer<Item> {
    @Override
    public Item deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject value = element.getAsJsonObject();
        int count = -1;
        if (value.has("Count")) {
            JsonPrimitive countObj = value.get("Count").getAsJsonPrimitive();
            if (countObj.isNumber()) {
                count = countObj.getAsInt();
            } else if (countObj.isString()) {
                String cString = countObj.getAsString();
                char last = cString.charAt(cString.length() - 1);
                if (last == 'b' || last == 's' || last == 'l' || last == 'f' || last == 'd') {
                    cString = cString.substring(0, cString.length() - 1);
                }
                try {
                    count = Integer.parseInt(cString);
                }
                catch (NumberFormatException ex) {
                    throw new JsonParseException("Could not parse count: " + ex);
                }
            }
        }
        return new Item(value.has("id") ? value.get("id").getAsString() : null, count, value.has("tag") ? (ItemTag)context.deserialize(value.get("tag"), (Type)((Object)ItemTag.class)) : null);
    }

    @Override
    public JsonElement serialize(Item content, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("id", content.getId() == null ? "minecraft:air" : content.getId());
        if (content.getCount() != -1) {
            object.addProperty("Count", content.getCount());
        }
        if (content.getTag() != null) {
            object.add("tag", context.serialize(content.getTag()));
        }
        return object;
    }
}

