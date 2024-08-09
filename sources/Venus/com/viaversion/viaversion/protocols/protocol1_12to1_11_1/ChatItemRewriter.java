/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_12to1_11_1;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import java.util.regex.Pattern;

public class ChatItemRewriter {
    private static final Pattern indexRemoval = Pattern.compile("(?<![\\w-.+])\\d+:(?=([^\"\\\\]*(\\\\.|\"([^\"\\\\]*\\\\.)*[^\"\\\\]*\"))*[^\"]*$)");

    public static void toClient(JsonElement jsonElement, UserConnection userConnection) {
        block5: {
            block3: {
                JsonObject jsonObject;
                block4: {
                    JsonElement jsonElement2;
                    JsonObject jsonObject2;
                    block6: {
                        String string;
                        if (!(jsonElement instanceof JsonObject)) break block3;
                        jsonObject = (JsonObject)jsonElement;
                        if (!jsonObject.has("hoverEvent")) break block4;
                        if (!(jsonObject.get("hoverEvent") instanceof JsonObject) || !(jsonObject2 = (JsonObject)jsonObject.get("hoverEvent")).has("action") || !jsonObject2.has("value") || !(string = jsonObject2.get("action").getAsString()).equals("show_item") && !string.equals("show_entity")) break block5;
                        jsonElement2 = jsonObject2.get("value");
                        if (!jsonElement2.isJsonPrimitive() || !jsonElement2.getAsJsonPrimitive().isString()) break block6;
                        String string2 = indexRemoval.matcher(jsonElement2.getAsString()).replaceAll("");
                        jsonObject2.addProperty("value", string2);
                        break block5;
                    }
                    if (!jsonElement2.isJsonArray()) break block5;
                    JsonArray jsonArray = new JsonArray();
                    for (JsonElement jsonElement3 : jsonElement2.getAsJsonArray()) {
                        if (!jsonElement3.isJsonPrimitive() || !jsonElement3.getAsJsonPrimitive().isString()) continue;
                        String string = indexRemoval.matcher(jsonElement3.getAsString()).replaceAll("");
                        jsonArray.add(new JsonPrimitive(string));
                    }
                    jsonObject2.add("value", jsonArray);
                    break block5;
                }
                if (!jsonObject.has("extra")) break block5;
                ChatItemRewriter.toClient(jsonObject.get("extra"), userConnection);
                break block5;
            }
            if (jsonElement instanceof JsonArray) {
                JsonArray jsonArray = (JsonArray)jsonElement;
                for (JsonElement jsonElement4 : jsonArray) {
                    ChatItemRewriter.toClient(jsonElement4, userConnection);
                }
            }
        }
    }
}

