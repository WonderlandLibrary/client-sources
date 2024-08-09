/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_9to1_8.chat;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.EntityTracker1_9;

public class ChatRewriter {
    public static void toClient(JsonObject jsonObject, UserConnection userConnection) {
        if (jsonObject.get("translate") != null && jsonObject.get("translate").getAsString().equals("gameMode.changed")) {
            EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)userConnection.getEntityTracker(Protocol1_9To1_8.class);
            String string = entityTracker1_9.getGameMode().getText();
            JsonObject jsonObject2 = new JsonObject();
            jsonObject2.addProperty("text", string);
            jsonObject2.addProperty("color", "gray");
            jsonObject2.addProperty("italic", true);
            JsonArray jsonArray = new JsonArray();
            jsonArray.add(jsonObject2);
            jsonObject.add("with", jsonArray);
        }
    }
}

