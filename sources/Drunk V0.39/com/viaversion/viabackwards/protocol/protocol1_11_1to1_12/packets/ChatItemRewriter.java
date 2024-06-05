/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import java.util.Iterator;

public class ChatItemRewriter {
    public static void toClient(JsonElement element, UserConnection user) {
        if (element instanceof JsonObject) {
            JsonElement value;
            JsonObject obj = (JsonObject)element;
            if (!obj.has("hoverEvent")) {
                if (!obj.has("extra")) return;
                ChatItemRewriter.toClient(obj.get("extra"), user);
                return;
            }
            if (!(obj.get("hoverEvent") instanceof JsonObject)) return;
            JsonObject hoverEvent = (JsonObject)obj.get("hoverEvent");
            if (!hoverEvent.has("action")) return;
            if (!hoverEvent.has("value")) return;
            String type = hoverEvent.get("action").getAsString();
            if (!type.equals("show_item")) {
                if (!type.equals("show_entity")) return;
            }
            if (!(value = hoverEvent.get("value")).isJsonArray()) return;
            JsonArray newArray = new JsonArray();
            int index = 0;
            Iterator<JsonElement> iterator = value.getAsJsonArray().iterator();
            while (true) {
                if (!iterator.hasNext()) {
                    hoverEvent.add("value", newArray);
                    return;
                }
                JsonElement valueElement = iterator.next();
                if (!valueElement.isJsonPrimitive() || !valueElement.getAsJsonPrimitive().isString()) continue;
                String newValue = index + ":" + valueElement.getAsString();
                newArray.add(new JsonPrimitive(newValue));
            }
        }
        if (!(element instanceof JsonArray)) return;
        JsonArray array = (JsonArray)element;
        Iterator<JsonElement> iterator = array.iterator();
        while (iterator.hasNext()) {
            JsonElement value = iterator.next();
            ChatItemRewriter.toClient(value, user);
        }
    }
}

