/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.bungeecordchat.chat;

import java.lang.reflect.Type;
import us.myles.viaversion.libs.bungeecordchat.api.chat.ScoreComponent;
import us.myles.viaversion.libs.bungeecordchat.chat.BaseComponentSerializer;
import us.myles.viaversion.libs.gson.JsonDeserializationContext;
import us.myles.viaversion.libs.gson.JsonDeserializer;
import us.myles.viaversion.libs.gson.JsonElement;
import us.myles.viaversion.libs.gson.JsonObject;
import us.myles.viaversion.libs.gson.JsonParseException;
import us.myles.viaversion.libs.gson.JsonSerializationContext;
import us.myles.viaversion.libs.gson.JsonSerializer;

public class ScoreComponentSerializer
extends BaseComponentSerializer
implements JsonSerializer<ScoreComponent>,
JsonDeserializer<ScoreComponent> {
    @Override
    public ScoreComponent deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject json = element.getAsJsonObject();
        if (!json.has("score")) {
            throw new JsonParseException("Could not parse JSON: missing 'score' property");
        }
        JsonObject score = json.get("score").getAsJsonObject();
        if (!score.has("name") || !score.has("objective")) {
            throw new JsonParseException("A score component needs at least a name and an objective");
        }
        String name = score.get("name").getAsString();
        String objective = score.get("objective").getAsString();
        ScoreComponent component = new ScoreComponent(name, objective);
        if (score.has("value") && !score.get("value").getAsString().isEmpty()) {
            component.setValue(score.get("value").getAsString());
        }
        this.deserialize(json, component, context);
        return component;
    }

    @Override
    public JsonElement serialize(ScoreComponent component, Type type, JsonSerializationContext context) {
        JsonObject root = new JsonObject();
        this.serialize(root, component, context);
        JsonObject json = new JsonObject();
        json.addProperty("name", component.getName());
        json.addProperty("objective", component.getObjective());
        json.addProperty("value", component.getValue());
        root.add("score", json);
        return root;
    }
}

