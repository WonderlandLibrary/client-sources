/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_12to1_11_1;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.data.AchievementTranslationMapping;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import com.viaversion.viaversion.rewriter.ComponentRewriter;

public class TranslateRewriter {
    private static final ComponentRewriter<ClientboundPackets1_9_3> ACHIEVEMENT_TEXT_REWRITER = new ComponentRewriter<ClientboundPackets1_9_3>(){

        @Override
        protected void handleTranslate(JsonObject jsonObject, String string) {
            String string2 = AchievementTranslationMapping.get(string);
            if (string2 != null) {
                jsonObject.addProperty("translate", string2);
            }
        }

        @Override
        protected void handleHoverEvent(JsonObject jsonObject) {
            String string = jsonObject.getAsJsonPrimitive("action").getAsString();
            if (!string.equals("show_achievement")) {
                super.handleHoverEvent(jsonObject);
                return;
            }
            JsonElement jsonElement = jsonObject.get("value");
            String string2 = jsonElement.isJsonObject() ? jsonElement.getAsJsonObject().get("text").getAsString() : jsonElement.getAsJsonPrimitive().getAsString();
            if (AchievementTranslationMapping.get(string2) == null) {
                JsonObject jsonObject2 = new JsonObject();
                jsonObject2.addProperty("text", "Invalid statistic/achievement!");
                jsonObject2.addProperty("color", "red");
                jsonObject.addProperty("action", "show_text");
                jsonObject.add("value", jsonObject2);
                super.handleHoverEvent(jsonObject);
                return;
            }
            try {
                JsonObject jsonObject3 = new JsonObject();
                jsonObject3.addProperty("text", "\n");
                JsonArray jsonArray = new JsonArray();
                jsonArray.add("");
                JsonObject jsonObject4 = new JsonObject();
                JsonObject jsonObject5 = new JsonObject();
                jsonArray.add(jsonObject4);
                jsonArray.add(jsonObject3);
                jsonArray.add(jsonObject5);
                if (string2.startsWith("achievement")) {
                    jsonObject4.addProperty("translate", string2);
                    jsonObject4.addProperty("color", AchievementTranslationMapping.isSpecial(string2) ? "dark_purple" : "green");
                    jsonObject5.addProperty("translate", "stats.tooltip.type.achievement");
                    JsonObject jsonObject6 = new JsonObject();
                    jsonObject5.addProperty("italic", true);
                    jsonObject6.addProperty("translate", jsonElement + ".desc");
                    jsonArray.add(jsonObject3);
                    jsonArray.add(jsonObject6);
                } else if (string2.startsWith("stat")) {
                    jsonObject4.addProperty("translate", string2);
                    jsonObject4.addProperty("color", "gray");
                    jsonObject5.addProperty("translate", "stats.tooltip.type.statistic");
                    jsonObject5.addProperty("italic", true);
                }
                jsonObject.addProperty("action", "show_text");
                jsonObject.add("value", jsonArray);
            } catch (Exception exception) {
                Via.getPlatform().getLogger().warning("Error rewriting show_achievement: " + jsonObject);
                exception.printStackTrace();
                JsonObject jsonObject7 = new JsonObject();
                jsonObject7.addProperty("text", "Invalid statistic/achievement!");
                jsonObject7.addProperty("color", "red");
                jsonObject.addProperty("action", "show_text");
                jsonObject.add("value", jsonObject7);
            }
            super.handleHoverEvent(jsonObject);
        }
    };

    public static void toClient(JsonElement jsonElement, UserConnection userConnection) {
        JsonObject jsonObject;
        JsonElement jsonElement2;
        if (jsonElement instanceof JsonObject && (jsonElement2 = (jsonObject = (JsonObject)jsonElement).get("translate")) != null && jsonElement2.getAsString().startsWith("chat.type.achievement")) {
            ACHIEVEMENT_TEXT_REWRITER.processText(jsonObject);
        }
    }
}

