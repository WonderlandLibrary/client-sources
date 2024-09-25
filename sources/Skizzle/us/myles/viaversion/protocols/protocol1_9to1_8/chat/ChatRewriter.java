/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.protocols.protocol1_9to1_8.chat;

import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.storage.EntityTracker1_9;
import us.myles.viaversion.libs.gson.JsonArray;
import us.myles.viaversion.libs.gson.JsonObject;

public class ChatRewriter {
    public static void toClient(JsonObject obj, UserConnection user) {
        if (obj.get("translate") != null && obj.get("translate").getAsString().equals("gameMode.changed")) {
            String gameMode = user.get(EntityTracker1_9.class).getGameMode().getText();
            JsonObject gameModeObject = new JsonObject();
            gameModeObject.addProperty("text", gameMode);
            gameModeObject.addProperty("color", "gray");
            gameModeObject.addProperty("italic", true);
            JsonArray array = new JsonArray();
            array.add(gameModeObject);
            obj.add("with", array);
        }
    }
}

