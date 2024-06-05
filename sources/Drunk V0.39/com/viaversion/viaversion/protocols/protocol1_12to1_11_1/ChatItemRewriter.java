/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.protocol1_12to1_11_1;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import java.util.Iterator;
import java.util.regex.Pattern;

public class ChatItemRewriter {
    private static final Pattern indexRemoval = Pattern.compile("(?<![\\w-.+])\\d+:(?=([^\"\\\\]*(\\\\.|\"([^\"\\\\]*\\\\.)*[^\"\\\\]*\"))*[^\"]*$)");

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
            if ((value = hoverEvent.get("value")).isJsonPrimitive() && value.getAsJsonPrimitive().isString()) {
                String newValue = indexRemoval.matcher(value.getAsString()).replaceAll("");
                hoverEvent.addProperty("value", newValue);
                return;
            }
            if (!value.isJsonArray()) return;
            JsonArray newArray = new JsonArray();
            Iterator<JsonElement> iterator = value.getAsJsonArray().iterator();
            while (true) {
                if (!iterator.hasNext()) {
                    hoverEvent.add("value", newArray);
                    return;
                }
                JsonElement valueElement = iterator.next();
                if (!valueElement.isJsonPrimitive() || !valueElement.getAsJsonPrimitive().isString()) continue;
                String newValue = indexRemoval.matcher(valueElement.getAsString()).replaceAll("");
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

